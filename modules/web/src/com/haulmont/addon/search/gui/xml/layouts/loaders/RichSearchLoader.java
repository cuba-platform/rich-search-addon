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

package com.haulmont.addon.search.gui.xml.layouts.loaders;

import com.haulmont.addon.search.gui.components.RichSearch;
import com.haulmont.addon.search.gui.xml.layouts.loaders.mapper.RichSearchConfigurationMapper;
import com.haulmont.addon.search.presenter.SearchPresenter;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.xml.layout.loaders.SuggestionFieldLoader;

/**
 * <p>Create and load {@link RichSearch} component to frame.<br />
 * Also links component with presenter {@link SearchPresenter}.</p>
 *
 * Configuration mapper: {@link RichSearchConfigurationMapper}
 */
public class RichSearchLoader extends SuggestionFieldLoader {

    protected RichSearchConfigurationMapper configurationMapper = AppBeans.get(RichSearchConfigurationMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void createComponent() {
        resultComponent = (RichSearch) factory.create(RichSearch.NAME);
        loadId(resultComponent, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadComponent() {
        super.loadComponent();
        AppBeans.get(SearchPresenter.class).init((RichSearch)resultComponent, configurationMapper.map(context, element));
    }
}
