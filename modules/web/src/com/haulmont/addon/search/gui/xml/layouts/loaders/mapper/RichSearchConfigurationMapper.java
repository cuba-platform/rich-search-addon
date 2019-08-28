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

package com.haulmont.addon.search.gui.xml.layouts.loaders.mapper;

import com.google.common.base.Preconditions;
import com.haulmont.addon.search.context.SearchConfiguration;
import com.haulmont.addon.search.strategy.ContextualSearchStrategy;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.addon.search.strategy.SearchStrategy;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.xml.layout.ComponentLoader;
import com.haulmont.cuba.gui.xml.layout.ComponentLoader.Context;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Maps XML Configuration to {@code com.haulmont.addon.search.context.SearchConfiguration} bean, which used for search component initialisation
 * <p>
 * configuration examples:
 * <ul>
 * <li>
 * <b>Strategy bean example</b>
 * <pre>{@code
 *             <window xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
 *                 <search:richSearch id="search" inputPrompt="msg://searching">
 *                     <search:strategyBean name="search_MainMenuSearchProvider" />
 *                 </search:richSearch>
 *             </window>
 * }</pre>
 * </li>
 * <li>
 * <b>Inline frame methods based strategy example</b>
 * <pre>{@code
 *             <window xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
 *                 <search:richSearch id="search" inputPrompt="msg://searching">
 *                     <search:strategy name="Custom Strategy" searchMethod="methodName" invokeMethod="methodName" />
 *                 </search:richSearch>
 *             </window>
 * }</pre>
 * </li>
 * </ul>
 * <br />
 *
 * @see ApplicationContext
 * @see SearchStrategy
 * @see com.haulmont.addon.search.gui.xml.layouts.loaders.RichSearchLoader
 * @see ContextualSearchStrategy
 */
@Component("search_RichSearchConfigurationMapper")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RichSearchConfigurationMapper {

    @Inject
    protected Logger logger;

    public SearchConfiguration map(Context context, Element element) {
        return () -> Stream.of(
                mapProviderBeans(element.elements("strategyBean")),
                mapContextualProviders(context, element.elements("strategy"))
        ).flatMap(Function.identity())
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(SearchStrategy::name, Function.identity()));
    }

    protected Stream<SearchStrategy> mapProviderBeans(List elements) {
        return Optional.ofNullable((List<Element>) elements)
                .orElse(Collections.emptyList()).stream().map(this::strategyBeanMap);
    }

    protected Stream<SearchStrategy> mapContextualProviders(Context context, List elements) {
        return Optional.ofNullable((List<Element>) elements)
                .orElse(Collections.emptyList()).stream().map(e -> contextualStrategyMap(context, e));
    }

    protected SearchStrategy strategyBeanMap(Element element) {
        String name = element.attributeValue("name");
        Preconditions.checkNotNull(name);
        try {
            return AppBeans.get(name, SearchStrategy.class);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }

    protected SearchStrategy contextualStrategyMap(Context context, Element element) {
        String name = element.attributeValue("name");
        String searcher = element.attributeValue("searchMethod");
        String invoker = element.attributeValue("invokeMethod");

        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(searcher);
        Preconditions.checkNotNull(invoker);

        return new ContextualSearchStrategy<>(name,
                (ctx, query) -> {
                    try {
                        return (List<SearchEntry>) MethodUtils.invokeMethod(
                                ((ComponentLoader.ComponentContext) context).getFrame(),
                                searcher,
                                ctx,
                                query
                        );
                    } catch (Exception e) {
                        logger.warn(e.getMessage(), e);
                        return Collections.emptyList();
                    }
                }, (ctx, value) -> {
            try {
                MethodUtils.invokeMethod(((ComponentLoader.ComponentContext) context).getFrame(), invoker, ctx, value);
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        });
    }
}
