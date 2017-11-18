package com.epam.hospital.ui;

import com.epam.hospital.model.User;
import com.epam.hospital.util.LabelsHolder;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import static com.epam.hospital.util.LabelsHolder.*;



import java.util.Locale;
import java.util.ResourceBundle;

public class Menu extends HorizontalLayout {


    private ResourceBundle resourceBundle;
    NativeSelect<String> select;
    Label label;
    public static Locale local;
    public static void initLocale(String locale){
        local = new Locale(locale);
    }


    public Menu(User user) {
//        if(!VaadinSession.getCurrent().getLocale().equals(local)) {
//            VaadinSession.getCurrent().setLocale(local);
//
//        }

        LabelsHolder.chageLocale(VaadinSession.getCurrent().getLocale());
        String sel = "en".equalsIgnoreCase(VaadinSession.getCurrent().getLocale().getLanguage()) ? EN : RU;
        label = new Label();
        label.setCaption(SIGNED + user.getUsername());
        Button buttonLogout = new Button(SIGNOUT);
        buttonLogout.addClickListener(clickEvent -> {
            getUI().getPage().setLocation("/logout");
        });
        select = new NativeSelect<>();
        select.setEmptySelectionAllowed(false);
        select.setItems(RU, EN);
        select.setSelectedItem(sel);
        setSizeFull();
        addComponent(label);
        addComponent(select);
        addComponent(buttonLogout);
        setWidth("100%");
        setComponentAlignment(label, Alignment.TOP_LEFT);
        setComponentAlignment(select, Alignment.TOP_RIGHT);
        setComponentAlignment(buttonLogout, Alignment.TOP_RIGHT);

        select.addValueChangeListener(event -> {// Java 8
            Notification.show("Selected " +
                    event.getValue());
            String lan = event.getValue();

            if (lan.equals("English")){
                VaadinSession.getCurrent().setLocale(new Locale("en"));
            } else if (lan.equals("Русский")) {
                VaadinSession.getCurrent().setLocale(new Locale("ru"));
            }
            Page.getCurrent().reload();
        });

    }
}

