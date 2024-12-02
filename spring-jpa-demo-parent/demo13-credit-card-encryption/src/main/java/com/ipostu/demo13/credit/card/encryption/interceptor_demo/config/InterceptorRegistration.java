package com.ipostu.demo13.credit.card.encryption.interceptor_demo.config;

import java.util.Map;

import com.ipostu.demo13.credit.card.encryption.interceptor_demo.interceptors.EncryptionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class InterceptorRegistration implements HibernatePropertiesCustomizer {

    @Autowired
    EncryptionInterceptor interceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", interceptor);
    }
}
