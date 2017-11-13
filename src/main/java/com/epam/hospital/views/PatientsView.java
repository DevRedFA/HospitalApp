package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.epam.hospital.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@UIScope
@SpringView
@SpringComponent
public class PatientsView extends VerticalLayout implements View {

    @Autowired
    PatientService patientService;

    private Grid patientGrid = new Grid<>(Patient.class);
    private Button previousPage = new Button("Previous");
    private Button patientDetails = new Button("patientDetails");
    private Button nextPage = new Button("Next");

    @PostConstruct
    void init() {
        setSpacing(true);
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent(previousPage);
        components.addComponent(patientDetails);
        components.addComponent(nextPage);
        patientGrid.setColumns("name", "surname", "birthdate");
        patientGrid.setSizeFull();
        List<Patient> firstPartOfPatients = patientService.getFirstPartOfPatients();
        patientGrid.setItems(firstPartOfPatients);
        previousPage.addClickListener(clickEvent ->
                patientGrid.setItems(patientService.getPreviousPartOfPatients()));
        nextPage.addClickListener(clickEvent ->
                patientGrid.setItems(patientService.getNextPartOfPatients()));
        patientDetails.addClickListener(clickEvent -> {
            Set selectedItems = patientGrid.getSelectedItems();
            if (selectedItems.size() == 1) {
                Object[] objects = selectedItems.toArray();
                Patient selectedPatient = (Patient) objects[0];
                String s = MainUI.CARD + "/" + selectedPatient.getId();
                getUI().getNavigator().navigateTo(s);
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
