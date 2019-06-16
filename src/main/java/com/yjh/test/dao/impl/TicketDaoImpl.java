package com.yjh.test.dao.impl;

import com.yjh.test.dao.TicketDao;
import com.yjh.test.model.ENUM.SeatStatus;
import com.yjh.test.model.ENUM.TicketStatus;
import com.yjh.test.model.Seat;
import com.yjh.test.model.Ticket;
import com.yjh.test.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {
    @Override
    public int create(int planID, List<Seat> seatList, int playID, boolean recreate) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        connection.setAutoCommit(false);

        if (recreate) {
            String deleteTickets = "delete from ticket where plan_id=" + planID;
            PreparedStatement statement = connection.prepareStatement(deleteTickets);
            statement.executeUpdate();
            JDBCUtil.close(statement);
        }

        String createTickets = "insert into ticket "
                + "(plan_id, seat_id, status, studio_id, play_id, seat_row, seat_col) "
                + "values "
                + "(?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(createTickets);
        int count = 0;
        for (Seat seat : seatList) {
            if (seat.getStatus() == SeatStatus.GOOD) {
                statement.clearParameters();
                statement.setInt(1, planID);
                statement.setInt(2, seat.getId());
                statement.setString(3, TicketStatus.NOMAL.toString());
                statement.setInt(4, seat.getStudioID());
                statement.setInt(5, playID);
                statement.setInt(6, seat.getRow());
                statement.setInt(7, seat.getCol());
                count += statement.executeUpdate();
            }
        }
        connection.commit();
        JDBCUtil.close(statement);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public int insert(int ticketID, int playID, double price, TicketStatus status, int userID) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        connection.setAutoCommit(false);

        String updateTicket = "update ticket set status=? where ticket_id=" + ticketID;
        PreparedStatement ticketStatement = connection.prepareStatement(updateTicket);
        ticketStatement.setString(1, String.valueOf(status));
        ticketStatement.executeUpdate();
        JDBCUtil.close(ticketStatement);

        String addSale = "insert into sale "
                + "(ticket_id, price, sale_type, play_id, user_id) "
                + "values "
                + "(?, ?, ?, ?, ?)";
        PreparedStatement saleStatment = connection.prepareStatement(addSale);
        saleStatment.setInt(1, ticketID);
        saleStatment.setFloat(2, (float) price);
        saleStatment.setString(3, String.valueOf(status));
        saleStatment.setInt(4, playID);
        saleStatment.setInt(5, userID);
        int count = saleStatment.executeUpdate();

        String querySalesanalysis = "select * from salesanalysis where play_id=" + playID;
        PreparedStatement statement = connection.prepareStatement(querySalesanalysis);
        ResultSet set = statement.executeQuery();
        if (!set.next()) {
            String insertSalesanalysis = "insert into salesanalysis "
                    + "(tickets, sales, play_id) "
                    + "values "
                    + "(?,?,?)";
            PreparedStatement insertSalesanalysisStatement = connection.prepareStatement(insertSalesanalysis);
            if (status == TicketStatus.SOLD) {
                insertSalesanalysisStatement.setInt(1, 1);
                insertSalesanalysisStatement.setFloat(2, (float) price);
            } else {
                insertSalesanalysisStatement.setInt(1, 0);
                insertSalesanalysisStatement.setFloat(2, 0);
            }
            insertSalesanalysisStatement.setInt(3, playID);
            insertSalesanalysisStatement.executeUpdate();
            JDBCUtil.close(insertSalesanalysisStatement);
        } else {
            String updateSalesanalysis;
            switch (status) {
                case SOLD:
                    updateSalesanalysis = "update salesanalysis set "
                            + "tickets=tickets+1, sales=sales+" + price
                            + " where play_id=" + playID;
                    break;
                case NOMAL:
                    updateSalesanalysis = "update salesanalysis set "
                            + "tickets=tickets-1, sales=sales-" + price
                            + " where play_id=" + playID;
                    break;
                default:
                    updateSalesanalysis = "update salesanalysis set "
                            + "tickets=tickets, sales=sales "
                            + " where play_id=" + playID;
                    break;
            }
            PreparedStatement updateSalesStatement = connection.prepareStatement(updateSalesanalysis);
            updateSalesStatement.executeUpdate();
            JDBCUtil.close(updateSalesStatement);
        }
        connection.commit();
        JDBCUtil.close(statement);

        JDBCUtil.close(set);
        JDBCUtil.close(saleStatment);
        JDBCUtil.close(connection);
        return count;
    }

    @Override
    public List<Ticket> query(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String query = "select * from ticket where ticket_id=" + id;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        List<Ticket> ticketList = new ArrayList<>();
        while (set.next()) {
            Ticket ticket = new Ticket();
            ticket.setTicketID(set.getInt("ticket_id"));
            ticket.setPlanID(set.getInt("plan_id"));
            ticket.setPlayID(set.getInt("play_id"));
            ticket.setSeatID(set.getInt("seat_id"));
            ticket.setStudioID(set.getInt("studio_id"));
            ticket.setStatus(TicketStatus.valueOf(set.getString("status")));
            ticket.setRow(set.getInt("seat_row"));
            ticket.setCol(set.getInt("seat_col"));
            ticketList.add(ticket);
        }
        JDBCUtil.close(set, statement, connection);
        return ticketList;
    }

    @Override
    public List<Ticket> queryByPlanID(int id) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String query = "select * from ticket where plan_id=" + id;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        List<Ticket> ticketList = new ArrayList<>();
        while (set.next()) {
            Ticket ticket = new Ticket();
            ticket.setTicketID(set.getInt("ticket_id"));
            ticket.setPlanID(set.getInt("plan_id"));
            ticket.setPlayID(set.getInt("play_id"));
            ticket.setSeatID(set.getInt("seat_id"));
            ticket.setStudioID(set.getInt("studio_id"));
            ticket.setStatus(TicketStatus.valueOf(set.getString("status")));
            ticket.setRow(set.getInt("seat_row"));
            ticket.setCol(set.getInt("seat_col"));
            ticketList.add(ticket);
        }
        JDBCUtil.close(set, statement, connection);
        return ticketList;
    }
}
