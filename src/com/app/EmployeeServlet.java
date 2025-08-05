package com.app;

import com.app.DbUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class EmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String Role = req.getParameter("Role");

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO employees(user_id, name, email, Role) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, Role);
            ps.executeUpdate();

            req.setAttribute("message", "Employee details saved successfully.");
            req.getRequestDispatcher("success.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
