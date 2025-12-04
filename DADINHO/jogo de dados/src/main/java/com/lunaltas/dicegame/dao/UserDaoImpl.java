package com.lunaltas.dicegame.dao;

import org.springframework.stereotype.Repository;

import com.lunaltas.dicegame.domain.User;

@Repository
public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

}
