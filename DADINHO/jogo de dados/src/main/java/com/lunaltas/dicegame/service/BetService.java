package com.lunaltas.dicegame.service;

import java.util.List;

import com.lunaltas.dicegame.domain.Bet;

public interface BetService {

	void save(Bet bet);
	
	void update(Bet bet);
	
	void delete(Long id);
	
	Bet findById(Long id);
	
	List<Bet> findAll();
	
}
