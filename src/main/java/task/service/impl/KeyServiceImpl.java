package task.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import task.entity.KeyEntity;
import task.repository.KeyRepository;
import task.service.KeyService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class KeyServiceImpl implements KeyService {
    private final KeyRepository keyRepository;

    @Value("${keys.capacity}")
    private long capacity;

    public KeyServiceImpl(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    @Override
    @Scheduled(initialDelay = 0, fixedDelayString = "${keys.update.delay}")
    public void generateKeys() {
        log.debug("check free keys presence");
        List<KeyEntity> keysList = keyRepository.findByFree(true);
        if (CollectionUtils.isEmpty(keysList) || keysList.size() < capacity / 2) {
            log.debug("generate new keys");
            List<KeyEntity> keys = Stream.generate(() -> RandomStringUtils.randomAlphanumeric(8)).
                    limit(capacity).
                    filter(key -> !keyRepository.existsById(key)).
                    map(key -> new KeyEntity(key, true)).
                    collect(Collectors.toList());
            keyRepository.saveAll(keys);
        }
    }

    @Override
    public String orderFreeKey() {
        log.debug("order free key");
        Optional<KeyEntity> keyEntity = keyRepository.findFirstByFree(true);
        String key;
        if (!keyEntity.isPresent()) {
            key = getNewKey();
        } else {
            key = keyEntity.get().getKey();
        }
        keyRepository.save(new KeyEntity(key, false));
        return key;
    }

    @Override
    public void setFree(String key) {
        log.debug("new free key {}", key);
        keyRepository.save(new KeyEntity(key, true));
    }

    private String getNewKey() {
        log.debug("get new key");
        String newKey;
        do {
            newKey = RandomStringUtils.randomAlphanumeric(8);
        } while (keyRepository.existsById(newKey));
        return newKey;
    }
}
