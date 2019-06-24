package com.diccionariobd.diccionariodatos.dao.impl;

import java.util.List;

import com.diccionariobd.diccionariodatos.dao.IGenericaDAO;
import com.diccionariobd.diccionariodatos.dao.dto.AbstracDTO;

public class AbstractoDaoImpl<T extends AbstracDTO> implements IGenericaDAO<T> {

	@Override
	public List<T> consultaAllRegistros() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void crearRegistros(T dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRegistro(T dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarRegistro(int dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T consultaOneRegistro(int dato) {
		// TODO Auto-generated method stub
		return null;
	}

}
 