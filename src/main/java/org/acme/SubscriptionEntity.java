package org.acme;
import lombok.Data;

/**
 * Can´t import javax persistence
 * @Entity(name = "Subscription")
 * @Table(name = "subscription")
 */
@Data
public class SubscriptionEntity {

    /**
     *  @Id
     *  @GeneratedValue(strategy = GenerationType.IDENTITY)
     *  @Column(name = "subscription_id")
     */
    public Long id;

    /**
     * @Column(name = "subscription_name")
     * @NotEmpty
     */
    public String subscriptionName;

    /**
     * @Column(name = "subscription_status")
     * @NotEmpty
     */
    public String status;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(Long id, String subscriptionName, String status) {
        this.id = id;
        this.subscriptionName = subscriptionName;
        this.status = status;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
