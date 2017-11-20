package com.haulmont.components.search.presenter;

import com.haulmont.components.search.context.SearchConfiguration;
import com.haulmont.components.search.context.SearchSession;
import com.haulmont.components.search.context.SessionFactory;
import com.haulmont.components.search.gui.components.GlobalSearch;
import com.haulmont.components.search.provider.SearchEntity;
import com.haulmont.components.search.provider.SearchProviderFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("search_SearchPresenter")
public class SearchPresenter {

    @Inject
    SearchProviderFactory providerFactory;

    @Inject
    SessionFactory sessionFactory;

    public void decorate(GlobalSearch component, SearchConfiguration configuration) {
        component.setSearchProvider(this::query);
        component.addValueChangeListener((SearchEntity entry) -> {
            if (entry == null || entry == SearchEntity.EMPTY) return;
            SearchSession session = sessionFactory.session();
            providerFactory.defaultProvider()
                    .choiceHandler(session)
                    .accept(entry);
            component.reset();
        });
    }

    public List<SearchEntity> query(Map<String, Object> params) {
        return providerFactory.defaultProvider()
                .loader(sessionFactory.session())
                .query(params.get("searchString").toString())
                .stream().map(SearchEntity::new).collect(Collectors.toList());
    }
}
