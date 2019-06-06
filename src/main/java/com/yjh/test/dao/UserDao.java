package com.yjh.test.dao;

import com.yjh.test.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    int insert(User user) throws SQLException;

    int delete(String userName) throws SQLException;

    int update(String userName, String password) throws SQLException;

    List<User> quary(String username, String password) throws SQLException;

    List<User> quary(int id) throws SQLException;

    List<User> quaryAll() throws SQLException;
}
