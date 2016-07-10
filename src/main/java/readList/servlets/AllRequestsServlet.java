package readList.servlets;


import readList.templater.PageGenerator;

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

        response.getWriter().println(PageGenerator.instance().getPage("page.html", new HashMap<>()));

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
            response.sendRedirect("/error");
        } else {
            Map<String, Object> pageVariables = createPageVariablesMap(request);
            response.getWriter().println(PageGenerator.instance().getPage("out.html", pageVariables));
        }
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        File file = new File(request.getParameter("message"));
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            list.add(reader.readLine());
        }
        pageVariables.put("lines", list);
        return pageVariables;
    }
}
