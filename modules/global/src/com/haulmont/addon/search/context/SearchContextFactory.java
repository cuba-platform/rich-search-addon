package com.haulmont.addon.search.context;

/**
 * <p>Provides instances of {@link SearchContext}.
 * The implementation depends on client type.</p>
 *
 * See also the default web implementation:
 * <pre>com.haulmont.addon.search.web.context.WebSearchContextFactory</pre>
 */
public interface SearchContextFactory {
    String NAME = "search_SearchContextFactory";

    /**
     * @return <b>context object</b> with current search environment
     */
    SearchContext session();
}
