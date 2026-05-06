package me.nightletter.swaggercustom.config;

import java.util.Set;

public interface SwaggerTag {
    String MAIN = "메인";
    String NOTIFICATION = "알림";

    static Set<String> getTags() {
        return Set.of(
                MAIN,
                NOTIFICATION
        );
    }
}
