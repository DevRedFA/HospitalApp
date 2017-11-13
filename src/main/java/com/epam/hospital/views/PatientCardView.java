package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.implementation.PatientServiceImpl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@UIScope
@SpringView
@SpringComponent
public class PatientCardView extends VerticalLayout implements View {

    private Patient patient;

    private Label patientInfo = new Label();

    @Autowired
    PatientService patientService;

    @PostConstruct
    void init() {
        setSpacing(true);
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent(patientInfo);
    }


    @Override
    public void enter(ViewChangeEvent event) {
        if (event.getParameters() != null) {
            int idPatient = Integer.parseInt(event.getParameters());
            this.patient = patientService.getPatientById(idPatient);
            Notification.show("patient: " + patient.toString());
            patientInfo.setValue("Name:" + patient.getName() + System.lineSeparator()
                    + "Name:" + patient.getSurname() + System.lineSeparator()
                    + "Birth day: " + patient.getBirthdate() + System.lineSeparator());
        }
    }


}


