package executions;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import newExecution.DtoStartExecution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(name = "send execution servlet", urlPatterns = "/sendExecution")
@MultipartConfig
public class SendExecutionServlet extends HttpServlet {
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
            DtoStartExecution dtoSendExecution = gson.fromJson(json, DtoStartExecution.class);
            DtoStartExecution dtoStartExecution = manager.createNewSimulation(dtoSendExecution, Integer.parseInt(request.getParameter("request id")));
            String jsonResponse = gson.toJson(dtoStartExecution);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
        }
    }
}
