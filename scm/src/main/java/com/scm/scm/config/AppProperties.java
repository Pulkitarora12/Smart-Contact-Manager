package com.scm.scm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${app.default-profile-pic}")
    private String defaultProfilePic;

    public String getDefaultProfilePic() {
        return this.defaultProfilePic;
    }
}
