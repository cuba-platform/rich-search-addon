package com.haulmont.components.search.web.gui.components;

import com.haulmont.components.search.gui.components.GlobalSearch;
import com.haulmont.components.search.provider.SearchEntity;
import com.haulmont.cuba.gui.components.CaptionMode;
import com.haulmont.cuba.web.gui.components.WebSuggestionField;
import com.haulmont.cuba.web.toolkit.ui.CubaSuggestionField;
import com.vaadin.server.AbstractClientConnector;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

public class WebGlobalSearch extends WebSuggestionField implements GlobalSearch {

    Function<Map<String, Object>, List<SearchEntity>> searcher = (options)-> Collections.emptyList();

    public WebGlobalSearch() {
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
    }

    public void reset() {
        unwrap(CubaSuggestionFieldExtended.class).reset();
    }

    protected List<SearchEntity> search(String searchString, Map searchParams) {
        Map<String, Object> params = new HashMap<>(searchParams);
        params.put("searchString", searchString);
        return searcher.apply(params);
    }

    @Override
    public void setSearchProvider(Function<Map<String, Object>, List<SearchEntity>> searcher) {
        this.searcher = searcher;
    }

    @Override
    public void addValueChangeListener(Consumer<SearchEntity> listener) {
        super.addValueChangeListener(e-> listener.accept((SearchEntity) e.getValue()));
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
                needsToReset.set(false);
                //FIXME: no way to reset value without reset state
                setValue(SearchEntity.EMPTY);
                setValue(null);
                //FIXME: this works, but only once
                getState().text = null;
            }
        }
    }
}
