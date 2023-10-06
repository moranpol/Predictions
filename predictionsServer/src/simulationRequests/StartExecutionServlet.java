package simulationRequests;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;

@WebServlet(name = "start execution servlet", urlPatterns = "/startExecution")
@MultipartConfig
public class StartExecutionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            manager.startSimulation(Integer.parseInt(request.getParameter("simulation id")), Integer.parseInt(request.getParameter("request id")));

        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
