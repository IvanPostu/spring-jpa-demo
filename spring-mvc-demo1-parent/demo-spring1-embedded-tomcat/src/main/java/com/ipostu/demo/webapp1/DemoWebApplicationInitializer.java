package com.ipostu.demo.webapp1;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.logging.Logger;

public class DemoWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger LOG = Logger.getLogger(DemoWebApplicationInitializer.class.getName());


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        LOG.info("Executing DemoWebApplicationInitializer::onStartup");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/app/*");
    }
}
