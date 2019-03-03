package com.haulmont.addon.search.strategy;

import com.haulmont.addon.search.context.SearchContext;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * <p>Declares specific strategy skeleton for dynamic {@link SearchStrategy} generation.<br />
 * Basically used for internal purposes in {@link com.haulmont.addon.search.gui.xml.layouts.loaders.mapper.RichSearchConfigurationMapper}.
 * </p>
 * @param <T> depends on entry {@link SearchEntry} implementation
 */
public class ContextualSearchStrategy<T extends SearchEntry> implements SearchStrategy<T> {

    protected String name;
    protected BiFunction<SearchContext, String, List<T>> searcher;
    protected BiConsumer<SearchContext, T> invoker;

    public ContextualSearchStrategy(String name, BiFunction<SearchContext, String, List<T>> searcher,
                                    BiConsumer<SearchContext, T> invoker) {
        this.name = name;
        this.searcher = searcher;
        this.invoker = invoker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> load(SearchContext context, String query) {
        return searcher.apply(context, query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invoke(SearchContext context, T value) {
        invoker.accept(context, value);
    }
}
