package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.ChatMessage;
import com.FindTutor.FindTutor.Repository.ChatMessageRepository;
import com.FindTutor.FindTutor.Service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private OnlineUserService onlineUserService;

    @MessageMapping("/chat.sendPrivateMessage/{receiver}")
    public void sendPrivateMessage(@DestinationVariable String receiver, ChatMessage chatMessage) {
        System.out.println("Received message: " + chatMessage.getSender() + " -> " + receiver + ", Type: " + chatMessage.getType());
        // Kiểm tra vai trò: chỉ cho phép Admin-User hoặc User-Admin tương tác
        String sender = chatMessage.getSender();
        if (!((sender.equals("admin") && !receiver.equals("admin")) || (receiver.equals("admin") && !sender.equals("admin")))) {
            System.out.println("Invalid interaction: Only Admin-User or User-Admin allowed");
            return; // Không xử lý nếu không phải Admin-User hoặc User-Admin
        }
        chatMessage.setReceiver(receiver);
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        System.out.println("Saved message with ID: " + savedMessage.getId());
        messagingTemplate.convertAndSendToUser(receiver, "/queue/private", savedMessage);
        System.out.println("Sent to receiver: " + receiver + " via /user/" + receiver + "/queue/private");
    }

    @MessageMapping("/chat.addUser")
    public void addUser(ChatMessage chatMessage) {
        System.out.println("User joined: " + chatMessage.getSender());
        chatMessage.setContent(chatMessage.getSender() + " đã tham gia!");
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        onlineUserService.addUser(chatMessage.getSender());
        messagingTemplate.convertAndSend("/topic/public", savedMessage);
        messagingTemplate.convertAndSend("/topic/online", onlineUserService.getOnlineUsers());
    }

    @MessageMapping("/chat.removeUser")
    public void removeUser(ChatMessage chatMessage) {
        System.out.println("User left: " + chatMessage.getSender());
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
}