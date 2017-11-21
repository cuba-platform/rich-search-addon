package com.haulmont.components.search.context;

/**
 * Provides instances of {@link SearchContext}.
 * The implementation relates of client type
 * <br />
 * See also the default web implementation:
 * <pre>com.haulmont.components.search.web.context.WebSearchContextFactory</pre>
 */
public interface SearchContextFactory {
    String NAME = "search_SearchContextFactory";

    /**
     * @return context object with current search environment
     */
    SearchContext session();
}
