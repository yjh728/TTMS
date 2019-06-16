package com.yjh.test.servlet.ticket;

import com.yjh.test.model.Sale;
import com.yjh.test.service.SaleService;
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

@WebServlet("/ticket/purchased")
public class TicketPurchased extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        int userID = Integer.valueOf(req.getParameter("user_id"));
        SaleService saleService = new SaleService();
        List<Sale> list = null;
        try {
            list = saleService.queryByUserID(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writer.write("{\"saleList\":" + GSONUtil.toJson(list) + "}");
    }
}
