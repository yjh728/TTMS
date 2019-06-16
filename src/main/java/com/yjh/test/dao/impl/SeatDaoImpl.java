package com.yjh.test.dao.impl;

import com.yjh.test.dao.SeatDao;
import com.yjh.test.model.ENUM.SeatStatus;
import com.yjh.test.model.Seat;
import com.yjh.test.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDaoImpl implements SeatDao {
    @Override
    public int insert(Seat seat) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String sql = "insert into seat "
                + "(studio_id, seat_row, seat_col, seat_status)"
                + "values "
                + "(?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, seat.getStudioID());
        statement.setInt(2, seat.getRow());
        statement.setInt(3, seat.getCol());
        statement.setString(4, String.valueOf(seat.getStatus()));
        int count = statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet != null) {
            resultSet.next();
            seat.setId(resultSet.getInt(1));
        }
        JDBCUtil.close(resultSet, statement, connection);
        return count;
    }

    @Override
    public int delete(int seatID) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String sql = "delete from seat where seat_id=" + seatID;
        PreparedStatement statement = connection.prepareStatement(sql);
        int count = statement.executeUpdate();
        JDBCUtil.close(statement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public int update(Seat seat) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String sql = "update seat "
                + "set seat_status=? "
                + "where seat_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(seat.getStatus()));
        statement.setInt(2, seat.getId());
        int count = statement.executeUpdate();
        JDBCUtil.close(statement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public List<Seat> quary(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String sql = "select * from seat "
                + "where seat_id=" + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        List<Seat> seats = new ArrayList<>();
        while (set.next()) {
            Seat seat = new Seat();
            seat.setId(set.getInt("seat_id"));
            seat.setStudioID(set.getInt("studio_id"));
            seat.setCol(set.getInt("seat_col"));
            seat.setRow(set.getInt("seat_row"));
            seat.setStatus(SeatStatus.valueOf(set.getString("seat_status")));
            seats.add(seat);
        }
        JDBCUtil.close(set, statement, connection);
        return seats;
    }

    @Override
    public List<Seat> quaryByStudioID(int studioID) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String sql = "select * from seat "
                + "where studio_id=" + studioID;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        List<Seat> seats = new ArrayList<>();
        while (set.next()) {
            Seat seat = new Seat();
            seat.setId(set.getInt("seat_id"));
            seat.setStudioID(set.getInt("studio_id"));
            seat.setCol(set.getInt("seat_col"));
            seat.setRow(set.getInt("seat_row"));
            seat.setStatus(SeatStatus.valueOf(set.getString("seat_status")));
            seats.add(seat);
        }
        JDBCUtil.close(set, statement, connection);
        return seats;
    }
}
