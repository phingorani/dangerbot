package com.waffle.dangerbot.service;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.pojos.UserBasePojo;
import com.waffle.dangerbot.repository.DiscordUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscordUserService {

    @Autowired
    private final DiscordUserRepository discordUserRepository;

    public DiscordUserService(DiscordUserRepository discordUserRepository) {
        this.discordUserRepository = discordUserRepository;
    }

    public void save(DiscordUser discordUser) {
        discordUserRepository.save(discordUser);
    }

    public DiscordUser findByDiscordId(long id) {
       return discordUserRepository.findByDiscordId(id);
    }

    public List<DiscordUser> findByDiscordIds(List<Long> userIdsMentioned) {
        return discordUserRepository.findAllById(userIdsMentioned);
    }

    public void saveUserList(List<UserBasePojo> userBasePojoList) {
        userBasePojoList.stream().forEach(userBasePojo -> {
            if (discordUserRepository.existsById(userBasePojo.user.id)) {
                DiscordUser discordUser = new DiscordUser();
                if(StringUtils.isEmpty(userBasePojo.nick)) {
                    discordUser.setDisplayName(userBasePojo.user.username);
                }
                else {
                    discordUser.setDisplayName(userBasePojo.nick);
                }
                discordUser.setDiscordId(userBasePojo.user.id);
                System.out.println(discordUser.getDiscordId() + " + " + discordUser.getDisplayName());
                discordUserRepository.save(discordUser);
            }
        });
    }
}
