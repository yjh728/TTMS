package com.yjh.test.dao;

import com.yjh.test.model.Studio;

import java.sql.SQLException;
import java.util.List;

public interface StudioDao {
    int insert(Studio studio) throws SQLException;
    int delete(int id) throws SQLException;
    int update(int studioID, String newName) throws SQLException;
    List<Studio> quary(int id) throws SQLException;
}
