package com.haulmont.components.search.web.context;

import com.haulmont.components.search.context.SearchContext;
import com.haulmont.components.search.context.SearchContextFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component(SearchContextFactory.NAME)
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class WebSearchContextFactory implements SearchContextFactory {
    @Override
    public SearchContext session() {
        return new WebSearchContext();
    }
}
