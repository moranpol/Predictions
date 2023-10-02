package ThreadInfoServlet;

import com.google.gson.Gson;
import details.DtoWorldsList;
import header.DtoSimulationQueue;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "thread info servlet", urlPatterns = "/threadInfo")
@MultipartConfig
public class ThreadInfoServlet  extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
        Gson gson = new Gson();

        try{
            DtoSimulationQueue dtoSimulationQueue = manager.createDtoSimulationQueue();
            String jsonResponse = gson.toJson(dtoSimulationQueue);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        } catch (Exception ignore){
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");

        try (PrintWriter out = response.getWriter()) {
            Integer threadNum = Integer.parseInt(request.getParameter("threadNamber"));
            manager.changeThreadPoolSize(threadNum);

        } catch (Exception ignore) {

        }
    }
}
