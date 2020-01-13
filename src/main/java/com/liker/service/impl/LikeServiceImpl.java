package com.liker.service.impl;

import com.liker.config.SiteConfig;
import com.liker.service.DriverService;
import com.liker.service.LikeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LikeServiceImpl implements LikeService {

    @Value("${expclan.like.url}")
    private String likeUrl;

    @Value("${xf.tfa.trust.expired.date}")
    private Integer tfaTrustCookieExpiredDate;

    @Value("${swp.token.expired.date}")
    private Integer swpTokenCookieExpiredDate;

    @Value("${stormwall.protection}")
    private String stormWallProtection;

    private final SiteConfig siteConfig;
    private final DriverService driverService;

    @Autowired
    public LikeServiceImpl(SiteConfig siteConfig, DriverService driverService) {
        this.siteConfig = siteConfig;
        this.driverService = driverService;
    }

    @Override
    public void like() {
        WebDriver driver = getWebDriverWithCookie();
        for (int i = siteConfig.getStartPage(); i <= siteConfig.getEndPage(); i++) {
            driver.get(likeUrl + i);
            String source = driver.getPageSource();
            if (!source.contains(stormWallProtection)) {
                if (isFoundThread(source)) {
                    System.out.println("Лайкаем " + likeUrl + i);
                    clickLike(driver);
                }
            } else {
                System.out.println("StormWall protection");
                System.exit(0);
            }
        }
    }

    private void clickLike(WebDriver driver) {
        try {
            while(true) {
                List<WebElement> elements = driver.findElements(By.xpath("//a[contains(@class, 'reaction--small')]"));
                elements.forEach(this::clickIsNotReaction);
                List<WebElement> nextPage = driver.findElements(By.cssSelector(".pageNav-jump--next"));
                if (nextPage.size() > 0) {
                    WebElement paginate = nextPage.get(0);
                    paginate.click();
                } else {
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    private void clickIsNotReaction(WebElement webElement) {
        if (!webElement.getAttribute("class").contains("has-reaction")) {
            webElement.click();
        }
    }

    private boolean isFoundThread(String pageSource) {
        Document doc = Jsoup.parse(pageSource);
        return doc.select(".message-inner").size() > 0;
    }

    private WebDriver getWebDriverWithCookie() {
        WebDriver webDriver = driverService.setHost(siteConfig.getDomainName())
                .setJavaScriptEnable(true)
                .setUserAgent(siteConfig.getUserAgent())
                .build();
        webDriver
                .manage()
                .window()
                .setSize(new Dimension(1400, 900));
        Set<Cookie> cookies = getCookies();

        cookies.forEach(it -> {
            try {
                webDriver.manage().addCookie(it);
            } catch (Exception ignored) {

            }
        });
        return webDriver;
    }

    private Set<Cookie> getCookies() {
        Set<Cookie> cookies = new HashSet<>();
        cookies.add(getTfaTrustCookie());
        cookies.add(getXfUserCookie());
        cookies.add(getXfSession());
        cookies.add(getSwpToken());
        return cookies;
    }

    private Cookie getTfaTrustCookie() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, tfaTrustCookieExpiredDate);
        return new Cookie.Builder("xf_tfa_trust", siteConfig.getXfTfaTrust())
                .path("/")
                .domain(siteConfig.getDomainName())
                .expiresOn(cal.getTime())
                .isHttpOnly(true)
                .isSecure(true)
                .build();
    }

    private Cookie getSwpToken() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, swpTokenCookieExpiredDate);
        return new Cookie.Builder("swp_token", siteConfig.getSwpToken())
                .path("/")
                .domain(siteConfig.getDomainName())
                .expiresOn(cal.getTime())
                .isHttpOnly(true)
                .isSecure(false)
                .build();
    }


    private Cookie getXfUserCookie() {
        return new Cookie.Builder("xf_user", siteConfig.getXfUser())
                .path("/")
                .domain(siteConfig.getDomainName())
                .expiresOn(new Date())
                .isHttpOnly(true)
                .isSecure(true)
                .build();
    }

    private Cookie getXfSession() {
        return new Cookie.Builder("xf_session", siteConfig.getXfSession())
                .path("/")
                .domain(siteConfig.getDomainName())
                .isHttpOnly(true)
                .isSecure(true)
                .build();
    }
}
