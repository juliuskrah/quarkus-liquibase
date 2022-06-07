package com.juliuskrah.model;
import static javax.persistence.FetchType.LAZY;
import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * @author Julius Krah
 */
@Embeddable
public class ServiceSettingId implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    @ManyToOne(fetch = LAZY)
    private Service service;
    @ManyToOne(fetch = LAZY)
    private Client receiverClient;
    @ManyToOne(fetch = LAZY)
    private Client payerClient;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Client getReceiverClient() {
        return receiverClient;
    }

    public void setReceiverClient(Client receiverClient) {
        this.receiverClient = receiverClient;
    }

    public Client getPayerClient() {
        return payerClient;
    }

    public void setPayerClient(Client payerClient) {
        this.payerClient = payerClient;
    }

}
