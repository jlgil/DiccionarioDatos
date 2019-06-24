package com.diccionariobd.diccionariodatos.ui.tablas;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;
import com.diccionariobd.diccionariodatos.dao.dto.ImpresionDTO;
import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.exception.ApplicationException;
import com.diccionariobd.diccionariodatos.model.MensajeErrores;
import com.diccionariobd.diccionariodatos.reportes.ReportePDF;
import com.diccionariobd.diccionariodatos.ui.columnas.ColumnasFormulario;
import com.diccionariobd.diccionariodatos.ui.columnas.ColumnasTabla;
import com.diccionariobd.diccionariodatos.ui.columnas.ColumnasTablasPresenter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.components.grid.DetailsGenerator;
import com.vaadin.ui.components.grid.ItemClickListener;

import ch.qos.logback.core.subst.Token.Type;
import de.steinwedel.messagebox.MessageBox;

import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Component
@Scope("prototype")
public class TablasView extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(TablasView.class);
	
	private List<TablasDTO> listTablas;
	
	private Grid<TablasDTO> tablasGrid;
	
	private Button botonIngresoTabla = new Button("Ingresar Tabla",VaadinIcons.ADD_DOCK);
	
	
	@Autowired
	private ColumnasTabla columnasTablas;
	
	//@Autowired
	//@Qualifier(TablasPresenter.BEAN_NAME)
	//private IPresenter tablasPresenter;
	
	@Autowired
	private TablasPresenter tablasPresenter;
	
	@Autowired
	private TablasFormulario formaInfreso;
	
	@Autowired
	private ColumnasTablasPresenter columnasPresenter;	
	
	private TablasDTO itemAnterior = null;
	
	
	public TablasView(){
		this.setSizeFull();
		//log.debug("Desde Tablas View");
	}
	
	
	public void iniGrid(){
		 this.setVisible(true);
		 columnasTablas.setVisible(false);
		
		 this.tablasGrid = new Grid<>(TablasDTO.class);
		 
		 tablasGrid.setSelectionMode(SelectionMode.SINGLE);
		 
	     this.listTablas = (List<TablasDTO>) tablasPresenter.consultarAllTablas();
	       
		   // Items
	     this.tablasGrid.setItems(listTablas);
	     	
	     this.tablasGrid.setSizeFull();
	     
	     this.addComponent(botonIngresoTabla);
	     this.addComponent(tablasGrid);
	     this.addComponent(columnasTablas);
	     this.setExpandRatio(tablasGrid, 1);
	     
	     botonIngresoTabla.addClickListener(event -> mostrarFormulario("Ingreso de Tabla",null));
	    
     
	     tablasGrid.addItemClickListener(new ItemClickListener<TablasDTO>() {

			@Override
			public void itemClick(ItemClick<TablasDTO> event) {
				if (event.getMouseEventDetails().isDoubleClick()){
					
					if (tablasGrid.isDetailsVisible(itemAnterior)){
						tablasGrid.setDetailsVisible(itemAnterior, false);
					}

		    		if (tablasGrid.isDetailsVisible(event.getItem())){
		    			tablasGrid.setDetailsVisible(event.getItem(), false);
		    			itemAnterior = null;
					}else {
						tablasGrid.setDetailsVisible(event.getItem(), true);
						itemAnterior = event.getItem();
					}
				}
				else{
					if (tablasGrid.isDetailsVisible(itemAnterior)){
						tablasGrid.setDetailsVisible(itemAnterior, false);
					}
				}
				
				
				
			}
	    	 
	     });
	     
	     

	     
	     
	     columnasTablas.setChangeHandler(() -> {
	    	 columnasTablas.setVisible(false);
	    	 tablasGrid.setVisible(true);
	    	 botonIngresoTabla.setVisible(true);
	        });	 
	     
	     ;
	
	     tablasGrid.setDetailsGenerator(t ->{
				VerticalLayout detalleLayout = new VerticalLayout();
				
				TextField nombreTabla = new TextField("Nombre Tabla");
				nombreTabla.setValue(t.getNombreTabla());
				
				Button editarTabla = new Button("Editar", new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						
						mostrarFormulario("Actualizar Tabla",t);
						
					}
				});
				Button borrarTabla = new Button("Borrar", new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						MessageBox
					    .createQuestion()
					    .withCaption("Confirmacion")
					    .withMessage("Desea Eliminar la tabla: " + t.getNombreTabla() + "?")
					    .withYesButton(() -> { eliminarRegistro(t); })
					    .withNoButton(() -> { System.out.println("No button was pressed."); })
					    .open();
					}


				});
				
				Button verCamposTablas = new Button("Ver Campos", new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
				    	if (tablasGrid.isDetailsVisible(t)){
			    			tablasGrid.setDetailsVisible(t, false);
     					}

				    	 tablasGrid.setVisible(false);
				    	 botonIngresoTabla.setVisible(false);
				    	 columnasTablas.setVisible(true);
				    	 columnasTablas.cargarGrid(t);
				    	 
						
					}
					
				});
				
				Button imprimirTabla = new Button("Imprimir Tabla", new ClickListener(){

					@Override
					public void buttonClick(ClickEvent event) {
						Notification.show("En Construccion...Impresion");
						
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

					    List<TablasDTO> listaTablas = new ArrayList<TablasDTO>();
					    
					    listaTablas.add(t);

					    StreamResource resource;
						try {
							resource = new StreamResource(new ReportePDF(listaTablas,columnasPresenter),"diccionario.pdf");
						} catch (Throwable e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							log.debug("Desde el Error de Impresion de tabla: " +e1.getMessage());
							
							throw new ApplicationException(new MensajeErrores(e1.getMessage(),79999, "TablasView", "Impresion"), e1.getMessage());
						}
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
					
				});
				
				
				HorizontalLayout botones = new HorizontalLayout(editarTabla,borrarTabla,verCamposTablas,imprimirTabla);
				
				detalleLayout.addComponent(nombreTabla);
				detalleLayout.addComponent(botones);
						
				
				return detalleLayout;
	    	 
	     });
	     
	     
		 formaInfreso.setTablasPresenter(tablasPresenter);
		 formaInfreso.llenarCampos();


		
	}


	private void mostrarFormulario(String caption,TablasDTO tablas) {
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
	    formaInfreso.setTablasview(this);
	    
	    if (tablas != null){
	    	formaInfreso.modificarBinder(tablas);
	    	formaInfreso.setModo("update");
	    }else{
	    	formaInfreso.setModo("new");
	    }
	    	
	    
	    window.setContent(formaInfreso);
	    UI.getCurrent().addWindow(window);

		
	}
	
    public void refrescarGrid(){
	     this.listTablas = (List<TablasDTO>) tablasPresenter.consultarAllTablas();
	       
	     this.tablasGrid.setItems(listTablas);
    }
    
	private void eliminarRegistro(TablasDTO t) {
		tablasPresenter.eliminarTabla(t);
		this.listTablas.remove(t);
		tablasGrid.getDataProvider().refreshAll();
		Notification.show("Confirmacion", "Registro eliminado con exito", Notification.TYPE_HUMANIZED_MESSAGE);
		//refrescarGrid();
		
	}




}
