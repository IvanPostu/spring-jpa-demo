package com.ipostu.demo.webapp1;


import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.juli.OneLineFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class App {

    private static final Logger LOG = Logger.getLogger(App.class.getName());


    public static void main(String[] args) throws LifecycleException {
        Logger.getLogger("").getHandlers()[0].setFormatter(new OneLineFormatter());
        Tomcat tomcat = new Tomcat();

        String absolutePath = new File(".").getAbsolutePath();
        String catalinaBase = System.getProperty("catalina.base");
        String catalinaHome = System.getProperty("catalina.home");
        String userDir = System.getProperty("user.dir");

        LOG.info("[Before] Catalina base: " + catalinaBase);
        LOG.info("[Before] Catalina home: " + catalinaHome);
        LOG.info("[Before] User dir: " + userDir);
        LOG.info("[Before] Absolute path: " + absolutePath);

        Connector connector = new Connector();
        connector.setPort(8080);

        tomcat.getService().addConnector(connector);

        absolutePath = new File(".").getAbsolutePath();
        catalinaBase = System.getProperty("catalina.base");
        catalinaHome = System.getProperty("catalina.home");
        userDir = System.getProperty("user.dir");

        LOG.info("[After] Catalina base: " + catalinaBase);
        LOG.info("[After] Catalina home: " + catalinaHome);
        LOG.info("[After] User dir: " + userDir);
        LOG.info("[After] Absolute path: " + absolutePath);

        createFolderRecursively(Paths.get(catalinaBase, "webapps", "appDocBase"));

        Context context = tomcat.addContext("/app", "appDocBase");

        // serves static files, e.g. html, images, css ... path: ./tomcat.8080/webapps/appDocBase/index.html
        // accessible via: localhost;8080/app/index.html
        tomcat.addServlet("/app", "servlet01", new DefaultServlet());
        context.addServletMappingDecoded("/", "servlet01");

        tomcat.addWebapp("/springapp", ".");

        LOG.info("Starting tomcat server");

        tomcat.start();
    }

    private static void createFolderRecursively(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
