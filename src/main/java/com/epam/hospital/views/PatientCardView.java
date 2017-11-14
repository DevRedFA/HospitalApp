package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientAppointment;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.AppointmentService;
import com.epam.hospital.service.api.DiagnosesService;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.implementation.PatientServiceImpl;
import com.epam.hospital.ui.Menu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

@UIScope
@SpringView
@SpringComponent
public class PatientCardView extends VerticalLayout implements View {

    private Patient patient;

    private Label patientInfo = new Label();

    @Autowired
    PatientService patientService;

    @Setter
    User user;

    Menu menu;

    HorizontalLayout components = new HorizontalLayout();

    @PostConstruct
    void init() {
        setSpacing(true);

        components.addComponent(patientInfo);

    }


    @Override
    public void enter(ViewChangeEvent event) {
        if (event.getParameters() != null) {
            if (menu == null) {
                menu = new Menu(user);
            }
            addComponent(menu);
            addComponent(components);
            int idPatient = Integer.parseInt(event.getParameters());
            this.patient = patientService.getPatientById(idPatient);
            patientInfo.setContentMode(ContentMode.PREFORMATTED);
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ")
                    .append(patient.getName())
                    .append(System.lineSeparator())
                    .append("Surname: ")
                    .append(patient.getSurname())
                    .append(System.lineSeparator())
                    .append("Birth day: ")
                    .append(patient.getBirthdate())
                    .append(System.lineSeparator())
                    .append("Diagnoses: ")
                    .append(System.lineSeparator());
            Set<PatientDiagnosis> patientDiagnoses = patient.getPatientDiagnoses();
            if (!patientDiagnoses.isEmpty()) {
                patientDiagnoses.forEach(s -> sb
                        .append(DiagnosesService.getFormattedView(s)).append(System.lineSeparator()));
            }
            sb.append("Appointments:")
                    .append(System.lineSeparator());

            Set<PatientAppointment> patientAppointments = patient.getPatientAppointments();
            if (!patientAppointments.isEmpty()) {
                patientAppointments.forEach(s -> sb
                        .append(AppointmentService.getFormattedView(s)).append(System.lineSeparator()));
            }

            patientInfo.setValue(sb.toString());

        }
    }


}


