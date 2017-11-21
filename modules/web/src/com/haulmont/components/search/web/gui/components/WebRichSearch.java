package com.haulmont.components.search.web.gui.components;

import com.haulmont.components.search.context.SearchContext;
import com.haulmont.components.search.gui.components.RichSearch;
import com.haulmont.components.search.presenter.SearchPresenter;
import com.haulmont.components.search.strategy.SearchEntity;
import com.haulmont.components.search.strategy.SearchEntry;
import com.haulmont.components.search.strategy.SearchStrategy;
import com.haulmont.cuba.gui.components.CaptionMode;
import com.haulmont.cuba.web.gui.components.WebSuggestionField;
import com.haulmont.cuba.web.toolkit.ui.CubaSuggestionField;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implements Web Vaadin version of component
 * <br />
 * @see SearchPresenter
 * @see com.haulmont.components.search.presenter.impl.SearchPresenterImpl
 */
public class WebRichSearch extends WebSuggestionField implements RichSearch {

    protected SearchContext context;
    protected SearchPresenter presenter;

    public WebRichSearch() {
        component = new CubaSuggestionFieldExtended();
        component.setTextViewConverter(this::convertToTextView);
        component.setCancelSearchHandler(this::cancelSearch);
        component.setSearchExecutor(query -> {
            cancelSearch();
            searchSuggestions(query);
        });
        attachListener(component);

        setCaptionProperty("caption");
        setCaptionMode(CaptionMode.PROPERTY);

        setSearchExecutor(this::search);

        addValueChangeListener((context, value)-> {
            if (presenter != null) {
                presenter.invoke(context, value);
            }
        });
    }

    @Override
    public void init(SearchContext context, SearchPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
    }

    public void resetValue() {
        unwrap(CubaSuggestionFieldExtended.class).reset();
    }

    @Override
    public void addValueChangeListener(BiConsumer<SearchContext, SearchEntry> listener) {
        super.addValueChangeListener(e-> listener.accept(context, ((SearchEntity) e.getValue()).getDelegate()));
    }

    @Override
    public void addStrategy(SearchStrategy searchStrategy) {
        presenter.addStrategy(searchStrategy);
    }

    @Override
    public <T extends SearchEntry> void addStrategy(String name, Function<String, List<T>> searcher, Consumer<T> invoker) {
        presenter.addStrategy(name, (context, query)-> searcher.apply(query), (context, value)-> invoker.accept(value));
    }

    @Override
    public void removeStrategy(String name) {
        presenter.removeStrategy(name);
    }

    protected List<SearchEntity> search(String searchString, Map<String, Object> searchParams) {
        return presenter != null ? presenter.load(context.withParams(searchParams), searchString)
                : Collections.emptyList();
    }

    static class CubaSuggestionFieldExtended extends CubaSuggestionField {

        AtomicBoolean needsToReset = new AtomicBoolean(false);

        public void reset() {
            needsToReset.set(true);
        }

        @Override
        public void beforeClientResponse(boolean initial) {
            super.beforeClientResponse(initial);
            if (needsToReset.getAndSet(false)) {
                //FIXME: no way to resetValue value without resetValue state
                setValue(SearchEntity.EMPTY);
                setValue(null);
                //FIXME: this works, but only once
                getState().text = null;
            }
        }
    }
}
