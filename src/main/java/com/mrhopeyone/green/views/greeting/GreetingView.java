package com.mrhopeyone.green.views.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mrhopeyone.green.views.MainLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import jakarta.annotation.PostConstruct;

@PageTitle("Greeting")
@Route(value = "greeting", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class GreetingView extends VerticalLayout {

	@Value("${app.mainmessage}")
	String theMessage;
	@Value("${app.secretpassword}")
	String thePassword;
	
	@Value("${spring.application.name}")
	private String appName;
	@Value("${spring.profiles.active: default}")
	private String activeProfile;
	@Value("${app.version2property: *NOPE*}")
	private String version2Property;
	
    private Span name;
    private Span profiles;
    private Span messageText;
    private Span appPassword;

    private Span version2prop;

    @PostConstruct
    public void setupView() {
    	
        name = new Span("spring.application.name: " + appName);
        profiles = new Span("spring.profiles.active: " + activeProfile);
        messageText = new Span("app.mainmessage: " + theMessage);
        appPassword = new Span("app.secretpassword: " + thePassword);
        version2prop = new Span("app.version2property: " + version2Property);

        setMargin(true);

        add(name, profiles, messageText, appPassword, version2prop);
    }

}
