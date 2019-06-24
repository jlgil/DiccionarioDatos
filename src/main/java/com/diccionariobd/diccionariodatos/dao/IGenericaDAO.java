package com.diccionariobd.diccionariodatos.dao;

import java.util.List;

import com.diccionariobd.diccionariodatos.dao.dto.AbstracDTO;
import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.exception.ApplicationException;

public interface IGenericaDAO  <T extends AbstracDTO>{
	
	public List<T> consultaAllRegistros();
	
	public T consultaOneRegistro(int dato);
	
	public void crearRegistros(T dato) throws ApplicationException;
	
	public void updateRegistro(T dato);
	
	public void eliminarRegistro(int dato);

}
