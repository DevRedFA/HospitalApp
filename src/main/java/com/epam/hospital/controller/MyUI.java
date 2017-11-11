package com.epam.hospital.controller;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final String userName = vaadinRequest.getRemoteUser();
        final VerticalLayout root = new VerticalLayout();

        Label label = new Label("Hello " + userName);

        Link linkLogout = new Link("Log out!",
                new ExternalResource("/login?logout"));

        root.addComponent(label);
        root.addComponent(linkLogout);

        this.setContent(root);

    }

    /*
    * /login path doesnt work - http://javainside.ru/spring-security-spring-mvc-spring-jpa-vaadin-chast-1/
    * /VAADIN/* must be indicated necessarily
    * */

    @WebServlet(value = {"/vaadin/*", "/VAADIN/*"}, name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
