package com.haulmont.components.search.context;

import com.haulmont.cuba.security.global.UserSession;

import java.io.Serializable;
import java.util.Map;

/**
 * Contains context depended data for search mechanism.
 * The implementation relates on client type and must provide user session and form data params.
 * <br />
 * The context instance must be created via factory methods {@link SearchContextFactory#session()}.
 * <br />
 * See also the default web implementation:
 * <pre>com.haulmont.components.search.web.context.WebSearchContext</pre>
 */
public interface SearchContext extends Serializable {
    /**
     * @return user session object, currently logged in
     */
    UserSession session();

    /**
     * @return context dependent params, that can be provided by search strategy
     */
    Map<String, Object> params();

    /**
     * Implements client related access for pushing callback
     * @param callback function that will be called in client side after access
     */
    void applyUICallback(Runnable callback);

    /**
     * @param params that will be added to context
     * @return new version of context
     */
    SearchContext withParams(Map<String, Object> params);
    /**
     * @param params that will be added to context
     * @return current version of context
     */
    SearchContext extendParams(Map<String, Object> params);
}
