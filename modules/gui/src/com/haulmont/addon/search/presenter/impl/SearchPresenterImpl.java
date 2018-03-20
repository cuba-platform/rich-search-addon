package com.haulmont.addon.search.presenter.impl;

import com.google.common.base.Preconditions;
import com.haulmont.addon.search.context.SearchConfiguration;
import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.context.SearchContextFactory;
import com.haulmont.addon.search.context.configuration.ExtendableSearchConfiguration;
import com.haulmont.addon.search.gui.components.RichSearch;
import com.haulmont.addon.search.presenter.SearchPresenter;
import com.haulmont.addon.search.strategy.ContextualSearchStrategy;
import com.haulmont.addon.search.strategy.HeaderEntry;
import com.haulmont.addon.search.strategy.SearchEntity;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.addon.search.strategy.SearchStrategy;
import com.haulmont.cuba.core.global.Messages;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component(SearchPresenter.NAME)
@Scope("prototype")
public class SearchPresenterImpl implements SearchPresenter {

    public static final String STRATEGY_MESSAGE_PREFIX = "searchStrategy.";

    @Inject
    protected SearchContextFactory searchContextFactory;

    @Inject
    protected Messages messages;

    protected RichSearch component;

    protected ExtendableSearchConfiguration configuration;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(RichSearch component, SearchConfiguration basicConfiguration) {
        component.init(searchContextFactory.session(), this);

        this.component = component;
        this.configuration = new ExtendableSearchConfiguration(basicConfiguration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchEntity> load(SearchContext context, String query) {
        Preconditions.checkNotNull(context);

        if (StringUtils.isEmpty(query)) {
            return Collections.emptyList();
        }

        return Optional.ofNullable(configuration.strategyProviders()).orElse(Collections.emptyMap())
                .values().stream().flatMap(strategy -> load(context, strategy, query.toLowerCase()))
                .filter(Objects::nonNull)
                .map(SearchEntity::new)
                .collect(Collectors.toList());
    }

    protected Stream<SearchEntry> load(SearchContext context, SearchStrategy searchStrategy, String query) {
        List<SearchEntry> searchEntries = searchStrategy.load(context, query);
        if (searchEntries.isEmpty()) {
            return Stream.empty();
        }

        SearchEntry header = getHeaderEntryForStrategy(searchStrategy);
        return Stream.concat(Stream.of(header), searchEntries.stream());
    }

    protected SearchEntry getHeaderEntryForStrategy(SearchStrategy searchStrategy) {
        String localizedName = messages.getMainMessage(STRATEGY_MESSAGE_PREFIX + searchStrategy.name());
        return new HeaderEntry(localizedName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invoke(SearchContext context, SearchEntry entry) {
        if (entry == null) {
            return;
        }

        SearchStrategy strategy = configuration.strategyProviders().get(entry.getStrategyName());
        if (strategy == null || entry instanceof HeaderEntry) {
            return;
        }

        context.applyUICallback(() -> strategy.invoke(context, entry));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStrategy(SearchStrategy searchStrategy) {
        configuration.addStrategy(searchStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends SearchEntry> void addStrategy(String name, BiFunction<SearchContext, String, List<T>> searcher,
                                                    BiConsumer<SearchContext, T> invoker) {
        configuration.addStrategy(new ContextualSearchStrategy<>(name, searcher, invoker));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeStrategy(String name) {
        configuration.removeStrategy(name);
    }
}
