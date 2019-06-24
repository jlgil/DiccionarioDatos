package com.diccionariobd.diccionariodatos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diccionariobd.diccionariodatos.dao.ICamposTablasDAO;
import com.diccionariobd.diccionariodatos.dao.ITablasDAO;
import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;
import com.diccionariobd.diccionariodatos.service.IColumnasTablas;

@Service
public class ColumnasTablasImpl implements IColumnasTablas {
	
	@Autowired
	private ICamposTablasDAO camposTablas;

	@Override
	public List<CamposTablasDTO> consultaAllTablas() {
		// TODO Auto-generated method stub
		return camposTablas.consultaAllRegistros();
	}

	@Override
	public CamposTablasDTO consultaCampos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearCampos(CamposTablasDTO dato) {
      camposTablas.crearRegistros(dato);
		
	}



	@Override
	public void eliminarCampos(int dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CamposTablasDTO> consultaCamposTabla(int idTabla) {
		
		return camposTablas.consultaCamposTabla(idTabla);
	}

	@Override
	public CamposTablasDTO consultaCamposTablaColumna(int idTabla, int idColumna) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCampos(CamposTablasDTO dato) {

		camposTablas.updateRegistro(dato);
		//camposTablas.crearRegistros(dato);
			
	}

}
