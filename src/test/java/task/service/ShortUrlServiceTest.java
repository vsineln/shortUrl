package task.service;

import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import task.entity.UrlEntity;
import task.exception.ShortUrlException;
import task.model.UrlDto;
import task.repository.ShortUrlRepository;
import task.service.impl.ShortUrlServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShortUrlServiceTest {
    private static final String SHORT_KEY = "z2cz10uk";
    private static final String ORIGINAL_URL = "https://tests/example/link";
    private final UrlEntity url = new UrlEntity(SHORT_KEY, ORIGINAL_URL, null, null);

    @Mock
    private ShortUrlRepository shortUrlRepository;
    @Mock
    private KeyService keyService;
    @Mock
    private MapperFacade mapper;

    @InjectMocks
    private ShortUrlServiceImpl shortUrlService;

    @Test
    void getOriginalUrl_whenExist() {
        when(shortUrlRepository.findById(SHORT_KEY)).thenReturn(Optional.of(url));
        String returnedLink = shortUrlService.getOriginalUrl(SHORT_KEY);
        assertEquals(ORIGINAL_URL, returnedLink);
    }

    @Test
    void getOriginalUrl_whenDoesNotExist() {
        when(shortUrlRepository.findById(SHORT_KEY)).thenReturn(Optional.empty());
        assertThrows(ShortUrlException.class, () -> shortUrlService.getOriginalUrl(SHORT_KEY));
    }

    @Test
    void createUrl() {
        when(keyService.orderFreeKey()).thenReturn(SHORT_KEY);

        shortUrlService.createUrl(getDto(null, ORIGINAL_URL));

        ArgumentCaptor<UrlEntity> captor = ArgumentCaptor.forClass(UrlEntity.class);
        verify(shortUrlRepository).save(captor.capture());
        assertEquals(SHORT_KEY, captor.getValue().getShortUrl());
    }

    @Test
    void deleteUrl() {
        shortUrlService.deleteUrl(SHORT_KEY);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(shortUrlRepository).deleteById(captor.capture());
        verify(keyService).setFree(captor.capture());
        assertEquals(SHORT_KEY, captor.getValue());
    }

    private UrlDto getDto(String shortUrl, String originalUrl){
        UrlDto dto = new UrlDto();
        dto.setShortUrl(shortUrl);
        dto.setOriginalUrl(originalUrl);
        return dto;
    }
}
