package com.epam.hospital.views;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.util.LabelsHolder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.epam.hospital.ui.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import static com.epam.hospital.util.LabelsHolder.*;
import static com.epam.hospital.util.Utils.getRole;

@SuppressWarnings("serial")
@UIScope
@SpringView
public class PatientsView extends VerticalLayout implements View {

    @Autowired
    PatientService patientService;

    @Setter
    User user;

    private Grid<Patient> patientGrid = new Grid<>(Patient.class);
    private Button previousPage;
    private Button patientDetails;
    private Button nextPage;
    private Button createPatient;
    private Button deletePatient;
    HorizontalLayout components = new HorizontalLayout();
    Menu menu;


    @PostConstruct
    void init() {
        previousPage = new Button(PREV);
        patientDetails = new Button(P_CARD);
        nextPage = new Button(NEXT);
        createPatient = new Button(ADD_P);
        deletePatient = new Button(DEL_P);

        setSpacing(true);
        components.addComponent(previousPage);
        components.addComponent(nextPage);
        components.addComponent(patientDetails);
        components.addComponent(createPatient);
        components.addComponent(deletePatient);
        previousPage.setEnabled(false);
        patientGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        patientGrid.removeAllColumns();
        patientGrid.addColumn(Patient::getName).setCaption(NAME);
        patientGrid.addColumn(Patient::getSurname).setCaption(SURNAME);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
        patientGrid.addColumn(s -> simpleDateFormat.format(s.getBirthdate())).setCaption(DOB);
        patientGrid.setDescription("Patients");
        List<Patient> firstPartOfPatients = patientService.getFirstPartOfPatients();
        patientGrid.setItems(firstPartOfPatients);
        previousPage.addClickListener(clickEvent -> {
            patientGrid.setItems(patientService.getPreviousPartOfPatients());
            nextPage.setEnabled(true);
            previousPage.setEnabled(patientService.isPreviousPageAvailable());
        });
        patientGrid.addItemClickListener(item -> {
            if (item.getMouseEventDetails().isDoubleClick()) {
                Patient patient = (Patient) item.getItem();
                String s = MainUI.CARD + "/" + patient.getId();
                getUI().getNavigator().navigateTo(s);
            }
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
                Notification.show(SELECTPATIENT);
            }
        });
        deletePatient.addClickListener(clickEvent -> {
            Set selectedItems = patientGrid.getSelectedItems();
            if (selectedItems.size() == 1) {
                Object[] objects = selectedItems.toArray();
                Patient selectedPatient = (Patient) objects[0];
                patientService.deletePatient(selectedPatient);
                patientService.getAllPatients();
                patientGrid.setItems(patientService.updatePartOfPatients());
//                Page.getCurrent().reload();
            } else {
                Notification.show(SELECTPATIENT);
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
        addComponent(components);
        addComponent(patientGrid);
        patientGrid.setWidth("100%");
        patientGrid.setHeightByRows(16);

        setSizeFull();
        String userRole = getRole(user);
        if (userRole.equals("ROLE_NURSE")) {
            createPatient.setEnabled(false);
            deletePatient.setEnabled(false);
        }
    }
}
