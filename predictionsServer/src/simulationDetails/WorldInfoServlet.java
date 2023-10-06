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

@WebServlet(name = "world info servlet", urlPatterns = "/worldInfo")
@MultipartConfig
public class WorldInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            response.setContentType("application/json");
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            WorldManager worldManager = manager.getWorldManager(request.getParameter("world name"));
            Gson gson = new Gson();

            DtoWorldInfo dtoWorldInfo = worldManager.getDtoWorldInfo();
            String jsonResponse = gson.toJson(dtoWorldInfo);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception ignore){
        }
    }
}
