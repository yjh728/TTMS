package com.yjh.test.dao.impl;

import com.yjh.test.dao.StudioDao;
import com.yjh.test.model.ENUM.SeatStatus;
import com.yjh.test.model.Seat;
import com.yjh.test.model.Studio;
import com.yjh.test.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudioDaoImpl implements StudioDao {
    @Override
    public int insert(Studio studio) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        connection.setAutoCommit(false);
        String insertStudio = "insert into studio "
                + "(studio_name, rows_count, cols_count, seats_count)"
                + "values"
                + "(?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(insertStudio, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, studio.getStudioName());
        statement.setInt(2, studio.getRow());
        statement.setInt(3, studio.getCol());
        statement.setInt(4, studio.getSeatsCount());
        int count = statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet != null) {
            resultSet.next();
            studio.setStudioID(resultSet.getInt(1));
        }
        for (int i = 0; i < studio.getRow(); i++) {
            for (int j = 0; j < studio.getCol(); j++) {
                Seat seat = new Seat();
                seat.setStudioID(studio.getStudioID());
                seat.setRow(i);
                seat.setCol(j);
                seat.setStatus(SeatStatus.GOOD);
                String insertSeats = "insert into seat "
                        + "(studio_id, seat_row, seat_col, seat_status)"
                        + "values "
                        + "(?,?,?,?)";
                PreparedStatement statement1 = connection.prepareStatement(insertSeats, Statement.RETURN_GENERATED_KEYS);
                statement1.setInt(1, seat.getStudioID());
                statement1.setInt(2, seat.getRow());
                statement1.setInt(3, seat.getCol());
                statement1.setString(4, String.valueOf(seat.getStatus()));
                statement1.executeUpdate();
                ResultSet resultSet1 = statement1.getGeneratedKeys();
                if (resultSet1 != null) {
                    resultSet1.next();
                    seat.setId(resultSet1.getInt(1));
                }
                JDBCUtil.close(statement1);
                JDBCUtil.close(resultSet1);
            }
        }
        connection.commit();
        JDBCUtil.close(statement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public int delete(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        connection.setAutoCommit(false);

        String ticketsDelete = "delete from ticket "
                + "where studio_id=" + id;
        PreparedStatement ticketsStatment = connection.prepareStatement(ticketsDelete);
        ticketsStatment.executeUpdate();
        JDBCUtil.close(ticketsStatment);

        String plansDelete = "delete from plan "
                +"where studio_id="+id;
        PreparedStatement plansStatement = connection.prepareStatement(plansDelete);
        plansStatement.executeUpdate();
        JDBCUtil.close(plansStatement);

        String seatsDelete = "delete from seat "
                + "where studio_id=" + id;
        PreparedStatement seatsStatement = connection.prepareStatement(seatsDelete);
        seatsStatement.executeUpdate();
        JDBCUtil.close(seatsStatement);

        String studioDelete = "delete from studio "
                + "where studio_id=" + id;
        PreparedStatement studioStatement = connection.prepareStatement(studioDelete);
        int count = studioStatement.executeUpdate();

        connection.commit();
        JDBCUtil.close(studioStatement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public int update(int id, String newName) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String sql = "update studio "
                + "set studio_name=? "
                + "where studio_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newName);
        statement.setInt(2, id);
        int count = statement.executeUpdate();
        JDBCUtil.close(statement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public List<Studio> quary(int id) throws SQLException {
        String sql;
        if (id < 0) {
            sql = "select * from studio";
        } else {
            sql  = "select * from studio where studio_id=" + id;
        }
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        List<Studio> studios = new ArrayList<>();
        while (set.next()) {
            Studio studio = new Studio();
            studio.setStudioID(set.getInt("studio_id"));
            studio.setStudioName(set.getString("studio_name"));
            studio.setCol(set.getInt("cols_count"));
            studio.setRow(set.getInt("rows_count"));
            studio.setSeatsCount(set.getInt("seats_count"));
            studios.add(studio);
        }
        return studios;
    }
}
