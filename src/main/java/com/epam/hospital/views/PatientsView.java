package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.epam.hospital.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@SpringView
@SpringComponent
public class PatientsView extends VerticalLayout implements View {

    @Autowired
    PatientService patientService;

    protected Grid patientGrid = new Grid<>(Patient.class);
    protected Button previousPage = new Button("Previous");
    protected Button patientDetails = new Button("patientDetails");
    protected Button nextPage = new Button("Next");

    public PatientsView() {
        setSpacing(true);


        HorizontalLayout components = new HorizontalLayout();
        components.addComponent(previousPage);
        components.addComponent(patientDetails);
        components.addComponent(nextPage);
        patientGrid.setColumns("name", "surname", "birthdate");
        patientGrid.setSizeFull();
        List<Patient> patientList = new ArrayList<>();
        Patient patient = new Patient();
        patient.setName("Ivan");
        Patient patient2 = new Patient();
        patient2.setName("Victor");
        patientList.add(patient);
        patientList.add(patient2);
        patientService.init();
        patientGrid.setItems(patientService.getPreviousPartOfPatients());
        previousPage.addClickListener(clickEvent ->
                patientGrid.setItems(patientService.getPreviousPartOfPatients()));
        nextPage.addClickListener(clickEvent ->
                patientGrid.setItems(patientService.getNextPartOfPatients()));
        patientDetails.addClickListener(clickEvent -> {
            Set selectedItems = patientGrid.getSelectedItems();
            if (selectedItems.size() == 1) {
                Object[] objects = selectedItems.toArray();
                Patient selectedPatient = (Patient) objects[0];
                getUI().getNavigator().navigateTo(MainUI.CARD + "/" + selectedPatient.getId());
            }
        });

        addComponent(patientGrid);
        addComponent(components);
    }


    @Override
    public void enter(ViewChangeEvent event) {
        Notification.show("Showing view: PatientsView!");
    }

    private Label headingLabel() {
        return new Label("Main");
    }


}
