package com.yjh.test.servlet.ticket;

import com.yjh.test.model.ENUM.TicketStatus;
import com.yjh.test.model.Ticket;
import com.yjh.test.service.TicketService;
import com.yjh.test.util.GSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ticket/buy")
public class TicketsBuy extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        String[] ticketsID = req.getParameterValues("tickets_id");
        TicketService ticketService = new TicketService();
        int playID = Integer.valueOf(req.getParameter("play_id"));
        float price = Float.valueOf(req.getParameter("price"));
        int userID = Integer.valueOf(req.getParameter("user_id"));
        List<Ticket> ticketList = new ArrayList<>();
        for (String sid : ticketsID) {
            try {
                int id = Integer.valueOf(sid);
                List<Ticket> tickets = ticketService.query(id);
                if (ticketService.insert(id, playID, price, TicketStatus.SOLD, userID) > 0) {
                    ticketList.add(tickets.get(0));
                } else {
                    tickets.get(0).setStatus(TicketStatus.NOMAL);
                    ticketList.add(tickets.get(0));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.getWriter().write(GSONUtil.toJson(ticketList));
    }
}
