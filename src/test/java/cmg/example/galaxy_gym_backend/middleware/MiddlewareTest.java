package cmg.example.galaxy_gym_backend.middleware;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MiddlewareTest {

    @Test
    void testMiddlewareInstantiation() {
        Middleware middleware = new Middleware();
        assertNotNull(middleware);
    }
}
