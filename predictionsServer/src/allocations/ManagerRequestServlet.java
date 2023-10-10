package allocations;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.LogicManager;
import requests.DtoNewRequest;
import requests.DtoRequestsInfo;

import java.io.*;

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

@WebServlet(name = "manager requests servlet", urlPatterns = "/managerRequests")
@MultipartConfig
public class ManagerRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            response.setContentType("application/json");
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            Gson gson = new Gson();
            DtoRequestsInfo dtoRequestsInfo = manager.createDtoRequestsInfoForManager();
            String jsonResponse = gson.toJson(dtoRequestsInfo);
            response.getWriter().print(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/plain;charset=UTF-8");
            LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
            String requestStatus = request.getParameter("requestStatus");
            Integer requestId = Integer.parseInt(request.getParameter("requestId"));
            manager.updateRequestStatus(requestId, requestStatus);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
        }
    }
}