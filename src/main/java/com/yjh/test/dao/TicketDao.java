package com.yjh.test.dao;

import com.yjh.test.model.ENUM.TicketStatus;
import com.yjh.test.model.Seat;
import com.yjh.test.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface TicketDao {
    int create(int planID, List<Seat> seatList, int playID, boolean recreate) throws SQLException;
    int insert(int ticketID, int playID, double price, TicketStatus status) throws SQLException;
    List<Ticket> query(int id) throws SQLException;
    List<Ticket> queryByPlanID(int id) throws SQLException;
}
