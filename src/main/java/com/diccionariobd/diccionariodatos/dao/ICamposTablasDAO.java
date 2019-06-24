package com.diccionariobd.diccionariodatos.dao;

import java.util.List;

import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;

public interface ICamposTablasDAO extends IGenericaDAO<CamposTablasDTO> {

	public List<CamposTablasDTO> consultaCamposTabla(int idTabla);
	
	public CamposTablasDTO consultaCamposTablaColumna(int idTabla,int idColumna);
}
