package io.jenkins.plugins.xygeni.events;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.listeners.ItemListener;
import io.jenkins.plugins.xygeni.model.ItemEvent;
import io.jenkins.plugins.xygeni.services.XygeniApiClient;
import java.util.logging.Logger;

/**
 * Monitor item events.
 *
 * @author Victor de la Rosa
 */
@Extension
public class XygeniItemListener extends ItemListener {

    private static final Logger logger = Logger.getLogger(XygeniItemListener.class.getName());

    @Override
    public void onDeleted(Item item) {

        XygeniApiClient client = XygeniApiClient.getInstance();
        if (client == null) {
            logger.finest("[XygeniItemListener] Client null. Event Not Send.");
            return;
        }

        ItemEvent event = ItemEvent.from(item, ItemEvent.Action.DELETE);

        logger.finer("[XygeniItemListener] send event " + event);

        client.sendEvent(event);
    }

    @Override
    public void onCreated(Item item) {

        XygeniApiClient client = XygeniApiClient.getInstance();
        if (client == null) {
            logger.fine("[XygeniItemListener] Client null. Event Not Send.");
            return;
        }

        ItemEvent event = ItemEvent.from(item, ItemEvent.Action.CREATED);

        logger.finer("[XygeniItemListener] send event " + event);

        client.sendEvent(event);
    }

    @Override
    public void onRenamed(Item item, String oldName, String newName) {

        XygeniApiClient client = XygeniApiClient.getInstance();
        if (client == null) {
            logger.fine("[XygeniItemListener] Client null. Event Not Send.");
            return;
        }

        ItemEvent event = ItemEvent.from(item, ItemEvent.Action.RENAMED);

        logger.finer("[XygeniItemListener] send event " + event);

        client.sendEvent(event);
    }

    @Override
    public void onUpdated(Item item) {

        XygeniApiClient client = XygeniApiClient.getInstance();
        if (client == null) {
            logger.fine("[XygeniItemListener] Client null. Event Not Send.");
            return;
        }

        ItemEvent event = ItemEvent.from(item, ItemEvent.Action.UPDATED);

        logger.finer("[XygeniItemListener] send event " + event);

        client.sendEvent(event);
    }

    @Override
    public void onLocationChanged(Item item, String oldFullName, String newFullName) {

        XygeniApiClient client = XygeniApiClient.getInstance();
        if (client == null) {
            logger.fine("[XygeniItemListener] Client null. Event Not Send.");
            return;
        }

        ItemEvent event = ItemEvent.from(item, ItemEvent.Action.LOCATION_CHANGED);

        logger.finer("[XygeniItemListener] send event " + event);

        client.sendEvent(event);
    }
}