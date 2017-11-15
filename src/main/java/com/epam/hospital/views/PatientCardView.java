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
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.Collections;
import java.util.Set;

import static com.epam.hospital.util.Utils.formatTime;

@UIScope
@SpringView
public class PatientCardView extends VerticalLayout implements View {

    private Patient patient;
    private Label diagnoses = new Label("dianoses: ");
    private Label appointments = new Label("appointments: ");
    private TextField username = new TextField("Username");
    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private DateField birthday = new DateField("Birth Day");
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
    private Button newDiagnosis = new Button("new Diagnosis");
    private Button savePatientData = new Button("save patient data");
    private Button newAppointment = new Button("new Appointment");
    private Button discharge = new Button("discharge");
    private Button fulfil = new Button("fulfil");
    private Button changeDiagnosis = new Button("change Diagnosis");
    private Button changeAppointment = new Button("change Appointment");

    private HorizontalLayout patientData = new HorizontalLayout();
    private HorizontalLayout diagnosisButtons = new HorizontalLayout();
    private HorizontalLayout appointmentsButtons = new HorizontalLayout();
    @Setter
    User user;

    private Menu menu;

    private VerticalLayout components = new VerticalLayout();

    @PostConstruct
    void init() {
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
        diagnoses.setValue("Dianoses:");
        diagnoses.setContentMode(ContentMode.PREFORMATTED);
        appointments.setValue("Appointments:");
        appointments.setContentMode(ContentMode.PREFORMATTED);
        appointmentsButtons.addComponent(newAppointment);
        appointmentsButtons.addComponent(changeAppointment);
        appointmentsButtons.addComponent(fulfil);
        components.addComponent(diagnoses);
        components.addComponent(diagnosesGrid);
        diagnosisButtons.addComponent(newDiagnosis);
        diagnosisButtons.addComponent(changeDiagnosis);
        diagnosisButtons.addComponent(discharge);
        components.addComponent(diagnosisButtons);
        components.addComponent(appointments);
        components.addComponent(appointmentsGrid);
        components.addComponent(appointmentsButtons);
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
                diagnosesGrid.setItems(Collections.EMPTY_LIST);
                appointmentsGrid.setItems(Collections.EMPTY_LIST);


            } else {
                int idPatient = Integer.parseInt(event.getParameters());
                patient = patientService.getPatientById(idPatient);
            }
        }
        diagnosesGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        diagnosesGrid.removeAllColumns();
        diagnosesGrid.addColumn(s -> String.valueOf(s.getId())).setCaption("Id");
        diagnosesGrid.addColumn(s -> s.getDiagnosis().getName()).setCaption("Diagnosis");
        diagnosesGrid.addColumn(s -> formatTime(s.getDiagnosedDate())).setCaption("Diagnosed Date");
        diagnosesGrid.addColumn(s -> s.getDiagnosedBy().getUsername()).setCaption("Diagnosed By");
        diagnosesGrid.addColumn(s -> {
            String details = s.getDetails();
            return details != null ? details : "";
        }).setCaption("Details");
        diagnosesGrid.addColumn(s -> getDischargeInText(s.getDischarge())).setCaption("Discharge");
        diagnosesGrid.setSizeFull();

        appointmentsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        appointmentsGrid.removeAllColumns();
        appointmentsGrid.addColumn(s -> String.valueOf(s.getId())).setCaption("Id");
        appointmentsGrid.addColumn(s -> s.getAppointment().getName()).setCaption("Appointment");
        appointmentsGrid.addColumn(s -> s.getAppointment().getAppointmentType().getName()).setCaption("Appointment Type");
        appointmentsGrid.addColumn(s -> formatTime(s.getAppointedDate())).setCaption("Appointed Date");
        appointmentsGrid.addColumn(s -> {
                    User appointedBy = s.getAppointedBy();
                    return appointedBy != null ? appointedBy.getUsername() : "";
                }
        ).setCaption("Appointed By");
        appointmentsGrid.addColumn(s -> formatTime(s.getFulfilledDate())).setCaption("Fulfilled Date");
        appointmentsGrid.addColumn(s -> {
                    User appointedBy = s.getFulfilledBy();
                    return appointedBy != null ? appointedBy.getUsername() : "";
                }
        ).setCaption("Fulfilled By");
        appointmentsGrid.setSizeFull();

        Set<Role> roles = user.getRoles();
        String userRole = null;
        for (Role role : roles) {
            userRole = role.getName();
        }
        assert userRole != null;
        switch (userRole) {
            case "ROLE_PATIENT":
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

        if (patient.getUser() != null) {
            username.setValue(patient.getUser().getUsername());
        } else {
            username.setValue("");
        }
        username.addValueChangeListener(valueChangeEvent -> {
            try {
                User newUser = userService.findByUsername(valueChangeEvent.getValue());
                patient.setUser(newUser);
                patientService.saveOrUpdatePatient(patient);
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
        if (!patientDiagnoses.isEmpty()) {
            diagnosesGrid.setItems(patientDiagnoses);
        }


        Set<PatientAppointment> patientAppointments = patient.getPatientAppointments();
        if (!patientAppointments.isEmpty()) {
            appointmentsGrid.setItems(patientAppointments);
        }

        newDiagnosis.addClickListener(clickEvent -> {
            getUI().getNavigator().navigateTo(MainUI.DIAGNOSIS + "/new/" + patient.getId());
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
            getUI().getNavigator().navigateTo(MainUI.APPOINTMENT + "/new/" + patient.getId());
        });

        String finalUserRole = userRole;
        changeAppointment.addClickListener(clickEvent -> {
            try {
                Set<PatientAppointment> selectedItems = appointmentsGrid.getSelectedItems();
                PatientAppointment appointment = null;
                for (PatientAppointment appointment1 : selectedItems) {
                    appointment = appointment1;
                }
                if (!finalUserRole.equals("ROLE_PATIENT")
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
        return discharge ? "Discharged" : "In treatment";
    }
}


