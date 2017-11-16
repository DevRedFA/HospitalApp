package com.epam.hospital.views;

import com.epam.hospital.model.*;
import com.epam.hospital.service.api.PatientAppointmentService;
import com.epam.hospital.service.api.PatientDiagnosesService;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.api.UserService;
import com.epam.hospital.ui.MainUI;
import com.epam.hospital.ui.Menu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import static com.epam.hospital.util.Utils.formatTime;
import static com.epam.hospital.util.Utils.getRole;

@UIScope
@SpringView
public class PatientCardView extends VerticalLayout implements View {

    private Patient patient;
    private Label diagnoses;
    private Label appointments;
    private TextField username;
    private TextField name;
    private TextField surname;
    private DateField birthday;


    @Autowired
    PatientService patientService;
    @Autowired
    PatientDiagnosesService patientDiagnosesService;
    @Autowired
    PatientAppointmentService patientAppointmentService;
    @Autowired
    UserService userService;

    private Grid<PatientDiagnosis> diagnosesGrid = new Grid<>(PatientDiagnosis.class);
    private Grid<PatientAppointment> appointmentsGrid = new Grid<>(PatientAppointment.class);
    private Button newDiagnosis;
    private Button savePatientData;
    private Button newAppointment;
    private Button discharge;
    private Button fulfil;
    private Button changeDiagnosis;
    private Button changeAppointment;


    private String DIAGNOSES;
    private String APPOINTMENTS;
    private String USERNAME;
    private String NAME;
    private String SURNAME;
    private String BIRTHDATE;
    private String NEWDIAGNOSIS;
    private String SAVEPATIENT;
    private String NEWAPPOINT;
    private String DISCHARGE;
    private String FULFILL;
    private String CHANGEDIAGNOSIS;
    private String CHANGEAPPOINT;
    private String USERNOTFOUND;
    private String APPTOCHANGE;
    private String APPTOFULFILL;
    private String APPTODISCHARGE;
    private String ID;
    private String DIAGNOSISDEATE;
    private String DIAGNOSIS;
    private String DIAGNOSEDBY;
    private String APPOINTMENT;
    private String APPOINTMENTTYPE;
    private String APPOINTMENTDATE;
    private String FULFILLDATE;
    private String FULFILLBY;
    private String DETAILS;
    private String APPOINTEDBY;
    private String INTREATMENT;


    private Logger logger = Logger.getLogger(PatientCardView.class);
    private HorizontalLayout patientData = new HorizontalLayout();
    private HorizontalLayout diagnosisButtons = new HorizontalLayout();
    private HorizontalLayout appointmentsButtons = new HorizontalLayout();
    @Setter
    User user;

    private Menu menu;

    private VerticalLayout components = new VerticalLayout();

    @PostConstruct
    void init() {
        initStrings();

        appointments = new Label(APPOINTMENTS);
        username = new TextField(USERNAME);
        name = new TextField(NAME);
        surname = new TextField(SURNAME);
        birthday = new DateField(BIRTHDATE);

        diagnoses = new Label(DIAGNOSES);
        newDiagnosis = new Button(NEWDIAGNOSIS);
        savePatientData = new Button(SAVEPATIENT);
        newAppointment = new Button(NEWAPPOINT);
        discharge = new Button(DISCHARGE);
        fulfil = new Button(FULFILL);
        changeDiagnosis = new Button(CHANGEDIAGNOSIS);
        changeAppointment = new Button(CHANGEAPPOINT);


        setSpacing(true);
        patientData.addComponent(username);
        patientData.addComponent(name);
        patientData.addComponent(surname);
        patientData.addComponent(birthday);
        patientData.addComponent(savePatientData);
        patientData.setComponentAlignment(savePatientData, Alignment.BOTTOM_RIGHT);
        savePatientData.setWidth("100%");
        components.addComponent(patientData);
        components.addComponent(diagnoses);
        diagnoses.setContentMode(ContentMode.PREFORMATTED);
        appointments.setContentMode(ContentMode.PREFORMATTED);
        appointmentsButtons.addComponent(appointments);
        appointmentsButtons.addComponent(newAppointment);
        appointmentsButtons.addComponent(changeAppointment);
        appointmentsButtons.addComponent(fulfil);
        appointmentsButtons.setComponentAlignment(appointments, Alignment.MIDDLE_LEFT);
        diagnosisButtons.addComponent(diagnoses);
        diagnosisButtons.addComponent(newDiagnosis);
        diagnosisButtons.addComponent(changeDiagnosis);
        diagnosisButtons.addComponent(discharge);
        diagnosisButtons.setComponentAlignment(diagnoses, Alignment.MIDDLE_LEFT);
        components.addComponent(diagnosisButtons);
        components.addComponent(diagnosesGrid);
        components.addComponent(appointmentsButtons);
        components.addComponent(appointmentsGrid);

    }


    @Override
    public void enter(ViewChangeEvent event) {
        if (event.getParameters() != null) {
            if (event.getParameters().contains("new")) {
                patient = new Patient();
                username.setValue("");
                name.setValue("");
                surname.setValue("");
                birthday.clear();
            } else {
                int idPatient = Integer.parseInt(event.getParameters());
                try {
                    patient = patientService.getPatientById(idPatient);
                } catch (Exception e) {
                    Notification.show("Can not find patient");
                }

            }
        }
        diagnosesGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        diagnosesGrid.removeAllColumns();
        diagnosesGrid.addColumn(s -> String.valueOf(s.getId())).setCaption(ID);
        diagnosesGrid.addColumn(s -> s.getDiagnosis().getName()).setCaption(DIAGNOSIS);
        diagnosesGrid.addColumn(s -> formatTime(s.getDiagnosedDate())).setCaption(DIAGNOSISDEATE);
        diagnosesGrid.addColumn(s -> s.getDiagnosedBy().getUsername()).setCaption(DIAGNOSEDBY);
        diagnosesGrid.addColumn(s -> {
            String details = s.getDetails();
            return details != null ? details : "";
        }).setCaption(DETAILS);
        diagnosesGrid.addColumn(s -> getDischargeInText(s.getDischarge())).setCaption(DISCHARGE);


        appointmentsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        appointmentsGrid.removeAllColumns();
        appointmentsGrid.addColumn(s -> String.valueOf(s.getId())).setCaption(ID);
        appointmentsGrid.addColumn(s -> s.getAppointment().getName()).setCaption(APPOINTMENT);
        appointmentsGrid.addColumn(s -> s.getAppointment().getAppointmentType().getName()).setCaption(APPOINTMENTTYPE);
        appointmentsGrid.addColumn(s -> formatTime(s.getAppointedDate())).setCaption(APPOINTMENTDATE);
        appointmentsGrid.addColumn(s -> {
                    User appointedBy = s.getAppointedBy();
                    return appointedBy != null ? appointedBy.getUsername() : "";
                }
        ).setCaption(APPOINTEDBY);
        appointmentsGrid.addColumn(s -> formatTime(s.getFulfilledDate())).setCaption(FULFILLDATE);
        appointmentsGrid.addColumn(s -> {
                    User appointedBy = s.getFulfilledBy();
                    return appointedBy != null ? appointedBy.getUsername() : "";
                }
        ).setCaption(FULFILLBY);



        String userRole = getRole(user);
        switch (userRole) {
            case "ROLE_PATIENT":
                patient.setUser(user);
                username.setValue(userRole.toString());
                newDiagnosis.setEnabled(false);
                changeDiagnosis.setEnabled(false);
                fulfil.setEnabled(false);
                discharge.setEnabled(false);
                break;
            case "ROLE_NURSE":
                newAppointment.setEnabled(false);
                changeAppointment.setEnabled(false);
                newDiagnosis.setEnabled(false);
                changeDiagnosis.setEnabled(false);
                discharge.setEnabled(false);
                break;
        }

        if (menu == null) {
            menu = new Menu(user);
        }
        addComponent(menu);

        addComponent(components);
        appointmentsGrid.setWidth("100%");
        diagnosesGrid.setWidth("100%");
        appointmentsGrid.setHeightByRows(5);
        diagnosesGrid.setHeightByRows(5);
        if (patient.getUser() != null) {
            username.setValue(patient.getUser().getUsername());
        } else {
            username.setValue("");
        }
        username.addValueChangeListener(valueChangeEvent -> {
            try {
                User newUser = userService.findByUsername(valueChangeEvent.getValue());
                patient.setUser(newUser);
            } catch (Exception e) {
                Notification.show("Can't find user with username: " + valueChangeEvent.getValue());
            }
        });
        if (patient.getName() != null) {
            name.setValue(patient.getName());
        }
        name.addValueChangeListener(changeEvent -> {
            patient.setName(changeEvent.getValue());
        });

        if (patient.getSurname() != null) {
            surname.setValue(patient.getSurname());
        }
        surname.addValueChangeListener(changeEvent -> {
            patient.setSurname(changeEvent.getValue());
        });

        if (patient.getBirthdate() != null) {
            birthday.setValue(patient.getBirthdate().toLocalDate());
        }
        birthday.addValueChangeListener(changeEvent -> {
            patient.setBirthdate(Date.valueOf(changeEvent.getValue()));
        });

        savePatientData.addClickListener(changeEvent -> {
            patientService.saveOrUpdatePatient(patient);
        });

        fulfil.addClickListener(clickEvent -> {
            try {
                Set<PatientAppointment> selectedItems = appointmentsGrid.getSelectedItems();
                PatientAppointment appointment = null;
                for (PatientAppointment appointment1 : selectedItems) {
                    appointment = appointment1;
                }
                patientAppointmentService.fulfil(appointment, user);
                Page.getCurrent().reload();
            } catch (Exception e) {
                Notification.show("Select one appointment to fulfil");
            }
        });

        discharge.addClickListener(clickEvent -> {
            try {
                Set<PatientDiagnosis> selectedItems = diagnosesGrid.getSelectedItems();
                PatientDiagnosis diagnosis = null;
                for (PatientDiagnosis diagnosis1 : selectedItems) {
                    diagnosis = diagnosis1;
                }
                patientDiagnosesService.discharge(diagnosis);
                Page.getCurrent().reload();
            } catch (Exception e) {
                Notification.show("Select one appointment to discharge");
            }
        });

        Set<PatientDiagnosis> patientDiagnoses = patient.getPatientDiagnoses();
        if (patientDiagnoses != null && !patientDiagnoses.isEmpty()) {
            diagnosesGrid.setItems(patientDiagnoses);
        } else {
            diagnosesGrid.setItems(Collections.EMPTY_LIST);
            patient.setPatientDiagnoses(Collections.emptySet());
        }


        Set<PatientAppointment> patientAppointments = patient.getPatientAppointments();
        if (patientAppointments != null && !patientAppointments.isEmpty()) {
            appointmentsGrid.setItems(patientAppointments);
        } else {
            appointmentsGrid.setItems(Collections.EMPTY_LIST);
            patient.setPatientAppointments(Collections.emptySet());
        }

        newDiagnosis.addClickListener(clickEvent -> {
            try {
                getUI().getNavigator().navigateTo(MainUI.DIAGNOSIS + "/new/" + patient.getId());
            } catch (Exception e) {
                logger.error(e);
            }
        });

        changeDiagnosis.addClickListener(clickEvent -> {
            try {
                Set<PatientDiagnosis> selectedItems = diagnosesGrid.getSelectedItems();
                PatientDiagnosis diagnosis = null;
                for (PatientDiagnosis diagnosis1 : selectedItems) {
                    diagnosis = diagnosis1;
                }
                getUI().getNavigator().navigateTo(MainUI.DIAGNOSIS + "/" + diagnosis.getId());
            } catch (Exception e) {
                Notification.show("Select one appointment to change");
            }
        });

        newAppointment.addClickListener(clickEvent -> {
            try {
                getUI().getNavigator().navigateTo(MainUI.APPOINTMENT + "/new/" + patient.getId());
            } catch (Exception e) {
                logger.error(e);
            }
        });

        changeAppointment.addClickListener(clickEvent -> {
            try {
                Set<PatientAppointment> selectedItems = appointmentsGrid.getSelectedItems();
                PatientAppointment appointment = null;
                for (PatientAppointment appointment1 : selectedItems) {
                    appointment = appointment1;
                }
                if (!userRole.equals("ROLE_PATIENT")
                        || appointment.getAppointment()
                        .getAppointmentType()
                        .getName()
                        .equals("EXTRA_SERVICE")) {
                    getUI().getNavigator().navigateTo(MainUI.APPOINTMENT + "/" + appointment.getId());
                }
            } catch (Exception e) {
                Notification.show("Select one appointment to change");
            }
        });
    }


    private String getDischargeInText(boolean discharge) {
        return discharge ? DISCHARGE : INTREATMENT;
    }


    private void initStrings() {
        Locale locale = VaadinSession.getCurrent().getLocale();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("components", locale);
        DIAGNOSES = resourceBundle.getString("card.diagnoses");
        DIAGNOSIS = resourceBundle.getString("card.grid.diagnosis");
        APPOINTMENTS = resourceBundle.getString("card.appointments");
        USERNAME = resourceBundle.getString("card.username");
        NAME = resourceBundle.getString("patient.list.table.name");
        SURNAME = resourceBundle.getString("patient.list.table.surname");
        BIRTHDATE = resourceBundle.getString("patient.list.table.birthdate");
        NEWDIAGNOSIS = resourceBundle.getString("card.diagnosis.add.button");
        SAVEPATIENT = resourceBundle.getString("card.patient.save.button");
        NEWAPPOINT = resourceBundle.getString("card.appointment.new.button");
        DISCHARGE = resourceBundle.getString("card.discharge.button");
        FULFILL = resourceBundle.getString("card.fulfill.button");
        CHANGEDIAGNOSIS = resourceBundle.getString("card.diagnosis.change.button");
        CHANGEAPPOINT = resourceBundle.getString("card.appointment.change.button");
        USERNOTFOUND = resourceBundle.getString("card.usernotfound");
        APPTOCHANGE = resourceBundle.getString("card.appointment.tochange");
        APPTOFULFILL = resourceBundle.getString("card.appointment.tofulfill");
        APPTODISCHARGE = resourceBundle.getString("card.diagnosis.todischarge");
        ID = resourceBundle.getString("card.grid.id");
        DIAGNOSISDEATE = resourceBundle.getString("card.grid.diagnosisdate");
        DIAGNOSEDBY = resourceBundle.getString("card.grid.diagnosisby");
        APPOINTMENT = resourceBundle.getString("card.grid.appointment");
        APPOINTMENTTYPE = resourceBundle.getString("card.grid.appointmenttype");
        APPOINTMENTDATE = resourceBundle.getString("card.grid.appointmentdate");
        FULFILLDATE = resourceBundle.getString("card.grid.fulfilldate");
        FULFILLBY = resourceBundle.getString("card.grid.fulfilledby");
        DETAILS = resourceBundle.getString("card.grid.details");
        APPOINTEDBY = resourceBundle.getString("card.grid.appointedby");
        INTREATMENT = resourceBundle.getString("card.intreatment.lable");
    }

}


