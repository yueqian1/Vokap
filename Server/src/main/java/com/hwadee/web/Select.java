package com.hwadee.web;

import com.alibaba.fastjson.JSON;
import com.hwadee.web.entity.Users;
import com.hwadee.web.entity.Word;
import com.hwadee.web.util.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/select")
public class Select extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //参数获取
        String reqID = null;
        reqID = req.getParameter("reqID");
        String userid = req.getParameter("userid");
        String username = req.getParameter("name");
        String passwd = req.getParameter("passwd");
        String englishWord = req.getParameter("englishWord");
        //网页端请求测试
        if (reqID == null) {
            System.out.println("网页测试");
            resp.getWriter().write("Test Success!");
        }

        //用户登录请求
        else if (reqID.equals("1")) {
            Users user = Login(username, passwd);
            if (user != null) {
                System.out.println("登陆成功");
                //自定义响应码
                resp.setStatus(203);
                String result = user.getUsers_id() + "";
                resp.getWriter().write(result);
            } else {
                resp.setStatus(202);
                System.out.println("Username：" + username + "\n Passwword:" + passwd + "错误的用户名或密码");
                resp.getWriter().write("Login Failed!");
            }
        }

        //浏览生词本
        else if (reqID.equals("3")) {
            Word[] words = SeeWords(userid);
            int i = 0;
            if (words != null) {
                resp.getWriter().write("[");
                while (words[i] != null) {
                    resp.getWriter().write(JSON.toJSONString(words[i]));
                    i++;
                }
                resp.getWriter().write("]");
            }
            System.out.println("查看成功!");
        }

        //在生词本中查单词
        else if (reqID.equals("4")) {
            Word word = Seeword(userid, englishWord);
            if (word == null) {
                resp.setStatus(208);
                resp.getWriter().write("生词库中不存在" + englishWord);
            } else {
                resp.setStatus(207);
                resp.getWriter().write(JSON.toJSONString(word));
                System.out.println(JSON.toJSONString(word));
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }


    public Users Login(String username, String passwd) {
        Users result = null;
        try {
            Object[] objects = {username, passwd};
            result = new JDBCUtil().selectOne(Users.class, "select * from users where name=? and passwd=?", objects);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Word[] SeeWords(String userid) {
        int i = Integer.parseInt(userid);
        Object[] objects = {i};
        Word[] words = null;
        try {
            words = new JDBCUtil().selectMore("select englishWord,chineseWord,instance,id from words,have\n" +
                    "where words.id=have.wordID\n" +
                    "and have.userID=?", objects);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }

    public Word Seeword(String userid, String englishWord) {
        int i = Integer.parseInt(userid);
        Word result = null;
        try {
            Object[] objects = {i, englishWord};
            result = new JDBCUtil().selectOne(Word.class, "select englishWord,chineseWord,instance,id from words,have\n" +
                    "where words.id=have.wordID\n" +
                    "and have.userID=?\n" +
                    "and englishWord=?", objects);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
