package task.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import task.entity.KeyEntity;
import task.repository.KeyRepository;
import task.service.impl.KeyServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KeyServiceTest {
    private static final String KEY = "z2cz10uk";

    @Mock
    private KeyRepository keyRepository;

    @InjectMocks
    private KeyServiceImpl keyService;

    @Test
    void orderFreeKey_generateNew() {
        String key = keyService.orderFreeKey();
        assertTrue(StringUtils.isNotBlank(key));
    }

    @Test
    void orderFreeKey_returnExistent() {
        when(keyRepository.findFirstByFree(true)).thenReturn(Optional.of(new KeyEntity(KEY, true)));
        String key = keyService.orderFreeKey();
        assertEquals(KEY, key);
    }

    @Test
    void setFree() {
        keyService.setFree(KEY);

        ArgumentCaptor<KeyEntity> captor = ArgumentCaptor.forClass(KeyEntity.class);
        verify(keyRepository).save(captor.capture());
        assertEquals(KEY, captor.getValue().getKey());
        assertTrue(captor.getValue().isFree());
    }
}