package task.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import task.entity.UrlEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ShortUrlRepositoryTest {
    private final Timestamp created = Timestamp.valueOf(LocalDateTime.now());
    private final Timestamp expired1 = Timestamp.valueOf(LocalDateTime.now().plusDays(7));
    private final Timestamp expired2 = Timestamp.valueOf(LocalDateTime.now().minusDays(7));
    private final UrlEntity url1 = new UrlEntity("PVe1857v",
            "https://en.wikipedia.org/wiki/Eurasian_bullfinch", created, expired1);
    private final UrlEntity url2 = new UrlEntity("VhzGLaOp",
            "https://en.wikipedia.org/wiki/Common_chaffinch", created, expired2);
    private final UrlEntity url3 = new UrlEntity("z2cz10uk",
            "https://en.wikipedia.org/wiki/Common_chiffchaff", created, expired1);

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    void findUrl() {
        Optional<UrlEntity> url = shortUrlRepository.findById("z2cz10uk");

        assertTrue(url.isPresent());
    }

    @Test
    void deleteUrl() {
        shortUrlRepository.delete(url2);
        Iterable<UrlEntity> urls = shortUrlRepository.findAll();

        assertThat(urls, containsInAnyOrder(url1, url3));
    }

    @Test
    void findExpiredUrl() {
        List<UrlEntity> urls = shortUrlRepository.findByDateExpiredBefore(created);

        assertThat(urls, containsInAnyOrder(url2));
    }

    @BeforeEach
    private void fillDataBase() {
        List<UrlEntity> keyEntities = getUrls();
        shortUrlRepository.saveAll(keyEntities);
    }

    @AfterEach
    private void cleanDataBase() {
        shortUrlRepository.deleteAll();
    }

    private List<UrlEntity> getUrls() {
        return Arrays.asList(url1, url2, url3);
    }
}
