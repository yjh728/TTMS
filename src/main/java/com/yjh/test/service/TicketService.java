package com.yjh.test.service;

import com.yjh.test.dao.DaoFactory;
import com.yjh.test.dao.TicketDao;
import com.yjh.test.model.ENUM.TicketStatus;
import com.yjh.test.model.Seat;
import com.yjh.test.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketService {
    private TicketDao ticketDao = DaoFactory.getTicketDao();

    public int create(int planID, List<Seat> seatList, int playID, boolean recreate) throws SQLException {
        return ticketDao.create(planID, seatList, playID, recreate);
    }

    public int insert(int ticketID, int playID, double price, TicketStatus status, int userID) throws SQLException {
        return ticketDao.insert(ticketID, playID, price, status, userID);
    }

    public List<Ticket> query(int id) throws SQLException {
        return ticketDao.query(id);
    }

    public List<Ticket> queryByplanID(int id) throws SQLException {
        return ticketDao.queryByPlanID(id);
    }

}
