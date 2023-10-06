package executions;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import newExecution.DtoNewExecution;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "new execution info servlet", urlPatterns = "/newExecutionInfo")
@MultipartConfig
public class NewExecutionInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            response.setContentType("application/json");
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            DtoNewExecution dtoNewExecution = manager.createDtoNewExecution(Integer.parseInt(request.getParameter("request id")));
            String jsonResponse = gson.toJson(dtoNewExecution);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
