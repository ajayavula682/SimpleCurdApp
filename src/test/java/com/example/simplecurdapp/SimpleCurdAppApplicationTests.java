package com.example.simplecurdapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SimpleCurdAppApplicationTests {

    @Test
    void applicationClassShouldBeLoadable() {
        SimpleCurdAppApplication application = new SimpleCurdAppApplication();
        assertNotNull(application);
    }

}
