package com.diccionariobd.diccionariodatos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tablasbd")
public class Tablas {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tabla")
	private int idtabla;
	
	@Column(name="nombre_basedatos")
	private String nombreBaseDatos;
	
	@Column(name="nombre_tabla")
	private String nombreTabla;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaTabla;
	
	private String descripcion;
	private String tipoTabla;
	private String dataSensible;
	private String frecuenciaRespaldo;
	private String tipoParticion;	

	/**
	 * @return the idtabla
	 */
	public int getIdtabla() {
		return idtabla;
	}

	/**
	 * @param idtabla the idtabla to set
	 */
	public void setIdtabla(int idtabla) {
		this.idtabla = idtabla;
	}

	/**
	 * @return the nombreBaseDatos
	 */
	public String getNombreBaseDatos() {
		return nombreBaseDatos;
	}

	/**
	 * @param nombreBaseDatos the nombreBaseDatos to set
	 */
	public void setNombreBaseDatos(String nombreBaseDatos) {
		this.nombreBaseDatos = nombreBaseDatos;
	}

	/**
	 * @return the nombreTabla
	 */
	public String getNombreTabla() {
		return nombreTabla;
	}

	/**
	 * @param nombreTabla the nombreTabla to set
	 */
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	/**
	 * @return the fechaTabla
	 */
	public Date getFechaTabla() {
		return fechaTabla;
	}

	/**
	 * @param fechaTabla the fechaTabla to set
	 */
	public void setFechaTabla(Date fechaTabla) {
		this.fechaTabla = fechaTabla;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipoTabla
	 */
	public String getTipoTabla() {
		return tipoTabla;
	}

	/**
	 * @param tipoTabla the tipoTabla to set
	 */
	public void setTipoTabla(String tipoTabla) {
		this.tipoTabla = tipoTabla;
	}

	/**
	 * @return the dataSensible
	 */
	public String getDataSensible() {
		return dataSensible;
	}

	/**
	 * @param dataSensible the dataSensible to set
	 */
	public void setDataSensible(String dataSensible) {
		this.dataSensible = dataSensible;
	}

	/**
	 * @return the frecuenciaRespaldo
	 */
	public String getFrecuenciaRespaldo() {
		return frecuenciaRespaldo;
	}

	/**
	 * @param frecuenciaRespaldo the frecuenciaRespaldo to set
	 */
	public void setFrecuenciaRespaldo(String frecuenciaRespaldo) {
		this.frecuenciaRespaldo = frecuenciaRespaldo;
	}

	/**
	 * @return the tipoParticion
	 */
	public String getTipoParticion() {
		return tipoParticion;
	}

	/**
	 * @param tipoParticion the tipoParticion to set
	 */
	public void setTipoParticion(String tipoParticion) {
		this.tipoParticion = tipoParticion;
	}
	
	
	
	
	

}
