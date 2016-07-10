package readList.main;


import readList.servlets.AllRequestsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import readList.servlets.ErrorServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        ErrorServlet errorServlet = new ErrorServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allRequestsServlet), "/");
        context.addServlet(new ServletHolder(errorServlet), "/error");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
