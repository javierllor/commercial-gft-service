package com.gft.commercial;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class CommercialApiApplicationTest {

    @Test
    public void contextLoads() {
        assertDoesNotThrow(() -> {
            new CommercialApiApplication().configure(new SpringApplicationBuilder());
        });
    }

}
