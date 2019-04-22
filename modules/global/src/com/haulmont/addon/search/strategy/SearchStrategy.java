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

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Declares named search strategy bean, that must provide:
 * <ul>
 *     <li>data searching by string query</li>
 *     <li>data presenting by entry list
 *     <li>specific logic invocation on entry choosing</li>
 * </ul>
 * @see SearchContext
 * @see SearchEntity
 * @see DefaultSearchEntry
 */
public interface SearchStrategy<T extends SearchEntry>  {

    /**
     * Method must return unique name of searching strategy
     */
    @Nonnull
    String name();

    /**
     * Provides founded data
     * and will be triggered on each search action.
     * <br />
     * @param context search context object with params
     * @param query search pattern
     */
    @Nonnull
    List<T> load(@Nonnull SearchContext context, String query);

    /**
     * Method will be invoked on each choose triggered by search component.
     * <br />
     * @param context search context object with params
     * @param entry chosen value
     */
    void invoke(@Nonnull SearchContext context, T entry);
}
