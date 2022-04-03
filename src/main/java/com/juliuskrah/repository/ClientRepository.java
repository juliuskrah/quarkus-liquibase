package com.juliuskrah.repository;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import com.juliuskrah.model.Client;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
@ApplicationScoped
public class ClientRepository implements PanacheRepositoryBase<Client, UUID> {

    public Uni<Client> findByCodeIgnoreCase(String code) {
        return find("LOWER(code) = LOWER(?1)", code).firstResult();
    }

    public Uni<Client> findByNameIgnoreCase(String name) {
        return find("LOWER(name) = LOWER(?1)", name).firstResult();
    }
}
