package com.yjh.test.servlet.ticket;

import com.yjh.test.model.Plan;
import com.yjh.test.model.Result;
import com.yjh.test.model.Seat;
import com.yjh.test.model.Ticket;
import com.yjh.test.service.PlanService;
import com.yjh.test.service.SeatService;
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

@WebServlet("/ticket/create")
public class TicketCreate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<List<Ticket>> result = new Result<>();
        TicketService ticketService = new TicketService();
        SeatService seatService = new SeatService();
        PlanService planService = new PlanService();
        int planID = Integer.valueOf(req.getParameter("plan_id"));
        boolean recreate = Boolean.valueOf(req.getParameter("recreate"));
        try {
            List<Plan> planList = planService.query(planID);
            if (planList.size() == 0) {
                result.setStatus(false);
                result.setReasons("演出计划不存在");
            } else {
                int playID = planList.get(0).getPlayID();
                List<Seat> seatList = seatService.quaryByStudioID(planList.get(0).getStudioID());
                if (ticketService.create(planID, seatList, playID, recreate) > 0) {
                    List<Ticket> ticketList = ticketService.queryByplanID(planID);
                    result.setStatus(true);
                    result.setReasons("添加票成功");
                    result.setData(ticketList);
                } else {
                    result.setStatus(false);
                    result.setReasons("添加票失败");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("添加票失败");
        } finally {
            writer.write(result.toString());
            writer.close();
        }
    }
}
