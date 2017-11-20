package com.haulmont.components.search.provider;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.model.impl.AbstractInstance;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;

@MetaClass(name = "search$SearchEntry")
public class SearchEntity extends AbstractInstance implements Entity<String>, SearchEntry {

    public static SearchEntity EMPTY = new SearchEntity(new DefaultSearchEntry(null, "", "", null));

    static class SearchProviderTypeFactoryHolder {
        static final SearchProviderTypeFactory INSTANCE = AppBeans.get(SearchProviderTypeFactory.NAME);
    }

    private SearchEntry delegate;

    public SearchEntity() {}

    public SearchEntity(SearchEntry delegate) {
        this.delegate = delegate;
    }

    @Override
    @MetaProperty
    public String getId() {
        return delegate.getId();
    }

    @Override
    @MetaProperty
    public String getQueryString() {
        return delegate.getQueryString();
    }

    @Override
    @MetaProperty
    public String getCaption() {
        return delegate.getCaption();
    }

    @Override
    @MetaProperty
    public Integer getTypeId() {
        return delegate.getTypeId();
    }

    public SearchProviderType getType() {
        return SearchProviderTypeFactoryHolder.INSTANCE.typeBy(delegate.getTypeId());
    }

    @Override
    public com.haulmont.chile.core.model.MetaClass getMetaClass() {
        Metadata metadata = AppBeans.get(Metadata.NAME);
        return metadata.getSession().getClassNN(getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchEntity that = (SearchEntity) o;

        return delegate != null ? delegate.equals(that.delegate) : that.delegate == null;
    }

    @Override
    public int hashCode() {
        return delegate != null ? delegate.hashCode() : 0;
    }
}
