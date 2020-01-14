package com.liker.service.impl;

import com.liker.config.SiteConfig;
import com.liker.service.CliService;
import com.liker.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CliServiceImpl implements CliService {

    private final LikeService likeService;
    private final SiteConfig siteConfig;

    @Autowired
    public CliServiceImpl(LikeService likeService, SiteConfig siteConfig) {
        this.likeService = likeService;
        this.siteConfig = siteConfig;
    }

    @Override
    public void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите значение xf_user: ");
        siteConfig.setXfUser(reader.readLine());
        System.out.print("Введите значение xf_session: ");
        siteConfig.setXfSession(reader.readLine());
        System.out.print("Введите значение xf_tfa_trust: ");
        siteConfig.setXfTfaTrust(reader.readLine());
        System.out.print("Введите значение swp_token: ");
        siteConfig.setSwpToken(reader.readLine());
        int startPage;
        int endPage;
        while (true) {
            System.out.print("Введите номер темы (от): ");
            startPage = Integer.parseInt(reader.readLine());
            System.out.print("Введите номер темы (до) : ");
            endPage = Integer.parseInt(reader.readLine());
            if (startPage > endPage) {
                System.out.println("Начало не может быть больше конца :C");
            } else {
                break;
            }

        }
        siteConfig.setStartPage(startPage);
        siteConfig.setEndPage(endPage);
        likeService.like();
        System.out.println("Закончил работу");
    }
}
