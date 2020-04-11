package task.repository;

import org.springframework.data.repository.CrudRepository;
import task.entity.KeyEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository for creating a pool of keys
 */
public interface KeyRepository extends CrudRepository<KeyEntity, String> {

    List<KeyEntity> findByFree(boolean free);

    Optional<KeyEntity> findFirstByFree(boolean free);
}
