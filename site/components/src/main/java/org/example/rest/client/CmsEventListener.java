package org.example.rest.client;

import org.onehippo.cms7.event.HippoEvent;
import org.onehippo.cms7.services.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmsEventListener {

    private static final Logger log = LoggerFactory.getLogger(CmsEventListener.class);

    @Subscribe
    public void handleEvent(HippoEvent event) {
        if(event.getValues().containsKey("documentPath")
                && event.getValues().get("documentPath").toString().startsWith("/content/")){
            event.getValues().forEach((key, value) -> log.info(String.format("%s:%s",key,value)));
        }
    }
}
