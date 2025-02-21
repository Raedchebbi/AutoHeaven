package org.example;

import models.User;
import services.UserService;
import utils.MyDb;

public class Main {
    public static void main(String[] args) {
        UserService us = new UserService();
        try {
            // User user = new User(1,20, "houssemme", "labidi");
            // us.create(user);
            // System.out.println("User created");
            // us.update(user);
            System.out.println(us.getAll());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}