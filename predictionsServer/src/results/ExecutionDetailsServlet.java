package results;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import results.simulationRunningDetails.DtoSimulationRunningDetails;

import java.io.IOException;

@WebServlet(name = "execution details servlet", urlPatterns = "/executionDetails")
@MultipartConfig
public class ExecutionDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            DtoSimulationRunningDetails dtoSimulationRunningDetails = manager.createDtoSimulationRunningDetails(Integer.parseInt(request.getParameter("request id")), Integer.parseInt(request.getParameter("simulation id")));
            String jsonResponse = gson.toJson(dtoSimulationRunningDetails);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
