package com.epam.hospital.ui;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.views.PatientCardView;
import com.epam.hospital.views.PatientsView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@SpringUI
@Component
@Setter
public class MainUI extends UI {


    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }


    public static final String VAADINVIEW = "vaadin";
    public Navigator navigator;
    public static final String PATIENTVIEW = "patients";
    public static final String CARD = "card";

    @Autowired
    PatientService patientService;
    @Autowired
    PatientsView patientsView;
    @Autowired
    PatientCardView patientCardView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout root = new VerticalLayout();
        final HorizontalLayout menu = new HorizontalLayout();
        final String userName = vaadinRequest.getRemoteUser();
        Label label = new Label("Singed in: " + userName);
        Button buttonLogout = new Button("sign out",
                new ExternalResource("/login?logout"));
        menu.setSizeFull();
        menu.addComponent(label);
        menu.addComponent(buttonLogout);

        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(root);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView(PATIENTVIEW, patientsView);
        navigator.addView(CARD, patientCardView);
        root.addComponent(menu);
        root.addComponent(new PatientsView());
        this.setContent(root);
    }


    /*
    * /login path doesnt work - http://javainside.ru/spring-security-spring-mvc-spring-jpa-vaadin-chast-1/
    * /VAADIN/* must be indicated necessarily
    * */

    @WebServlet(value = {"/vaadin/*", "/VAADIN/*", "/patients/*", "/PATIENTS/*", "/help/*", "/HELP/*"}, name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
