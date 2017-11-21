package com.haulmont.components.search.strategy;

import com.haulmont.components.search.context.SearchContext;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Declares specific strategy skeleton for dynamic search strategy {@link SearchStrategy} generation.
 * Basically used for internal purposes {@link com.haulmont.components.search.gui.xml.layouts.loaders.mapper.RichSearchConfigurationMapper}.
 * <br />
 *
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

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<T> load(SearchContext context, String query) {
        return searcher.apply(context, query);
    }

    @Override
    public void invoke(SearchContext context, T value) {
        invoker.accept(context, value);
    }
}
