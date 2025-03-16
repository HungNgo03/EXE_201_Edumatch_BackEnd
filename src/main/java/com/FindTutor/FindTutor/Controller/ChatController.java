package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.ChatMessage;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Repository.ChatMessageRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
import com.FindTutor.FindTutor.Service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OnlineUserService onlineUserService;

    @MessageMapping("/chat.sendPrivateMessage/{receiver}")
    public void sendPrivateMessage(@DestinationVariable String receiver, ChatMessage chatMessage) {
        String sender = chatMessage.getSender();
        String senderRole = userRepository.findRoleByUsername(sender);
        String receiverRole = userRepository.findRoleByUsername(receiver);

        if (!isValidInteraction(sender, senderRole, receiver, receiverRole)) {
            System.out.println("Invalid interaction: Only Student-Tutor or Tutor-Student allowed, except Admin");
            return;
        }

        chatMessage.setReceiver(receiver);
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        // Gửi tin nhắn đến cả người nhận và người gửi để hiển thị ngay lập tức
        messagingTemplate.convertAndSendToUser(receiver, "/queue/private", savedMessage);
        messagingTemplate.convertAndSendToUser(sender, "/queue/private", savedMessage);
    }

    private boolean isValidInteraction(String sender, String senderRole, String receiver, String receiverRole) {
        if (sender.equals("admin") || receiver.equals("admin")) {
            return true;
        }
        return (senderRole.equals("Student") && receiverRole.equals("Tutor")) ||
                (senderRole.equals("Tutor") && receiverRole.equals("Student"));
    }

    @MessageMapping("/chat.addUser")
    public void addUser(ChatMessage chatMessage) {
        chatMessage.setContent(chatMessage.getSender() + " đã tham gia!");
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        onlineUserService.addUser(chatMessage.getSender());
        messagingTemplate.convertAndSend("/topic/public", savedMessage);
        messagingTemplate.convertAndSend("/topic/online", onlineUserService.getOnlineUsers());
    }

    @MessageMapping("/chat.removeUser")
    public void removeUser(ChatMessage chatMessage) {
        chatMessage.setContent(chatMessage.getSender() + " đã rời khỏi!");
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        onlineUserService.removeUser(chatMessage.getSender());
        messagingTemplate.convertAndSend("/topic/public", savedMessage);
        messagingTemplate.convertAndSend("/topic/online", onlineUserService.getOnlineUsers());
    }

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory(@RequestParam String sender, @RequestParam String receiver) {
        return chatMessageRepository.findChatHistory(sender, receiver);
    }

    @GetMapping("/chatted-users")
    public List<UserDTO> getChattedUsers(@RequestParam String username) {
        String role = userRepository.findRoleByUsername(username);
        List<Users> users = new ArrayList<>();
        Set<String> uniqueUsernames = new HashSet<>();

        if (username.equals("admin")) {
            // Admin thấy tất cả user trừ chính mình
            users = userRepository.findAll().stream()
                    .filter(u -> !u.getUsername().equals("admin"))
                    .filter(u -> uniqueUsernames.add(u.getUsername())) // Loại bỏ trùng lặp
                    .collect(Collectors.toList());
        } else if (role.equals("Student")) {
            // Student thấy tất cả Tutor và Admin
            users = userRepository.findByRole("Tutor").stream()
                    .filter(u -> uniqueUsernames.add(u.getUsername())) // Loại bỏ trùng lặp
                    .collect(Collectors.toList());
            Users admin = userRepository.findByUsername("admin");
            if (admin != null && uniqueUsernames.add(admin.getUsername())) {
                users.add(admin);
            }
        } else if (role.equals("Tutor")) {
            // Tutor thấy tất cả Student và Admin
            users = userRepository.findByRole("Student").stream()
                    .filter(u -> uniqueUsernames.add(u.getUsername())) // Loại bỏ trùng lặp
                    .collect(Collectors.toList());
            Users admin = userRepository.findByUsername("admin");
            if (admin != null && uniqueUsernames.add(admin.getUsername())) {
                users.add(admin);
            }
        } else {
            // Trường hợp không xác định, chỉ trả về admin
            Users admin = userRepository.findByUsername("admin");
            if (admin != null) {
                users = List.of(admin);
            }
        }

        return users.stream()
                .filter(u -> u != null && !u.getUsername().equals(username))
                .map(user -> new UserDTO(user.getUsername(), user.getFullname()))
                .collect(Collectors.toList());
    }

    public static class UserDTO {
        private String username;
        private String fullname;

        public UserDTO(String username, String fullname) {
            this.username = username;
            this.fullname = fullname;
        }

        public String getUsername() {
            return username;
        }

        public String getFullname() {
            return fullname;
        }
    }
}