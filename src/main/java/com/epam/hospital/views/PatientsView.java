package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.implementation.PatientServiceImpl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.epam.hospital.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@UIScope
@SpringView
@SpringComponent
public class PatientsView extends VerticalLayout implements View {

    @Autowired
    PatientService patientService;

    protected Grid patientGrid = new Grid<>(Patient.class);
    protected Button previousPage = new Button("Previous");
    protected Button patientDetails = new Button("patientDetails");
    protected Button nextPage = new Button("Next");

    @PostConstruct
    void init() {
        setSpacing(true);
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent(previousPage);
        components.addComponent(patientDetails);
        components.addComponent(nextPage);
        patientGrid.setColumns("name", "surname", "birthdate");
        patientGrid.setSizeFull();
        patientService.init();
        List<Patient> nextPartOfPatients = patientService.getNextPartOfPatients();
        patientGrid.setItems(nextPartOfPatients);
        previousPage.addClickListener(clickEvent ->
                patientGrid.setItems(patientService.getPreviousPartOfPatients()));
        nextPage.addClickListener(clickEvent ->
                patientGrid.setItems(patientService.getNextPartOfPatients()));
        patientDetails.addClickListener(clickEvent -> {
            Set selectedItems = patientGrid.getSelectedItems();
            if (selectedItems.size() == 1) {
                Object[] objects = selectedItems.toArray();
                Patient selectedPatient = (Patient) objects[0];
                String s = MainUI.CARD + selectedPatient.getId();
//                getUI().getNavigator().navigateTo(s);
                getUI().getNavigator().navigateTo("card/");
            }
        });
        addComponent(new Menu());
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
