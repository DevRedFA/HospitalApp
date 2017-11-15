package com.epam.hospital.views;


import com.epam.hospital.model.*;
import com.epam.hospital.service.api.*;
import com.epam.hospital.ui.MainUI;
import com.epam.hospital.ui.Menu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.Editor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


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


    private String APPOINTEDBY;
    private String FULFILLEDBY;
    private String APPOINTMENT;
    private String PATIENT;
    private String APPDATE;
    private String FULFULLDATE;
    private String BACKTOPATIENT;
    private String SAVE;
    private String NOTFOUND;


    @Setter
    User user;

    private Menu menu;

    private VerticalLayout components = new VerticalLayout();

    @PostConstruct
    void init() {
        initString();

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
        appointmentData.addComponent(fulfilledBy);
        appointmentData.addComponent(fulfilledDate);
        buttons.addComponent(save);
        buttons.addComponent(backToPatient);
        appointmentSel.setEmptySelectionAllowed(false);
        appointedDate.setDateFormat("MM/dd/yyyy HH:mm:ss");
        fulfilledDate.setDateFormat("MM/dd/yyyy HH:mm:ss");
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
                appointedBy.setValue(APPOINTEDBY + " "+ patientAppointment.getAppointedBy().getUsername());
            }
            if (patientAppointment.getFulfilledBy() != null) {
                fulfilledBy.setValue(FULFILLEDBY +" "+ (patientAppointment.getFulfilledBy().getUsername()));
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
                try {
                    getUI().getNavigator().navigateTo(MainUI.CARD + "/" + patientAppointment.getPatient().getId());
                } catch (Exception e) {
                    logger.error(e);
                }
            });

            save.addClickListener(clickEvent -> {
                patientAppointmentService.saveOrUpdate(patientAppointment);
            });
        }
    }

    private void initString() {
        Locale locale = VaadinSession.getCurrent().getLocale();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("components", locale);

        APPOINTEDBY = resourceBundle.getString("card.grid.appointedby");
        FULFILLEDBY = resourceBundle.getString("card.grid.fulfilledby");
        APPOINTMENT = resourceBundle.getString("card.grid.appointment");
        PATIENT = resourceBundle.getString("appview.patient");
        APPDATE = resourceBundle.getString("card.grid.appointmentdate");
        FULFULLDATE = resourceBundle.getString("card.grid.fulfilldate");
        BACKTOPATIENT = resourceBundle.getString("appview.backtopatient");
        SAVE = resourceBundle.getString("appview.save");
        NOTFOUND = resourceBundle.getString("card.usernotfound");
    }

}


