package com.juliuskrah.service;

import java.util.UUID;
import com.juliuskrah.dto.ClientWithServices;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
public interface ClientService {
    /**
     * Find a client given an id
     * 
     * @param id the identity of a client
     * @return the client with the given identity or null if non is found
     */
    Uni<ClientWithServices> findClientById(UUID id);

    /**
     * Find a client given a code
     * 
     * @param code the client code
     * @return the client with the given code of null if non is found
     */
    Uni<ClientWithServices> findClientByCode(String code);

    /**
     * Find all clients
     * 
     * @return all clients
     */
    Multi<ClientWithServices> findAllClients();

    /**
     * Save a new client
     * 
     * @param client client to add
     * @return new client
     */
    Uni<ClientWithServices> addClient(ClientWithServices client);
}
