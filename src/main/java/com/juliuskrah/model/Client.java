package com.juliuskrah.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

/**
 * @author Julius Krah
 */
@Entity
@Cacheable
public class Client extends PanacheEntityBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    public UUID id;
    @Column(length = 40, unique = true)
    public String name;
    public String code;
    public String contactPerson;
}
