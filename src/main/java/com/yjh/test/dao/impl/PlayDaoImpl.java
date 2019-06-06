package com.yjh.test.dao.impl;

import com.yjh.test.dao.PlayDao;
import com.yjh.test.model.ENUM.PlayType;
import com.yjh.test.model.Play;
import com.yjh.test.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayDaoImpl implements PlayDao {
    @Override
    public int insert(Play play) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String insertPlay = "insert into play "
                + "(play_name, play_type, area, duration, start_date, end_date, price, picture_url) "
                + "values "
                + "(?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertPlay, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, play.getPlayName());
        statement.setString(2, String.valueOf(play.getPlayType()));
        statement.setString(3, play.getPlayArea());
        statement.setInt(4, play.getDuration());
        statement.setDate(5, play.getStartDate());
        statement.setDate(6, play.getEndDate());
        statement.setFloat(7, play.getPrice());
        statement.setString(8, play.getPictureUrl());
        int count = statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet != null) {
            resultSet.next();
            play.setPlayID(resultSet.getInt(1));
        }
        JDBCUtil.close(resultSet, statement, connection);
        return count;
    }

    @Override
    public int delete(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        connection.setAutoCommit(false);

        String deleteTickets = "delete from ticket "
                + "where play_id=" + id;
        PreparedStatement ticketsStatement = connection.prepareStatement(deleteTickets);
        ticketsStatement.executeUpdate();
        JDBCUtil.close(ticketsStatement);

        String deletePlans = "delete from plan "
                + "where play_id=" + id;
        PreparedStatement plansStatement = connection.prepareStatement(deletePlans);
        plansStatement.executeUpdate();
        JDBCUtil.close(plansStatement);

        String deletePlay = "delete from play "
                + "where play_id=" + id;
        PreparedStatement playStatement = connection.prepareStatement(deletePlay);
        int count = playStatement.executeUpdate();
        JDBCUtil.close(playStatement);
        connection.commit();

        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public int update(Play play) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        connection.setAutoCommit(false);
        String updatePlay = "update play "
                + "set play_name=?,"
                + "play_type=?,"
                + "area=?,"
                + "duration=?,"
                + "start_date=?,"
                + "end_date=?,"
                + "price=?,"
                + "picture_url=? "
                + "where play_id=?";
        PreparedStatement updatePlayStatement = connection.prepareStatement(updatePlay);
        updatePlayStatement.setString(1, play.getPlayName());
        updatePlayStatement.setString(2, String.valueOf(play.getPlayType()));
        updatePlayStatement.setString(3, play.getPlayArea());
        updatePlayStatement.setInt(4, play.getDuration());
        updatePlayStatement.setDate(5, play.getStartDate());
        updatePlayStatement.setDate(6, play.getEndDate());
        updatePlayStatement.setFloat(7, play.getPrice());
        updatePlayStatement.setString(8, play.getPictureUrl());
        int count = updatePlayStatement.executeUpdate();
        JDBCUtil.close(updatePlayStatement);

        String quaryPlan = "select plan_id from plan where play_id=? and play_date<? and play_date>?";
        PreparedStatement quaryPlanStatemment = connection.prepareStatement(quaryPlan);
        quaryPlanStatemment.setInt(1, play.getPlayID());
        quaryPlanStatemment.setDate(2, play.getStartDate());
        quaryPlanStatemment.setDate(3, play.getEndDate());
        ResultSet set = quaryPlanStatemment.executeQuery();
        while (set.next()) {
            int id = set.getInt("plan_id");
            String deleteTickets = "delete ticket where plan_id=" + id;
            PreparedStatement deleteTicketsStatement = connection.prepareStatement(deleteTickets);
            deleteTicketsStatement.executeUpdate();
            JDBCUtil.close(deleteTicketsStatement);

            String deletePlan = "delete plan where plan_id=" + id;
            PreparedStatement deleteStatement = connection.prepareStatement(deletePlan);
            deleteStatement.executeUpdate();
            JDBCUtil.close(deleteStatement);
        }
        JDBCUtil.close(set);
        JDBCUtil.close(quaryPlanStatemment);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public List<Play> quary(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String quary;
        if (id > 0) {
            quary = "select * from play where id=" + id;
        } else {
            quary = "select * from play";
        }
        List<Play> playList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(quary);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            Play play = new Play();
            play.setPlayID(set.getInt("play_id"));
            play.setDuration(set.getInt("duration"));
            play.setEndDate(set.getDate("end_date"));
            play.setPictureUrl(set.getString("picture_url"));
            play.setPlayArea(set.getString("area"));
            play.setPlayName(set.getString("play_name"));
            play.setPlayType(PlayType.valueOf(set.getString("play_type")));
            play.setPrice(set.getDouble("price"));
            play.setStartDate(set.getDate("start_date"));
        }
        return playList;
    }

    @Override
    public List<Play> quary(String name) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String quary = "select * from play where play_name=?";
        List<Play> playList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(quary);
        statement.setString(1, name);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            Play play = new Play();
            play.setPlayID(set.getInt("play_id"));
            play.setDuration(set.getInt("duration"));
            play.setEndDate(set.getDate("end_date"));
            play.setPictureUrl(set.getString("picture_url"));
            play.setPlayArea(set.getString("area"));
            play.setPlayName(set.getString("play_name"));
            play.setPlayType(PlayType.valueOf(set.getString("play_type")));
            play.setPrice(set.getDouble("price"));
            play.setStartDate(set.getDate("start_date"));
        }
        return playList;
    }
}
