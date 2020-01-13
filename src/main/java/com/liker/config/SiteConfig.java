package com.liker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SiteConfig {

    @Value("${host}")
    private String domainName;

    @Value("${user.agent}")
    private String userAgent;

    private String xfUser;

    private String xfSession;

    private String xfTfaTrust;

    private String swpToken;

    private int startPage;

    private int endPage;

    public String getDomainName() {
        return domainName;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getXfUser() {
        return xfUser;
    }

    public String getXfSession() {
        return xfSession;
    }

    public String getXfTfaTrust() {
        return xfTfaTrust;
    }

    public String getSwpToken() {
        return swpToken;
    }

    public void setXfUser(String xfUser) {
        this.xfUser = xfUser;
    }

    public void setXfSession(String xfSession) {
        this.xfSession = xfSession;
    }

    public void setXfTfaTrust(String xfTfaTrust) {
        this.xfTfaTrust = xfTfaTrust;
    }

    public void setSwpToken(String swpToken) {
        this.swpToken = swpToken;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}
