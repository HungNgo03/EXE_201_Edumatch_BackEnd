package com.FindTutor.FindTutor.Service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
@Service
public class OnlineUserService {
    private Set<String> onlineUsers = Collections.synchronizedSet(new HashSet<>());

    public void addUser(String username) {
        onlineUsers.add(username);
    }

    public void removeUser(String username) {
        onlineUsers.remove(username);
    }

    public Set<String> getOnlineUsers() {
        return onlineUsers;
    }
}
