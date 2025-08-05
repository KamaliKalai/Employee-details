package com.app;

import com.app.DbUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class UpdateEmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String department = req.getParameter("department");

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE employees SET name=?, email=?, department=? WHERE user_id=?"
            );
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, department);
            ps.setInt(4, userId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                req.setAttribute("message", "Employee details updated successfully.");
            } else {
                req.setAttribute("message", "No employee record found to update.");
            }
            req.getRequestDispatcher("success.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}