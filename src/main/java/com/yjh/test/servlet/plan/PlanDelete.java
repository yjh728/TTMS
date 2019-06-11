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

@WebServlet("/plan/delete")
public class PlanDelete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<Plan> result = new Result<>();
        PlanService service = new PlanService();
        int id = Integer.valueOf(req.getParameter("plan_id"));
        try {
            List<Plan> planList = service.query(id);
            if (planList.size() == 0) {
                result.setStatus(false);
                result.setReasons("该演出计划不存在");
            } else if (service.delete(id) > 0) {
                result.setStatus(true);
                result.setReasons("删除演出计划成功");
                result.setData(planList.get(0));
            } else {
                result.setStatus(false);
                result.setReasons("删除演出计划失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("删除演出计划失败");
        } finally {
            writer.write(result.toString());
            writer.close();
        }
    }
}
