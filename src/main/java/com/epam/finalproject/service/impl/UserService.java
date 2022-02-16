package com.epam.finalproject.service.impl;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.dao.UserDao;
import com.epam.finalproject.model.User;
import com.epam.finalproject.service.IUserService;

import java.util.List;

public class UserService implements IUserService {
    UserDao userDaoImpl = AppContext.getInstance().getUserDao();

    @Override
    public void create(User user) {
        userDaoImpl.create(user);
    }
    @Override
    public User findById(Integer id ){
        return userDaoImpl.getById(id);
    }

    @Override
    public boolean checkUser(String login, String password) {
        return userDaoImpl.getByLoginAndPassword(login, password) != null;
    }
    @Override
    public boolean isValidLogin(String login){
        return userDaoImpl.getByLogin(login) != null;
    }

    @Override
    public int getNumberOfRows() {
        return userDaoImpl.getNumberOfRows();
    }

    @Override
    public List<User> findUsersUsingLimitAndOffset(int currentPage, int recordsPerPage) {
        return userDaoImpl.findUsersUsingLimitAndOffset(currentPage,recordsPerPage);
    }

    @Override
    public void deleteById(int id) {
        userDaoImpl.deleteById(id);
    }

    @Override
    public void update(User user) {
        userDaoImpl.update(user);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        return userDaoImpl.getByLoginAndPassword(login,password);
    }

    @Override
    public boolean isNotNullSignUp(User user) {
        return user.getLogin()!=null&&user.getName()!=null&&user.getSurname()!=null&&user.getPassword()!=null;
    }

    @Override
    public boolean isNotNullLogin(User user) {
        return  user.getLogin()!=null&&user.getPassword()!=null;
    }

}
