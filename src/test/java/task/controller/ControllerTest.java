package task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.rakugakibox.spring.boot.orika.OrikaAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.configuration.SwaggerConfiguration;
import task.model.UrlDto;
import task.service.ShortUrlService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShortUrlApiController.class)
@Import({OrikaAutoConfiguration.class, SwaggerConfiguration.class})
class ControllerTest {
    private final static String KEY = "duaeqtVs";
    private final static String BASE_URL = "/v1/url";
    private final static String URL_WITH_KEY = BASE_URL + "/" + KEY;
    private final static String ORIGINAL_URL = "https://en.wikipedia.org/wiki/Common_chiffchaff";

    @MockBean
    private ShortUrlService shortUrlService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    void createUrl() throws Exception {
        UrlDto requestedDto = createDto(null, ORIGINAL_URL);
        UrlDto responseDto = createDto(KEY, ORIGINAL_URL);

        when(shortUrlService.createUrl(any())).thenReturn(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToString(requestedDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectToString(responseDto)));
    }

    @Test
    void deleteUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_WITH_KEY))
                .andExpect(status().isAccepted());
    }

    @Test
    void redirectByUrl() throws Exception {
        when(shortUrlService.getOriginalUrl(KEY)).thenReturn(ORIGINAL_URL);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_KEY))
                .andExpect(status().is3xxRedirection());
    }

    private UrlDto createDto(String shortUrl, String originalUrl) {
        UrlDto urlDto = new UrlDto();
        urlDto.setShortUrl(shortUrl);
        urlDto.setOriginalUrl(originalUrl);
        return urlDto;
    }


    private static String objectToString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}