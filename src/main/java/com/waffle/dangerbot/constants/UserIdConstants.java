package com.waffle.dangerbot.constants;

import java.util.List;

public class UserIdConstants {
    static Long DEATH_ROLL_CHANNEL = 857362959109586984L;
    static List<Long> ADMINS;


    public UserIdConstants() {
        ADMINS.add(148359142107512832L);
        ADMINS.add(229732076616810497L);
    }

    public static List<Long> getADMINS() {
        return ADMINS;
    }
}
