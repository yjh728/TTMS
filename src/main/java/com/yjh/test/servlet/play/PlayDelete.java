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

@WebServlet("/play/delete")
public class PlayDelete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        Result<Play> result = new Result<>();
        PrintWriter writer = resp.getWriter();
        PlayService playService = new PlayService();
        int id = Integer.valueOf(req.getParameter("play_id"));
        try {
            List<Play> playList = playService.quary(id);
            if (playList.size() == 0) {
                result.setStatus(false);
                result.setReasons("该剧目不存在");
            } else if (playService.delete(id) > 0) {
                result.setStatus(true);
                result.setReasons("删除剧目成功");
                result.setData(playList.get(0));
            } else {
                result.setStatus(false);
                result.setReasons("删除剧目失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("删除剧目失败");
        } finally {
            writer.write(result.toString());
        }
    }
}
