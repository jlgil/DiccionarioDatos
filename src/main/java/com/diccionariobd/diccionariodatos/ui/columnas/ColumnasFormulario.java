package com.diccionariobd.diccionariodatos.ui.columnas;

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

import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;
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
public class ColumnasFormulario extends FormLayout {

	
	private static final Logger log = LoggerFactory.getLogger(ColumnasFormulario.class);	
	
	
	//@Autowired
	//@Qualifier(TablasPresenter.BEAN_NAME)
	//private IPresenter tablasPresenter;
	
	@Autowired
	private ColumnasTablasPresenter camposPresenter;
	
	private ColumnasTabla columnasview;

	private TextField idtabla ;
	private TextField idCampo;
	private TextField nombreCampo ;
	private ComboBox<String> tipoDato;
	private TextField longitudCampo;
	private NativeSelect<String> permiteNulo;
	private NativeSelect<String> campoIndice;
	private TextField descripcionCampo;	
		

	Button salvar = new Button("Guardar",VaadinIcons.BOOK);
	Button cancelar = new Button("Cancelar",VaadinIcons.EXIT);
	HorizontalLayout botones = new HorizontalLayout();
	
	Binder<CamposTablasDTO> camposBinder;
	CamposTablasDTO camposDTO = null;
	
	public String modo;
	
	
	public ColumnasFormulario(){
		this.setSizeFull();
		
		nombreCampo = new TextField("Nombre de Campo");
		idtabla = new TextField("ID Tabla");
		
		idCampo = new TextField("ID Campo");
		
		longitudCampo = new TextField("Longitud de Campo");
		
		descripcionCampo = new TextField("Descripcion de Campo");
		descripcionCampo.setWidth("100%");
		
		tipoDato= new ComboBox("Tipo Dato");
		permiteNulo = new NativeSelect("Permite Nulo S/N");
		
		permiteNulo.setEmptySelectionAllowed(false);
		
		List<String> lista = new ArrayList<String>();
		lista.add("SI");
		lista.add("NO");
		permiteNulo.setItems(lista);
		
		
		campoIndice = new NativeSelect("Indice S/N");
		campoIndice.setItems(lista);
		campoIndice.setEmptySelectionAllowed(false);
		
		botones.addComponents(salvar,cancelar);

		this.addComponents(nombreCampo,idtabla,idCampo,nombreCampo,tipoDato,longitudCampo,permiteNulo,campoIndice,descripcionCampo,botones);
		
		
		camposBinder = new Binder<>(CamposTablasDTO.class);
		iniciarBinder();
		
		//camposBinder.setBean(camposDTO);
		camposDTO = new CamposTablasDTO();
		camposBinder.readBean(camposDTO);
		
		salvar.addClickListener(event -> salvarRegistro());
		cancelar.addClickListener(event -> cancelar());
		


		
	}
	
	private void cancelar() {
		// TODO Auto-generated method stub
		Notification.show("Cancelando Registro");
		camposBinder.readBean(camposDTO);
		
		cerrarWindow();
	}

	private int salvarRegistro() {
		try {
			
			log.debug("Salvanddo Valores desde Tablas Formulario");
			
			// This will make all current validation errors visible
			BinderValidationStatus<CamposTablasDTO> status = camposBinder.validate();

			if (status.hasErrors()) {
			  Notification.show("Error por eel stauts: "
			    + status.getValidationErrors().size());
			}
			
			//camposDTO = new camposDTO();
			camposBinder.writeBean(camposDTO);
			
			if (this.getModo()=="new"){
				camposPresenter.crearCampoTabla(camposDTO);
				Notification.show("Registro Creado con exito..");
			}else if (this.getModo()=="update"){
				camposPresenter.actualizarCampoTabla(camposDTO);
				Notification.show("Registro Actualizado con exito..");
			}else
				throw new ApplicationException(new MensajeErrores("Error Actualizando Registro no esta definido el modo", 7755, "ColumnasFormulario", "SalvarRegistro"), "Error Actualizando Registro no esta definido el modo");
			    
			

			
			//refrescar el grid
			this.columnasview.refrescarGrid();
			cerrarWindow();


			} catch (ValidationException e) {
			  Notification.show("Validation error count: "
			    + e.getValidationErrors().size());
			}
		return 0;
	}

	public void iniciarBinder() {
		
		
		camposBinder.forField(idtabla)
		.asRequired("Campo no puede estar en Blanco")
		.withConverter(new StringToIntegerConverter("Valor debe ser numerico"))
		.bind(CamposTablasDTO::getIdTabla,CamposTablasDTO::setIdTabla);
		
		
		camposBinder.forField(idCampo)
		.asRequired("ID Campo no puede estar en Blanco")
		.withConverter(new StringToIntegerConverter("Valor debe ser numerico"))
		.bind(CamposTablasDTO::getIdCampo,CamposTablasDTO::setIdCampo);
		
		camposBinder.forField(nombreCampo)
		.asRequired("Campo no puede estar en Blanco")
		.bind(CamposTablasDTO::getNameCampo,CamposTablasDTO::setNameCampo);		
		
		camposBinder.forField(tipoDato)
		.asRequired("Tipo dee Tabla no puede estar en Blanco")
		.bind(CamposTablasDTO::getTipoDato,CamposTablasDTO::setTipoDato);		
		
		camposBinder.forField(longitudCampo)
		.asRequired("Longitud de Campo no puede estar en Blanco")
		.withConverter(new StringToIntegerConverter("Valor debe ser numerico"))
		.bind(CamposTablasDTO::getLongCampo,CamposTablasDTO::setLongCampo);	
		
		camposBinder.forField(permiteNulo)
		.asRequired("Tipo dee Tabla no puede estar en Blanco")
		.bind(CamposTablasDTO::getPermiteNulo,CamposTablasDTO::setPermiteNulo);		
		
		
		camposBinder.forField(campoIndice)
		.asRequired("Campo Indice no puede estar en Blanco")
		.bind(CamposTablasDTO::getCampoIndice,CamposTablasDTO::setCampoIndice);
		
		camposBinder.forField(descripcionCampo)
		.asRequired("Descripcion de Tabla no puede estar en Blanco")
		.bind(CamposTablasDTO::getDescripcionCampo,CamposTablasDTO::setDescripcionCampo);
		
		
		
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
	


	public void llenarCampos(){
		this.tipoDato.setItems(this.getCamposPresenter().consultatipoDato());
	}
	
	public void cerrarWindow(){
		for (Iterator iterator = UI.getCurrent().getWindows().iterator(); iterator.hasNext();) {
			Window component = (Window) iterator.next();
			component.close();
		
		}

	}
	
	public void modificarBinder(CamposTablasDTO tabla){
		this.camposBinder.readBean(tabla);
	}



	/**
	 * @return the columnasview
	 */
	public ColumnasTabla getColumnasview() {
		return columnasview;
	}

	/**
	 * @param columnasview the columnasview to set
	 */
	public void setColumnasview(ColumnasTabla columnasview) {
		this.columnasview = columnasview;
	}

	/**
	 * @return the camposPresenter
	 */
	public ColumnasTablasPresenter getCamposPresenter() {
		return camposPresenter;
	}

	/**
	 * @param camposPresenter the camposPresenter to set
	 */
	public void setCamposPresenter(ColumnasTablasPresenter camposPresenter) {
		this.camposPresenter = camposPresenter;
	}
	



}

