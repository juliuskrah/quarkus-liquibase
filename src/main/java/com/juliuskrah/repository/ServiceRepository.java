package com.juliuskrah.repository;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import com.juliuskrah.model.Service;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
@ApplicationScoped
public class ServiceRepository implements PanacheRepositoryBase<Service, UUID> {

    public Uni<Service> findByNameIgnoreCase(String name) {
        return find("LOWER(name) = LOWER(?1)", name).firstResult();
    }

    public Uni<Service> findByCodeIgnoreCase(String code) {
        return find("LOWER(code) = LOWER(?1)", code).firstResult();
    }

    public Multi<Service> findByClientNameIgnoreCase(String name) {
        return find("LOWER(client.name) = LOWER(?1)", name).stream();
    }

    public Multi<Service> findByClientCodeIgnoreCase(String code) {
        return find("LOWER(client.code) = LOWER(?1)", code).stream();
    }
}
