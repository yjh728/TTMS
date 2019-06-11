package com.yjh.test.servlet.play;

import com.yjh.test.model.Play;
import com.yjh.test.model.Result;
import com.yjh.test.service.PlayService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/play/queryall")
public class PlayQueryAll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        PlayService service = new PlayService();
        Result<List<Play>> result = new Result<>();
        
        try {
            List<Play> playList = service.quary(-1);
            if (playList.size() == 0) {
                result.setStatus(false);
                result.setReasons("没有剧目");
            } else {
                result.setStatus(true);
                result.setReasons("查询成功");
                result.setData(playList);
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
