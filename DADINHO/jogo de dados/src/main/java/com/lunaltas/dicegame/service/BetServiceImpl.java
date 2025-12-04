package com.lunaltas.dicegame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lunaltas.dicegame.dao.BetDao;
import com.lunaltas.dicegame.domain.Bet;

@Service @Transactional(readOnly = false)
public class BetServiceImpl implements BetService {
	
	@Autowired
	private BetDao dao;

	@Override
	public void save(Bet bet) {
		dao.save(bet);		
	}

	@Override
	public void update(Bet bet) {
		dao.update(bet);		
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);		
	}

	@Override @Transactional(readOnly = true)
	public Bet findById(Long id) {
		
		return dao.findById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Bet> findAll() {
		
		return dao.findAll();
	}


}
