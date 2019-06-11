package com.yjh.test.servlet.seat;

import com.yjh.test.model.ENUM.SeatStatus;
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

@WebServlet("/seat/update")
public class SeatUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        Result<Seat> result = new Result<>();
        PrintWriter writer =resp.getWriter();
        SeatService seatService = new SeatService();
        int id = Integer.valueOf(req.getParameter("seat_id"));
        SeatStatus status = SeatStatus.valueOf(req.getParameter("seat_status"));
        try {
            List<Seat> seats = seatService.quary(id);
            if (seats.size()==0) {
                result.setStatus(false);
                result.setReasons("该座位不存在");
            } else {
                seats.get(0).setStatus(status);
                if (seatService.update(seats.get(0)) > 0) {
                    result.setStatus(true);
                    result.setReasons("修改座位状态成功");
                    result.setData(seats.get(0));
                } else {
                    result.setStatus(false);
                    result.setReasons("修改座位状态失败");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("修改座位状态失败");
        } finally {
            writer.write(result.toString());
        }
    }
}
