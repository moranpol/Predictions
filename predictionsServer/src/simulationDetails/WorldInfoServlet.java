package simulationDetails;

import com.google.gson.Gson;
import details.DtoWorldInfo;
import details.DtoWorldsList;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import manager.WorldManager;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "world info servlet", urlPatterns = "/worldInfo")
@MultipartConfig
public class WorldInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
        WorldManager worldManager = manager.getWorldManager(request.getParameter("world name"));
        Gson gson = new Gson();

        try{
            DtoWorldInfo dtoWorldInfo = worldManager.getDtoWorldInfo();
            String jsonResponse = gson.toJson(dtoWorldInfo);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        } catch (Exception ignore){
        }
    }
}
