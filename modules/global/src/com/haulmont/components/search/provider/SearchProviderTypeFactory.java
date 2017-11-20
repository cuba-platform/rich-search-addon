package com.haulmont.components.search.provider;

import java.io.Serializable;

public interface SearchProviderTypeFactory extends Serializable {
    String NAME = "search_SearchProviderTypeFactory";

    SearchProviderType typeBy(String name);

    SearchProviderType typeBy(Integer id);
}
