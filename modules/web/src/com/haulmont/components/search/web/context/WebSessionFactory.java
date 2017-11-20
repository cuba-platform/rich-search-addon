package com.haulmont.components.search.web.context;

import com.haulmont.components.search.context.SearchSession;
import com.haulmont.components.search.context.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component(SessionFactory.NAME)
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class WebSessionFactory implements SessionFactory {
    @Override
    public SearchSession session() {
        return new WebSearchSession();
    }
}
