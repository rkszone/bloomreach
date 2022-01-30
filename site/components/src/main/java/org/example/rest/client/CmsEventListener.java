package org.example.rest.client;

import org.onehippo.cms7.event.HippoEvent;
import org.onehippo.cms7.services.eventbus.Subscribe;

public class MyListener {

    @Subscribe
    public void handleEvent(HippoEvent event) {
        if(event.getValues().containsKey("documentPath")
                && event.getValues().get("documentPath").toString().startsWith("/content/")){
            event.getValues().forEach((key, value) -> System.out.println(key + ":" + value));
        }
    }
}
