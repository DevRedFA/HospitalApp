package com.epam.hospital.views;

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
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

@UIScope
@SpringView
@SpringComponent
public class DiagnosisView extends VerticalLayout implements View {

    private PatientDiagnosis patientDiagnosis;

    private TextField id = new TextField("id");
    private TextField details = new TextField("Details");
    private TextField diagnosis = new TextField("Diagnosis");
    private TextField diagnosedBy = new TextField("Diagnosed By");
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
        diagnosisData.addComponent(id);
        diagnosisData.addComponent(diagnosis);
        diagnosisData.addComponent(details);
        diagnosisData.addComponent(diagnosedBy);
        diagnosisData.addComponent(diagnosedDate);
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
                patientDiagnosis = new PatientDiagnosis();
                patientDiagnosis.setPatient(patientService
                        .getPatientById(Integer.valueOf(split[1])));
            } else {
                int idDiagnosis = Integer.parseInt(event.getParameters());
                patientDiagnosis = patientDiagnosesService.getPatientDiagnosisById(idDiagnosis);
                id.setValue(String.valueOf(patientDiagnosis.getId()));
                diagnosis.setValue(patientDiagnosis.getDiagnosis().getName());
                if (patientDiagnosis.getDetails() != null) {
                    details.setValue(patientDiagnosis.getDetails());
                }
                if (patientDiagnosis.getDiagnosedBy() != null) {
                    diagnosedBy.setValue(patientDiagnosis.getDiagnosedBy().getUsername());
                }
                if (patientDiagnosis.getDiagnosedDate() != null) {
                    diagnosedDate.setValue((patientDiagnosis.getDiagnosedDate().toLocalDateTime()));
                }
            }
            id.addValueChangeListener(valueChangeEvent -> {
                try {
                    patientDiagnosis.setId(Integer.parseInt(valueChangeEvent.getValue()));
//                    patientDiagnosesService.saveOrUpdate(patientDiagnosis);
                } catch (NumberFormatException e) {
                    Notification.show("Enter correct number");
                }

            });

            diagnosis.addValueChangeListener(changeEvent -> {
                patientDiagnosis.setDiagnosis(diagnosisService.getDiagnosisById(1));
//                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });

            diagnosedBy.addValueChangeListener(changeEvent -> {
                User byUsername = userService.findByUsername(changeEvent.getValue());
                patientDiagnosis.setDiagnosedBy(byUsername);
//                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });

            details.addValueChangeListener(valueChangeEvent -> {
                patientDiagnosis.setDetails(valueChangeEvent.getValue());
//                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });


            diagnosedDate.addValueChangeListener(changeEvent -> {
                patientDiagnosis.setDiagnosedDate(Timestamp.valueOf(changeEvent.getValue()));
//                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });

            backToPatient.addClickListener(clickEvent -> {
                getUI().getNavigator().navigateTo(MainUI.CARD + "/" + patientDiagnosis.getPatient().getId());
            });

            save.addClickListener(clickEvent -> {
                patientDiagnosesService.saveOrUpdate(patientDiagnosis);
            });
        }
    }
}


