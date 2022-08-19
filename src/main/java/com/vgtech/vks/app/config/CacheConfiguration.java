package com.vgtech.vks.app.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.vgtech.vks.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.vgtech.vks.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.vgtech.vks.app.domain.User.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.Authority.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.User.class.getName() + ".authorities");
            createCache(cm, com.vgtech.vks.app.domain.SecurityUser.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SecurityUser.class.getName() + ".securityPermissions");
            createCache(cm, com.vgtech.vks.app.domain.SecurityUser.class.getName() + ".securityRoles");
            createCache(cm, com.vgtech.vks.app.domain.SecurityRole.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SecurityRole.class.getName() + ".securityPermissions");
            createCache(cm, com.vgtech.vks.app.domain.SecurityRole.class.getName() + ".securityUsers");
            createCache(cm, com.vgtech.vks.app.domain.SecurityPermission.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SecurityPermission.class.getName() + ".securityRoles");
            createCache(cm, com.vgtech.vks.app.domain.SecurityPermission.class.getName() + ".securityUsers");
            createCache(cm, com.vgtech.vks.app.domain.Society.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.State.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.District.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.Taluka.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.Village.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.ParameterLookup.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SocietyAssets.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SocietyBanksDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SocietyAssetsData.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SocietyConfig.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SocietyPrerequisite.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.LedgerAccounts.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.AccountMapping.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SocietyCropRegistration.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SocietyNpaSetting.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.ExpenditureType.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SchemesDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.SocietyLoanProduct.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.GRInterestDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.Member.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.Documents.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.MemberBank.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.MemberAssets.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.MemberLandAssets.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.Nominee.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.BankDhoranDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.LoanDemand.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.LoanDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.AmortizationDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.LoanDisbursementDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.LoanRepaymentDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.LoanWatapDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.Vouchers.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.VoucherDetails.class.getName());
            createCache(cm, com.vgtech.vks.app.domain.VouchersHistory.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
