package com.app;

import com.app.DbUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password); // In production, hash passwords!
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                req.getSession().setAttribute("userId", rs.getInt("id"));
                resp.sendRedirect("employee.jsp");
            } else {
                req.setAttribute("error", "Invalid login");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
