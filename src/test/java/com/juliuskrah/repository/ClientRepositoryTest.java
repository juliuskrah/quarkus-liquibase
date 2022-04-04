package com.juliuskrah.repository;

import static org.assertj.core.api.Assertions.assertThat;
import javax.inject.Inject;
import com.juliuskrah.model.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;

/**
 * @author Julius Krah
 */
@QuarkusTest
class ClientRepositoryTest {
    @Inject
    ClientRepository clientRepository;

    @Test
    @DisplayName("Test find by Client_Code ignoring case")
    void testFindByCode() {
        var clientUni = clientRepository.findByCodeIgnoreCase("acme");
        UniAssertSubscriber<Client> subscriber = clientUni.subscribe().withSubscriber(UniAssertSubscriber.create());
        var client = subscriber.awaitItem().getItem();
        assertThat(client).isNotNull()
          .hasFieldOrProperty("contactPerson")
          .hasFieldOrPropertyWithValue("name", "acme corporation");
    }

    @Test
    @DisplayName("Test find by Client name ignore case")
    void testFindByClientName() {
        var clientUni = clientRepository.findByNameIgnoreCase("hey foods");
        UniAssertSubscriber<Client> subscriber = clientUni.subscribe().withSubscriber(UniAssertSubscriber.create());
        var client = subscriber.awaitItem().getItem();
        assertThat(client).isNotNull()
          .hasFieldOrProperty("contactPerson")
          .hasFieldOrPropertyWithValue("code", "HEY");
    }
}
