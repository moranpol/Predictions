package simulationRequests;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;

import java.io.IOException;

@WebServlet(name = "check servlet", urlPatterns = "/check")
@MultipartConfig
public class check extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
        manager.check();
    }
}
