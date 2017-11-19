package com.epam.hospital.views;

import com.epam.hospital.model.Diagnosis;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.User;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.hospital.util.LabelsHolder.*;
import static java.text.DateFormat.SHORT;
import static java.text.DateFormat.getDateInstance;

@UIScope
@SpringView
public class DiagnosisView extends VerticalLayout implements View {

    private PatientDiagnosis patientDiagnosis;
    private Logger logger = Logger.getLogger(DiagnosisView.class);


    private TextArea details;
    private Label diagnosis;
    private Label patientField;
    private NativeSelect<String> diagnosisSel = new NativeSelect<>();
    private Label diagnosedBy;
    private DateTimeField diagnosedDate;
    private Button backToPatient;
    private Button save;
    private VerticalLayout diagnosisData = new VerticalLayout();
    private HorizontalLayout buttons = new HorizontalLayout();

    @Autowired
    PatientService patientService;
    @Autowired
    PatientDiagnosesService patientDiagnosesService;
    @Autowired
    UserService userService;
    @Autowired
    DiagnosisService diagnosisService;


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
        details = new TextArea(DETAILS);
        details.setWidth(500, Unit.PIXELS);
        details.setHeight(150, Unit.PIXELS);
        diagnosis = new Label(DIAGNOSIS);
        diagnosedBy = new Label(DIAGNISEDBY);
        diagnosedDate = new DateTimeField(DIAGNOSEDDATE);
        patientField = new Label(PATIENT);
        backToPatient = new Button(BACKTOTHEPATIENT);
        save = new Button(SAVE);


        setSpacing(true);
        components.addComponent(diagnosisData);
        components.addComponent(buttons);
        diagnosisData.addComponent(patientField);
        diagnosisData.addComponent(diagnosis);
        diagnosisData.addComponent(diagnosisSel);
        diagnosisData.addComponent(details);
        diagnosisData.addComponent(diagnosedBy);
        diagnosisData.addComponent(diagnosedDate);
        buttons.addComponent(save);
        buttons.addComponent(backToPatient);
        diagnosisSel.setEmptySelectionAllowed(false);
        diagnosedDate.setDateFormat(DATETIMEFORMAT);

    }


    @Override
    public void enter(ViewChangeEvent event) {
        if (event.getParameters() != null) {
            if (menu == null) {
                menu = new Menu(user);
            }
            addComponent(menu);
            addComponent(components);

            List<Diagnosis> allDiagnosis = diagnosisService.getAllDiagnosis();
            Map<String, Diagnosis> map = allDiagnosis.stream().collect(Collectors.toMap(Diagnosis::getName, diagnosis1 -> diagnosis1));
            diagnosisSel.setItems(map.keySet());


            if (event.getParameters().contains("new")) {
                String[] split = event.getParameters().split("/");
                patientDiagnosis = new PatientDiagnosis();
                patientDiagnosis.setPatient(patientService
                        .getPatientById(Integer.valueOf(split[1])));
                patientDiagnosis.setDiagnosedBy(user);
                patientDiagnosis.setDiagnosedDate(Timestamp.valueOf(LocalDateTime.now()));
                patientDiagnosis.setDischarge(false);
            } else {
                int idDiagnosis = Integer.parseInt(event.getParameters());
                patientDiagnosis = patientDiagnosesService.getPatientDiagnosisById(idDiagnosis);
                diagnosisSel.setValue(patientDiagnosis.getDiagnosis().getName());

            }
            if (patientDiagnosis.getPatient() != null) {
                patientField.setValue(PATIENT + ": " + patientDiagnosis.getPatient().getName()
                        + " " + patientDiagnosis.getPatient().getSurname());
            }
            if (patientDiagnosis.getDetails() != null) {
                details.setValue(patientDiagnosis.getDetails());
            }
            if (patientDiagnosis.getDiagnosedBy() != null) {
                diagnosedBy.setValue(DIAGNISEDBY + " " + patientDiagnosis.getDiagnosedBy().getUsername());
            }
            if (patientDiagnosis.getDiagnosedDate() != null) {
                diagnosedDate.setValue((patientDiagnosis.getDiagnosedDate().toLocalDateTime()));
            }

            details.addValueChangeListener(valueChangeEvent -> {
                patientDiagnosis.setDetails(valueChangeEvent.getValue());
            });


            diagnosedDate.addValueChangeListener(changeEvent -> {
                patientDiagnosis.setDiagnosedDate(Timestamp.valueOf(changeEvent.getValue()));
            });

            backToPatient.addClickListener(clickEvent -> {
                try {
                    getUI().getNavigator().navigateTo(MainUI.CARD + "/" + patientDiagnosis.getPatient().getId());
                } catch (Exception e) {
                    logger.error(e);
                }
            });

            diagnosisSel.addValueChangeListener(valueChangeEvent -> {
                patientDiagnosis.setDiagnosis(map.get(valueChangeEvent.getValue()));
            });

            save.addClickListener(clickEvent -> {
                boolean correctData = true;
                if (patientDiagnosis.getDiagnosis() == null) {
                    correctData = false;
                    Notification.show(CHANGEDIAGNOSIS);
                }
                if (correctData) {
                    patientDiagnosesService.saveOrUpdate(patientDiagnosis);
                    getUI().getNavigator().navigateTo(MainUI.CARD + "/" + patientDiagnosis.getPatient().getId());
                }
            });
        }
    }

}


