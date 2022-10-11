package org.acme;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    public List<Subscription> findAll() {
        return this.subscriptionMapper.toDomainList(subscriptionRepository.findAll().list());
    }

    public Optional<Subscription> findById(@NonNull Integer customerId) {
        return subscriptionRepository.findByIdOptional(subscriptionId)
                .map(subscriptionMapper::toDomain);
    }

    @Transactional
    public void save(@Valid Subscription subscription) {
        log.debug("Saving Subscription: {}", subscription);
        SubscriptionEntity subscriptionEntity = subscriptionMapper.toEntity(subscription);
        subscriptionRepository.persist(subscriptionEntity);
        subscriptionMapper.updateDomainFromEntity(subscriptionEntity,subscription);
    }

    @Transactional
    public void update(@Valid Subscription subscription) {
        log.debug("Updating Subscription: {}", subscription);
        if (Objects.isNull(subscription.getSubscriptionId())) {
            throw new ServiceException("Subscription does not have a subscriptionId");
        }
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByIdOptional(subscription.getSubscriptionId())
                .orElseThrow(() -> new ServiceException("No Subscription found for subscriptionId[%s]", subscription.getSubscriptionId()));
        subscriptionMapper.updateEntityFromDomain(subscription,subscriptionEntity);
        subscriptionRepository.persist(subscriptionEntity);
        subscriptionMapper.updateDomainFromEntity(subscriptionEntity,subscription);
    }

}
