package com.epam.hospital.views;


import com.epam.hospital.model.*;
import com.epam.hospital.service.api.*;
import com.epam.hospital.ui.MainUI;
import com.epam.hospital.ui.Menu;
import com.epam.hospital.util.LabelsHolder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.hospital.util.LabelsHolder.*;


import static com.epam.hospital.util.Utils.getRole;

@UIScope
@SpringView
public class AppointmentView extends VerticalLayout implements View {

    private PatientAppointment patientAppointment;
    private Patient patient;

    private Label appointedBy;
    private TextField fulfilledBy;
    private Label appointment;
    private NativeSelect<String> appointmentSel = new NativeSelect<>();
    private TextField patientField;
    private DateTimeField appointedDate;
    private DateTimeField fulfilledDate;

    private Logger logger = Logger.getLogger(DiagnosisView.class);

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

        appointedBy = new Label(APPOINTEDBY);
        fulfilledBy = new TextField(FULFILLEDBY);
        appointment = new Label(APPOINTMENT);
        patientField = new TextField(PATIENT);
        appointedDate = new DateTimeField(APPDATE);
        fulfilledDate = new DateTimeField(FULFULLDATE);
        backToPatient = new Button(BACKTOPATIENT);
        save = new Button(SAVE);


        setSpacing(true);
        components.addComponent(appointmentData);
        components.addComponent(buttons);
        appointmentData.addComponent(patientField);
        appointmentData.addComponent(appointment);
        appointmentData.addComponent(appointmentSel);
        appointmentData.addComponent(appointedBy);
        appointmentData.addComponent(appointedBy);
        appointmentData.addComponent(appointedDate);
//        appointmentData.addComponent(fulfilledBy);
//        appointmentData.addComponent(fulfilledDate);
        buttons.addComponent(save);
        buttons.addComponent(backToPatient);
        appointmentSel.setEmptySelectionAllowed(false);
        appointedDate.setDateFormat(DATETIMEFORMAT);
        fulfilledDate.setDateFormat(DATETIMEFORMAT);
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
            String userRole = getRole(user);
            if (userRole.equals("ROLE_PATIENT")) {
                allAppointments = appointmentService.getAllCommercialAppointments();
                appointedBy.setEnabled(false);
                appointedDate.setEnabled(false);
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
                appointedBy.setValue(APPOINTEDBY + " " + patientAppointment.getAppointedBy().getUsername());
            }
            if (patientAppointment.getFulfilledBy() != null) {
                fulfilledBy.setValue(FULFILLEDBY + " " + (patientAppointment.getFulfilledBy().getUsername()));
            }


            appointmentSel.addValueChangeListener(valueChangeEvent -> {
                patientAppointment.setAppointment(map.get(valueChangeEvent.getValue()));
            });

            fulfilledBy.addValueChangeListener(changeEvent -> {
                try {
                    User byUsername = userService.findByUsername(changeEvent.getValue());
                    patientAppointment.setFulfilledBy(byUsername);
                } catch (Exception e) {
                    Notification.show(NOTFOUND + changeEvent.getValue());
                }
            });


            appointedDate.addValueChangeListener(changeEvent -> {
//                if (changeEvent.getValue().compareTo(LocalDateTime.now()) < 0) {
//                    Notification.show("Put correct date and time");
//                } else {
                patientAppointment.setAppointedDate(Timestamp.valueOf(changeEvent.getValue()));
//                }
            });
            fulfilledDate.addValueChangeListener(changeEvent -> {
//                if (changeEvent.getValue().compareTo(LocalDateTime.now()) < 0) {
//                    Notification.show("Put correct date and time");
//                } else {
                patientAppointment.setFulfilledDate(Timestamp.valueOf(changeEvent.getValue()));
//                }
            });

            backToPatient.addClickListener(clickEvent -> {
                try {
                    getUI().getNavigator().navigateTo(MainUI.CARD + "/" + patientAppointment.getPatient().getId());
                } catch (Exception e) {
                    logger.error(e);
                }
            });

            save.addClickListener(clickEvent -> {
                patientAppointmentService.saveOrUpdate(patientAppointment);
                getUI().getNavigator().navigateTo(MainUI.CARD + "/" + patientAppointment.getPatient().getId());
            });
        }
    }
}


