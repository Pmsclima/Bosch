package org.acme;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SubscriptionRepository implements PanacheRepositoryBase<SubscriptionEntity,Integer> {
}
