package com.yjh.test.dao;

import com.yjh.test.model.Seat;

import java.sql.SQLException;
import java.util.List;

public interface SeatDao {
    int insert(Seat seat) throws SQLException;
    int delete(int seatID) throws SQLException;
    int update(Seat seat) throws SQLException;
    List<Seat> quary(int id) throws SQLException;
    List<Seat> quaryByStudioID(int studioID) throws SQLException;
}
