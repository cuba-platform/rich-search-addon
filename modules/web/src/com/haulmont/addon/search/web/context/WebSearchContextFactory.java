package com.haulmont.addon.search.web.context;

import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.context.SearchContextFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Provides instances of {@link WebSearchContext}.
 */
@Component(SearchContextFactory.NAME)
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class WebSearchContextFactory implements SearchContextFactory {
    /**
     * {@inheritDoc}
     */
    @Override
    public SearchContext session() {
        return new WebSearchContext();
    }
}
