package com.juliuskrah.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import java.util.UUID;
import javax.inject.Inject;
import com.juliuskrah.model.Client;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.junit.QuarkusTest;

/**
 * @author Julius Krah
 */
@QuarkusTest
class ServiceSettingRepositoryTest {
    @Inject
    ServiceSettingRepository serviceSettingRepository;

    @Test
    @DisplayName("Test find by payer client Id")
    void testFindByPayerClientId() {
        var serviceSettings = serviceSettingRepository.findByPayerClientId(UUID.fromString("15759e2d-21ab-4ec2-bb9a-2aee55c409fe"));
        assertThat(serviceSettings.collect().asList().await().indefinitely()).isNotEmpty()
            .hasSize(1)
            .extracting("acknowledgePayer").contains(true);
    }

    @Test
    @DisplayName("Test find by receiver client Id")
    void testFindByReceiverClientId() {
        Panache.withTransaction(() -> 
            serviceSettingRepository.findByReceiverClientId(UUID.fromString("b0c02363-5031-4c0c-9a83-f242df4039b1"))
        )
        .invoke(services -> {
            assertThat(services).isNotEmpty()
            .hasSize(3)
            .extracting("notifyCustomer", "acknowledgePayer")
            .contains(
                tuple(false, true), 
                tuple(false, false), 
                tuple(true, true)
            );
        })
        .subscribeAsCompletionStage().join();
        
    }

    @Test
    @DisplayName("Test find by service id")
    void testFindByServiceId() {
        var serviceSettings = serviceSettingRepository.findByServiceId(UUID.fromString("69fe1c30-57c0-44b4-a1cb-b398890174f3"));
        SoftAssertions.assertSoftly(softly -> {
            var ss = serviceSettings.collect().asList().await().indefinitely();
            softly.assertThat(ss).as("Service setting must not be null").isNotNull();
            softly.assertThat(ss).first()
                .extracting("id.payerClient",
                    InstanceOfAssertFactories.type(Client.class)
            ).hasFieldOrPropertyWithValue("name", "acme corporation");
            softly.assertThat(ss).last()
                .extracting("id.receiverClient",
                InstanceOfAssertFactories.type(Client.class)
            ).hasFieldOrPropertyWithValue("name", "hey foods");
        });
    }
}
