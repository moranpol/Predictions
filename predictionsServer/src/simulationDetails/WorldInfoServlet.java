package simulationDetails;

import com.google.gson.Gson;
import details.DtoWorldInfo;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import manager.WorldManager;

import java.io.IOException;

@WebServlet(name = "world info servlet", urlPatterns = "/worldInfo")
@MultipartConfig
public class WorldInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            response.setContentType("application/json");
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            WorldManager worldManager = manager.getWorldManager(request.getParameter("world name"));
            Gson gson = new Gson();

            DtoWorldInfo dtoWorldInfo = worldManager.getDtoWorldInfo();
            String jsonResponse = gson.toJson(dtoWorldInfo);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
