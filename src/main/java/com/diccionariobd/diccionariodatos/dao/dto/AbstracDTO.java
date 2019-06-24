package com.diccionariobd.diccionariodatos.dao.dto;

import java.io.Serializable;

import com.diccionariobd.diccionariodatos.model.Tablas;

public abstract class AbstracDTO<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public abstract T toEntity();
    public abstract T toEntity(T entity);
    public abstract Object getId();
	

}
