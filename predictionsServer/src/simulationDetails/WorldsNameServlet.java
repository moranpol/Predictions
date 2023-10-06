package simulationDetails;

import com.google.gson.Gson;
import details.DtoWorldsList;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;

import java.io.IOException;

@WebServlet (name = "worlds name servlet", urlPatterns = "/worldsName")
@MultipartConfig
public class WorldsNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            response.setContentType("application/json");
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();

            DtoWorldsList dtoWorldsList = manager.getDtoWorldsList();
            String jsonResponse = gson.toJson(dtoWorldsList);
           response.getWriter().print(jsonResponse);
           response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
