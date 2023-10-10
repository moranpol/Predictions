package simulationRequests;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;

import java.io.IOException;

@WebServlet(name = "start execution servlet", urlPatterns = "/startExecution")
@MultipartConfig
public class StartExecutionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            manager.startSimulation(Integer.parseInt(request.getParameter("simulation id")), Integer.parseInt(request.getParameter("request id")));

        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
