package com.juliuskrah.service;

import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.juliuskrah.dto.ClientWithServices;
import com.juliuskrah.model.Client;
import com.juliuskrah.repository.ClientRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
@Singleton
public class ClientServiceImpl implements ClientService {
    @Inject
    ClientRepository clientRepository;

    private ClientWithServices toClientDto(Client client) {
        return new ClientWithServices(
            client.getId(), 
            client.getName(),
            client.getCode(), 
            client.getContactPerson(), 
            List.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Uni<ClientWithServices> findClientById(UUID id) {
        return clientRepository.findById(id).map(this::toClientDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Uni<ClientWithServices> findClientByCode(String code) {
        return clientRepository.findByCodeIgnoreCase(code).map(this::toClientDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Multi<ClientWithServices> findAllClients() {
        return clientRepository.findAll().stream().map(this::toClientDto);
    }
    
}
