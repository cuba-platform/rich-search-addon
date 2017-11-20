package com.haulmont.components.search.provider;

public enum DefaultSearchProvider implements SearchProviderType {
    MENU(1)
    ;



    DefaultSearchProvider(int id) {
        this.id = id;
    }

    int id;
    
    @Override
    public int id() {
        return id;
    }
}
