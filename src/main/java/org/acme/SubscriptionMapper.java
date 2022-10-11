package org.acme;

import java.util.List;

/**
 * Can´t import this
 * import org.mapstruct.InheritInverseConfiguration;
 * import org.mapstruct.Mapper;
 * import org.mapstruct.MappingTarget;
 */

//@Mapper(componentModel = "cdi")
public interface SubscriptionMapper {

    List<SubscriptionEntity> toDomainList(List<SubscriptionEntity> entities);

    Customer toDomain(SubscriptionEntity entity);

    @InheritInverseConfiguration(name = "toDomain")
    SubscriptionEntity toEntity(Subscription domain);

    void updateEntityFromDomain(Subscription domain, @MappingTarget SubscriptionEntity entity);

    void updateDomainFromEntity(SubscriptionEntity entity, @MappingTarget Subscription domain);

}
