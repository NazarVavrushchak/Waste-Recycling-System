package com.ecowaste.recycling;

import org.springframework.boot.SpringApplication;

public class TestWasteRecyclingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.from(WasteRecyclingSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
