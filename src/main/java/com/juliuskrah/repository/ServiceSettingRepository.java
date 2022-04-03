package com.juliuskrah.repository;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import com.juliuskrah.model.ServiceSetting;
import com.juliuskrah.model.ServiceSettingId;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Multi;

/**
 * @author Julius Krah
 */
@ApplicationScoped
public class ServiceSettingRepository implements PanacheRepositoryBase<ServiceSetting, ServiceSettingId> {

    public Multi<ServiceSetting> findByPayerClientId(UUID id) {
        return find("""
            SELECT 
                ss 
            FROM 
                ServiceSetting ss 
            WHERE 
                ss.id.payerClient.id = ?1
            """, id).stream();
    }

    public Multi<ServiceSetting> findByReceiverClientId(UUID id){
        return find("""
            SELECT 
                ss 
            FROM 
                ServiceSetting ss 
            WHERE 
                ss.id.receiverClient.id = :id
            """, id).stream();
    }

    public Multi<ServiceSetting> findByServiceId(UUID id) {
        return find("""
            SELECT
                ss
            FROM
                ServiceSetting ss
            WHERE
                ss.id.service.id = :id
            """, id).stream();
    }
}
