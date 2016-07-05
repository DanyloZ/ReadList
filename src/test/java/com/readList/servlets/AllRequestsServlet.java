package com.readList.servlets;


import com.readList.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllRequestsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        StringBuilder page = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader( "templates/page.html"));
        while(reader.ready()) {
            page.append(reader.readLine());
            page.append("\n");
        }

        response.getWriter().println(page.toString());

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {


        String message = request.getParameter("message");

        response.setContentType("text/html;charset=utf-8");

        if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (!new File(request.getParameter("message")).exists()) {
            StringBuilder page = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader("templates/error.html"));
            while (reader.ready()) {
                page.append(reader.readLine());
                page.append("\n");
            }
            response.getWriter().println(page.toString());
        }
        else {
            Map<String, Object> pageVariables = createPageVariablesMap(request);
            response.getWriter().println(PageGenerator.instance().getPage("out.html", pageVariables));
        }
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) throws FileNotFoundException {
        Map<String, Object> pageVariables = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        File file = new File(request.getParameter("message"));
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            while(reader.ready()) {
                list.add(reader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        pageVariables.put("lines", list);
        return pageVariables;
    }
}
