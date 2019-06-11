package com.yjh.test.servlet.plan;

import com.yjh.test.model.Plan;
import com.yjh.test.model.Play;
import com.yjh.test.model.Result;
import com.yjh.test.model.Studio;
import com.yjh.test.service.PlanService;
import com.yjh.test.service.PlayService;
import com.yjh.test.service.StudioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@WebServlet("/plan/add")
public class PlanAdd extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        Result<Plan> result = new Result<>();
        PrintWriter writer = resp.getWriter();
        PlanService planService = new PlanService();
        PlayService playService = new PlayService();
        StudioService studioService = new StudioService();
        Plan plan = new Plan();
        int playID = Integer.valueOf(req.getParameter("play_id"));
        int studioID = Integer.valueOf(req.getParameter("studio_id"));
        Date date = Date.valueOf(req.getParameter("play_date"));
        Time startTime = Time.valueOf(req.getParameter("start_time") + ":00");
        try {
            List<Play> playList = playService.quary(playID);
            List<Studio> studioList = studioService.quary(studioID);
            Time endTime = null;
            long a = 0;
            long b = 0;
            if (playList.size() > 0) {
                endTime = new Time(startTime.getTime() + playList.get(0).getDuration() * 60000);
                a = new java.util.Date().getTime();
                b = date.getTime() + startTime.getTime();
            }
            if (studioList.size() == 0) {
                result.setStatus(false);
                result.setReasons("演出厅不存在");
            } else if (playList.size() == 0) {
                result.setStatus(false);
                result.setReasons("该剧目不存在");
            } else if (b < a) {
                result.setStatus(false);
                result.setReasons("演出时间必须在当前时间之后");
            } else {
                List<Plan> planList = planService.query(-1);
                boolean flag = true;
                for (Plan plan1 : planList) {
                    if (plan1.getStudioID() == studioID
                            && plan1.getPlayDate().equals(date) && plan1.getStartTime().before(endTime)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    plan.setPlayID(playID);
                    plan.setStudioID(studioID);
                    plan.setPlayDate(date);
                    plan.setStartTime(startTime);
                    plan.setEndTime(endTime);
                    if (planService.add(plan) > 0) {
                        result.setStatus(true);
                        result.setReasons("添加演出计划成功");
                        result.setData(plan);
                    } else {
                        result.setStatus(false);
                        result.setReasons("添加失败");
                    }
                } else {
                    result.setStatus(false);
                    result.setReasons("时间冲突，请重新选择时间");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("添加失败");
        } finally {
            writer.write(result.toString());
            writer.close();
        }
    }
}
