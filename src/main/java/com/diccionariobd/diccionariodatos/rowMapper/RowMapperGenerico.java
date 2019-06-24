package com.diccionariobd.diccionariodatos.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;

public class RowMapperGenerico implements RowMapper{

	@Override
	public TablasDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TablasDTO user = new TablasDTO();
        user.setIdtabla(rs.getInt("id_tabla"));;
        user.setNombreBaseDatos(rs.getString("nombre_basedatos"));
        user.setNombreTabla(rs.getString("nombre_tabla"));
        user.setFechaTabla(rs.getDate("fecha_creacion"));
        user.setDataSensible(rs.getString("data_sensible"));
        user.setDescripcion(rs.getString("descripcion"));
        user.setFrecuenciaRespaldo(rs.getString("frecuencia_respaldo"));
        user.setTipoParticion(rs.getString("tipo_particion"));
        user.setTipoTabla(rs.getString("tipo_tabla"));
        
        return user;
	}

}
