package com.haulmont.components.search.provider;

public interface SearchEntry {
    String getId();
    String getQueryString();
    String getCaption();
    Integer getTypeId();
}
