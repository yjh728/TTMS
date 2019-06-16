package com.yjh.test.servlet.ticket;

import com.yjh.test.model.ENUM.TicketStatus;
import com.yjh.test.model.Play;
import com.yjh.test.model.Result;
import com.yjh.test.model.Ticket;
import com.yjh.test.service.PlayService;
import com.yjh.test.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ticket/back")
public class TicketBack extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<Ticket> result = new Result<>();
        TicketService ticketService = new TicketService();
        int id = Integer.valueOf(req.getParameter("ticket_id"));
        int userID = Integer.valueOf(req.getParameter("user_id"));
        try {
            List<Ticket> ticketList = ticketService.query(id);
            if (ticketList.size() == 0) {
                result.setStatus(false);
                result.setReasons("该票不存在");
            } else if (ticketList.get(0).getStatus() == TicketStatus.NOMAL) {
                result.setStatus(false);
                result.setReasons("该票未售出或未被预定");
            } else {
                PlayService playService = new PlayService();
                int playID = ticketList.get(0).getPlayID();
                List<Play> playList = playService.quary(playID);
                if (ticketService.insert(id, playID, playList.get(0).getPrice(), TicketStatus.NOMAL, userID) > 0) {
                    result.setStatus(true);
                    result.setReasons("退票成功");
                    ticketList.get(0).setStatus(TicketStatus.NOMAL);
                    result.setData(ticketList.get(0));
                } else {
                    result.setStatus(false);
                    result.setReasons("退票失败");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("退票失败");
        } finally {
            writer.write(result.toString());
            writer.close();
        }
    }
}
