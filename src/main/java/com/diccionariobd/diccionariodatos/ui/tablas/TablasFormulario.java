package com.diccionariobd.diccionariodatos.ui.tablas;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.dao.impl.TablasDaoImpl;
import com.diccionariobd.diccionariodatos.exception.ApplicationException;
import com.diccionariobd.diccionariodatos.model.MensajeErrores;
import com.diccionariobd.diccionariodatos.ui.presenter.IPresenter;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClientMethodInvocation;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Extension;
import com.vaadin.server.Resource;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.shared.Registration;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.declarative.DesignContext;

import elemental.json.JsonObject;

@Component
@Scope("prototype")
public class TablasFormulario extends FormLayout {

	
	private static final Logger log = LoggerFactory.getLogger(TablasFormulario.class);	
	
	
	//@Autowired
	//@Qualifier(TablasPresenter.BEAN_NAME)
	//private IPresenter tablasPresenter;
	
	@Autowired
	private TablasPresenter tablasPresenter;
	
	private TablasView tablasview;

	private TextField nombreBaseDatos ;
	private TextField idtabla ;
	private TextField nombreTabla;
	private DateField fechaTabla;
	private TextField descripcionTabla;
	private ComboBox<String> tipoTabla;
	private NativeSelect<String> dataSensible;
	private ComboBox<String> frecuenciaRespaldo;
	private ComboBox<String> tipoParticion;	
	

	Button salvar = new Button("Guardar",VaadinIcons.BOOK);
	Button cancelar = new Button("Cancelar",VaadinIcons.EXIT);
	HorizontalLayout botones = new HorizontalLayout();
	
	Binder<TablasDTO> tablasBinder;
	TablasDTO tablasDTO = null;
	public String modo;
	
	
	public TablasFormulario(){
		this.setSizeFull();
		
		nombreBaseDatos = new TextField("Nombre Base Datos");
		idtabla = new TextField("ID Tabla");
		nombreTabla= new TextField("Nombre Tabla");
		fechaTabla = new DateField("Fecha Creacion");
		
		descripcionTabla = new TextField("Descripcion de Tabla");
		descripcionTabla.setWidth("100%");
		tipoTabla= new ComboBox("Tipo Tabla");
		dataSensible = new NativeSelect("Data Sensible S/N");
		
		dataSensible.setEmptySelectionAllowed(false);
		
		List<String> lista = new ArrayList<String>();
		lista.add("SI");
		lista.add("NO");
		
		
		dataSensible.setItems(lista);
		frecuenciaRespaldo = new ComboBox("Frecuencia Respaldo");
		tipoParticion = new ComboBox("Tipo Particion");	
		
		
		
		
		botones.addComponents(salvar,cancelar);
		this.addComponents(nombreBaseDatos,idtabla,nombreTabla,fechaTabla,descripcionTabla,dataSensible,frecuenciaRespaldo,tipoParticion,tipoTabla,botones);
		
		
		tablasBinder = new Binder<>(TablasDTO.class);
		iniciarBinder();
		
		//tablasBinder.setBean(tablasDTO);
		tablasDTO = new TablasDTO();
		tablasBinder.readBean(tablasDTO);
		
		salvar.addClickListener(event -> salvarRegistro());
		cancelar.addClickListener(event -> cancelar());
		
		//this.setTablasPresenter(tablasPresenter);
		//this.llenarCampos();
		

		
	}
	
	private void cancelar() {
		// TODO Auto-generated method stub
		Notification.show("Cancelando Registro");
		tablasBinder.readBean(tablasDTO);
		
		cerrarWindow();
	}

	private int salvarRegistro() {
		try {
			
			log.debug("Salvanddo Valores desde Tablas Formulario");
			
			// This will make all current validation errors visible
			BinderValidationStatus<TablasDTO> status = tablasBinder.validate();

			if (status.hasErrors()) {
			  Notification.show("Error por eel stauts: "
			    + status.getValidationErrors().size());
			}
			
			//tablasDTO = new TablasDTO();
			tablasBinder.writeBean(tablasDTO);
			
			if (this.getModo()=="new"){
				tablasPresenter.creaTabla(tablasDTO);
				Notification.show("Registro Creado con exito..");
			}else if (this.getModo()=="update"){
				tablasPresenter.updateTabla(tablasDTO);
				Notification.show("Registro Actualizado con exito..");
			}else
				throw new ApplicationException(new MensajeErrores("Error Actualizando Registro no esta definido el modo", 7755, "TablasFormulario", "SalvarRegistro"), "Error Actualizando Registro no esta definido el modo");
			    
			

			
			//refrescar el grid
			this.tablasview.refrescarGrid();
			cerrarWindow();


			} catch (ValidationException e) {
			  Notification.show("Validation error count: "
			    + e.getValidationErrors().size());
			}
		return 0;
	}

	public void iniciarBinder() {
		
	
		tablasBinder.forField(nombreBaseDatos)
		.asRequired("Campo no puede estar en Blanco")
		.bind(TablasDTO::getNombreBaseDatos,TablasDTO::setNombreBaseDatos);
		
		tablasBinder.forField(idtabla)
		.asRequired("Campo no puede estar en Blanco")
		.withConverter(new StringToIntegerConverter("Valor debe ser numerico"))
		.bind(TablasDTO::getIdtabla,TablasDTO::setIdtabla);
		
		tablasBinder.forField(nombreTabla)
		.asRequired("Campo no puede estar en Blanco")
		.bind(TablasDTO::getNombreTabla,TablasDTO::setNombreTabla);
		
		tablasBinder.forField(fechaTabla)
        .withConverter(new LocalDateToDateConverter(ZoneId.systemDefault()))
        .bind(TablasDTO::getFechaTabla, TablasDTO::setFechaTabla);
		
		tablasBinder.forField(descripcionTabla)
		.asRequired("Descripcion de Tabla no puede estar en Blanco")
		.bind(TablasDTO::getDescripcion,TablasDTO::setDescripcion);
		
		tablasBinder.forField(tipoTabla)
		.asRequired("Tipo dee Tabla no puede estar en Blanco")
		.bind(TablasDTO::getTipoTabla,TablasDTO::setTipoTabla);
		
		tablasBinder.forField(dataSensible)
		.asRequired("Tipo dee Tabla no puede estar en Blanco")
		.bind(TablasDTO::getDataSensible,TablasDTO::setDataSensible);

		tablasBinder.forField(frecuenciaRespaldo)
		.asRequired("Frecuencia de Respaldo no puede estar en Blanco")
		.bind(TablasDTO::getFrecuenciaRespaldo,TablasDTO::setFrecuenciaRespaldo);
		
		tablasBinder.forField(tipoParticion)
		.asRequired("Tipo de Particion no puede estar en Blanco")
		.bind(TablasDTO::getTipoParticion,TablasDTO::setTipoParticion);
		
		
	}

	/**
	 * @return the tablasPresenter
	 */
	public TablasPresenter getTablasPresenter() {
		return tablasPresenter;
	}
	
	/**
	 * @return the modo
	 */
	public String getModo() {
		return modo;
	}

	/**
	 * @param modo the modo to set
	 */
	public void setModo(String modo) {
		this.modo = modo;
	}
	

	/**
	 * @param tablasPresenter the tablasPresenter to set
	 */
	public void setTablasPresenter(TablasPresenter tablasPresenter) {
		this.tablasPresenter = tablasPresenter;
	}
	
	/**
	 * @return the tablasview
	 */
	public TablasView getTablasview() {
		return tablasview;
	}

	/**
	 * @param tablasview the tablasview to set
	 */
	public void setTablasview(TablasView tablasview) {
		this.tablasview = tablasview;
	}

	public void llenarCampos(){
		this.tipoTabla.setItems(this.getTablasPresenter().consultaTipoTabla());
		this.frecuenciaRespaldo.setItems(this.getTablasPresenter().consultaFrecuenciaRespaldo());
		this.tipoParticion.setItems(this.getTablasPresenter().consultaTipoParticion());
	}
	
	public void cerrarWindow(){
		for (Iterator iterator = UI.getCurrent().getWindows().iterator(); iterator.hasNext();) {
			Window component = (Window) iterator.next();
			component.close();
		
		}

	}
	
	public void modificarBinder(TablasDTO tabla){
		this.tablasBinder.readBean(tabla);
	}
	



}

