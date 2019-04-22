/*
 * Copyright (c) 2008-2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.addon.search.web.gui.components;

import com.google.common.collect.Lists;
import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.gui.components.RichSearch;
import com.haulmont.addon.search.presenter.SearchPresenter;
import com.haulmont.addon.search.strategy.HeaderEntry;
import com.haulmont.addon.search.strategy.SearchEntity;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.addon.search.strategy.SearchStrategy;
import com.haulmont.addon.search.web.gui.components.toolkit.ui.RichSearchField;
import com.haulmont.cuba.gui.components.CaptionMode;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.web.gui.components.WebSuggestionField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implements Web Vaadin version of component
 *
 * @see SearchPresenter
 * @see com.haulmont.addon.search.presenter.impl.SearchPresenterImpl
 */
public class WebRichSearch extends WebSuggestionField implements RichSearch {

    protected SearchContext context;
    protected SearchPresenter presenter;

    public WebRichSearch() {

        component = new RichSearchField();
        RichSearchField richSearchField = (RichSearchField) component;

        richSearchField.setTextViewConverter(this::convertToTextView);

        richSearchField.setSearchExecutor(query -> {
            cancelSearch();
            searchSuggestions((String) query);
        });

        richSearchField.setCancelSearchHandler(this::cancelSearch);

        attachValueChangeListener(component);

        setCaptionProperty("caption");
        setCaptionMode(CaptionMode.PROPERTY);

        setSearchExecutor(this::search);

        addValueChangeListener((context, value) -> {
            if (presenter != null) {
                presenter.invoke(context, value);

                resetValue();
            }
        });

        setOptionsStyleProvider(this::defaultOptionsStyleProvider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(SearchContext context, SearchPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetValue() {
        setValue(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addValueChangeListener(BiConsumer<SearchContext, SearchEntry> listener) {
        super.addValueChangeListener(e -> {
            ValueChangeEvent changeEvent = (ValueChangeEvent) e;
            listener.accept(context,
                    changeEvent.getValue() == null ? null : ((SearchEntity) changeEvent.getValue()).getDelegate());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStrategy(SearchStrategy searchStrategy) {
        presenter.addStrategy(searchStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends SearchEntry> void addStrategy(String name, Function<String, List<T>> searcher, Consumer<T> invoker) {
        presenter.addStrategy(name, (context, query) -> searcher.apply(query), (context, value) -> invoker.accept(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeStrategy(String name) {
        presenter.removeStrategy(name);
    }

    protected List<SearchEntity> search(String searchString, Map<String, Object> searchParams) {
        List<SearchEntity> searchResult = Lists.newArrayList();
        if (presenter != null) {
            searchResult = presenter.load(context.withParams(searchParams), searchString);
        }
        return CollectionUtils.isNotEmpty(searchResult) ? searchResult : Lists.newArrayList(SearchEntity.NO_RESULTS);
    }

    protected String defaultOptionsStyleProvider(Component component, Object o) {

        if (! (o instanceof SearchEntry) || SearchEntity.NO_RESULTS.equals(o)) {
            return StringUtils.EMPTY;
        }

        SearchEntity searchEntity = (SearchEntity) o;
        if (searchEntity.getDelegate() instanceof HeaderEntry) {
            return "rs-header-entry";
        }

        return "rs-search-entry";
    }
}
