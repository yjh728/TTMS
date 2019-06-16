package com.yjh.test.dao.impl;

import com.yjh.test.dao.SaleDao;
import com.yjh.test.model.Sale;
import com.yjh.test.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleDaoImpl implements SaleDao {
    @Override
    public List<Sale> queryByUserID(int userID) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        List<Sale> saleList = new ArrayList<>();
        String querySale = "select play_id, ticket_id from sale where user_id=" + userID;
        PreparedStatement querySaleStatement = connection.prepareStatement(querySale);
        ResultSet querySaleSet = querySaleStatement.executeQuery();
        while (querySaleSet.next()) {
            String queryTicket = "select plan_id,seat_col,seat_row from ticket where status='SOLD' and ticket_id=" + querySaleSet.getInt("ticket_id");
            PreparedStatement queryTicketStatement = connection.prepareStatement(queryTicket);
            ResultSet ticketSet = queryTicketStatement.executeQuery();
            if (ticketSet.next()) {
                Sale sale = new Sale();
                sale.setTicketID(querySaleSet.getInt("ticket_id"));
                sale.setSeatCol(ticketSet.getInt("seat_col"));
                sale.setSeatRow(ticketSet.getInt("seat_row"));
                String queryPlay = "select play_name, picture_url from play where play_id=" + querySaleSet.getInt("play_id");
                PreparedStatement queryPlayStatement = connection.prepareStatement(queryPlay);
                ResultSet playSet = queryPlayStatement.executeQuery();
                if (playSet.next()) {
                    sale.setPlayName(playSet.getString("play_name"));
                    sale.setPictureUrl(playSet.getString("picture_url"));
                }
                String queryPlan = "select play_date, start_time from plan where plan_id=" + ticketSet.getInt("plan_id");
                PreparedStatement queryPlanStatement = connection.prepareStatement(queryPlan);
                ResultSet planSet = queryPlanStatement.executeQuery();
                if (planSet.next()) {
                    sale.setDate(planSet.getDate("play_date"));
                    sale.setTime(planSet.getTime("start_time"));
                }
                saleList.add(sale);
            }
        }
        return saleList;
    }
}
