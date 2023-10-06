package results;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import newExecution.DtoStartExecution;

import java.io.IOException;

@WebServlet(name = "start execution details servlet", urlPatterns = "/startExecutionDetails")
@MultipartConfig
public class StartExecutionDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            DtoStartExecution dtoStartExecution = manager.createDtoStartExecution(Integer.parseInt(request.getParameter("request id")), Integer.parseInt(request.getParameter("simulation id")));
            String jsonResponse = gson.toJson(dtoStartExecution);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }
}
