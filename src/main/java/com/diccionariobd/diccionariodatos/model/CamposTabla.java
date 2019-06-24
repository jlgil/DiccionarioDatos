package com.diccionariobd.diccionariodatos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="campos_tablas")
public class CamposTabla {
	//id_tabla|id_columna|name_columna|tipo_columna|longitud_col|permite_nul|campo_indice
  
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tabla")
	private int idTabla;
	
	@Column(name="id_campo")
	private int idCampo;
	
	@Column(name="nombre_campo")
	private String nameCampo;
	
	@Column(name="tipo_dato")
	private String tipoDato;
	
	@Column(name="longitud_campo")
	private int longCampo;
	
	@Column(name="permite_nulo")
	private String permiteNulo;
	
	@Column(name="campo_indice")
	private String campoIndice;
	
	@Column(name="descripcion_campo")
	private String descripcionCampo;
	
	

	public CamposTabla() {
		// TODO Auto-generated constructor stub
	}

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
	 * @return the longCampo
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

	public String getDescripcionCampo() {
		return descripcionCampo;
	}

	public void setDescripcionCampo(String descripcionCampo) {
		this.descripcionCampo = descripcionCampo;
	}
	
	
	
	
}
