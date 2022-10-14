package com.online.shop.service;

import com.online.shop.domain.User;

public interface MailSenderService {
    void sendActivateCode(User user);
}
