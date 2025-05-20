package com.example.library;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("admin", "admin123"));
        users.add(new User("librarian", "libpass"));
    }

    public static boolean isValid(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    public static boolean addUser(String username, String password) {
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) return false;
        users.add(new User(username, password));
        return true;
    }

    public static boolean changePassword(String username, String oldPassword, String newPassword) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(oldPassword)) {
                users.remove(user);
                users.add(new User(username, newPassword));
                return true;
            }
        }
        return false;
    }
}
