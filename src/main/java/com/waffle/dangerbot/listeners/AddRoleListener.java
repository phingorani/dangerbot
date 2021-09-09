package com.waffle.dangerbot.listeners;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.ServerUpdater;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AddRoleListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessage().getContent().equalsIgnoreCase("!role new")) {
            List<Role> rolesList = event.getServer().get().getRoles();
            rolesList.stream().map(Role::getName);
            Optional<Role> role = rolesList.stream().filter(innerRole -> innerRole.getName().equalsIgnoreCase("New")).findFirst();
            ServerUpdater serverUpdater = new ServerUpdater(event.getServer().get());
            if (role.isPresent()) {
                System.out.println("Adding role new to "+event.getMessage().getUserAuthor().get().getName());
                serverUpdater.addRoleToUser(event.getMessage().getUserAuthor().get(),role.get());
                serverUpdater.update();
                event.getMessage().getUserAuthor().get().addRole(role.get(),null);
            }
        }
    }
}
