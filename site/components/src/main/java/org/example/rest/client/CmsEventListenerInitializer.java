package org.example.rest.client;

import org.onehippo.cms7.services.eventbus.HippoEventListenerRegistry;

public class CmsEventListenerInitializer {

    private CmsEventListener listener;

    public void init() {
        listener = new CmsEventListener();
        HippoEventListenerRegistry.get().register(listener);
    }

    public void destroy() {
        HippoEventListenerRegistry.get().unregister(listener);
    }

}
