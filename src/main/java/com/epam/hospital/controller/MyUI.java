package com.epam.hospital.controller;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
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

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        final VerticalLayout verticalLayout = new VerticalLayout();
        final HorizontalLayout loginHorizontalLayoutLayout = new HorizontalLayout();
        final HorizontalLayout passwordHorizontalLayoutLayout = new HorizontalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Login:"); // use ResourceBundleService for diff languages

        final TextField pass = new TextField();
        pass.setCaption("Password:");

        Button button = new Button("Sign in");
        button.addClickListener(e -> {
            Notification.show("Hello " + name.getValue().toString(), Notification.Type.TRAY_NOTIFICATION);
        });

        loginHorizontalLayoutLayout.addComponent(name);
        passwordHorizontalLayoutLayout.addComponent(pass);
        
        verticalLayout.addComponents(loginHorizontalLayoutLayout, passwordHorizontalLayoutLayout, button);

        root.addComponent(verticalLayout);
        root.setComponentAlignment(verticalLayout, Alignment.MIDDLE_CENTER);
        
        this.setContent(root);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
