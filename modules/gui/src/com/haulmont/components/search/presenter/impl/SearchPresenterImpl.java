package com.haulmont.components.search.presenter.impl;

import com.google.common.base.Preconditions;
import com.haulmont.components.search.context.configuration.ExtendableSearchConfiguration;
import com.haulmont.components.search.context.SearchConfiguration;
import com.haulmont.components.search.context.SearchContext;
import com.haulmont.components.search.context.SearchContextFactory;
import com.haulmont.components.search.gui.components.RichSearch;
import com.haulmont.components.search.presenter.SearchPresenter;
import com.haulmont.components.search.strategy.ContextualSearchStrategy;
import com.haulmont.components.search.strategy.SearchEntity;
import com.haulmont.components.search.strategy.SearchEntry;
import com.haulmont.components.search.strategy.SearchStrategy;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component(SearchPresenter.NAME)
@Scope("prototype")
public class SearchPresenterImpl implements SearchPresenter {

    @Inject
    protected SearchContextFactory searchContextFactory;

    protected RichSearch component;

    protected ExtendableSearchConfiguration configuration;

    public void init(RichSearch component, SearchConfiguration basicConfiguration) {
        component.init(searchContextFactory.session(), this);

        this.component = component;
        this.configuration = new ExtendableSearchConfiguration(basicConfiguration);
    }

    public List<SearchEntity> load(SearchContext context, String query) {
        Preconditions.checkNotNull(context);

        if(StringUtils.isEmpty(query)) return Collections.emptyList();

        return Optional.ofNullable(configuration.strategyProviders()).orElse(Collections.emptyMap())
                .values().stream().flatMap(strategy-> load(context, strategy, query.toLowerCase()))
                .filter(Objects::nonNull)
                .map(SearchEntity::new)
                .collect(Collectors.toList());
    }

    protected Stream<SearchEntry> load(SearchContext context, SearchStrategy searchStrategy, String query) {
        return searchStrategy.load(context, query).stream();
    }

    public void invoke(SearchContext context, SearchEntry entry) {
        SearchStrategy strategy = configuration.strategyProviders().get(entry.getStrategyName());
        if (strategy == null) return;
        context.applyUICallback(()-> strategy.invoke(context, entry));
    }

    public void addStrategy(SearchStrategy searchStrategy) {
        configuration.addStrategy(searchStrategy);
    }

    public <T extends SearchEntry> void addStrategy(String name, BiFunction<SearchContext, String, List<T>> searcher,
                                                    BiConsumer<SearchContext, T> invoker) {
        configuration.addStrategy(new ContextualSearchStrategy<>(name, searcher, invoker));
    }

    public void removeStrategy(String name) {
        configuration.removeStrategy(name);
    }
}
