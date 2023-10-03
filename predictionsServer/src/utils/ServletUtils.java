package utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import manager.LogicManager;
import manager.UserManager;

@WebListener
public class ServletUtils implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LogicManager logicManager = new LogicManager();
        UserManager userManager = new UserManager();
        sce.getServletContext().setAttribute("manager", logicManager);
        sce.getServletContext().setAttribute("userManager", userManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
