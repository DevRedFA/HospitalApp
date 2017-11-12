package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.ui.UserPageDesign;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SpringView
@SpringComponent
public class PatientCardView extends UserPageDesign implements View {

    Patient patient;

    @Autowired
    PatientService patientService;

    public PatientCardView() {
        patientInfo.setValue("Default");

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event.getParameters() != null) {
            String[] msgs = event.getParameters().split("/");
            int idPatient = Integer.parseInt(msgs[0]);
            this.patient = patientService.getPatientById(idPatient);
            patientInfo.setValue("Name:" + patient.getName() + System.lineSeparator()
                    + "Name:" + patient.getSurname() + System.lineSeparator()
                    + "Birth day: " + patient.getBirthdate() + System.lineSeparator());
        }
    }
}


