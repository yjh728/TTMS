package com.yjh.test.servlet.studio;

import com.yjh.test.model.Result;
import com.yjh.test.model.Seat;
import com.yjh.test.service.SeatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/studio/query")
public class StudioQuary extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        Result<List<Seat>> result = new Result<>();
        PrintWriter writer = resp.getWriter();
        int id = Integer.valueOf(req.getParameter("studio_id"));
        SeatService seatService = new SeatService();
        try {
            List<Seat> seats = seatService.quary(id);
            if (seats.size() == 0) {
                result.setStatus(false);
                result.setReasons("演出厅不存在或没有座位");
            } else {
                result.setStatus(true);
                result.setReasons("查询演出厅座位成功");
                result.setData(seats);
            }
            writer.write(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
