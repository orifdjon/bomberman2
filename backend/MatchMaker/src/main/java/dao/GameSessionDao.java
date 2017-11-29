package dao;

import model.GameSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionDao extends CrudRepository<GameSession, Integer> {
}
