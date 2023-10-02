package com.medical.springserver.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
	ExecutorService executor = Executors.newFixedThreadPool(5);

	@Autowired
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendCodigo")
    public void sendEmail(
        @RequestParam String to,
        @RequestParam String codigo
    ) {
        executor.execute(() -> {
            emailService.sendEmail(to, codigo);
        });
    }
}
