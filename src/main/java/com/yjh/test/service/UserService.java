package com.yjh.test.service;

import com.yjh.test.dao.DaoFactory;
import com.yjh.test.dao.UserDao;
import com.yjh.test.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDao userDao = DaoFactory.getUserDao();

    public int add(User user) throws SQLException {
        return userDao.insert(user);
    }

    public int delete(String userName) throws SQLException {
        return userDao.delete(userName);
    }

    public int update(String userName, String password) throws SQLException {
        return userDao.update(userName, password);
    }

    public List<User> quary(String username, String password) throws SQLException {
        return userDao.quary(username, password);
    }

    public List<User> quary(int id) throws SQLException {
        return userDao.quary(id);
    }

    public List<User> quaryAll() throws SQLException {
        return userDao.quaryAll();
    }
}
