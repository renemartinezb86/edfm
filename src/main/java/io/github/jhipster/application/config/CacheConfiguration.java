package io.github.jhipster.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BucoVersion.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BucoSheet.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ProductOffering.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ProductOffering.class.getName() + ".rules", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ProductOffering.class.getName() + ".resources", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ProductOffering.class.getName() + ".networkResources", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ProductOffering.class.getName() + ".services", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ProductOffering.class.getName() + ".cfssPops", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ProductOffering.class.getName() + ".freeUnits", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.POCharacteristic.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Service.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Service.class.getName() + ".productOfferings", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.CfssPop.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.CfssPop.class.getName() + ".productOfferings", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Rule.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Rule.class.getName() + ".productOfferings", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.NetworkResource.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.NetworkResource.class.getName() + ".networkParameters", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.NetworkResource.class.getName() + ".productOfferings", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.NetworkParameter.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.NetworkParameter.class.getName() + ".networkResources", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Resource.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Resource.class.getName() + ".productOfferings", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.PoPrice.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.FreeUnit.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.FreeUnit.class.getName() + ".productOfferings", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ChargingSystem.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Bscs.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
