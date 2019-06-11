package com.yjh.test.servlet.ticket;

import com.yjh.test.model.Plan;
import com.yjh.test.model.Result;
import com.yjh.test.model.Ticket;
import com.yjh.test.service.PlanService;
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

@WebServlet("/ticket/query")
public class TicketQuery extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<List<Ticket>> result = new Result<>();
        TicketService ticketService = new TicketService();
        PlanService planService = new PlanService();
        int id = Integer.valueOf(req.getParameter("plan_id"));
        try {
            List<Plan> planList = planService.query(id);
            List<Ticket> ticketList = ticketService.queryByplanID(id);
            if (planList.size() == 0) {
                result.setStatus(false);
                result.setReasons("该演出计划不存在");
            } else if (ticketList.size() == 0) {
                result.setStatus(true);
                result.setReasons("无演出票，请生成");
            } else {
                result.setStatus(true);
                result.setReasons("查询成功");
                result.setData(ticketList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("查询失败");
        } finally {
            writer.write(result.toString());
        }
    }
}
