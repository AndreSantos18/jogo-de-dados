package com.lunaltas.dicegame.dao;

import java.util.List;

import com.lunaltas.dicegame.domain.Bet;

public interface BetDao {

    void save(Bet bet );

    void update(Bet bet);

    void delete(Long id);

    Bet findById(Long id);

    List<Bet> findAll();
}
