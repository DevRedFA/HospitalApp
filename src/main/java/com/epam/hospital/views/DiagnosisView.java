package com.epam.hospital.views;

import com.epam.hospital.model.Diagnosis;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.*;
import com.epam.hospital.ui.MainUI;
import com.epam.hospital.ui.Menu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UIScope
@SpringView
@SpringComponent
public class DiagnosisView extends VerticalLayout implements View {

    private PatientDiagnosis patientDiagnosis;

    private TextField details = new TextField("Details");
    private Label diagnosis = new Label("Diagnosis:");
    private NativeSelect<String> diagnosisSel = new NativeSelect<>();
    private Label diagnosedBy = new Label("Diagnosed By");
    private Logger logger = Logger.getLogger(DiagnosisView.class);
    private DateTimeField diagnosedDate = new DateTimeField("Diagnosed Date");
    @Autowired
    PatientService patientService;
    @Autowired
    PatientDiagnosesService patientDiagnosesService;
    @Autowired
    UserService userService;
    @Autowired
    DiagnosisService diagnosisService;


    private Button backToPatient = new Button("Back to patient");
    private Button save = new Button("Save");
    private VerticalLayout diagnosisData = new VerticalLayout();
    private HorizontalLayout buttons = new HorizontalLayout();

    @Setter
    User user;

    private Menu menu;

    private VerticalLayout components = new VerticalLayout();

    @PostConstruct
    void init() {
        setSpacing(true);
        components.addComponent(diagnosisData);
        components.addComponent(buttons);
        diagnosisData.addComponent(diagnosis);
        diagnosisData.addComponent(diagnosisSel);
        diagnosisData.addComponent(details);
        diagnosisData.addComponent(diagnosedBy);
        diagnosisData.addComponent(diagnosedDate);
        buttons.addComponent(save);
        buttons.addComponent(backToPatient);
        diagnosisSel.setEmptySelectionAllowed(false);
        diagnosedDate.setDateFormat("MM/dd/yyyy HH:mm:ss");
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

            if (patientDiagnosis.getDetails() != null) {
                details.setValue(patientDiagnosis.getDetails());
            }
            if (patientDiagnosis.getDiagnosedBy() != null) {
                diagnosedBy.setValue("Diagnosed By: " + patientDiagnosis.getDiagnosedBy().getUsername());
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
                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });
        }
    }
}


