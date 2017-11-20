package com.haulmont.components.search.provider;

import com.haulmont.components.search.context.SearchSession;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface SearchProvider {

    SearchProviderType type();

    DataLoader loader(SearchSession context);

    Consumer<SearchEntry> choiceHandler(SearchSession context);

}
