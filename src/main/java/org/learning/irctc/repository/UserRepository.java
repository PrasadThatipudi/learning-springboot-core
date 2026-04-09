package org.learning.irctc.repository;

import org.learning.di.annotation.Component;

@Component
public class UserRepository {
    public UserRepository() {}

    public void getUser(String user) {
        System.out.println("UserRepository: getting user " + user);
    }
}
