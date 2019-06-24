package com.diccionariobd.diccionariodatos.reportes;

import com.diccionariobd.diccionariodatos.dao.dto.AbstracDTO;


public interface IReportes<T extends Object> {

	public void imprimir(T data); 
}
