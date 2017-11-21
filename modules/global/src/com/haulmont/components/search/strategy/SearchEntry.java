package com.haulmont.components.search.strategy;

import javax.annotation.Nonnull;

/**
 * Declares the number of methods used for search result presentation layer
 * <br />
 * Default implementation:
 * @see DefaultSearchEntry
 */
public interface SearchEntry {
    /**
     * @return unique entry identifier
     */
    @Nonnull
    String getId();

    /**
     * @return entry presentation name
     */
    @Nonnull
    String getCaption();

    /**
     * @return the name of search strategy which the entry belongs
     */
    @Nonnull
    String getStrategyName();
}
