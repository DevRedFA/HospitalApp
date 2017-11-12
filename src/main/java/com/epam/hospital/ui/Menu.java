package com.epam.hospital.ui;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

public class Menu extends CustomComponent {

    public Menu() {
        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout menu = new HorizontalLayout();
//        final String userName = vaadinRequest.getRemoteUser();
        Label label = new Label("Singed in: ");
        Button buttonLogout = new Button("sign out",
                new ExternalResource("/login?logout"));
//        menu.setSizeFull();
        menu.addComponent(label);
        menu.addComponent(buttonLogout);
        layout.addComponent(menu);
        layout.setWidth("95%");
        layout.setComponentAlignment(menu, Alignment.MIDDLE_RIGHT);
    }
}