package com.epam.hospital.ui;

import com.epam.hospital.model.User;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.hibernate.Session;

import java.util.Locale;
import java.util.ResourceBundle;

public class Menu extends HorizontalLayout {

    private String RU;
    private String EN;
    private String SIGNED;
    private String SIGNOUT;
    private static String SELECTED_LANG;
    private Locale locale;
    private ResourceBundle resourceBundle;
    NativeSelect<String> select;
    Label label;

    public Menu(User user) {
        locale = VaadinSession.getCurrent().getLocale();
        resourceBundle = ResourceBundle.getBundle("components", locale);
        RU = resourceBundle.getString("menu.select.russian");
        EN = resourceBundle.getString("menu.select.english");
        SIGNED = resourceBundle.getString("menu.signed.caption");
        SIGNOUT = resourceBundle.getString("menu.signout.button");
        String sel = SELECTED_LANG == null ? EN : SELECTED_LANG;
        label = new Label();
        label.setCaption(SIGNED + user.getUsername());
        Button buttonLogout = new Button(SIGNOUT,
                new ExternalResource("/login?logout"));
        select = new NativeSelect<>();
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

            if (lan.equals("English") || lan.equals("Английский")) {
                VaadinSession.getCurrent().setLocale(new Locale("en_US"));
                ResourceBundle rb =ResourceBundle.getBundle("components", VaadinSession.getCurrent().getLocale());
                SELECTED_LANG = rb.getString("menu.select.english");
            } else if (lan.equals("Russian") || lan.equals("Русский")) {
                VaadinSession.getCurrent().setLocale(new Locale("ru"));
                ResourceBundle rb =ResourceBundle.getBundle("components", VaadinSession.getCurrent().getLocale());
                SELECTED_LANG = rb.getString("menu.select.russian");
            }
            Page.getCurrent().reload();
        });

    }
}