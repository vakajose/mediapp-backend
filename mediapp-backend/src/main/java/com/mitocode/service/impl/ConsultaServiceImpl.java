package com.mitocode.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.dao.IConsultaDAO;
import com.mitocode.dao.IConsultaExamenDAO;
import com.mitocode.dto.ConsultaListaExamenDTO;
import com.mitocode.dto.FiltroConsultaDTO;
import com.mitocode.model.Consulta;
import com.mitocode.service.IConsultaService;

@Service
public class ConsultaServiceImpl  implements IConsultaService{

	@Autowired
	private IConsultaDAO dao;
	
	@Autowired
	private IConsultaExamenDAO ceDao;

	@Override
	public Consulta registrar(Consulta consulta) {
		consulta.getDetalleConsulta().forEach(det -> det.setConsulta(consulta));
		return dao.save(consulta);
	}

	@Override
	public Consulta modificar(Consulta t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Consulta listarId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Consulta> listar() {
		return dao.findAll();
	}


	@Transactional
	@Override
	public Consulta registrarTransaccional(ConsultaListaExamenDTO dto) {
		dto.getConsulta().getDetalleConsulta().forEach(det -> det.setConsulta(dto.getConsulta()));
		dao.save(dto.getConsulta());	
		dto.getLstExamen().forEach(e -> ceDao.registrar(dto.getConsulta().getIdConsulta(), e.getIdExamen()));
		
		return dto.getConsulta();
	}
	
	@Override
	public List<Consulta> buscar(FiltroConsultaDTO filtro) {
		return dao.buscar(filtro.getDni(), filtro.getNombreCompleto());
	}

	@Override
	public List<Consulta> buscarfecha(FiltroConsultaDTO filtro) {
		LocalDateTime fechaSgte = filtro.getFechaConsulta().plusDays(1);
		return dao.buscarFecha(filtro.getFechaConsulta(), fechaSgte);
	}	
	
}
