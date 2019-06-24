package com.diccionariobd.diccionariodatos.ui.tablas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.exception.ApplicationException;
import com.diccionariobd.diccionariodatos.service.ITablasService;


@Component(TablasPresenter.BEAN_NAME)
@Scope("prototype")
public class TablasPresenter {

    public static final String BEAN_NAME = "TablasPresenter";
    
	@Autowired
	private ITablasService tablasService;


	public List<TablasDTO> consultarAllTablas() {
		// TODO Auto-generated method stub

		return tablasService.consultaAllTablas();
	}
	
	public List<String> consultaTipoTabla(){
		
		List<String> listaTipoTabla = new ArrayList();
		
		listaTipoTabla.add("PARAMETRICA");
		listaTipoTabla.add("DATOS");
		listaTipoTabla.add("TRANSACCIONAL");
		
		return listaTipoTabla;
		
	}
	
	public List<String> consultaFrecuenciaRespaldo(){
		
		List<String> listaFrecuenciaRespaldo = new ArrayList();
		
		listaFrecuenciaRespaldo.add("DIARIA");
		listaFrecuenciaRespaldo.add("MENSUAL");
		listaFrecuenciaRespaldo.add("TRIMESTRAL");
		listaFrecuenciaRespaldo.add("ANUAL");
		listaFrecuenciaRespaldo.add("SEMESTRAL");
		listaFrecuenciaRespaldo.add("N/A");
		
		return listaFrecuenciaRespaldo;
		
	}

	public List<String> consultaTipoParticion(){
		
		List<String> listaTipoParticion = new ArrayList();
		
		listaTipoParticion.add("DATAROWS");
		listaTipoParticion.add("PAGEROWS");
		
		return listaTipoParticion;
		
	}
	
	public void creaTabla(TablasDTO tabla) {
		tabla.setDataSensible(tabla.getDataSensible().substring(0,1));
		tablasService.crearTabla(tabla);
	}
	
	public void updateTabla(TablasDTO tabla) {
		tabla.setDataSensible(tabla.getDataSensible().substring(0,1));
		tablasService.updateTabla(tabla);
	}
	
	public void eliminarTabla(TablasDTO tabla) {
		tablasService.eliminarTabla(tabla.getIdtabla());
	}


	

}
