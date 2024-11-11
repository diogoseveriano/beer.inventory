package context;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class UserContext {
    @Inject
    SecurityIdentity identity;

    public String getCurrentUsername() {
        return identity != null ? identity.getPrincipal().getName() : "anonymous";
    }
}