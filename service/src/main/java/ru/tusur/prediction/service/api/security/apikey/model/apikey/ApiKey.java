package ru.tusur.prediction.service.api.security.apikey.model.apikey;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Класс представляет собой Api Key, выданный клиенту.
 */
@EqualsAndHashCode(callSuper = false)
public class ApiKey extends AbstractAuthenticationToken {

    private final String value;

    private final Long organizationId;

    public ApiKey(String value) {
        super(null);
        this.value = value;
        this.organizationId = null;
        setAuthenticated(false);
    }

    public ApiKey(String value, Long organization) {
        super(null);
        this.value = value;
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
