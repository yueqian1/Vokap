package com.hwadee.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hwadee.web.entity.Users;
import com.hwadee.web.entity.Word;
import com.hwadee.web.util.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
public class Update extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//接收参数
        String reqID = null;
        reqID = req.getParameter("reqID");
        String englishWord = req.getParameter("englishWord");
        String userid = req.getParameter("userid");
        String chineseWord = req.getParameter("chineseWord");
        String instance = req.getParameter("instance");
        String username = req.getParameter("name");
        String passwd = req.getParameter("passwd");
        String words = req.getParameter("words");
//网页测试
        if (reqID == null) {
            resp.getWriter().write("Test Success!");
        }
        //加入生词本
        else if (reqID.equals("1")) {
            //该单词是否已经加入生词库
            if (isInwords(englishWord, userid) == null) {
                //未加入生词库,执行sql语句
                AddWord(englishWord, chineseWord, instance, userid);
                resp.setStatus(201);
                resp.getWriter().write(englishWord + "加入生词本成功！");
            } else {
                //已经存在于生词库中
                resp.setStatus(205);
                resp.getWriter().write(englishWord + "在生词库中已经存在！");
            }
        }

        //从生词库中删除生词
        else if (reqID.equals("2")) {
            //该单词是否已经加入生词库
            if (isInwords(englishWord, userid) == null) {
                //未加入生词库
                resp.setStatus(206);
                resp.getWriter().write(englishWord + "不在生词本中，无需删除");
            } else {
                //已经存在于生词库中
                DeleteWord(englishWord, userid);
                resp.setStatus(204);
                resp.getWriter().write(englishWord + "删除成功！");
            }

        }

        //注册账号
        else if (reqID.equals("3")) {
            //该用户名是否已被使用过
            if (!isInusers(username)) {
                //未使用过
                Register(username, passwd);
                Users user = ReturnId(username, passwd);
                String result = user.getUsers_id() + "";
                resp.getWriter().write(result);
            } else {
                //使用过
                resp.setStatus(209);
                resp.getWriter().write(username + "已被使用过");
            }

        }

        //同步生词库
        else if (reqID.equals("4")) {
            //生词库中是否存在该单词
            Word example = isInwords(englishWord, userid);
            if (example == null) {
                //不存在
                resp.setStatus(210);
                resp.getWriter().write("修改失败，不存在该单词");
            } else {
                //存在
                resp.setStatus(211);
                int wordid = example.getid();
                UpdateWord(wordid, chineseWord, instance);
                resp.getWriter().write(example.getEnglishWord() + "修改成功!");
            }

        } else if (reqID.equals("5")) {
            resp.setStatus(211);
            Word[] Allwords = JSONArray.parseObject(words, Word[].class);
            int i = Allwords.length;
            DeleteWordAll(userid);
            for (int j = 0; j < i; j++) {
                AddWord(Allwords[j].getEnglishWord(), Allwords[j].getChineseWord(), Allwords[j].getInstance(), userid);
            }
            System.out.println("Success!");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    public void AddWord(String englishWord, String chineseWord, String instance, String userid) {
        int i = Integer.parseInt(userid);
        Object objects1[] = {englishWord, chineseWord, instance};
        Object objects2[] = {userid};
        try {
            new JDBCUtil().execute("insert into words(englishword,chineseword,`instance`) values\n" +
                    "(?,?,?)\n", objects1);
            new JDBCUtil().execute("insert into have\n" +
                    "select ?,id\n" +
                    "from words\n" +
                    "where id>=all(select id from words)", objects2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Word isInwords(String englishWord, String userid) {
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

    public void DeleteWord(String englishWord, String userid) {
        int i = Integer.parseInt(userid);
        Object objects1[] = {i, englishWord};
        Object objects2[] = {englishWord};
        try {
            new JDBCUtil().execute("delete from have\n" +
                    "where wordID in\n" +
                    "(select * from\n" +
                    "(select id from words,have\n" +
                    "where words.id=have.wordID\n" +
                    "and have.userID=?\n" +
                    "and englishWord=?)as temp);\n", objects1);
            new JDBCUtil().execute("delete from words\n" +
                    "where englishWord=?", objects2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void DeleteWordAll(String userid) {
        int i = Integer.parseInt(userid);
        Object objects1[] = {i};
        try {
            new JDBCUtil().execute("delete from words\n" +
                    "where id in\n" +
                    "(select * from\n" +
                    "(select id from words,have\n" +
                    "where words.id=have.wordID\n" +
                    "and have.userID=?\n" +
                    ")as temp);\n", objects1);
            new JDBCUtil().execute("delete from have\n" +
                    "where userid=?", objects1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isInusers(String username) {
        boolean result = true;
        Object[] objects = {username};
        try {
            Users user = new JDBCUtil().selectOne(Users.class, "select * from users where name=?", objects);
            if (user == null) result = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void Register(String username, String passwd) {
        Object objects[] = {username, passwd};
        try {
            new JDBCUtil().execute("insert into users(name,passwd) values (?,?)", objects);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Users ReturnId(String username, String passwd) {
        Users result = null;
        try {
            Object[] objects = {username, passwd};
            result = new JDBCUtil().selectOne(Users.class, "select * from users where name=? and passwd=?", objects);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void UpdateWord(int wordid, String chineseWord, String instance) {
        Object objects1[] = {chineseWord, instance, wordid};
        try {
            new JDBCUtil().execute("update words\n" +
                    "set chineseWord=?,instance=?\n" +
                    "where id=?", objects1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
