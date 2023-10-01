package simulationDetails.servlets;

import com.google.gson.Gson;
import details.DtoWorldsList;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet (name = "world info servlet", urlPatterns = "/worldInfo")
@MultipartConfig
public class WorldInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
        Gson gson = new Gson();

        try{
            DtoWorldsList dtoWorldsList = manager.getDtoWorldsList();
            String jsonResponse = gson.toJson(dtoWorldsList);
           try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
           }
        } catch (Exception ignore){
        }
    }
}
