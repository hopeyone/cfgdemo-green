package com.mrhopeyone.green.views.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mrhopeyone.green.services.GreetingService;
import com.mrhopeyone.green.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import jakarta.annotation.PostConstruct;

@PageTitle("Greeting")
@Route(value = "greeting", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class GreetingView extends VerticalLayout {

	@Autowired
	GreetingService greetingService;
	
	@Value("${app.secretpassword}")
	String thePassword;
	
	@Value("${spring.profiles.active: default}")
	private String activeProfile;
	
    private Span name;
    private Span profiles;
    private Span messageText;
    private Span appPassword;

    @PostConstruct
    public void setupView() {
    	
        name = new Span("spring.application.name: " + greetingService.getAppName());
        profiles = new Span("spring.profiles.active: " + activeProfile);
        messageText = new Span("app.mainmessage: " + greetingService.getMessage());
        appPassword = new Span("app.secretpassword: " + thePassword);

        setMargin(true);

        add(name, profiles, messageText, appPassword);
    }

}
