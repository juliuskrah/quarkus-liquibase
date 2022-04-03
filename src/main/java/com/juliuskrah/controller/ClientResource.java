package com.juliuskrah.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import com.juliuskrah.dto.ClientWithServices;
import com.juliuskrah.dto.ServiceDto;
import com.juliuskrah.service.ClientService;
import com.juliuskrah.service.CompanyService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
@Path("/clients")
public class ClientResource {

    @Inject
    ClientService clientService;
    @Inject
    CompanyService companyService;

    
    @GET @Path("/{id}")
    public Uni<ClientWithServices> client(@PathParam("id") UUID id) {
        return clientService.findClientById(id);
    }

    @GET @Path("/")
    public Multi<ClientWithServices> clients() {
        return clientService.findAllClients();
    }

    @GET @Path("/{code}/code")
    public CompletableFuture<ClientWithServices> clientByCode(@PathParam("code") String code) {
        return clientService.findClientByCode(code).subscribeAsCompletionStage();
    }

    @GET @Path("/{clientCode}/services")
    public Multi<ServiceDto> servicesByClient(@PathParam("clientCode") String clientCode) {
        return companyService.findServicesForClient(clientCode);
    }
}
