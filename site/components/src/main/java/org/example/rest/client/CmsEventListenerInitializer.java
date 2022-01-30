package org.example.rest.client;

import org.onehippo.cms7.services.eventbus.HippoEventListenerRegistry;

public class List {

    private MyListener listener;

    public void init() {
        listener = new MyListener();
        HippoEventListenerRegistry.get().register(listener);
    }

    public void destroy() {
        HippoEventListenerRegistry.get().unregister(listener);
    }

}
