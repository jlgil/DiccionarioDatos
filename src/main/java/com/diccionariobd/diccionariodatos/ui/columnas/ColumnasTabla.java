package com.diccionariobd.diccionariodatos.ui.columnas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;
import com.diccionariobd.diccionariodatos.dao.dto.ImpresionDTO;
import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.reportes.ReportePDF;
import com.diccionariobd.diccionariodatos.ui.presenter.IPresenter;
import com.diccionariobd.diccionariodatos.ui.tablas.TablasFormulario;
import com.diccionariobd.diccionariodatos.ui.tablas.TablasPresenter;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.components.grid.Editor;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;


@Component
@Scope("prototype")
public class ColumnasTabla extends VerticalLayout{
	

	@Autowired
	private ColumnasTablasPresenter presenterColumnas;
	
	@Autowired
	private ColumnasFormulario formulario;
	
	
	private int idTabla;
	
	private TablasDTO tablaDTO;
	

	private ChangeHandler changeHandler;
	
	private Grid<CamposTablasDTO> gridColumnas;
	
	private Button botonRegreso;
	private Button botonImprimir;
	private Button botonIngreso;
	private Button botonEliminar;
	
	List<CamposTablasDTO> listaCampos = null;
	

	TextField idCampo = new TextField();
	TextField nameCampo = new TextField();
	TextField tipoDatos = new TextField();
	TextField longCampo = new TextField();
	TextField permiteNulo = new TextField();
	TextField campoIndice = new TextField();
	TextField descripcionCampo = new TextField();
	
	HorizontalLayout botones = new HorizontalLayout();
	
	public ColumnasTabla(){
		this.setSizeFull();
		mostrarColumnasGrid();
	}
	
	public void mostrarColumnasGrid(){
		//gridColumnas.setSizeFull();
		//gridColumnas.setCaption("Grid Columas");
		
		gridColumnas = new Grid<>(CamposTablasDTO.class);
		gridColumnas.setSizeFull();
		gridColumnas.setSelectionMode(SelectionMode.SINGLE);
		
		
		
		botonRegreso = new Button("Regresar",VaadinIcons.BACKSPACE_A);	
		
		botonRegreso.addClickListener(clickEvent -> {
			changeHandler.onChange();
		
		});
		

		
		botonImprimir = new Button (VaadinIcons.PRINT);
		
		botonImprimir.addClickListener(clickEvent -> {
			try {
				generarPDF();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		});
		
		botonIngreso = new Button(VaadinIcons.ARCHIVE);
		botonIngreso.setDescription("Ingreso de Campos");
		
		botonIngreso.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				formulario.setCamposPresenter(presenterColumnas);
				formulario.llenarCampos();		
				mostrarFormulario("Ingreso de Campos",null);
				
			}
		});
		
		botonEliminar = new Button(VaadinIcons.DEL);
		botonEliminar.setDescription("Eliminar de Campos");
		
		botonEliminar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {	
				Notification.show("En Construccion....");
				
			}
		});
		
		
		
		
		botones.addComponents(botonRegreso,botonImprimir,botonIngreso,botonEliminar);
		
		//this.addComponent(botonRegreso);
		//this.addComponent(botonImprimir);
		this.addComponent(botones);
		this.addComponent(gridColumnas);
		this.setExpandRatio(gridColumnas, 1);
		

	}
	
	public void generarPDF() throws Throwable {
		Window window = new Window();
	    //((VerticalLayout) window.getContent()).setSizeFull();
	    window.setResizable(true);
	    window.setWidth("800");
	    window.setHeight("600");
	    window.center();
	    window.setModal(true);
	    Embedded e = new Embedded();
	    e.setSizeFull();
	    e.setType(Embedded.TYPE_BROWSER);
	
	    // Here we create a new StreamResource which downloads our StreamSource,
	    // which is our pdf.
	    
	    List<CamposTablasDTO> listaCampos = (List<CamposTablasDTO>) presenterColumnas.consultarCamposPorIDTabla(idTabla);
	    
	    ImpresionDTO impresion = new ImpresionDTO();
	    
	    impresion.setListaCamposTablasDTO(listaCampos);
	    impresion.setTablas(tablaDTO);
	    
	    
	    StreamResource resource = new StreamResource(new ReportePDF(impresion,"C"),"test.pdf");
	    //(new ReportePDF(), "test.pdf?" + System.currentTimeMillis(), this);
	    // Set the right mime type
	    resource.setMIMEType("application/pdf");
        resource.setCacheTime(0);
        
	
	    e.setSource(resource);
	    window.setContent(e);
	    //window.addComponent(e);
	    //getMainWindow().addWindow(window);
	    UI.getCurrent().addWindow(window);
		
	}

/*	public void cargarGrid(int idTabla){
		List<CamposTablasDTO> listaCampos = (List<CamposTablasDTO>) presenterColumnas.consultarCamposPorIDTabla(idTabla);
		
		gridColumnas.removeAllColumns();
		gridColumnas.setItems(listaCampos);

		
		//gridColumnas.setColumnOrder("idCampo","nameCampo","tipoDato","longCampo","permiteNulo","campoIndice");
		//gridColumnas.setColumns("idCampo","nameCampo","tipoDato","longCampo","permiteNulo","campoIndice");
		
		
		
		 TextField idCampo = new TextField();
	TextField nameCampo = new TextField();
	TextField tipoDatos = new TextField();
	TextField longCampo = new TextField();
	TextField permiteNulo = new TextField();
	TextField campoIndice = new TextField();
		 
		

		//Nombre Campo
		gridColumnas.addColumn(CamposTablasDTO::getNameCampo, new TextRenderer())
         .setEditorComponent(nameCampo, CamposTablasDTO::setNameCampo)
         .setCaption("Nombre Campo")
         .setExpandRatio(2);
	
		//tipoDatos
		gridColumnas.addColumn(CamposTablasDTO::getTipoDato, new TextRenderer())
         .setEditorComponent(tipoDatos, CamposTablasDTO::setTipoDato)
         .setCaption("Tipo de Datos")
         .setExpandRatio(2);
		
		//longCampo
		gridColumnas.addColumn(CamposTablasDTO::getLongCampo)
        .setEditorComponent(longCampo, CamposTablasDTO::setLongCampo)
        .setCaption("Longitud de Campos")
        .setExpandRatio(2);
	


		//Permite Nulo
		gridColumnas.addColumn(CamposTablasDTO::getPermiteNulo, new TextRenderer())
         .setEditorComponent(permiteNulo, CamposTablasDTO::setPermiteNulo)
         .setCaption("Permite Nulo")
         .setExpandRatio(2);			
		
		//campoIndice
      	 gridColumnas.addColumn(CamposTablasDTO::getCampoIndice, new TextRenderer())
         .setEditorComponent(campoIndice, CamposTablasDTO::setCampoIndice)
         .setCaption("Campo Indice")
         .setExpandRatio(2);
      	 
 		Grid.Column<CamposTablasDTO,Button> editorColumn = gridColumnas.addComponentColumn(CamposTablasDTO -> {
		    Button edit = new Button("Edit");
		    edit.addClickListener(e -> {
		        //editor.editItem(CamposTablasDTO);
		    	//editor.setEnabled(true);
		    	gridColumnas.getEditor().setEnabled(true);
                
		    });
		    return edit;
		});
 		
		
		gridColumnas.getEditor().setEnabled(true);
		
		gridColumnas.getEditor().addSaveListener(event -> {
			CamposTablasDTO campo = event.getBean();
			Notification.show("Salvando Valores "
			+ "Nombre Campo: " + campo.getNameCampo()
			+ "Tipo de Datos:" + campo.getTipoDato()
			+ "Permite nulo:" + campo.getPermiteNulo()
			+ "Longituddddd: " + campo.getLongCampo(), Notification.TYPE_WARNING_MESSAGE);
			
			
		
		});
		
		
	}
*/	
	
	public void cargarGrid(TablasDTO idTabla){
		
		this.setIdTabla(idTabla.getIdtabla());
		
		this.tablaDTO = idTabla;
		
		List<CamposTablasDTO> listaCampos = (List<CamposTablasDTO>) presenterColumnas.consultarCamposPorIDTabla(idTabla.getIdtabla());
		
		gridColumnas.removeAllColumns();
		gridColumnas.setItems(listaCampos);
		
		Binder<CamposTablasDTO> binder = new Binder<>(CamposTablasDTO.class);
		
		gridColumnas.getEditor().setBinder(binder).setBuffered(true);
		
		gridColumnas.getEditor().setBuffered(true);
		
		//Id Campo
		gridColumnas.addColumn(CamposTablasDTO::getIdCampo, new TextRenderer())
        .setCaption("Id Campo")
        .setExpandRatio(2);

		//Nombre Camp
		gridColumnas.addColumn(CamposTablasDTO::getNameCampo, new TextRenderer())
        .setEditorComponent(nameCampo, CamposTablasDTO::setNameCampo)
        .setCaption("Nombre Campo")
        .setExpandRatio(2);
		
		/*
		//Tipo de Datos
		gridColumnas.addColumn(CamposTablasDTO::getTipoDato, new TextRenderer())
        .setEditorComponent(tipoDatos, CamposTablasDTO::setTipoDato)
        .setCaption("Tipo de Datos")
        .setExpandRatio(2);
		*/
		
		ComboBox combo = new ComboBox<>("Seleccion Tipo de Datos");
		
		List<String> cadenaCombo = new ArrayList<>();
		cadenaCombo.add("char");
		cadenaCombo.add("int");
		cadenaCombo.add("varchar");
		cadenaCombo.add("datetime");
		cadenaCombo.add("money");
		cadenaCombo.add("numeric");
		cadenaCombo.add("smallint");
		cadenaCombo.add("tinyint");
		cadenaCombo.add("catalogo");
		cadenaCombo.add("cuenta");
		cadenaCombo.add("login");
		
		combo.setItems(cadenaCombo);
		
		//Tipo de Datos
		gridColumnas.addColumn(CamposTablasDTO::getTipoDato, new TextRenderer())
        .setEditorComponent(combo, CamposTablasDTO::setTipoDato)
        .setCaption("Tipo de Datos")
        .setExpandRatio(2);
		
		//Longitud
		TextField longitud = new TextField();
		Binder.Binding<CamposTablasDTO, Integer> caloriesBinder = binder.forField(longitud)
				.withConverter(new StringToIntegerConverter("Solo es permitido valores numericos"))
			    .bind(CamposTablasDTO::getLongCampo, CamposTablasDTO::setLongCampo);
		
		
		Grid.Column<CamposTablasDTO,String> editorColdumn = gridColumnas.addColumn(
			    todo -> String.valueOf(todo.getLongCampo()));
		
		editorColdumn.setCaption("Longitud");
		
		editorColdumn.setEditorBinding(caloriesBinder);
		editorColdumn.setExpandRatio(2);
		
		/*		 
		TextField campoIndice = new TextField();
		*/
		
		//Descripcion
		gridColumnas.addColumn(CamposTablasDTO::getDescripcionCampo, new TextRenderer())
        .setEditorComponent(descripcionCampo, CamposTablasDTO::setDescripcionCampo)
        .setCaption("Descripcion")
        .setExpandRatio(3);

		
		//Permite Nulo
		gridColumnas.addColumn(CamposTablasDTO::getPermiteNulo, new TextRenderer())
        .setEditorComponent(permiteNulo, CamposTablasDTO::setPermiteNulo)
        .setCaption("Permite Nulo")
        .setExpandRatio(2);
		
		//Campo Indice
		gridColumnas.addColumn(CamposTablasDTO::getCampoIndice, new TextRenderer())
        .setEditorComponent(campoIndice, CamposTablasDTO::setCampoIndice)
        .setCaption("Campo Indice")
        .setExpandRatio(2);
		
		gridColumnas.getEditor().addSaveListener(event -> {
			CamposTablasDTO campo = event.getBean();
			Notification.show("Salvando Valores "
			+ "Nombre Campo: " + campo.getNameCampo()
			+ "Longituddddd: " + campo.getLongCampo() 
			+ "Descr: " + campo.getDescripcionCampo(),Notification.TYPE_WARNING_MESSAGE);
			
			presenterColumnas.actualizarCampoTabla(campo);
		});		

		/*
		CheckBox doneField = new CheckBox();

		Binder<Todo> binder = grid.getEditor().getBinder();

		Binding<Todo, Boolean> doneBinding = binder.bind(
		    doneField, Todo::isDone, Todo::setDone);

		Column<Todo, String> column = grid.addColumn(
		    todo -> String.valueOf(todo.isDone()));
		column.setEditorBinding(doneBinding);
*/
		

		
		
		
		
		gridColumnas.getEditor().setEnabled(true);
		
		
	}
	
	public void ocultar(){
		this.setVisible(false);
	}
	
	 public interface ChangeHandler {
	        void onChange();
	    }
	 
	   public void setChangeHandler(ChangeHandler h) {
	        changeHandler = h;
	    }
	   
		/**
		 * @return the idTabla
		 */
		public int getIdTabla() {
			return idTabla;
		}

		/**
		 * @param idTabla the idTabla to set
		 */
		public void setIdTabla(int idTabla) {
			this.idTabla = idTabla;
		}

		/**
		 * @return the tablaDTO
		 */
		public TablasDTO getTablaDTO() {
			return tablaDTO;
		}

		/**
		 * @param tablaDTO the tablaDTO to set
		 */
		public void setTablaDTO(TablasDTO tablaDTO) {
			this.tablaDTO = tablaDTO;
		}

		public void refrescarGrid() {
			gridColumnas.getDataProvider().refreshAll();
			
		}

		private void mostrarFormulario(String caption,CamposTablasDTO campos) {
			Window window = new Window();
		    //((VerticalLayout) window.getContent()).setSizeFull();
		    window.setResizable(true);
		    window.setWidth("800");
		    window.setHeight("600");
		    window.center();
		    window.setModal(true);
		    window.setCaption(caption);
		    window.setClosable(false);
		    

		    //window.setContent(new TablasFormulario());
		    //formaInfreso.setTablasPresenter(tablasPresenter);
		    //formaInfreso.llenarCampos();
		    formulario.setColumnasview(this);
		    
		    if (campos != null){
		    	formulario.modificarBinder(campos);
		    	formulario.setModo("update");
		    }else{
		    	formulario.setModo("new");
		    }
		    	
		    
		    window.setContent(formulario);
		    UI.getCurrent().addWindow(window);

			
		}


}
