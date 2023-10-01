package LoadXML;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import manager.LogicManager;

import java.io.IOException;
@WebServlet(name = "Load xml servlet", urlPatterns = "/loadXml")
@MultipartConfig
public class LoadXMLServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        LogicManager manager = (LogicManager) getServletContext().getAttribute("manager");
        Part xmlPart = req.getPart("xmlFile");

        try {
            manager.ReadXmlFile(xmlPart.getInputStream());
        } catch (Exception e) {
            resp.sendError(400, "Error processing the request: " + e.getMessage());
        }
    }
}

