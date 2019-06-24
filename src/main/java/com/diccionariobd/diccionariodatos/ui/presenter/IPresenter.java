package com.diccionariobd.diccionariodatos.ui.presenter;

import java.util.List;

import com.diccionariobd.diccionariodatos.dao.dto.AbstracDTO;

public interface IPresenter {
	
	public  List<? extends AbstracDTO> consultarAllTablas();

}
