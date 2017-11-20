package com.haulmont.components.search.gui.components;

import com.haulmont.components.search.provider.SearchEntity;
import com.haulmont.cuba.gui.components.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public interface GlobalSearch extends Component, Component.BelongToFrame, Component.HasCaption, Component.HasValue, Component.HasInputPrompt  {
    String NAME = "globalSearch";

    void setSearchProvider(Function<Map<String, Object>, List<SearchEntity>> searcher);

    void addValueChangeListener(Consumer<SearchEntity> listener);

    void reset();
}
