package com.waffle.dangerbot.pojos;

import java.time.LocalDateTime;
import java.util.List;

public class UserBasePojo {

    public List<Long> roles;

    public String nick;

    public String avatar;

    public LocalDateTime joinedAt;

    public UserPojo user;
}
