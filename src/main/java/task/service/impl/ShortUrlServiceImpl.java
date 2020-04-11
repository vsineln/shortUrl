package task.service.impl;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.entity.UrlEntity;
import task.exception.ShortUrlException;
import task.model.UrlDto;
import task.repository.ShortUrlRepository;
import task.service.KeyService;
import task.service.ShortUrlService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final KeyService keyService;
    private final MapperFacade mapper;

    @Value("${keys.expiration.days}")
    private int expirationDays;

    public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository, KeyService keyService, MapperFacade mapper) {
        this.shortUrlRepository = shortUrlRepository;
        this.keyService = keyService;
        this.mapper = mapper;
    }

    @Override
    public String getOriginalUrl(String key) {
        log.debug("get original url by key {}", key);
        return shortUrlRepository.findById(key).map(UrlEntity::getOriginalUrl).
                orElseThrow(() -> new ShortUrlException(String.format("Url %s doesn't exists", key)));
    }

    @Override
    public UrlDto createUrl(UrlDto dto) {
        log.debug("createUrl");
        String key = keyService.orderFreeKey();

        LocalDateTime now = LocalDateTime.now();
        Timestamp expiredTime = dto.getDateExpired() != null ? dto.getDateExpired() : Timestamp.valueOf(now.plusDays(expirationDays));

        UrlEntity urlEntity = new UrlEntity(key, dto.getOriginalUrl(), Timestamp.valueOf(now), expiredTime);
        shortUrlRepository.save(urlEntity);
        log.debug("key {} was created", key);
        return mapper.map(urlEntity, UrlDto.class);
    }

    @Override
    @Transactional
    public void deleteUrl(String key) {
        log.debug("deleteUrl");
        shortUrlRepository.deleteById(key);
        keyService.setFree(key);
        log.debug("key was deleted {}", key);
    }

    @Override
    @Transactional
    @Scheduled(cron = "${keys.check.expired}")
    public void checkExpired() {
        log.debug("check expired keys");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<UrlEntity> entityList = shortUrlRepository.findByDateExpiredBefore(now);
        entityList.forEach(entity -> keyService.setFree(entity.getShortUrl()));

        shortUrlRepository.deleteAll(entityList);
    }
}
