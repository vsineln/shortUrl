package task.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import task.entity.KeyEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class KeyRepositoryTest {
    private final KeyEntity key1 = new KeyEntity("PVe1857v", true);
    private final KeyEntity key2 = new KeyEntity("VhzGLaOp", false);
    private final KeyEntity key3 = new KeyEntity("z2cz10uk", true);

    @Autowired
    private KeyRepository keyRepository;

    @Test
    void findKey() {
        Optional<KeyEntity> key = keyRepository.findById("z2cz10uk");

        assertTrue(key.isPresent());
        assertTrue(key.get().isFree());
    }

    @Test
    void deleteKey() {
        keyRepository.delete(key3);
        Iterable<KeyEntity> keys = keyRepository.findAll();

        assertThat(keys, containsInAnyOrder(key1, key2));
    }

    @Test
    void findAllFreeKeys() {
        Iterable<KeyEntity> keys = keyRepository.findByFree(true);

        assertThat(keys, containsInAnyOrder(key1, key3));
    }

    @Test
    void findAnyFreeKey() {
        Optional<KeyEntity> key = keyRepository.findFirstByFree(true);

        assertTrue(key.isPresent());
        assertTrue(key.get().isFree());
    }

    @BeforeEach
    private void fillDataBase() {
        List<KeyEntity> keyEntities = getKeys();
        keyRepository.saveAll(keyEntities);
    }

    @AfterEach
    private void cleanDataBase() {
        keyRepository.deleteAll();
    }

    private List<KeyEntity> getKeys() {
        return Arrays.asList(key1, key2, key3);
    }
}