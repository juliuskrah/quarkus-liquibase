package com.juliuskrah.model;

import static javax.persistence.FetchType.EAGER;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Julius Krah
 */
@Entity
public class Service implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    @Id
    private UUID id;
    private String name;
    private String code;
    private String currency;
    private String queueName;
    // Fix change to LAZY once lazy loading is figured out
    @ManyToOne(fetch = EAGER)
    private Client client;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getQueueName() {
        return queueName;
    }
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}
