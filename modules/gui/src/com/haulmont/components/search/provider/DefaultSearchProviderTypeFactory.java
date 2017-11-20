package com.haulmont.components.search.provider;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component(SearchProviderTypeFactory.NAME)
public class DefaultSearchProviderTypeFactory implements SearchProviderTypeFactory {

    private Map<String, SearchProviderType> registryNameMap = new ConcurrentHashMap<>();
    private Map<Integer, SearchProviderType> registryIdMap = new ConcurrentHashMap<>();

    public DefaultSearchProviderTypeFactory() {
        for (SearchProviderType searchProviderType : DefaultSearchProvider.values()) {
            registryNameMap.put(searchProviderType.name(), searchProviderType);
            registryIdMap.put(searchProviderType.id(), searchProviderType);
        }
    }

    @Override
    public SearchProviderType typeBy(String name) {
        return registryNameMap.get(name);
    }

    @Override
    public SearchProviderType typeBy(Integer id) {
        return registryNameMap.get(id);
    }
}
