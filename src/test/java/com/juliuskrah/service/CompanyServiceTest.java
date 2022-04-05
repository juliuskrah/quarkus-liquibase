package com.juliuskrah.service;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import java.util.UUID;
import javax.inject.Inject;
import com.juliuskrah.dto.ServiceDto;
import com.juliuskrah.model.Client;
import com.juliuskrah.model.Service;
import com.juliuskrah.repository.ServiceRepository;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.AssertSubscriber;

@QuarkusTest
class CompanyServiceTest {
    @InjectMock
    ServiceRepository serviceRepository;
    @Inject CompanyService companyService;

    private Service buildService(String name) {
        var service = new Service();
        service.setId(UUID.randomUUID());
        service.setName(name);
        service.setCode(name.toUpperCase());
        service.setCurrency("GHS");
        service.setQueueName("%s_queueName".formatted(name));
        var client = new Client();
        client.setName("%s client".formatted(name));
        service.setClient(client);
        return service;
    }

    private Multi<Service> buiServices(String... names) {
        return Multi.createFrom().items(names)
            .map(this::buildService);
    }

    @Test
    @DisplayName("Test find service by id")
    void testFindById() {
        given(serviceRepository.findById(any(UUID.class))).willReturn(Uni.createFrom().item(buildService("jeremy's bakery")));

        var name = new Condition<ServiceDto>(service -> 
            service.name().equals("jeremy's bakery"), "Service has name: build and construct");
        var service = companyService.findServiceById(UUID.randomUUID());
        then(service.await().indefinitely()).is(name);
    }

    @Test
    @DisplayName("Test find service by code")
    void testFindByCode() {
        given(serviceRepository.findByCodeIgnoreCase(anyString())).willReturn(Uni.createFrom().item(buildService("build and construct")));

        var code = new Condition<ServiceDto>(service -> 
            service.code().equals("BUILD AND CONSTRUCT"), "Service has code: BUILD AND CONSTRUCT");
        var name = new Condition<ServiceDto>(service -> 
            service.name().equals("build and construct"), "Service has name: build and construct");
        var service = companyService.findServiceByCode("random");
        then(service.await().indefinitely()).has(allOf(code, name));
    }

    @Test
    @DisplayName("Test find all services by client code")
    void testFindAllByClientCode() {
        given(serviceRepository.findByClientCodeIgnoreCase(anyString())).willReturn(
            buiServices("wanda vision")
        );

        var servicesMulti = companyService.findServicesForClient("wanda");
        AssertSubscriber<ServiceDto> subscriber = servicesMulti.subscribe().withSubscriber(AssertSubscriber.create(1));
        var services = subscriber.awaitItems(1).getItems();

        assumeThat(services).isNotEmpty();
        then(services).hasSize(1)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("currency", "name", "id", "client")
            .containsExactlyInAnyOrder(new ServiceDto(null, null, "WANDA VISION", null, "wanda vision_queueName", ""));
    }
}
