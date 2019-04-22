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

package com.haulmont.addon.search.context;

import com.haulmont.addon.search.strategy.SearchStrategy;

import java.util.Map;

/**
 * <p>Contains configuration params for searching.<br />
 * Belongs to presenter as strategy holder for search component.
 * </p>
 * Basic mutable implementation:
 * {@link com.haulmont.addon.search.context.configuration.ExtendableSearchConfiguration}
 */
@FunctionalInterface
public interface SearchConfiguration {

    /**
     * @return <b>immutable</b> strategy collection, mapped by name
     */
    Map<String, SearchStrategy> strategyProviders();
}
