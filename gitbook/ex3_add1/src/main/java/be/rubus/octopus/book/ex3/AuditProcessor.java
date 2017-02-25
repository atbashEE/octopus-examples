package be.rubus.octopus.book.ex3;

import be.c4j.ee.security.audit.OctopusAuditEvent;
import be.c4j.ee.security.model.UserPrincipal;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 *
 */
@ApplicationScoped
public class AuditProcessor {

    public void onPageAccess(@Observes OctopusAuditEvent auditEvent) {
        UserPrincipal principal = (UserPrincipal) auditEvent.getPrincipal();
        String userName = principal == null ? "Anonymous" :  principal.getUserName();
        System.out.println(String.format("User %s accessed the resource %s from ip address %s with browser %s",
                userName, auditEvent.getRequestURI(), auditEvent.getRemoteAddress(), auditEvent.getUserAgent()));
    }
}
