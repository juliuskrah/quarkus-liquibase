package com.juliuskrah.service;

import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.juliuskrah.dto.ServiceDto;
import com.juliuskrah.model.Service;
import com.juliuskrah.repository.ServiceRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
@Singleton
public class CompanyServiceImpl implements CompanyService {

    @Inject
    ServiceRepository serviceRepository;

    private ServiceDto toServiceDto(Service service) {
        return new ServiceDto(
            service.getId(), 
            service.getName(), 
            service.getCode(), 
            service.getCurrency(), 
            service.getQueueName(), 
            service.getClient().getName()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Uni<ServiceDto> findServiceById(UUID id) {
        return serviceRepository.findById(id).map(this::toServiceDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Uni<ServiceDto> findServiceByCode(String code) {
        return serviceRepository.findByCodeIgnoreCase(code).map(this::toServiceDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Multi<ServiceDto> findAllServices() {
        return serviceRepository.findAll().stream().map(this::toServiceDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Multi<ServiceDto> findServicesForClient(String clientCode) {
        return serviceRepository.findByClientCodeIgnoreCase(clientCode).map(this::toServiceDto);
    }

}
