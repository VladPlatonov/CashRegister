package com.epam.finalproject.model;

public class User {
    private Integer id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private UserRole userRole;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + login.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User" +
                "\nlogin='" + login + '\'' +
                "\n password='" + password + '\'' +
                "\n name='" + name + '\'' +
                "\n surname='" + surname + '\'' +
                "\n userRole=" + userRole +
                '\n';
    }
}