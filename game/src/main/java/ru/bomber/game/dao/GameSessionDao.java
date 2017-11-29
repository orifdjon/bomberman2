package ru.bomber.game.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bomber.game.model.GameSession;

@Repository
public interface GameSessionDao extends CrudRepository<GameSession, Integer> {
}
