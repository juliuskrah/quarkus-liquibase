package com.juliuskrah.repository;

import static org.assertj.core.api.Assertions.assertThat;
import javax.inject.Inject;
import com.juliuskrah.model.Client;
import com.juliuskrah.model.Service;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

/**
 * @author Julius Krah
 */
@QuarkusTest
class ServiceRepositoryTest {
    @Inject
    ServiceRepository serviceRepository;

    @Test
    @DisplayName("Test find by service name ignoring case")
    void findByName() {
        var service = serviceRepository.findByNameIgnoreCase("acme bomb");
        assertThat(service.await().indefinitely()).isNotNull()
            .extracting(Service::getCode, Service::getCurrency, Service::getQueueName)
            .containsExactly("ACME_BOMB", "GHS", "queue_acme_bomb");
    }

    @Test
    @TestTransaction
    @DisplayName("Test find by service code ignoring case")
    void findByCode() {
        var service = serviceRepository.findByCodeIgnoreCase("blackmailing");
        assertThat(service.await().indefinitely()).isNotNull()
            .hasFieldOrPropertyWithValue("name", "blackmailing")
            .hasFieldOrPropertyWithValue("currency", "USD")
            .hasFieldOrPropertyWithValue("queueName", "queue_blackmailing")
            .extracting("client", InstanceOfAssertFactories.type(Client.class))
            .hasFieldOrPropertyWithValue("name", "evil corp")
            .hasFieldOrPropertyWithValue("contactPerson", "Tyrell Wellick");
    }

    @Test
    @DisplayName("Test find by client name ignoring case")
    void findByClientName() {
        var services = serviceRepository.findByClientNameIgnoreCase("freedom limited");
        assertThat(services.collect().asList().await().indefinitely()).isNotEmpty()
            .filteredOn(service -> "FREE_HEALTH".equals(service.getCode()))
            .allSatisfy(service -> {
                assertThat(service).extracting(Service::getName).isEqualTo("free health");
                assertThat(service).extracting(Service::getQueueName).isEqualTo("queue_free_health");
            });
    }

    @Test
    @DisplayName("Test find by client code ignoring case")
    void findByClientCode() {
        var services = serviceRepository.findByClientCodeIgnoreCase("hey");
        assertThat(services.collect().asList().await().indefinitely()).isNotEmpty()
            .element(1)
            .hasFieldOrPropertyWithValue("name", "coffee")
            .hasFieldOrPropertyWithValue("queueName", "queue_coffee");
    }
}
