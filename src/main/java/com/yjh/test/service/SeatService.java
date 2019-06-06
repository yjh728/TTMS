package com.yjh.test.service;

import com.yjh.test.dao.DaoFactory;
import com.yjh.test.dao.SeatDao;
import com.yjh.test.model.Seat;

import java.sql.SQLException;
import java.util.List;

public class SeatService {
    private SeatDao seatDao = DaoFactory.getSeatDao();
    public int add(Seat seat) throws SQLException {
        return seatDao.insert(seat);
    }
    public int delete(int id) throws SQLException {
        return seatDao.delete(id);
    }
    public int update(Seat seat) throws SQLException {
        return seatDao.update(seat);
    }
    public List<Seat> quary(int studioID) throws  SQLException{
        return seatDao.quary(studioID);
    }
}
