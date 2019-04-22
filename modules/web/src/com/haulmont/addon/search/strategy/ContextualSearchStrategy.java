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
