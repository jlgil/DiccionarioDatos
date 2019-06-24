package com.diccionariobd.diccionariodatos.model;

public class MensajeErrores {
	private String mensaje;
	private int numeroError;
	private String clase;
	private String metodo;
	

	public MensajeErrores() {
		
	}

	/**
	 * @param mensaje
	 * @param numeroError
	 * @param clase
	 * @param metodo
	 */
	public MensajeErrores(String mensaje, int numeroError, String clase, String metodo) {
		this.mensaje = mensaje;
		this.numeroError = numeroError;
		this.clase = clase;
		this.metodo = metodo;
	}
	/**
	 * @return the mensaje
	 */
	
	
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the numeroError
	 */
	public int getNumeroError() {
		return numeroError;
	}
	/**
	 * @param numeroError the numeroError to set
	 */
	public void setNumeroError(int numeroError) {
		this.numeroError = numeroError;
	}
	/**
	 * @return the clase
	 */
	public String getClase() {
		return clase;
	}
	/**
	 * @param clase the clase to set
	 */
	public void setClase(String clase) {
		this.clase = clase;
	}
	/**
	 * @return the metodo
	 */
	public String getMetodo() {
		return metodo;
	}
	/**
	 * @param metodo the metodo to set
	 */
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	
	
	

}
