package com.haulmont.components.search.gui.xml.layouts.loaders.parser;

import com.google.common.base.Preconditions;
import com.haulmont.components.search.context.SearchConfiguration;
import com.haulmont.components.search.strategy.ContextualSearchStrategy;
import com.haulmont.components.search.strategy.SearchEntry;
import com.haulmont.components.search.strategy.SearchStrategy;
import com.haulmont.cuba.gui.xml.layout.ComponentLoader.Context;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component("search_RichSearchConfigurationMapper")
@Scope("prototype")
public class RichSearchConfigurationMapper {

    @Autowired
    protected ApplicationContext injector;

    @Inject
    protected Logger logger;

    public SearchConfiguration map(Context context, Element element) {
        return ()-> Stream.of(
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
            return injector.getBean(name, SearchStrategy.class);
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
                        return (List<SearchEntry>)MethodUtils.invokeMethod(context.getFrame(), searcher, ctx, query);
                    } catch (Exception e) {
                        logger.warn(e.getMessage(), e);
                        return Collections.emptyList();
                    }
                }, (ctx, value) -> {
                    try {
                        MethodUtils.invokeMethod(context.getFrame(), invoker, ctx, value);
                    } catch (Exception e) {
                        logger.warn(e.getMessage(), e);
                    }
                });
    }
}
