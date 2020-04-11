package task.repository;

import org.springframework.data.repository.CrudRepository;
import task.entity.UrlEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Repository for keeping created short links and the original ones
 */
public interface ShortUrlRepository extends CrudRepository<UrlEntity, String> {

    List<UrlEntity> findByDateExpiredBefore(Timestamp timestamp);
}
