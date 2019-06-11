package com.yjh.test.servlet.plan;

import com.yjh.test.model.Plan;
import com.yjh.test.model.Result;
import com.yjh.test.service.PlanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/plan/queryall")
public class PlanQueryAll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<List<Plan>> result = new Result<>();
        PlanService service = new PlanService();
        try {
            List<Plan> planList = service.query(-1);
            if (planList.size() == 0) {
                result.setStatus(true);
                result.setReasons("没有影片信息");
            } else {
                result.setStatus(true);
                result.setReasons("查询成功");
                result.setData(planList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("查询失败");
        } finally {
            writer.write(result.toString());
            writer.close();
        }
    }
}
