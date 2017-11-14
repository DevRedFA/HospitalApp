package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.PatientService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.epam.hospital.ui.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@UIScope
@SpringView
public class PatientsView extends VerticalLayout implements View {

    @Autowired
    PatientService patientService;

    @Setter
    User user;

    private Grid patientGrid = new Grid<>(Patient.class);
    private Button previousPage = new Button("previous");
    private Button patientDetails = new Button("patientDetails");
    private Button nextPage = new Button("next");
    private Button createPatient = new Button("createPatient");
    HorizontalLayout components = new HorizontalLayout();
    Menu menu;

    @PostConstruct
    void init() {
        setSpacing(true);
        components.addComponent(previousPage);
        components.addComponent(patientDetails);
        components.addComponent(nextPage);
        components.addComponent(createPatient);
        previousPage.setEnabled(false);
        patientGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        patientGrid.setColumns("name", "surname", "birthdate");
        patientGrid.setSizeFull();
        List<Patient> firstPartOfPatients = patientService.getFirstPartOfPatients();
        patientGrid.setItems(firstPartOfPatients);
        previousPage.addClickListener(clickEvent -> {
            patientGrid.setItems(patientService.getPreviousPartOfPatients());
            nextPage.setEnabled(true);
            previousPage.setEnabled(patientService.isPreviousPageAvailable());
        });
        nextPage.addClickListener(clickEvent -> {
            patientGrid.setItems(patientService.getNextPartOfPatients());
            previousPage.setEnabled(true);
            nextPage.setEnabled(patientService.isNextPageAvailable());
        });
        patientDetails.addClickListener(clickEvent -> {
            Set selectedItems = patientGrid.getSelectedItems();
            if (selectedItems.size() == 1) {
                Object[] objects = selectedItems.toArray();
                Patient selectedPatient = (Patient) objects[0];
                String s = MainUI.CARD + "/" + selectedPatient.getId();
                getUI().getNavigator().navigateTo(s);
            }
        });

        createPatient.addClickListener(clickEvent -> {
            String s = MainUI.CARD + "/" + "new";
            getUI().getNavigator().navigateTo(s);
        });
    }


    @Override
    public void enter(ViewChangeEvent event) {
        if (menu == null) {
            menu = new Menu(user);
        }
        addComponent(menu);

        addComponent(patientGrid);

        addComponent(this.components);

        setWidth("100%");

        setHeight("100%");


        Set<Role> roles = user.getRoles();
        String userRole = null;
        for (Role role : roles) {
            userRole = role.getName();
        }
        assert userRole != null;
        if (userRole.equals("ROLE_NURSE")) {
            createPatient.setEnabled(false);
        }
    }
}
