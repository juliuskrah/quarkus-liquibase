package com.juliuskrah.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import com.juliuskrah.dto.ClientWithServices;
import com.juliuskrah.dto.ServiceDto;
import com.juliuskrah.model.Client;
import com.juliuskrah.repository.ClientRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;

/**
 * @author Julius Krah
 */
@QuarkusTest
class ClientServiceTest {
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

    @Test
    @DisplayName("Test find client by code")
    void testFindByCode() {
        when(clientRepository.findByCodeIgnoreCase(anyString())).thenReturn(Uni.createFrom().item(buildClient("freedom")));

        var clientUni = clientService.findClientByCode("FREEDOM");
        UniAssertSubscriber<ClientWithServices> subscriber = clientUni.subscribe().withSubscriber(UniAssertSubscriber.create());
        var client = subscriber.awaitItem().getItem();
        assertThat(client).isNotNull()
            .extracting("services", InstanceOfAssertFactories.LIST)
            .hasOnlyElementsOfType(ServiceDto.class).isEmpty();
        verify(clientRepository, only()).findByCodeIgnoreCase(anyString());
    }

    @Test
    @DisplayName("Test add client")
    void testFindByIdThrows() {
        var client = new Client();
        client.setId(UUID.randomUUID());
        client.setCode("TEST");
        client.setName("Test");
        client.setContactPerson("Test Test");

        when(clientRepository.persist(any(Client.class))).thenReturn(Uni.createFrom().item(client));
        var clientUni = clientService.addClient(new ClientWithServices(null, null, null, null, null));

        UniAssertSubscriber<ClientWithServices> subscriber = clientUni.subscribe().withSubscriber(UniAssertSubscriber.create());
        var c = subscriber.awaitItem().getItem();
        assertThat(c).isNotNull()
            .extracting("services", InstanceOfAssertFactories.LIST)
            .hasOnlyElementsOfType(ServiceDto.class).isEmpty();
        verify(clientRepository, only()).persist(any(Client.class));
    }

}
