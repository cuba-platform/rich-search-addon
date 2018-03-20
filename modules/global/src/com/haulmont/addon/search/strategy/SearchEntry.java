package com.haulmont.addon.search.strategy;

import javax.annotation.Nonnull;

/**
 * <p>Declares the number of methods used in search for result presentation</p>
 * Default implementation:
 * {@link DefaultSearchEntry}
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
