package com.yjh.test;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 *  请求乱码解决办法：
 *      post方法：
 *          req.setCharseterEncoding("UTF-8");
 *      get方法：
 *          req.setCharseterEncoding("UTF-8");
 *          在tomcat的目录下的conf目录中修改server.xml中的Connector标签中添加属性：useBodyEncodingForUrl="true"
 *  请求转发：实现多个servlet联动操作处理请求，避免代码冗余，让servlet职责更加明确
 *      参数为servlet的别名或者jsp
 *      req.getRequestDispatcher("user/logout").forward(req, resp);
 *  在请求转发之前，使用request对象实现不同servlet的数据流转：
 *      向之后的servlet传递数据：req.setAttribute("键", "值");
 *      清除之前的servlet传递到数据：req.removeAttribute("键");
 *  重定向：解决请求转发造成表单数据重复提交的问题 || 当前请求servlet无法进行处理
 *      resp.sendRedirect("路径");
 *      问题：之前提交的数据无法传递到重定向的地方，解决：session
 *  Cookie解决了发送的不同请求的数据共享问题:
 *      cookie是浏览器端的数据存储技术
 *      存储的数据声明在服务器端
 *      临时存储：存储在浏览器的运行内存中，浏览器关闭即失效
 *          Cookie cookie = new Cookie("键", "值");
 *          resp.addCookie(cookie);
 *      定时存储：设置了cookie的有效期，存储在浏览器的硬盘中，在有效期内符合路径要求的请求都会附带该cookie
 *          Cookie cookie = new Cookie("键", "值");
 *`         cookie.setMaxAge(60*60*24*3);//以秒为单位
 *      默认cookie信息设置好后，除非设置有效路径，否则每次请求都会携带
 *          cookie.setPath(String uri);
 *  Session解决了一个用户不同请求的数据共享问题：
 *      session是服务器端的数据存储技术
 *      服务器进行创建
 *      通过cookie技术将session的唯一JSESSIONID存储到浏览器内存中
 *      有效期是一次会话(浏览器为关闭时的所有操作)，因为cookie未设置有效期时关闭浏览器即失效
 *      默认存储时间是30分钟，在指定时间内session对象没有被使用时被销毁
 *      可以通过设置session.setMaxInactiveInterval(3*24*60*60);来设置服务器存储session的时长
 *      设置session强制失效：session.invalidate();
 */

//@WebServlet("/user/login")
public class TestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求编码格式
        req.setCharacterEncoding("UTF-8");
        Cookie cookie = new Cookie("", "");
        cookie.setMaxAge(60*60*24*3);
        HttpSession session = req.getSession();
        // 设置响应编码格式
        resp.setContentType("text/html;charset=UTF-8");
        // 获取请求URL
        System.out.println(req.getRequestURL());
        // 获取请求URI
        System.out.println(req.getRequestURI());
        // 获取请求方法
        System.out.println(req.getMethod());
        // 获取协议
        System.out.println(req.getScheme());
        // 获取请求首部键值集合
        Enumeration<String> enumeration = req.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String header = enumeration.nextElement();
            // 根据请求首部的键获取对应的值
            System.out.println(header + ":" + req.getHeader(header));
        }
        // 根据键值获取前端发送的数据
        String account = req.getParameter("account");
        String pwd = req.getParameter("pwd");
        Gson gson = new Gson();

        PrintWriter printWriter = resp.getWriter();
        LoginResponse response = new LoginResponse();
        UserModel userModel = new UserModel();
        userModel.setName(account);
        userModel.setPwd(pwd);
        if (account.equals("yjh728") && pwd.equals("yjh990728.")) {
            response.setStatus(200);
            response.setDate(userModel);
            response.setReasonPhrase("登录成功");
        } else {
            response.setStatus(400);
            response.setDate(userModel);
            response.setReasonPhrase("登录失败");
        }
        String s = "status:" + response.getStatus() + "<br/>"
                + "reason:" + response.getReasonPhrase() + "<br/>"
                + "date:<br/>"
                + "account:" + response.getDate().getName() + "<br/>"
                + "pwd:" + response.getDate().getPwd() + "<br/>";
//        printWriter.println(s);
        String responseS = gson.toJson(response);
//        resp.setHeader("aaa", "aaa");
//        resp.sendError(400, "sorry");
        printWriter.println(responseS);
        printWriter.close();
    }
}