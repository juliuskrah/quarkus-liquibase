package com.juliuskrah.service;

import java.util.UUID;
import com.juliuskrah.dto.ServiceDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
public interface CompanyService {
    /**
     * Find a service given an id
     * @param id
     * @return a service identified by the id
     */
    Uni<ServiceDto> findServiceById(UUID id);

    /**
     * Find a service given a service code
     * @param code
     * @return a service with the given code
     */
    Uni<ServiceDto> findServiceByCode(String code);

    /**
     * Find all services
     * @return all services
     */
    Multi<ServiceDto> findAllServices();

    /**
     * Find all services for client with given code
     * @param clientCode the client id
     * @return all services for the provided client code
     */
    Multi<ServiceDto> findServicesForClient(String clientCode);
}