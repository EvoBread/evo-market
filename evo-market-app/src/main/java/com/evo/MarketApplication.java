package com.evo;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Configurable
public class MarketApplication {

    public static void main(String[] args){
        SpringApplication.run(MarketApplication.class);
    }

}
