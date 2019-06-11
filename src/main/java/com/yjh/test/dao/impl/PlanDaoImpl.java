package com.yjh.test.dao.impl;

import com.yjh.test.dao.PlanDao;
import com.yjh.test.model.Plan;
import com.yjh.test.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDaoImpl implements PlanDao {
    @Override
    public int insert(Plan plan) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String insertPlan = "insert into plan "
                + "(play_id, studio_id, play_date, start_time, end_time) "
                + "values "
                + "(?, ?, ?, ?, ?)";
        PreparedStatement insertPlanStatement = connection.prepareStatement(insertPlan, Statement.RETURN_GENERATED_KEYS);
        insertPlanStatement.setInt(1, plan.getPlayID());
        insertPlanStatement.setInt(2, plan.getStudioID());
        insertPlanStatement.setDate(3, plan.getPlayDate());
        insertPlanStatement.setTime(4, plan.getStartTime());
        insertPlanStatement.setTime(5, plan.getEndTime());
        int count = insertPlanStatement.executeUpdate();
        ResultSet set = insertPlanStatement.getGeneratedKeys();
        if (set != null) {
            set.next();
            plan.setPlanID(set.getInt(1));
        }
        JDBCUtil.close(set, insertPlanStatement, connection);
        return count;
    }

    @Override
    public int delete(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        connection.setAutoCommit(false);

        String deleteTickets = "delete from ticket where plan_id=" + id;
        PreparedStatement deleteTicketsStatement = connection.prepareStatement(deleteTickets);
        deleteTicketsStatement.executeUpdate();
        JDBCUtil.close(deleteTicketsStatement);

        String deletePlan = "delete from plan where plan_id=" + id;
        PreparedStatement deletePlanStatement = connection.prepareStatement(deletePlan);
        int count = deletePlanStatement.executeUpdate();
        connection.commit();
        JDBCUtil.close(deletePlanStatement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public int update(Plan plan) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String updatePlan = "update plan "
                + "set play_id=?, studio_id=?, play_date=?, start_time=?, end_time=? "
                + "where plan_id=?";
        PreparedStatement updatePlanStatement = connection.prepareStatement(updatePlan);
        updatePlanStatement.setInt(1, plan.getPlayID());
        updatePlanStatement.setInt(2, plan.getStudioID());
        updatePlanStatement.setDate(3, plan.getPlayDate());
        updatePlanStatement.setTime(4, plan.getStartTime());
        updatePlanStatement.setTime(5, plan.getEndTime());
        updatePlanStatement.setInt(6, plan.getPlanID());
        int count = updatePlanStatement.executeUpdate();
        JDBCUtil.close(updatePlanStatement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public List<Plan> query(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String query;
        if (id <= 0) {
            query = "select * from plan";
        } else {
            query = "select * from plan where plan_id=" + id;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        List<Plan> list = new ArrayList<>();
        while (set.next()) {
            Plan plan = new Plan();
            plan.setPlanID(set.getInt("plan_id"));
            plan.setPlayID(set.getInt("play_id"));
            plan.setStudioID(set.getInt("studio_id"));
            plan.setPlayDate(set.getDate("play_date"));
            plan.setStartTime(set.getTime("start_time"));
            plan.setEndTime(set.getTime("end_time"));
            list.add(plan);
        }
        return list;
    }
}
