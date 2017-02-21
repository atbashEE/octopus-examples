package be.rubus.octopus.book.ex2;

import be.c4j.ee.security.model.UserPrincipal;
import be.c4j.ee.security.permission.NamedDomainPermission;
import be.c4j.ee.security.permission.PermissionLookup;
import be.c4j.ee.security.realm.AuthenticationInfoBuilder;
import be.c4j.ee.security.realm.AuthorizationInfoBuilder;
import be.c4j.ee.security.realm.SecurityDataProvider;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@ApplicationScoped
public class ApplicationSecurityData implements SecurityDataProvider {

    private int principalId = 0;

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) {
        if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

            AuthenticationInfoBuilder authenticationInfoBuilder = new AuthenticationInfoBuilder();
            authenticationInfoBuilder.principalId(principalId++).name(authenticationToken.getPrincipal().toString());
            authenticationInfoBuilder.userName(authenticationToken.getPrincipal().toString());
            // TODO: Change for production. Here we use username as password
            authenticationInfoBuilder.password(usernamePasswordToken.getUsername());

            return authenticationInfoBuilder.build();
        }

        return null;
    }

    @Override
    public AuthorizationInfo getAuthorizationInfo(PrincipalCollection principalCollection) {
        AuthorizationInfoBuilder builder = new AuthorizationInfoBuilder();
        UserPrincipal principal = (UserPrincipal) principalCollection.getPrimaryPrincipal();
        if ("admin".equalsIgnoreCase(principal.getUserName())) {
            builder.addPermission(AppPermission.ACCESS.name());
        }
        return builder.build();
    }

    @Produces
    public PermissionLookup<AppPermission> defineLookup() {
        List<NamedDomainPermission> allPermissions = new ArrayList<>();
        allPermissions.add(new NamedDomainPermission(AppPermission.ACCESS_DEMO.name(), "access:demo:*"));
        allPermissions.add(new NamedDomainPermission(AppPermission.ACCESS.name(), "access:*:*"));
        allPermissions.add(new NamedDomainPermission(AppPermission.TOP_SECRET.name(), "top:secret:*"));

        return new PermissionLookup<AppPermission>(allPermissions, AppPermission.class);
    }
}
