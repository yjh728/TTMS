package com.yjh.test.dao;

import com.yjh.test.model.Play;

import java.sql.SQLException;
import java.util.List;

public interface PlayDao {
    int insert(Play play) throws SQLException;

    int delete(int id) throws SQLException;

    int update(Play play) throws SQLException;

    List<Play> quary(int id) throws SQLException;

    List<Play> quary(String name) throws SQLException;
}
