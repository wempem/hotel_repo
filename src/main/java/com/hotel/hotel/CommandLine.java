package com.hotel.hotel;

import com.hotel.hotel.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLine implements CommandLineRunner {

    @Autowired
    MessageService messageService;

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        messageService.execute();
    }
}
