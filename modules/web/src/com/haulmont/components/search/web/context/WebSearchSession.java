package com.haulmont.components.search.web.context;

import com.haulmont.components.search.context.SearchSession;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.global.UserSession;
import com.vaadin.server.ClientConnector;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSearchSession implements SearchSession {

    VaadinSession vaadinSession = VaadinSession.getCurrent();
    SecurityContext securityContext = AppContext.getSecurityContextNN();
    static Logger logger = LoggerFactory.getLogger(WebSearchSession.class);

    @Override
    public UserSession userSession() {
        return vaadinSession.getAttribute(UserSession.class);
    }

    @Override
    public void applyUICallback(Runnable callback) {
        VaadinSession oldSession = VaadinSession.getCurrent();
        SecurityContext oldContext = AppContext.getSecurityContext();
        try {
            VaadinSession.setCurrent(vaadinSession);
            AppContext.setSecurityContext(securityContext);

            vaadinSession.getUIs().stream()
                    .filter(UI::isConnectorEnabled)
                    .forEach(ui-> {
                        if (ui.isAttached()) {
                            callback.run();
                        } else {
                            ui.addAttachListener(new ClientConnector.AttachListener() {
                                @Override
                                public void attach(ClientConnector.AttachEvent event) {
                                    ui.removeAttachListener(this);
                                    callback.run();
                                }
                            });
                        }
                    });

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            VaadinSession.setCurrent(oldSession);
            AppContext.setSecurityContext(oldContext);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebSearchSession that = (WebSearchSession) o;

        return securityContext.getSessionId() != null ?
                securityContext.getSessionId().equals(that.securityContext.getSessionId()) :
                that.securityContext.getSessionId() == null;
    }

    @Override
    public int hashCode() {
        return vaadinSession != null ? securityContext.getSessionId().hashCode() : 0;
    }
}
