package com.diccionariobd.diccionariodatos.dao.dto;

import java.util.List;

public class ImpresionDTO {
	
	private TablasDTO tablas;
	
	private List<TablasDTO> listaTablasDTO;
	private List<CamposTablasDTO> listaCamposTablasDTO;
	/**
	 * @return the tablas
	 */
	public TablasDTO getTablas() {
		return tablas;
	}
	/**
	 * @param tablas the tablas to set
	 */
	public void setTablas(TablasDTO tablas) {
		this.tablas = tablas;
	}
	/**
	 * @return the listaTablasDTO
	 */
	public List<TablasDTO> getListaTablasDTO() {
		return listaTablasDTO;
	}
	/**
	 * @param listaTablasDTO the listaTablasDTO to set
	 */
	public void setListaTablasDTO(List<TablasDTO> listaTablasDTO) {
		this.listaTablasDTO = listaTablasDTO;
	}
	/**
	 * @return the listaCamposTablasDTO
	 */
	public List<CamposTablasDTO> getListaCamposTablasDTO() {
		return listaCamposTablasDTO;
	}
	/**
	 * @param listaCamposTablasDTO the listaCamposTablasDTO to set
	 */
	public void setListaCamposTablasDTO(List<CamposTablasDTO> listaCamposTablasDTO) {
		this.listaCamposTablasDTO = listaCamposTablasDTO;
	}
	
	

	
	

}
