package com.diccionariobd.diccionariodatos.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.diccionariobd.diccionariodatos.dao.ITablasDAO;
import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.exception.ApplicationException;
import com.diccionariobd.diccionariodatos.model.MensajeErrores;
import com.diccionariobd.diccionariodatos.model.Tablas;
import com.diccionariobd.diccionariodatos.rowMapper.RowMapperGenerico;



@Repository
public class TablasDaoImpl implements ITablasDAO{
	
	private static final Logger log = LoggerFactory.getLogger(TablasDaoImpl.class);	
	
	@Autowired
	private JdbcTemplate template;


	@Override
	public List<TablasDTO> consultaAllRegistros() {
		return template.query("select * from tablasbd", new RowMapperGenerico());
	}


	@Override
	public void crearRegistros(TablasDTO dato) {
			
		Tablas tabla = new Tablas();
		
		tabla.setDataSensible(dato.getDataSensible());
		tabla.setDescripcion(dato.getDescripcion());
		tabla.setFechaTabla(dato.getFechaTabla());
		tabla.setFrecuenciaRespaldo(dato.getFrecuenciaRespaldo());
		tabla.setIdtabla(dato.getIdtabla());
		tabla.setNombreBaseDatos(dato.getNombreBaseDatos());
		tabla.setNombreTabla(dato.getNombreTabla());
		tabla.setTipoParticion(dato.getTipoParticion());
		tabla.setTipoTabla(dato.getTipoTabla());
		
		
		
		String sqlInsert = "INSERT INTO diccionario.tablasbd values(?,?,?,?,?,?,?,?,?)";
		
		
		log.debug("Desde impl Tablas DAO sql: " + sqlInsert );
		
		try {
			
			
			TablasDTO tablaConsulta =this.consultaOneRegistro(dato.getIdtabla());
			
			if (tablaConsulta != null){
				throw new ApplicationException(new MensajeErrores("ID de Tabla ya existe en la Base",5000,"TablasDaoImpl","crearRegistros"), "ID Tabla ya existe en Base de Datos...");
				
			}
			
			log.debug("Desde impl Antes del Error " + sqlInsert );
			template.update(sqlInsert,tabla.getIdtabla(),tabla.getNombreBaseDatos(),tabla.getNombreTabla(),tabla.getFechaTabla(),tabla.getDescripcion(),tabla.getTipoTabla(),tabla.getDataSensible(),tabla.getFrecuenciaRespaldo(),tabla.getTipoParticion());	
		} catch (Exception e) {
			log.debug("Por el Error del Insert" + e.getMessage());
			e.printStackTrace();
			MensajeErrores mensajeError = new MensajeErrores(e.getMessage(),5000,"TablasDaoImpl","crearRegistros");
			
		
			throw new ApplicationException(mensajeError, e.getMessage());
				
		}
		
		
		
	}

	@Override
	public void updateRegistro(TablasDTO dato) {
		Tablas tabla = new Tablas();
		
		tabla.setDataSensible(dato.getDataSensible());
		tabla.setDescripcion(dato.getDescripcion());
		tabla.setFechaTabla(dato.getFechaTabla());
		tabla.setFrecuenciaRespaldo(dato.getFrecuenciaRespaldo());
		tabla.setIdtabla(dato.getIdtabla());
		tabla.setNombreBaseDatos(dato.getNombreBaseDatos());
		tabla.setNombreTabla(dato.getNombreTabla());
		tabla.setTipoParticion(dato.getTipoParticion());
		tabla.setTipoTabla(dato.getTipoTabla());
		
		
		String sqlInsert = "update diccionario.tablasbd set nombre_basedatos = ?, nombre_tabla = ?,";
		sqlInsert = sqlInsert + "fecha_creacion = ?, descripcion =?, tipo_tabla=?,";
		sqlInsert = sqlInsert + "data_sensible=?, frecuencia_respaldo =?, tipo_particion=?";
		sqlInsert = sqlInsert + "where id_tabla=?";
		
		
		log.debug("Desde impl Tablas DAO Actualizando: " + sqlInsert );
		
		try {
			template.update(sqlInsert,tabla.getNombreBaseDatos(),tabla.getNombreTabla(),tabla.getFechaTabla(),tabla.getDescripcion(),tabla.getTipoTabla(),tabla.getDataSensible(),tabla.getFrecuenciaRespaldo(),tabla.getTipoParticion(),tabla.getIdtabla());	
		} catch (Exception e) {
			log.debug("Por el Error del DaoIMP Actualizando Registro" + e.getMessage());
			e.printStackTrace();
			MensajeErrores mensajeError = new MensajeErrores(e.getMessage(),5000,"TablasDaoImpl","UpdateRegistros");
			
		
			throw new ApplicationException(mensajeError, e.getMessage());
				
		}
		
	}

	@Override
	public void eliminarRegistro(int dato) {
		TablasDTO tablas = new TablasDTO();
		
		log.debug("Desde impl Tablas DAO Eliminar Registro: ");
		
        //Consulta para validar que exista el Registro a Eliminar
		 tablas = this.consultaOneRegistro(dato);
		 
		 if (tablas ==null){
				MensajeErrores mensajeError = new MensajeErrores("Registro a Eliminar no existe...",5000,"TablasDaoImpl","eliminarRegistro");
			
				throw new ApplicationException(mensajeError, "Registro a Eliminar no existe");
		 }
		 
		 String sqlConsulta = "delete from diccionario.tablasbd ";
		 sqlConsulta = sqlConsulta + "where id_tabla=?";
		 
			try {
				template.update(sqlConsulta,dato);
			} catch (Exception e) {
				log.debug("Por el Error del DaoIMP Eliminando Registro" + e.getMessage());
				e.printStackTrace();
				MensajeErrores mensajeError = new MensajeErrores(e.getMessage(),5000,"TablasDaoImpl","eliminarRegistro");

				throw new ApplicationException(mensajeError, e.getMessage());
					
			}
		 

	}

	@Override
	public TablasDTO consultaOneRegistro(int dato) {
		TablasDTO tablas = new TablasDTO();
		String sqlConsulta = "select * from diccionario.tablasbd ";
		sqlConsulta = sqlConsulta + "where id_tabla= ?";// + dato;
		
		
		log.debug("Desde impl Tablas DAO Consultando One Registros: " + sqlConsulta );
		
		try {
			tablas = (TablasDTO) template.queryForObject(sqlConsulta,new Object[]{dato},new RowMapperGenerico());
			//tablas = (TablasDTO) template.query(sqlConsulta,new RowMapperGenerico(),dato);

		}
		catch (EmptyResultDataAccessException empty){
			return null;
		}
		catch (Exception e) {
			log.debug("Por el Error del DaoIMP Consultando Registro" + e.getMessage());
			e.printStackTrace();
			MensajeErrores mensajeError = new MensajeErrores(e.getMessage(),5000,"TablasDaoImpl","consultaOneRegistro");
			
		
			throw new ApplicationException(mensajeError, e.getMessage());
				
		}
		
		return tablas;
	}

}
