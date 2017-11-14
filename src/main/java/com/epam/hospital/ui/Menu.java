package com.epam.hospital.ui;

import com.epam.hospital.model.User;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

public class Menu extends HorizontalLayout {

    public Menu(User user) {
        Label label = new Label("Singed in: " + user.getUsername());
        Button buttonLogout = new Button("sign out");
        buttonLogout.addClickListener(clickEvent -> {
            getUI().getPage().setLocation("/logout");
        });

        NativeSelect<String> select = new NativeSelect<>();
        select.setItems("Russian", "English");
        select.setSelectedItem("English");
        setSizeFull();
        addComponent(label);
        addComponent(select);
        addComponent(buttonLogout);
        setWidth("100%");
        setComponentAlignment(label, Alignment.TOP_LEFT);
        setComponentAlignment(select, Alignment.TOP_RIGHT);
        setComponentAlignment(buttonLogout, Alignment.TOP_RIGHT);
        select.addValueChangeListener(event -> // Java 8
                Notification.show("Selected " +
                        event.getValue()));
    }
}
