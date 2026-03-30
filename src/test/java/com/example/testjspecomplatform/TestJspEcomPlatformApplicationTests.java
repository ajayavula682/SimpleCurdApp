package com.example.testjspecomplatform;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestJspEcomPlatformApplicationTests {

    @Test
    void applicationClassShouldBeLoadable() {
        TestJspEcomPlatformApplication application = new TestJspEcomPlatformApplication();
        assertNotNull(application);
    }

}
