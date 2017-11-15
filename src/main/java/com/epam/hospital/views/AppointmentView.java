package com.epam.hospital.views;

import com.epam.hospital.model.*;
import com.epam.hospital.service.api.*;
import com.epam.hospital.ui.MainUI;
import com.epam.hospital.ui.Menu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.Editor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UIScope
@SpringView
@SpringComponent
public class AppointmentView extends VerticalLayout implements View {

    private PatientAppointment patientAppointment;
    private Patient patient;

    private Label appointedBy = new Label("Appointed By");
    private TextField fulfilledBy = new TextField("Fulfilled By");
    private Label appointment = new Label("Appointment: ");
    private NativeSelect<String> appointmentSel = new NativeSelect<>();
    private TextField patientField = new TextField("Patient");
    private DateTimeField appointedDate = new DateTimeField("Appointed Date");
    private DateTimeField fulfilledDate = new DateTimeField("Fulfilled Date");
    @Autowired
    PatientService patientService;
    @Autowired
    PatientAppointmentService patientAppointmentService;
    @Autowired
    UserService userService;
    @Autowired
    AppointmentService appointmentService;


    private Button backToPatient = new Button("Back to patient");
    private Button save = new Button("Save");
    private VerticalLayout appointmentData = new VerticalLayout();
    private HorizontalLayout buttons = new HorizontalLayout();

    @Setter
    User user;

    private Menu menu;

    private VerticalLayout components = new VerticalLayout();

    @PostConstruct
    void init() {
        setSpacing(true);
        components.addComponent(appointmentData);
        components.addComponent(buttons);
        appointmentData.addComponent(patientField);
        appointmentData.addComponent(appointment);
        appointmentData.addComponent(appointmentSel);
        appointmentData.addComponent(appointedBy);
        appointmentData.addComponent(appointedBy);
        appointmentData.addComponent(appointedDate);
        appointmentData.addComponent(fulfilledBy);
        appointmentData.addComponent(fulfilledDate);
        buttons.addComponent(save);
        buttons.addComponent(backToPatient);
        appointmentSel.setEmptySelectionAllowed(false);
    }


    @Override
    public void enter(ViewChangeEvent event) {
        if (event.getParameters() != null) {
            if (menu == null) {
                menu = new Menu(user);
            }
            addComponent(menu);
            addComponent(components);

            List<Appointment> allAppointments;
            Set<Role> roles = user.getRoles();
            String userRole = null;
            for (Role role : roles) {
                userRole = role.getName();
            }
            assert userRole != null;
            if (userRole.equals("ROLE_PATIENT")) {
                allAppointments = appointmentService.getAllCommercialAppointments();
            } else {
                allAppointments = appointmentService.getAllAppointments();
            }

            Map<String, Appointment> map = allAppointments.stream().collect(Collectors.toMap(Appointment::getName, appointment1 -> appointment1));
            appointmentSel.setItems(map.keySet());


            if (event.getParameters().contains("new")) {
                String[] split = event.getParameters().split("/");
                patientAppointment = new PatientAppointment();
                Integer id = Integer.valueOf(split[1]);
                patient = patientService.getPatientById(id);
                patientAppointment.setPatient(patient);
                patientAppointment.setAppointedBy(user);
                patientAppointment.setAppointedDate(Timestamp.valueOf(LocalDateTime.now()));
            } else {
                int idAppointment = Integer.parseInt(event.getParameters());
                patientAppointment = patientAppointmentService.getPatientAppointmentById(idAppointment);
                appointmentSel.setValue(patientAppointment.getAppointment().getName());
            }
            patientField.setValue(patient.getName() + " " + patient.getSurname());

            if (patientAppointment.getFulfilledDate() != null) {
                fulfilledDate.setValue(patientAppointment.getFulfilledDate().toLocalDateTime());
            }
            if (patientAppointment.getAppointedDate() != null) {
                appointedDate.setValue(patientAppointment.getAppointedDate().toLocalDateTime());
            }
            if (patientAppointment.getAppointedBy() != null) {
                appointedBy.setValue("Appointed By: " + patientAppointment.getAppointedBy().getUsername());
            }
            if (patientAppointment.getFulfilledBy() != null) {
                fulfilledBy.setValue("Fulfilled By: " + (patientAppointment.getFulfilledBy().getUsername()));
            }


            appointmentSel.addValueChangeListener(valueChangeEvent -> {
                patientAppointment.setAppointment(map.get(valueChangeEvent.getValue()));
            });

            fulfilledBy.addValueChangeListener(changeEvent -> {
                try {
                    User byUsername = userService.findByUsername(changeEvent.getValue());
                    patientAppointment.setFulfilledBy(byUsername);
                } catch (Exception e) {
                    Notification.show("Can't find user with username: " + changeEvent.getValue());
                }
            });


            appointedDate.addValueChangeListener(changeEvent -> {
                patientAppointment.setAppointedDate(Timestamp.valueOf(changeEvent.getValue()));
            });
            fulfilledDate.addValueChangeListener(changeEvent -> {
                patientAppointment.setFulfilledDate(Timestamp.valueOf(changeEvent.getValue()));
            });

            backToPatient.addClickListener(clickEvent -> {
                getUI().getNavigator().navigateTo(MainUI.CARD + "/" + patientAppointment.getPatient().getId());
            });

            save.addClickListener(clickEvent -> {
                patientAppointmentService.saveOrUpdate(patientAppointment);
            });
        }
    }
}


