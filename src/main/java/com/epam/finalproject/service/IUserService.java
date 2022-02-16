package com.epam.finalproject.service;

import com.epam.finalproject.model.User;

import java.util.List;

public interface IUserService {

    void create(User user);
    User findById(Integer id );
    boolean checkUser(String login, String password);
    boolean isValidLogin(String login);
    int getNumberOfRows();
    List<User> findUsersUsingLimitAndOffset(int currentPage, int recordsPerPage);
    void deleteById(int id);
    void update(User user);
    User findByLoginAndPassword(String login, String password);
    boolean isNotNullSignUp(User user);
    boolean isNotNullLogin(User user);
}
