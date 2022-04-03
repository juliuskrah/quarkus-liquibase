package com.juliuskrah.service;

import java.util.List;
import java.util.UUID;
import javax.inject.Singleton;
import com.juliuskrah.dto.ClientWithServices;
import com.juliuskrah.model.Client;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
@Singleton
public class ClientServiceImpl implements ClientService {

    private ClientWithServices toClientDto(Client client) {
        return new ClientWithServices(
            client.id, 
            client.name,
            client.code, 
            client.contactPerson, 
            List.of());
    }

    @Override
    public Uni<ClientWithServices> findClientById(UUID id) {
        return Client.<Client>findById(id).map(this::toClientDto);
    }

    @Override
    public Uni<ClientWithServices> findClientByCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Multi<ClientWithServices> findAllClients() {
        return Client.<Client>findAll().stream().map(this::toClientDto);
    }
    
}
