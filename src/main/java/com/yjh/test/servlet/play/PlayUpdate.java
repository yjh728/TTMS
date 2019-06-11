package com.yjh.test.servlet.play;

import com.yjh.test.model.ENUM.PlayType;
import com.yjh.test.model.Play;
import com.yjh.test.model.Result;
import com.yjh.test.service.PlayService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@MultipartConfig
@WebServlet("/play/update")
public class PlayUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<Play> result = new Result<>();
        PlayService playService = new PlayService();
        Play play = new Play();

        String rootPath = req.getSession().getServletContext().getRealPath("/web/image");
        File filePath = new File(rootPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        //获取对应的请求数据
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator iter = items.iterator();
        List<String> values = new ArrayList<>();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            if (item.isFormField()) {
                //对普通的key-value数据进行处理
                String fieldName = item.getFieldName();
                String value = item.getString("utf-8");
                values.add(value);
            } else {
                //获取文件数据并保存
                StringBuilder fileName = new StringBuilder(item.getName());
                fileName.insert(fileName.indexOf("."), System.currentTimeMillis());
                System.out.println(fileName);
                System.out.println(rootPath);
                File image = new File(rootPath, new String(fileName));
                System.out.println(image.getAbsolutePath());
                InputStream is = item.getInputStream();
                OutputStream os = new FileOutputStream(image);
                byte[] buf = new byte[1024];
                int read;
                while ((read = is.read(buf)) > 0) {
                    os.write(buf, 0, read);
                }

                //返回文件对应url
                String scheme = req.getScheme();    // 获取请求协议(http)
                String serverName = req.getServerName();    //获取服务器名称(localhost)
                int serverPort = req.getServerPort();   // 获取服务器端口号

                String path = scheme + "://" + serverName + ":" + serverPort + req.getSession().getServletContext().getContextPath() + "/web/image/" + image.getName();
                play.setPictureUrl(path);
            }
        }
        play.setPlayName(values.get(0));
        play.setPlayType(PlayType.valueOf(values.get(1)));
        play.setPlayArea(values.get(2));
        play.setDuration(Integer.valueOf(values.get(3)));
        play.setStartDate(Date.valueOf(values.get(4)));
        play.setEndDate(Date.valueOf(values.get(5)));
        play.setPrice(Double.valueOf(values.get(6)));
        try {
            List<Play> list = playService.quary(play.getPlayName());
            if (list.size() == 0) {
                result.setStatus(false);
                result.setReasons("该影片不存在");
            } else {
                play.setPlayID(list.get(0).getPlayID());
                if (playService.update(play) > 0) {
                    result.setStatus(true);
                    result.setReasons("修改影片成功");
                    result.setData(play);
                } else {
                    result.setStatus(false);
                    result.setReasons("修改影片失败");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("修改影片失败");
        } finally {
            writer.write(result.toString());
        }
    }
}
