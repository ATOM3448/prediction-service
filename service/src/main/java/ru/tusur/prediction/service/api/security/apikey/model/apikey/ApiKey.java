package ru.tusur.prediction.service.api.security.apikey.model.apikey;

import java.util.Collection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Класс представляет собой Api Key, выданный клиенту.
 */
@EqualsAndHashCode(callSuper = false)
public class ApiKey extends AbstractAuthenticationToken {

    @Getter private final String prefix;

    private final String value;

    private final Long organizationId;

    public ApiKey(String prefix, String value) {
        super(null);
        this.prefix = prefix;
        this.value = value;
        this.organizationId = null;
        setAuthenticated(false);
    }

    public ApiKey(Long organization, Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.prefix = null;
        this.value = null;
        this.organizationId = organization;
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return organizationId;
    }

    @Override
    public Object getCredentials() {
        return value;
    }
}
