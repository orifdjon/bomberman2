package thread;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;


public interface GameRepository extends CrudRepository<Connection, Integer> { // это пока не точно

}
