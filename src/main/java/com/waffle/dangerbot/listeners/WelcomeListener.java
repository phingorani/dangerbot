package com.waffle.dangerbot.listeners;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.service.DiscordUserService;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.ServerUpdater;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WelcomeListener implements ServerMemberJoinListener {

    @Autowired
    private DiscordUserService discordUserService;

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent event) {
        DiscordUser discordUserToSave = new DiscordUser(event.getUser().getName(), event.getUser().getId());
        discordUserService.save(discordUserToSave);
        List<Role> rolesList = event.getServer().getRoles();
        rolesList.stream().map(Role::getName);
        rolesList.forEach(role -> {System.out.println(role.getId()+ "  :  "+role.getName());});
        Optional<Role> role = rolesList.stream().filter(innerRole -> innerRole.getName().equalsIgnoreCase("New")).findFirst();
        ServerUpdater serverUpdater = new ServerUpdater(event.getServer());
        if (role.isPresent()) {
            serverUpdater.addRoleToUser(event.getUser(),role.get());
        }
    }
}
