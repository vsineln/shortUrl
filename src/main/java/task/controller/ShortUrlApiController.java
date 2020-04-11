package task.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import task.model.UrlDto;
import task.service.ShortUrlService;

import java.net.URI;

@Slf4j
@RestController
@AllArgsConstructor
public class ShortUrlApiController implements ShortUrlApi {

    private final ShortUrlService shortUrlService;

    @Override
    public ResponseEntity<Object> redirectByUrl(String key) {
        log.debug("redirect by {}", key);
        String originalUrl = shortUrlService.getOriginalUrl(key);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @Override
    public ResponseEntity<HttpStatus> deleteUrl(String key) {
        log.debug("delete {}", key);
        shortUrlService.deleteUrl(key);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<UrlDto> createUrl(UrlDto url) {
        log.debug("create short url for {}", url.getOriginalUrl());
        return ResponseEntity.ok(shortUrlService.createUrl(url));
    }
}
