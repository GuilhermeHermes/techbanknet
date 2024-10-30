package com.techbank.cqrs_core.domain;

import com.techbank.cqrs_core.events.BaseEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    protected String id;
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public String getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommitedChanges() {
        return this.changes;
    }

    public void markChangeAsCommited() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, Boolean isNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
           logger.log(Level.WARNING, MessageFormat.format("The apply method was not found in the aggregate for {0}", event.getClass().getName()));
        } catch(Exception e){
            logger.log(Level.SEVERE , MessageFormat.format("An error occurred while applying the event {0} to the aggregate", event.getClass().getName()));
        } finally {
            if (isNewEvent) {
                this.changes.add(event);
            }
        }
    }


    public void raiseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvent(Iterable<BaseEvent> events) {
    events.forEach(event -> applyChange(event, false));
    }
}
