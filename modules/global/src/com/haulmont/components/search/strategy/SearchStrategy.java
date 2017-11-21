package com.haulmont.components.search.strategy;

import com.haulmont.components.search.context.SearchContext;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Declares named search strategy bean, that must provides:
 * <ul>
 *     <li>data searching by string query</li>
 *     <li>data presenting by entry list {@code List<SearchEntry>}</li>
 *     <li>specific logic invocation on entry choosing</li>
 * </ul>
 * <br />
 * @see SearchContext
 * @see SearchEntry
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
     * and will be triggered on each search action
     * <br />
     * @param context search object with params
     * @param query pattern
     */
    @Nonnull
    List<T> load(@Nonnull SearchContext context, String query);

    /**
     * Method will be invoked on each choose triggered by search component
     * <br />
     * @param context search object with params
     * @param entry chosen
     */
    void invoke(@Nonnull SearchContext context, T entry);
}
