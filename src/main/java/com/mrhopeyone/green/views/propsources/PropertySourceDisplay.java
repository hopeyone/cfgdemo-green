package com.mrhopeyone.green.views.propsources;

import java.util.Collection;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class PropertySourceDisplay  extends VerticalLayout {

	Grid<SingleProperty> grid;
	

	public PropertySourceDisplay() {
		grid = new Grid<>(SingleProperty.class);
		add(grid);
	}
	
	public void setProperties(Collection<SingleProperty> items)
	{
		grid.setItems(items);
	}
}