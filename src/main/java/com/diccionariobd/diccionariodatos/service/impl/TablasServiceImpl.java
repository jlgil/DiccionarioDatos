package com.diccionariobd.diccionariodatos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diccionariobd.diccionariodatos.dao.ITablasDAO;
import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.exception.ApplicationException;
import com.diccionariobd.diccionariodatos.service.ITablasService;

@Service
public class TablasServiceImpl implements ITablasService{
	
	@Autowired
	private ITablasDAO tablas;
	

	@Override
	public List<TablasDTO> consultaAllTablas() {
		
		return tablas.consultaAllRegistros();
	}

	@Override
	public TablasDTO consultatabla() {
		return null;
	}

	@Override
	public void crearTabla(TablasDTO dato)  {
		tablas.crearRegistros(dato);
		
	}

	@Override
	public void updateTabla(TablasDTO dato) {
		tablas.updateRegistro(dato);
		
	}

	@Override
	public void eliminarTabla(int dato) {
		tablas.eliminarRegistro(dato);
		
	}

}
