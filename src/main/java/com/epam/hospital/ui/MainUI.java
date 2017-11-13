package com.epam.hospital.ui;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.api.UserService;
import com.epam.hospital.views.PatientCardView;
import com.epam.hospital.views.PatientsView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import java.util.Set;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@SpringUI
public class MainUI extends UI {

    public static final String VAADINVIEW = "vaadin";
    public static final String PATIENTVIEW = "patients";
    public static final String CARD = "card";
    public Navigator navigator;

    @Autowired
    PatientsView patientsView;

    @Autowired
    UserService userService;

    @Autowired
    PatientCardView patientCardView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
// Single menu for all views. Doesn't show in pages.
//        final HorizontalLayout menu = new HorizontalLayout();
//        Label label = new Label("Singed in: " + userName);
//        Button buttonLogout = new Button("sign out",
//                new ExternalResource("/login?logout"));
//        menu.setSizeFull();
//        menu.addComponent(label);
//        menu.addComponent(buttonLogout);
//        layout.addComponent(menu);
//        layout.addComponent(buttonLogout);
//        layout.setComponentAlignment(menu, Alignment.MIDDLE_RIGHT);
//
        final String userName = vaadinRequest.getRemoteUser();
        User user = userService.findByUsername(userName);
        Set<Role> roles = user.getRoles();
        if (roles.size() > 1) {
            throw new RuntimeException("User has more then one role");
        }
        String userRole = null;
        for (Role role : roles) {
            userRole = role.getName();
        }
        setContent(layout);
        ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        switch (userRole) {
            case "ROLE_PATIENT":
                //don't work
                Integer id = user.getPatient().getId();
                navigator.addView("/" + String.valueOf(id), patientCardView);
                break;
            case "ROLE_DOCTOR":
                navigator.addView(CARD, patientCardView);
                navigator.addView("", patientsView);
                break;
            case "ROLE_NURSE":
                navigator.addView(CARD, patientCardView);
                navigator.addView("", patientsView);
                break;
        }
    }


    @WebServlet(value = {"/vaadin/*", "/VAADIN/*", "/card/*", "/CARD/*", "/patients/*", "/PATIENTS/*", "/help/*", "/HELP/*"}, name = "MyUIServlet", asyncSupported = true)
    public static class MyUIServlet extends SpringVaadinServlet {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }

}
