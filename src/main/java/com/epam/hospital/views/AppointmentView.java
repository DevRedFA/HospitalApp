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
import java.util.Set;

@UIScope
@SpringView
@SpringComponent
public class AppointmentView extends VerticalLayout implements View {

    private PatientAppointment patientAppointment;
    private Patient patient;

    private TextField id = new TextField("id");
    private TextField appointedBy = new TextField("Appointed By");
    private TextField fulfilledBy = new TextField("Fulfilled By");
    private TextField appointment = new TextField("Appointment");
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
        appointmentData.addComponent(id);
        appointmentData.addComponent(patientField);
        appointmentData.addComponent(appointment);
        appointmentData.addComponent(appointedBy);
        appointmentData.addComponent(appointedBy);
        appointmentData.addComponent(appointedDate);
        appointmentData.addComponent(fulfilledBy);
        appointmentData.addComponent(fulfilledDate);
        buttons.addComponent(save);
        buttons.addComponent(backToPatient);
    }


    @Override
    public void enter(ViewChangeEvent event) {
        if (event.getParameters() != null) {
            if (menu == null) {
                menu = new Menu(user);
            }
            addComponent(menu);
            addComponent(components);

            if (event.getParameters().contains("new")) {
                String[] split = event.getParameters().split("/");
                patientAppointment = new PatientAppointment();
                Integer id = Integer.valueOf(split[1]);
                patient = patientService.getPatientById(id);
                patientAppointment.setPatient(patient);
            } else {
                int idAppointment = Integer.parseInt(event.getParameters());
                patientAppointment = patientAppointmentService.getPatientAppointmentById(idAppointment);
                id.setValue(String.valueOf(patientAppointment.getId()));
                appointment.setValue(patientAppointment.getAppointment().getName());
                if (patientAppointment.getFulfilledDate() != null) {
                    fulfilledDate.setValue(patientAppointment.getFulfilledDate().toLocalDateTime());
                }
                if (patientAppointment.getAppointedDate() != null) {
                    appointedDate.setValue(patientAppointment.getAppointedDate().toLocalDateTime());
                }
                if (patientAppointment.getAppointedBy() != null) {
                    appointedBy.setValue(patientAppointment.getAppointedBy().getUsername());
                }
                if (patientAppointment.getFulfilledBy() != null) {
                    fulfilledBy.setValue((patientAppointment.getFulfilledBy().getUsername()));
                }
            }

            id.addValueChangeListener(valueChangeEvent -> {
                try {
                    patientAppointment.setId(Integer.parseInt(valueChangeEvent.getValue()));
//                    patientDiagnosesService.saveOrUpdate(patientDiagnosis);
                } catch (NumberFormatException e) {
                    Notification.show("Enter correct number");
                }

            });

            appointment.addValueChangeListener(changeEvent -> {
                Appointment appointment = appointmentService.getAppointmentById(1);
                patientAppointment.setAppointment(appointment);
            });

            fulfilledBy.addValueChangeListener(changeEvent -> {
                User byUsername = userService.findByUsername(changeEvent.getValue());
                patientAppointment.setFulfilledBy(byUsername);
//                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });

            appointedBy.addValueChangeListener(changeEvent -> {
                User byUsername = userService.findByUsername(changeEvent.getValue());
                patientAppointment.setAppointedBy(byUsername);
//                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });

            appointedDate.addValueChangeListener(changeEvent -> {
                patientAppointment.setAppointedDate(Timestamp.valueOf(changeEvent.getValue()));
//                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });
            fulfilledDate.addValueChangeListener(changeEvent -> {
                patientAppointment.setFulfilledDate(Timestamp.valueOf(changeEvent.getValue()));
//                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
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


