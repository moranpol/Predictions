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
import java.io.PrintWriter;

@WebServlet(name = "user requests servlet", urlPatterns = "/userRequests")
@MultipartConfig
public class userRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
        Gson gson = new Gson();

        try{
            DtoRequestsInfo dtoRequestsInfo = manager.createDtoRequestsInfoForUser();
            String jsonResponse = gson.toJson(dtoRequestsInfo);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        } catch (Exception ignore){
        }
    }
}
