package simulationRequests;

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
        response.setContentType("application/json");
        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
        Gson gson = new Gson();

        try{
            DtoRequestsInfo dtoRequestsInfo = manager.createDtoRequestsInfoForManager();
            String jsonResponse = gson.toJson(dtoRequestsInfo);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        } catch (Exception ignore){
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");

        try (PrintWriter out = response.getWriter()) {
            String requestStatus = request.getParameter("requestStatus");
            Integer requestId = Integer.parseInt(request.getParameter("requestId")); /// exception
            manager.updateRequestStatus(requestId,requestStatus);

        } catch (Exception ignore) {

        }
    }
}