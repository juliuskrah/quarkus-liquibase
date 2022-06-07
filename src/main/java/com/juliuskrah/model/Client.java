package com.juliuskrah.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

/**
 * @author Julius Krah
 */
@Entity
public class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    private UUID id;
    @Column(length = 40, unique = true)
    private String name;
    private String code;
    private String contactPerson;
    
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
    public String getContactPerson() {
        return contactPerson;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @PrePersist
    void addId() {
        this.id = UUID.randomUUID();
    }
    
    @Override
    public String toString() {
        return "Client [code=" + code + ", contactPerson=" + contactPerson + ", id=" + id
                + ", name=" + name + "]";
    }
    
}
