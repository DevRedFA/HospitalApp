package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.implementation.PatientServiceImpl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView
@SpringComponent
public class PatientCardView extends VerticalLayout implements View {

    private Patient patient;

    private TextArea patientInfo = new TextArea();

    @Autowired
//    PatientService patientService;
            PatientService patientService = new PatientServiceImpl();

    public PatientCardView() {
        setSpacing(true);
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent(patientInfo);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event.getParameters() != null) {
            int idPatient = Integer.parseInt(event.getParameters());
            this.patient = patientService.getPatientById(idPatient);
            patientInfo.setValue("Name:" + patient.getName() + System.lineSeparator()
                    + "Name:" + patient.getSurname() + System.lineSeparator()
                    + "Birth day: " + patient.getBirthdate() + System.lineSeparator());
        }
    }
}


