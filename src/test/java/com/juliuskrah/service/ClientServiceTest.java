package com.juliuskrah.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import com.juliuskrah.dto.ClientWithServices;
import com.juliuskrah.model.Client;
import com.juliuskrah.repository.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
@QuarkusTest
public class ClientServiceTest {
    @Inject
    ClientService clientService;
    @InjectMock
    ClientRepository clientRepository;

    private Client buildClient(String name) {
        var client = new Client();
        client.setCode(name.toUpperCase());
        client.setContactPerson("%s's contact".formatted(name));
        client.setName(name);
        client.setId(UUID.randomUUID());
        return client;
    }
    
    @Test
    @DisplayName("Test find client by id")
    void testFindById() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Uni.createFrom().item(buildClient("jam")));

        var expected = new ClientWithServices(null, "jam", "JAM", "jam's contact", 
            List.of()
        );

        var client = clientService.findClientById(UUID.fromString("7602bbdd-ec04-4337-993e-3fdb1be76310"));
        assertThat(client.await().indefinitely()).isNotNull()
            .usingRecursiveComparison()
            .ignoringExpectedNullFields().isEqualTo(expected);

        verify(clientRepository, times(1)).findById(any(UUID.class));
    }
}
