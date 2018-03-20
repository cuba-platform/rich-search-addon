package com.haulmont.addon.search.context;

import com.haulmont.cuba.security.global.UserSession;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Contains context depended data for searching mechanism.<br />
 * The implementation depends on client type and must provide user session and form data params.
 * <br />
 * The context instance must be created via factory method {@link SearchContextFactory#session()}.
 * </p>
 * See also the default web implementation:
 * <pre>com.haulmont.addon.search.web.context.WebSearchContext</pre>
 */
public interface SearchContext extends Serializable {
    /**
     * @return <b>user session</b> object, currently logged in
     */
    UserSession session();

    /**
     * @return <b>context dependent params</b>, that can be provided by search strategy
     */
    Map<String, Object> params();

    /**
     * Implements client related access for pushing callback
     * @param callback function that will be called
     */
    void applyUICallback(Runnable callback);

    /**
     * @param params that will be added to context
     * @return new version of <b>context</b>
     */
    SearchContext withParams(Map<String, Object> params);
    /**
     * @param params that will be added to context
     * @return current version of <b>context</b>
     */
    SearchContext extendParams(Map<String, Object> params);
}
