package com.epam.finalproject.dao;

import com.epam.finalproject.model.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    void update(User user);
    User getById(int id);
    void deleteById(int id);
    List<User> findAll();
    User getByLogin(String login);
    User getByLoginAndPassword(String login, String password);
    List<User> findUsersUsingLimitAndOffset(int currentPage, int numOfRecords);
    int getNumberOfRows();
}
