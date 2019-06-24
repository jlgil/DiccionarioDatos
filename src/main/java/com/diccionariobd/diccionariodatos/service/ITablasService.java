package com.diccionariobd.diccionariodatos.service;

import java.util.List;

import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.exception.ApplicationException;

public interface ITablasService {
	
	public List<TablasDTO> consultaAllTablas();
	
	public TablasDTO consultatabla();
	
	public void crearTabla(TablasDTO dato) ;
	
	public void updateTabla(TablasDTO dato);
	
	public void eliminarTabla(int dato);

}
