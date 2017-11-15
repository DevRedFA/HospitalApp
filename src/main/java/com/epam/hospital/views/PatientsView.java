package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.PatientService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.epam.hospital.ui.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import static com.epam.hospital.util.Utils.getRole;

@SuppressWarnings("serial")
@UIScope
@SpringView
public class PatientsView extends VerticalLayout implements View {

    @Autowired
    PatientService patientService;

    @Setter
    User user;

    private Grid patientGrid = new Grid<>(Patient.class);
    private Button previousPage;
    private Button patientDetails;
    private Button nextPage;
    private Button createPatient;
    HorizontalLayout components = new HorizontalLayout();
    Menu menu;

    private String NAME;
    private String SURNAME;
    private String DOB;
    private String PREV;
    private String P_CARD;
    private String NEXT;
    private String ADD_P;


    @PostConstruct
    void init() {
        Locale locale = VaadinSession.getCurrent().getLocale();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("components", locale);
        NAME = resourceBundle.getString("patient.list.table.name");
        SURNAME = resourceBundle.getString("patient.list.table.surname");
        DOB = resourceBundle.getString("patient.list.table.birthdate");
        PREV = resourceBundle.getString("patient.list.button.previous");
        P_CARD = resourceBundle.getString("patient.list.button.patientDetails");
        NEXT = resourceBundle.getString("patient.list.button.next");
        ADD_P = resourceBundle.getString("patient.list.button.add");

        previousPage = new Button(PREV);
        patientDetails = new Button(P_CARD);
        nextPage = new Button(NEXT);
        createPatient = new Button(ADD_P);

        setSpacing(true);
        components.addComponent(previousPage);
        components.addComponent(patientDetails);
        components.addComponent(nextPage);
        components.addComponent(createPatient);
        previousPage.setEnabled(false);
        patientGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        patientGrid.setColumns("name", "surname", "birthdate");
        patientGrid.getColumn("name").setCaption(NAME);
        patientGrid.getColumn("surname").setCaption(SURNAME);
        patientGrid.getColumn("birthdate").setCaption(DOB);
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
            } else {
                Notification.show("Please select one patient");
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


        String userRole = getRole(user);
        if (userRole.equals("ROLE_NURSE")) {
            createPatient.setEnabled(false);
        }
    }
}
