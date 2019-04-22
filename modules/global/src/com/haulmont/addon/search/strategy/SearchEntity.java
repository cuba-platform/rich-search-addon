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

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.model.impl.AbstractInstance;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;

import javax.persistence.Id;

/**
 * <p>Implements entity meta structure. <br />
 * The structure may be used as search entry wrapper for value in Datasource (or Component)
 * </p>
 * <b>See also:</b> <br />
 * <pre>com.haulmont.cuba.gui.data.CollectionDatasource</pre>
 * <pre>com.haulmont.cuba.gui.components.Field</pre>
 * <pre>com.haulmont.cuba.gui.components.Component.HasValue</pre>
 */
@MetaClass(name = "search$SearchEntry")
public class SearchEntity extends AbstractInstance implements Entity<String>, SearchEntry {

    public static final SearchEntity EMPTY = new SearchEntity(new DefaultSearchEntry(null, "", "", null));
    public static final SearchEntity NO_RESULTS
            = new SearchEntity(new DefaultSearchEntry(null, "", AppBeans.get(Messages.class).getMainMessage("noResultsFound"), null));

    protected SearchEntry delegate;

    public SearchEntity() {
    }

    public <T extends SearchEntry> SearchEntity(T delegate) {
        this.delegate = delegate;
    }

    @Id
    @Override
    @MetaProperty
    public String getId() {
        return new StringBuilder(delegate.getStrategyName())
                .append('/')
                .append(delegate.getId()).toString();
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

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
