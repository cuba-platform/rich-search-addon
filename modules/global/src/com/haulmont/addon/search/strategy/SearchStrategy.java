package com.haulmont.addon.search.strategy;

import com.haulmont.addon.search.context.SearchContext;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Declares named search strategy bean, that must provide:
 * <ul>
 *     <li>data searching by string query</li>
 *     <li>data presenting by entry list
 *     <li>specific logic invocation on entry choosing</li>
 * </ul>
 * @see SearchContext
 * @see SearchEntity
 * @see DefaultSearchEntry
 */
public interface SearchStrategy<T extends SearchEntry>  {

    /**
     * Method must return unique name of searching strategy
     */
    @Nonnull
    String name();

    /**
     * Provides founded data
     * and will be triggered on each search action.
     * <br />
     * @param context search context object with params
     * @param query search pattern
     */
    @Nonnull
    List<T> load(@Nonnull SearchContext context, String query);

    /**
     * Method will be invoked on each choose triggered by search component.
     * <br />
     * @param context search context object with params
     * @param entry chosen value
     */
    void invoke(@Nonnull SearchContext context, T entry);
}
