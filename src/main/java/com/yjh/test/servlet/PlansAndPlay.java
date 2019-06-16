package com.yjh.test.servlet;

import com.yjh.test.model.Plan;
import com.yjh.test.model.Play;
import com.yjh.test.service.PlanService;
import com.yjh.test.service.PlayService;
import com.yjh.test.util.GSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/play/plans")
public class PlansAndPlay extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        int id = Integer.valueOf(req.getParameter("play_id"));
        PlayService playService = new PlayService();
        PlanService planService = new PlanService();
        try {
            List<Play> plays = playService.quary(id);
            List<Plan> plans = planService.queryByPlayID(id);
            writer.write("{\"play\":" + GSONUtil.toJson(plays.get(0)) +",\"plans\":"+ GSONUtil.toJson(plans) + "}");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
