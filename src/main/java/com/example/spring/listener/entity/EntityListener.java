package com.example.spring.listener.entity;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {
    @EventListener(condition = "#root.args[0].acceptType.name() == 'READ'")
    public void acceptEntity(EntityEvent event) {
        System.out.println("Entity: " + event);
    }
}
