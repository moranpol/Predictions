package simulationRequests;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import requests.DtoNewRequest;

import java.io.IOException;

@WebServlet(name = "new request servlet", urlPatterns = "/newRequest")
@MultipartConfig
public class NewRequestServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            assert request.getReader() != null;
            DtoNewRequest dtoNewRequest = gson.fromJson(request.getReader(), DtoNewRequest.class);
            manager.createSimulationRequest(dtoNewRequest);
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
