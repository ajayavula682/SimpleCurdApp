package com.example.testjspecomplatform;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KarateTests {

    @LocalServerPort
    private int port;

    @Karate.Test
    Karate testAll() {
        System.setProperty("karate.port", String.valueOf(port));
        return Karate.run().relativeTo(getClass());
    }
}
