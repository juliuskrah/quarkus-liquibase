package com.juliuskrah.repository;

import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import com.juliuskrah.model.ServiceSetting;
import com.juliuskrah.model.ServiceSettingId;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * @author Julius Krah
 */
@ApplicationScoped
public class ServiceSettingRepository implements PanacheRepositoryBase<ServiceSetting, ServiceSettingId> {

    public Multi<ServiceSetting> findByPayerClientId(UUID id) {
        return find("id.payerClient.id = ?1", id).stream();
    }

    public Uni<List<ServiceSetting>> findByReceiverClientId(UUID id){
        return find("id.receiverClient.id = :id", Parameters.with("id", id)).list();
    }

    public Multi<ServiceSetting> findByServiceId(UUID id) {
        return find("id.service.id = :id", Parameters.with("id", id)).stream();
    }
}
