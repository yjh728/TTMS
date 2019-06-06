package com.yjh.test.dao.impl;

import com.yjh.test.dao.UserDao;
import com.yjh.test.model.ENUM.UserStatus;
import com.yjh.test.model.User;
import com.yjh.test.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public int insert(User user) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String sql = "insert into user " +
                "(user_name, password, user_identity, answer)"
                + " values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.clearParameters();
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getPassword());
        statement.setString(3, String.valueOf(user.getIdentity()));
        statement.setString(4, user.getAnswer());
        int count = statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet != null) {
            resultSet.next();
            user.setId(resultSet.getInt(1));
        }
        JDBCUtil.close(statement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public int delete(String username) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String DELETE = "delete from user where user_name=?";
        PreparedStatement statement = connection.prepareStatement(DELETE);
        statement.clearParameters();
        statement.setString(1, username);
        int count = statement.executeUpdate();
        JDBCUtil.close(statement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public int update(String username, String password) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String UPDATE = "update user set password=? where user_name=?";
        PreparedStatement statement = connection.prepareStatement(UPDATE);
        statement.clearParameters();
        statement.setString(1, password);
        statement.setString(2, username);
        int count = statement.executeUpdate();
        JDBCUtil.close(statement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public List<User> quary(String username, String password) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String QUARY;
        if (password == null) {
            QUARY = "select * from user where user_name=?";
        } else {
            QUARY = "select * from user where user_name=? and password=?";
        }
        List<User> list = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(QUARY);
        statement.setString(1, username);
        if (password != null) {
            statement.setString(2, password);
        }
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            User user = new User();
            user.setId(set.getInt("user_id"));
            user.setUserName(set.getString("user_name"));
            user.setPassword(set.getString("password"));
            user.setIdentity(UserStatus.valueOf(set.getString("user_identity")));
            user.setAnswer(set.getString("answer"));
            list.add(user);
        }
        JDBCUtil.close(set, statement, connection);
        return list;
    }

    @Override
    public List<User> quary(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String QUARY = "select * from user where user_id=?";
        List<User> list = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(QUARY);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            User user = new User();
            user.setId(set.getInt("user_id"));
            user.setUserName(set.getString("user_name"));
            user.setPassword(set.getString("password"));
            user.setIdentity(UserStatus.valueOf(set.getString("user_identity")));
            user.setAnswer(set.getString("answer"));
            list.add(user);
        }
        JDBCUtil.close(set, statement, connection);
        return list;
    }

    @Override
    public List<User> quaryAll() throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String QUARY = "select * from user";
        List<User> list = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(QUARY);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            User user = new User();
            user.setId(set.getInt("user_id"));
            user.setUserName(set.getString("user_name"));
            user.setPassword(set.getString("password"));
            user.setIdentity(UserStatus.valueOf(set.getString("user_identity")));
            user.setAnswer(set.getString("answer"));
            list.add(user);
        }
        JDBCUtil.close(set, statement, connection);
        return list;
    }
}
