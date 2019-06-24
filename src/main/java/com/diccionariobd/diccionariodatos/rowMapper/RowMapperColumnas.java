package com.diccionariobd.diccionariodatos.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;
import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;

public class RowMapperColumnas implements RowMapper{

	@Override
	public CamposTablasDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CamposTablasDTO user = new CamposTablasDTO();
        user.setCampoIndice(rs.getString("campo_indice"));;
        user.setIdCampo(rs.getInt("id_campo"));
        user.setIdTabla(rs.getInt("id_tabla"));
        user.setLongCampo(rs.getInt("longitud_campo"));
        user.setNameCampo(rs.getString("nombre_campo"));
        user.setPermiteNulo(rs.getString("permite_nulo"));
        user.setTipoDato(rs.getString("tipo_dato"));
        user.setDescripcionCampo(rs.getString("descripcion_campo"));
        
        
        return user;
	}
	
}

	
	
