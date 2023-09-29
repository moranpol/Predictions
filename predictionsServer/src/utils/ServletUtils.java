package utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import manager.LogicManager;

@WebListener
public class ServletUtils implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LogicManager logicManager = new LogicManager();
        sce.getServletContext().setAttribute("manager", logicManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
