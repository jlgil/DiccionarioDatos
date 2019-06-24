package com.diccionariobd.diccionariodatos.dao.dto;


import org.springframework.beans.BeanUtils;

import com.diccionariobd.diccionariodatos.model.CamposTabla;

public class CamposTablasDTO extends AbstracDTO<CamposTabla> {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private int idTabla;
	
	private int idCampo;
	private String nameCampo;
	private String tipoDato;
	private int longCampo;
	private String permiteNulo;
	private String campoIndice;
	private String descripcionCampo;
	

	/**
	 * @return the idTabla
	 */
	public int getIdTabla() {
		return idTabla;
	}

	/**
	 * @param idTabla the idTabla to set
	 */
	public void setIdTabla(int idTabla) {
		this.idTabla = idTabla;
	}

	/**
	 * @return the idCampo
	 */
	public int getIdCampo() {
		return idCampo;
	}

	/**
	 * @param idCampo the idCampo to set
	 */
	public void setIdCampo(int idCampo) {
		this.idCampo = idCampo;
	}

	/**
	 * @return the nameCampo
	 */
	public String getNameCampo() {
		return nameCampo;
	}

	/**
	 * @param nameCampo the nameCampo to set
	 */
	public void setNameCampo(String nameCampo) {
		this.nameCampo = nameCampo;
	}

	/**
	 * @return the tipoDato
	 */
	public String getTipoDato() {
		return tipoDato;
	}

	/**
	 * @param tipoDato the tipoDato to set
	 */
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	/**
	 * @return the l```ongCampo
	 */
	public int getLongCampo() {
		return longCampo;
	}

	/**
	 * @param longCampo the longCampo to set
	 */
	public void setLongCampo(int longCampo) {
		this.longCampo = longCampo;
	}

	/**
	 * @return the permiteNulo
	 */
	public String getPermiteNulo() {
		return permiteNulo;
	}

	/**
	 * @param permiteNulo the permiteNulo to set
	 */
	public void setPermiteNulo(String permiteNulo) {
		this.permiteNulo = permiteNulo;
	}

	/**
	 * @return the campoIndice
	 */
	public String getCampoIndice() {
		return campoIndice;
	}

	/**
	 * @param campoIndice the campoIndice to set
	 */
	public void setCampoIndice(String campoIndice) {
		this.campoIndice = campoIndice;
	}

	@Override
	public CamposTabla toEntity() {
		CamposTabla camposTabla = new CamposTabla();
		
		BeanUtils.copyProperties(this,camposTabla);
		
		
		return camposTabla;
	}

	@Override
	public CamposTabla toEntity(CamposTabla entity) {
		CamposTabla camposTabla = new CamposTabla();
		
		BeanUtils.copyProperties(entity, camposTabla);
		return camposTabla;
	}

	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the descripcionCampo
	 */
	public String getDescripcionCampo() {
		return descripcionCampo;
	}

	/**
	 * @param descripcionCampo the descripcionCampo to set
	 */
	public void setDescripcionCampo(String descripcionCampo) {
		this.descripcionCampo = descripcionCampo;
	}

}
