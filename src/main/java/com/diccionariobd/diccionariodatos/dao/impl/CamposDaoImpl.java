package com.diccionariobd.diccionariodatos.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.diccionariobd.diccionariodatos.dao.ICamposTablasDAO;
import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;
import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.exception.ApplicationException;
import com.diccionariobd.diccionariodatos.model.CamposTabla;
import com.diccionariobd.diccionariodatos.model.MensajeErrores;
import com.diccionariobd.diccionariodatos.model.Tablas;
import com.diccionariobd.diccionariodatos.rowMapper.RowMapperColumnas;
import com.diccionariobd.diccionariodatos.rowMapper.RowMapperGenerico;

@Repository
public class CamposDaoImpl extends AbstractoDaoImpl<CamposTablasDTO> implements ICamposTablasDAO{

	private static final Logger log = LoggerFactory.getLogger(CamposDaoImpl.class);	
	
	@Autowired
	private JdbcTemplate template;
	
	/**
	 * Entrada idTabla
	 * Salida: Devuelve una Lista de Campos de la tabla
	 */
	@Override
	public List<CamposTablasDTO> consultaCamposTabla(int idTabla) {
		String sql = "select * from campos_tablas where id_tabla = ?";
		
		//return template.query(sql, new Object[] {idTabla},new RowMapperColumnas());
		return template.query(sql, new RowMapperColumnas(),idTabla);
	}

	@Override
	public CamposTablasDTO consultaCamposTablaColumna(int idTabla, int idColumna) {
		CamposTablasDTO campostabla = new CamposTablasDTO();
		String sqlConsulta = "select * from diccionario.campos_tablas ";
		sqlConsulta = sqlConsulta + "where id_tabla= ? and id_columna = ?";// + dato;
		
		
		log.debug("Desde impl Tablas DAO consultaCamposTablaColumnas: " + sqlConsulta );
		
		try {
			campostabla = (CamposTablasDTO) template.queryForObject(sqlConsulta,new Object[]{idTabla,idColumna},new RowMapperColumnas());

		}
		catch (EmptyResultDataAccessException empty){
			return null;
		}
		catch (Exception e) {
			log.debug("Por el Error del DaoIMP consultaCamposTablaColumna" + e.getMessage());
			e.printStackTrace();
			MensajeErrores mensajeError = new MensajeErrores(e.getMessage(),5000,"CamposDaoImpl","consultaCamposTablaColumna");
			
		
			throw new ApplicationException(mensajeError, e.getMessage());
				
		}
		
		return campostabla;
	}



	@Override
	public List<CamposTablasDTO> consultaAllRegistros() {
		return template.query("select * from campos_tablas", new RowMapperColumnas());
	}



	@Override
	public void crearRegistros(CamposTablasDTO dato) {
		// TODO Auto-generated method stub
		CamposTabla campos = new CamposTabla();
		
		campos.setCampoIndice(dato.getCampoIndice());
		campos.setDescripcionCampo(dato.getDescripcionCampo());
		campos.setIdCampo(dato.getIdCampo());;
		campos.setIdTabla(dato.getIdTabla());
		campos.setLongCampo(dato.getLongCampo());
		campos.setNameCampo(dato.getNameCampo());
		campos.setPermiteNulo(dato.getPermiteNulo());
		campos.setTipoDato(dato.getTipoDato());

		
		String sqlInsert = "INSERT INTO diccionario.campos_tablas values(?,?,?,?,?,?,?,?)";
		
	
		log.debug("Desde impl CampoDaoIMP sql: " + sqlInsert );
		
		try {
			
			
			CamposTablasDTO campoConsulta =this.consultaCamposTablaColumna(dato.getIdTabla(), dato.getIdCampo());
			
			if (campoConsulta != null){
				throw new ApplicationException(new MensajeErrores("ID de Tabla y Campo ya  existe en la Base",5000,"TablasDaoImpl","crearRegistros"), "ID de Tabla y Campo ya  existe en la Base...");
				
			}
			
			template.update(sqlInsert,campos.getIdTabla(),campos.getIdCampo(),campos.getNameCampo(),campos.getTipoDato(),campos.getLongCampo(),campos.getPermiteNulo(),campos.getCampoIndice(),campos.getDescripcionCampo());	
		} catch (Exception e) {
			log.debug("Por el Error del Insert CampoDaoIMP" + e.getMessage());
			e.printStackTrace();
			MensajeErrores mensajeError = new MensajeErrores(e.getMessage(),5000,"CamposDaoImpl","crearRegistros");
			
		
			throw new ApplicationException(mensajeError, e.getMessage());
				
		}
		

		
	}
	
	@Override
	public void updateRegistro(CamposTablasDTO dato) {
		// TODO Auto-generated method stub
		
		System.out.println("Actualzaido registro");
		
		CamposTabla camposTabla = new CamposTabla();
		
		camposTabla.setCampoIndice(dato.getCampoIndice());
		camposTabla.setIdCampo(dato.getIdCampo());
		camposTabla.setIdTabla(dato.getIdTabla());
		camposTabla.setLongCampo(dato.getLongCampo());
		camposTabla.setNameCampo(dato.getNameCampo());
		camposTabla.setPermiteNulo(dato.getPermiteNulo());
		camposTabla.setTipoDato(dato.getTipoDato());
		camposTabla.setDescripcionCampo(dato.getDescripcionCampo());
		
		
		String sql = "update campos_tablas set nombre_campo = ?,";
        sql  = sql + "tipo_dato = ?,";
        sql  = sql + "longitud_campo = ?,";
        sql  = sql + "permite_nulo = ?,";
        sql  = sql + "campo_indice = ?,";
        sql  = sql + "descripcion_campo = ?";
        sql  = sql + " where id_tabla  = ?";
        sql  = sql + " and id_campo = ?";
		
		System.out.println("Actualzaido registro:" + sql + " Descrip: " + dato.getDescripcionCampo());
        //this.template.update(sql,dato.getNameCampo(),dato.getTipoDato(),dato.getLongCampo(),dato.getPermiteNulo(),dato.getCampoIndice(),dato.getIdTabla(),dato.getIdCampo());
		this.template.update(sql,camposTabla.getNameCampo(),camposTabla.getTipoDato(),camposTabla.getLongCampo(),camposTabla.getPermiteNulo(),camposTabla.getCampoIndice(),camposTabla.getDescripcionCampo(),camposTabla.getIdTabla(),camposTabla.getIdCampo());
        
		
	}	


	@Override
	public void eliminarRegistro(int dato) {
		// TODO Auto-generated method stub
		
	}

}
