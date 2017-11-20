package com.haulmont.components.search.provider;

import java.util.List;

public interface DataLoader {
    List<SearchEntry> query(String pattern);
    default void destroy() {}
}
