package com.ipostu.demo.spring.jar14demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private static final Logger LOG = LoggerFactory.getLogger(AdminService.class);

    // requires: @EnableMethodSecurity(securedEnabled = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') ")
    public void doAdminStuff() {
        LOG.warn("doAdminStuff was called");
    }

}
