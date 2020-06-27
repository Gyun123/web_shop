package com.applepieme.controller;

import com.applepieme.bean.User;
import com.applepieme.service.FactoryService;
import com.applepieme.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 管理员Controller
 * 处理管理员相关请求
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:51
 */
@WebServlet("*.manage")
public class ManageController extends HttpServlet {
    /**
     * UserService实例对象
     */
    UserService userService = FactoryService.getService(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求路径
        String methodName = req.getServletPath();
        // 从请求路径获取方法名
        methodName = methodName.substring(1, methodName.length() - 7);
        try {
            // 获取封装了前面方法名对应方法的Method对象
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
                    HttpServletResponse.class);
            // 传递实例对象，调用对应方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if ("admin".equals(username) && "admin".equals(password)) {
            List<User> userList = userService.listUsers();
            req.setAttribute("userList", userList);
            req.getRequestDispatcher("WEB-INF/manage/showUser.jsp").forward(req, resp);
        }
    }
}
