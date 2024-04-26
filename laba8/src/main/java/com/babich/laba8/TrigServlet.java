package com.babich.laba8;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/trig")
public class TrigServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result", "0");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String function = request.getParameter("function");
        double value = Double.parseDouble(request.getParameter("value"));
        int precision = Integer.parseInt(request.getParameter("precision"));
        String format = "%." + precision + "f";

        double result = switch (function) {
            case "sin" -> Math.sin(value);
            case "cos" -> Math.cos(value);
            case "tan" -> Math.tan(value);
            default -> 0;
        };

        request.setAttribute("radians", String.format(format, Math.toRadians(value)));
        request.setAttribute("degrees", String.format(format, value));
        request.setAttribute("result", String.format(format, result));
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}