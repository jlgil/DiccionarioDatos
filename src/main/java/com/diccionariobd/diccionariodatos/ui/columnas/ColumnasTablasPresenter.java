package com.diccionariobd.diccionariodatos.ui.columnas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diccionariobd.diccionariodatos.dao.dto.AbstracDTO;
import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;
import com.diccionariobd.diccionariodatos.service.IColumnasTablas;
import com.diccionariobd.diccionariodatos.service.ITablasService;
import com.diccionariobd.diccionariodatos.ui.presenter.IPresenter;
import com.diccionariobd.diccionariodatos.ui.tablas.TablasPresenter;

@Component(ColumnasTablasPresenter.BEAN_NAME)
@Scope("prototype")

public class ColumnasTablasPresenter {

	public static final String BEAN_NAME = "ColumnasTablasPresente";
	
	@Autowired
	private IColumnasTablas camposService;
	
	public List<CamposTablasDTO> consultarAllTablas() {
		// TODO Auto-generated method stub
		return camposService.consultaAllTablas();
	}
	
	public List<CamposTablasDTO> consultarCamposPorIDTabla(int idTabla) {
		// TODO Auto-generated method stub
		return camposService.consultaCamposTabla(idTabla);
	}
	
	public void actualizarCampoTabla(CamposTablasDTO dato){
		camposService.updateCampos(dato);
		
	}
	
	public void crearCampoTabla(CamposTablasDTO dato){
		camposService.crearCampos(dato);
		
	}
	
	public List<String> consultatipoDato(){
		List<String> listaTipoDatos = new ArrayList();
		
		listaTipoDatos.add("VARCHAR");
		listaTipoDatos.add("CHAR");
		listaTipoDatos.add("INT");
		listaTipoDatos.add("SMALLINT");
		listaTipoDatos.add("TINTYINT");
		listaTipoDatos.add("MONEY");
		listaTipoDatos.add("NUMERIC");
		listaTipoDatos.add("BOOLEAN");
		listaTipoDatos.add("DATETIME");
		listaTipoDatos.add("SMALLDATETIME");
		
		return listaTipoDatos;

		
	}	
    

}
