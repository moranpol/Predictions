package results;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import results.simulations.DtoSimulationsInfoList;

import java.io.IOException;

@WebServlet(name = "simulations info list user servlet", urlPatterns = "/simulationsInfoUser")
@MultipartConfig
public class SimulationsInfoListUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            DtoSimulationsInfoList dtoSimulationsInfoList = manager.createDtoSimulationsInfoListForUser(request.getParameter("username"));
            String jsonResponse = gson.toJson(dtoSimulationsInfoList);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
