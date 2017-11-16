package com.epam.hospital.ui;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.api.UserService;
import com.epam.hospital.views.AppointmentView;
import com.epam.hospital.views.DiagnosisView;
import com.epam.hospital.views.PatientCardView;
import com.epam.hospital.views.PatientsView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import java.util.Locale;
import java.util.Set;

import static com.epam.hospital.util.Utils.getRole;

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

    public static final String CARD = "card";
    public static final String DIAGNOSIS = "diagnosis";
    public static final String APPOINTMENT = "appointment";

    public Navigator navigator;

    @Autowired
    PatientsView patientsView;

    @Autowired
    UserService userService;

    @Autowired
    PatientCardView patientCardView;

    @Autowired
    DiagnosisView diagnosisView;

    @Autowired
    AppointmentView appointmentView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        final String userName = vaadinRequest.getRemoteUser();
        User user = userService.findByUsername(userName);
        String userRole = getRole(user);
        setContent(layout);
        ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        patientsView.setUser(user);
        patientCardView.setUser(user);
        diagnosisView.setUser(user);
        appointmentView.setUser(user);
        switch (userRole) {
            case "ROLE_PATIENT":
                navigator.addView(CARD, patientCardView);
                navigator.addView(APPOINTMENT, appointmentView);
                if (user.getPatient() == null) {
                    getUI().getNavigator().navigateTo(CARD + "/" + "new");
                } else {
                    Integer id = user.getPatient().getId();
                    getUI().getNavigator().navigateTo(CARD + "/" + String.valueOf(id));
                }
                break;
            case "ROLE_DOCTOR":
                navigator.addView(CARD, patientCardView);
                navigator.addView("", patientsView);
                navigator.addView(DIAGNOSIS, diagnosisView);
                navigator.addView(APPOINTMENT, appointmentView);
                break;
            case "ROLE_NURSE":
                navigator.addView(CARD, patientCardView);
                navigator.addView("", patientsView);
                break;
        }
    }


    @WebServlet(value = {"/hospitalApp/*", "/VAADIN/*"}, name = "MyUIServlet", asyncSupported = true)
    public static class MyUIServlet extends SpringVaadinServlet {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }

}
