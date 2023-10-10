package results;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import results.simulationFailed.DtoSimulationFailedDetails;

import java.io.IOException;

@WebServlet(name = "simulation failed details servlet", urlPatterns = "/simulationFailedDetails")
@MultipartConfig
public class SimulationFailedDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            DtoSimulationFailedDetails dtoSimulationFailedDetails = manager.createDtoSimulationFailedDetails(Integer.parseInt(request.getParameter("request id")), Integer.parseInt(request.getParameter("simulation id")));
            String jsonResponse = gson.toJson(dtoSimulationFailedDetails);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
