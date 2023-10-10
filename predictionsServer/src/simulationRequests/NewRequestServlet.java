package simulationRequests;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import requests.DtoNewRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(name = "new request servlet", urlPatterns = "/newRequest")
@MultipartConfig
public class NewRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            StringBuilder requestBody = new StringBuilder();
            String line;

            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            String json = requestBody.toString();
            DtoNewRequest dtoNewRequest = gson.fromJson(json, DtoNewRequest.class);
            manager.createSimulationRequest(dtoNewRequest);
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
        }
    }
}
