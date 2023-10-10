package simulationRequests;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import requests.DtoRequestsInfo;

import java.io.IOException;

@WebServlet(name = "user requests servlet", urlPatterns = "/userRequests")
@MultipartConfig
public class UserRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            response.setContentType("application/json");
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            DtoRequestsInfo dtoRequestsInfo = manager.createDtoRequestsInfoForUser(request.getParameter("username"));
            String jsonResponse = gson.toJson(dtoRequestsInfo);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
