package com.juliuskrah.model;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author Julius Krah
 */
@Entity
public class ServiceSetting implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    @EmbeddedId
    private ServiceSettingId id;
    private String collectionAccount;
    private boolean notifyCustomer;
    private boolean acknowledgePayer;
    private String smsSourceAddress;
    private Double floatAmount;
    
    public ServiceSettingId getId() {
        return id;
    }
    public void setId(ServiceSettingId id) {
        this.id = id;
    }
    public String getCollectionAccount() {
        return collectionAccount;
    }
    public void setCollectionAccount(String collectionAccount) {
        this.collectionAccount = collectionAccount;
    }
    public boolean isNotifyCustomer() {
        return notifyCustomer;
    }
    public void setNotifyCustomer(boolean notifyCustomer) {
        this.notifyCustomer = notifyCustomer;
    }
    public boolean isAcknowledgePayer() {
        return acknowledgePayer;
    }
    public void setAcknowledgePayer(boolean acknowledgePayer) {
        this.acknowledgePayer = acknowledgePayer;
    }
    public String getSmsSourceAddress() {
        return smsSourceAddress;
    }
    public void setSmsSourceAddress(String smsSourceAddress) {
        this.smsSourceAddress = smsSourceAddress;
    }
    public Double getFloatAmount() {
        return floatAmount;
    }
    public void setFloatAmount(Double floatAmount) {
        this.floatAmount = floatAmount;
    }

}
