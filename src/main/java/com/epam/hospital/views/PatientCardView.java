package com.epam.hospital.views;

import com.epam.hospital.model.*;
import com.epam.hospital.service.api.PatientAppointmentService;
import com.epam.hospital.service.api.PatientDiagnosesService;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.api.UserService;
import com.epam.hospital.ui.MainUI;
import com.epam.hospital.ui.Menu;
import com.epam.hospital.util.LabelsHolder;
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
import java.util.Set;

import static com.epam.hospital.util.Utils.formatTime;
import static com.epam.hospital.util.Utils.getRole;
import static com.epam.hospital.util.LabelsHolder.*;

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
    private TabSheet tabSheet = new TabSheet();


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
    private Button deleteDiagnosis;
    private Button deleteAppointment;


    private Logger logger = Logger.getLogger(PatientCardView.class);
    private HorizontalLayout patientData = new HorizontalLayout();
    private HorizontalLayout diagnosisButtons = new HorizontalLayout();
    private HorizontalLayout appointmentsButtons = new HorizontalLayout();
    private VerticalLayout diagnosisTab = new VerticalLayout();
    private VerticalLayout appointmentsTab = new VerticalLayout();

    @Setter
    User user;

    private Menu menu;

    private VerticalLayout components = new VerticalLayout();

    @PostConstruct
    void init() {
        if (LabelsHolder.globalLocale == null) {
            LabelsHolder.chageLocale(VaadinSession.getCurrent().getLocale());
        } else {
            VaadinSession.getCurrent().setLocale(globalLocale);
        }
        appointments = new Label(APPOINTMENTS);
        username = new TextField(USERNAME);
        name = new TextField(NAME);
        surname = new TextField(SURNAME);
        birthday = new DateField(BIRTHDATE);
        deleteDiagnosis = new Button(DELETEDIAGNOSIS);
        deleteAppointment = new Button(DELETEAPPOINTMENTS);
        diagnoses = new Label(DIAGNOSES);
        newDiagnosis = new Button(NEWDIAGNOSIS);
        savePatientData = new Button(SAVEPATIENT);
        newAppointment = new Button(NEWAPPOINT);
        discharge = new Button(DISCHARGE);
        fulfil = new Button(FULFILL);
        changeDiagnosis = new Button(CHANGEDIAGNOSIS);
        changeAppointment = new Button(CHANGEAPPOINT);
        birthday.setDateFormat(DATEFORMAT);
        birthday.setLocale(VaadinSession.getCurrent().getLocale());
        setSpacing(true);
        patientData.addComponent(username);
        patientData.addComponent(name);
        patientData.addComponent(surname);
        patientData.addComponent(birthday);
        patientData.addComponent(savePatientData);
        patientData.setComponentAlignment(savePatientData, Alignment.BOTTOM_RIGHT);
        savePatientData.setWidth("100%");
        components.addComponent(patientData);
        diagnoses.setContentMode(ContentMode.PREFORMATTED);
        appointments.setContentMode(ContentMode.PREFORMATTED);
        appointmentsButtons.addComponent(newAppointment);
        appointmentsButtons.addComponent(changeAppointment);
        appointmentsButtons.addComponent(deleteAppointment);
        appointmentsButtons.addComponent(fulfil);
        diagnosisButtons.addComponent(newDiagnosis);
        diagnosisButtons.addComponent(changeDiagnosis);
        diagnosisButtons.addComponent(deleteDiagnosis);
        diagnosisButtons.addComponent(discharge);
        diagnosisTab.addComponent(diagnosisButtons);
        diagnosisTab.addComponent(diagnosesGrid);
        appointmentsTab.addComponent(appointmentsButtons);
        appointmentsTab.addComponent(appointmentsGrid);
        tabSheet.addTab(diagnosisTab, DIAGNOSES);
        tabSheet.addTab(appointmentsTab, APPOINTMENTS);
        components.addComponent(tabSheet);
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
                    Notification.show(PATIENTNOTFOUND);
                }

            }
        }
        diagnosesGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        diagnosesGrid.removeAllColumns();
        diagnosesGrid.setDescription(DIAGNOSES);
        diagnosesGrid.addColumn(s -> s.getDiagnosis().getName()).setCaption(DIAGNOSIS);
        diagnosesGrid.addColumn(s -> formatTime(s.getDiagnosedDate())).setCaption(DIAGNOSISDEATE);
        diagnosesGrid.addColumn(s -> s.getDiagnosedBy().getUsername()).setCaption(DIAGNOSEDBY);
        diagnosesGrid.addColumn(s -> {
            String details = s.getDetails();
            return details != null ? details : "";
        }).setCaption(DETAILS);
        diagnosesGrid.addColumn(s -> getDischargeInText(s.getDischarge())).setCaption(STATUS);// HERE


        appointmentsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        appointmentsGrid.removeAllColumns();
        appointmentsGrid.setDescription(APPOINTMENTS);
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
//                deleteAppointment.setEnabled(false);
                deleteDiagnosis.setEnabled(false);
                fulfil.setEnabled(false);
                discharge.setEnabled(false);
                break;
            case "ROLE_NURSE":
                newAppointment.setEnabled(false);
                changeAppointment.setEnabled(false);
                newDiagnosis.setEnabled(false);
                changeDiagnosis.setEnabled(false);
                deleteAppointment.setEnabled(false);
                deleteDiagnosis.setEnabled(false);
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
                Notification.show(USERNOTFOUND + valueChangeEvent.getValue());
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
            boolean correctData = true;
            if (patient.getBirthdate() == null) {
                Notification.show(SETBIRTHDAY);
                correctData = false;
            }
            if (patient.getUser() == null) {
                Notification.show(SETSURNAME);
                correctData = false;
            }
            if (patient.getName() == null) {
                Notification.show(SETNAME);
                correctData = false;
            }
            if (patient.getSurname() == null) {
                Notification.show(SETUSERNAME);
                correctData = false;
            }
            if (correctData) {
                patientService.saveOrUpdatePatient(patient);
            }
        });


        Set<PatientDiagnosis> patientDiagnoses = patient.getPatientDiagnoses();
        if (patientDiagnoses != null && !patientDiagnoses.isEmpty()) {
            diagnosesGrid.setItems(patientDiagnoses);
        } else {
            diagnosesGrid.setItems(Collections.EMPTY_LIST);
            patient.setPatientDiagnoses(Collections.emptySet());
        }


        discharge.addClickListener(clickEvent -> {
            try {
                Set<PatientDiagnosis> selectedItems = diagnosesGrid.getSelectedItems();
                PatientDiagnosis diagnosis = null;
                for (PatientDiagnosis diagnosis1 : selectedItems) {
                    diagnosis = diagnosis1;
                }
                patientDiagnosesService.discharge(diagnosis);
                diagnosesGrid.setItems(patient.getPatientDiagnoses());
                Page.getCurrent().reload();
            } catch (Exception e) {
                Notification.show(DIAGNOSISTODISCHARGE);
            }
        });

        Set<PatientAppointment> patientAppointments = patient.getPatientAppointments();
        if (patientAppointments != null && !patientAppointments.isEmpty()) {
            appointmentsGrid.setItems(patientAppointments);
        } else {
            appointmentsGrid.setItems(Collections.EMPTY_LIST);
            patient.setPatientAppointments(Collections.emptySet());
        }

        fulfil.addClickListener(clickEvent -> {
            try {
                Set<PatientAppointment> selectedItems = appointmentsGrid.getSelectedItems();
                PatientAppointment appointment = null;
                for (PatientAppointment appointment1 : selectedItems) {
                    appointment = appointment1;
                }
                patientAppointmentService.fulfil(appointment, user);
                appointmentsGrid.setItems(patient.getPatientAppointments());
//                Page.getCurrent().reload();
            } catch (Exception e) {
                Notification.show(APPTOFULFILL);
            }
        });

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
                Notification.show(APPTOCHANGE);
            }
        });

        deleteDiagnosis.addClickListener(clickEvent -> {
            try {
                Set<PatientDiagnosis> selectedItems = diagnosesGrid.getSelectedItems();
                PatientDiagnosis diagnosis = null;
                for (PatientDiagnosis diagnosis1 : selectedItems) {
                    diagnosis = diagnosis1;
                }
                patientDiagnosesService.deletePatientDiagnosis(diagnosis);
                Set<PatientDiagnosis> patientDiagnoses1 = patient.getPatientDiagnoses();
                patientDiagnoses1.remove(diagnosis);
                diagnosesGrid.setItems(patientDiagnoses1);
            } catch (Exception e) {
                Notification.show(DIAGNOSISTODELETE);
            }
        });

        deleteAppointment.addClickListener(clickEvent -> {
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
                        .equals("Extra service")) {
                    patientAppointmentService.deletePatientAppointment(appointment);
//                    Page.getCurrent().reload();
                    Set<PatientAppointment> patientAppointments1 = patient.getPatientAppointments();
                    patientAppointments1.remove(appointment);
                    appointmentsGrid.setItems(patientAppointments1);
                } else {
                    Notification.show(APPNODELETE);
                }
            } catch (Exception e) {
                Notification.show(APPTODELETE);
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
                        .equals("Extra service")) {
                    getUI().getNavigator().navigateTo(MainUI.APPOINTMENT + "/" + appointment.getId());
                }
            } catch (Exception e) {
                Notification.show(APPTOCHANGE);
            }
        });
    }


    private String getDischargeInText(boolean discharge) {
        return discharge ? PATIENTSTAT : INTREATMENT;
    }

}


