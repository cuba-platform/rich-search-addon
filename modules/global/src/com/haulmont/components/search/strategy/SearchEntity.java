package com.haulmont.components.search.strategy;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.model.impl.AbstractInstance;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;

/**
 * Implements entity meta structure.
 * The structure may be used as search entry wrapper for use in Datasource (or Component value)
 *
 * see:
 * <pre>com.haulmont.cuba.gui.data.CollectionDatasource</pre>
 * <pre>com.haulmont.cuba.gui.components.Field</pre>
 * <pre>com.haulmont.cuba.gui.components.Component.HasValue</pre>
 */
@MetaClass(name = "search$SearchEntry")
public class SearchEntity extends AbstractInstance implements Entity<String>, SearchEntry {

    public static SearchEntity EMPTY = new SearchEntity(new DefaultSearchEntry(null, "", "", null));

    protected SearchEntry delegate;

    public SearchEntity() {}

    public<T extends SearchEntry> SearchEntity(T delegate) {
        this.delegate = delegate;
    }

    @Override
    @MetaProperty
    public String getId() {
        return delegate.getId();
    }

    @Override
    @MetaProperty
    public String getCaption() {
        return delegate.getCaption();
    }

    @Override
    @MetaProperty
    public String getStrategyName() {
        return delegate.getStrategyName();
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

    public SearchEntry getDelegate() {
        return delegate;
    }
}
