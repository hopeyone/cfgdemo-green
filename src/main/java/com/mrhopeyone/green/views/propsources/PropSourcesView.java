package com.mrhopeyone.green.views.propsources;

import com.mrhopeyone.green.data.SamplePerson;
import com.mrhopeyone.green.services.SamplePersonService;
import com.mrhopeyone.green.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.context.support.StandardServletEnvironment;

@PageTitle("Prop Sources")
@Route(value = "propSources", layout = MainLayout.class)
public class PropSourcesView extends Div {

	Environment env;
	Grid<MapPropertySource> grid;
	PropertySourceDisplay propDisplay;
	
	private static final String LIT_TEMPLATE_HTML = """
            <vaadin-button title="Go to ..."
                           @click="${clickHandler}"
                           theme="tertiary-inline small link">
                ${item.id}
            </vaadin-button>""";

	
	public PropSourcesView(Environment env, PropertySourceDisplay propDisplay)
	{
		this.env = env;
		
		grid = new Grid<>(MapPropertySource.class);
		
        grid.setHeight("200px");
        //grid.setColumns("name");
        //grid.getColumnByKey("name").setWidth("50px").setFlexGrow(0);
        grid.setColumns();
        grid.addColumn(LitRenderer.<MapPropertySource>of(LIT_TEMPLATE_HTML)
                .withProperty("id", MapPropertySource::getName)
                .withFunction("clickHandler", propSource -> {
                    propDisplay.setProperties(getPropsFromSource(propSource));
                })).setHeader("Property Source");
        grid.setItems(getPropertySources(env));
		add(grid);
		add(propDisplay);
	}


    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }
    
	private Collection<SingleProperty> getPropsFromSource(MapPropertySource propSource)
	{
		return Arrays.stream(propSource.getPropertyNames())
				.map((name) -> new SingleProperty(name, propSource.getProperty(name).toString()))
				.collect(Collectors.toList());
	}
	private List<MapPropertySource> getPropertySources(Environment environment)
	{
		List<MapPropertySource> psList = new ArrayList<>();
	    if(environment != null && environment instanceof StandardServletEnvironment) {
	        StandardServletEnvironment env = (StandardServletEnvironment)environment;
	        MutablePropertySources mutablePropertySources = env.getPropertySources();
	        if(mutablePropertySources != null) {
	            for (PropertySource<?> propertySource : mutablePropertySources) {
	                if(propertySource instanceof MapPropertySource) {
	                    MapPropertySource mapPropertySource = (MapPropertySource)propertySource;
	                    if(mapPropertySource.getPropertyNames() != null) {
	                        System.out.println(propertySource.getName());
	                        String[] propertyNames = mapPropertySource.getPropertyNames();
	                        for (String propertyName : propertyNames) {
	                            Object val = mapPropertySource.getProperty(propertyName);
	                            System.out.print(propertyName);
	                            System.out.print(" = " + val);
	                        }
	                        psList.add(mapPropertySource);
	                    }
	                    
	                }
	                //psList.add(propertySource);
	            }
	        }
	    }
	    return psList;
	}
}
