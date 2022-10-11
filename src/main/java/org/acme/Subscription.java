package org.acme;

import lombok.Data;
import org.wildfly.common.annotation.NotNull;

@Data
public class Subscription {

    private Long subscriptionId;
    @NotNull
    private String subscriptionName;
    @NotNull
    private String subscriptionStatus;
}
