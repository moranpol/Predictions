package results;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;

import java.io.IOException;

@WebServlet(name = "pause simulation servlet", urlPatterns = "/pauseSimulation")
@MultipartConfig
public class PauseSimulationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            manager.pauseSimulation(Integer.parseInt(request.getParameter("request id")), Integer.parseInt(request.getParameter("simulation id")));
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
