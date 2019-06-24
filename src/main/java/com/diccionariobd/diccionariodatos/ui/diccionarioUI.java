package com.diccionariobd.diccionariodatos.ui;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.exception.UIErrorHandler;
import com.diccionariobd.diccionariodatos.service.ITablasService;
import com.diccionariobd.diccionariodatos.ui.presenter.IPresenter;
import com.diccionariobd.diccionariodatos.ui.tablas.TablasView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


/**
 * Vaadin UI initializer
 */
@Title("Diccionario")
@Theme("persephone")
@SpringUI(path = "/diccionario")
//@SpringViewDisplay
public class diccionarioUI extends UI /*implements ViewDisplay */{

	private Panel springViewDisplay;
	
	
	private List<TablasDTO> listTablas;
	
	private Grid<TablasDTO> tablasGrid;
	
	@Autowired
	private TablasView tablasView;
	
	//@Autowired
	//@Qualifier(TablasPresenter.BEAN_NAME)
	//private IPresenter tablasPresenter;
	
	
	/*
	@Override
	public void showView(View view) {
		// TODO Auto-generated method stub
		
	}
	*/

	@Override
	protected void init(VaadinRequest request) {
		// Root layout
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();

        root.setSpacing(false);
        root.setMargin(false);

        setContent(root);        
        
        tablasView.iniGrid(); 
        root.addComponent(tablasView);
        root.setExpandRatio(tablasView, 1);

        // Footer
        Layout footer = getFooter();
        root.addComponent(footer);
        root.setExpandRatio(footer, 0);

        // Error handler
		UI.getCurrent().setErrorHandler(new UIErrorHandler());

		// Disable session expired notification, the page will be reloaded on any action
		VaadinService.getCurrent().setSystemMessagesProvider(
				systemMessagesInfo -> {
					CustomizedSystemMessages msgs = new CustomizedSystemMessages();
					msgs.setSessionExpiredNotificationEnabled(false);
					return msgs;
				});

		
	}

	private Layout getFooter() {
		Layout footer = new HorizontalLayout();

		footer.addComponent(new Label("Diccionario v15.5"));
		footer.addComponent(new Link("Created by Vianney FAIVRE", new ExternalResource("https://vianneyfaiv.re"), "_blank", 0, 0, BorderStyle.DEFAULT));
		footer.addComponent(new Link("GitHub", new ExternalResource("https://github.com/vianneyfaivre/Persephone"), "_blank", 0, 0, BorderStyle.DEFAULT));

		footer.setHeight(20, Unit.PIXELS);
		footer.setStyleName("persephone-footer");
		return footer;
	}
	

}
