package com.yjh.test.dao;

import com.yjh.test.model.Plan;

import java.sql.SQLException;
import java.util.List;

public interface PlanDao {
    int insert(Plan plan) throws SQLException;

    int delete(int id) throws SQLException;

    int update(Plan plan) throws SQLException;

    List<Plan> query(int id) throws SQLException;

    List<Plan> queryByPlayID(int id) throws SQLException;
}