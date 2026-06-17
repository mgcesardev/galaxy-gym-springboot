package cmg.example.galaxy_gym_backend;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class GalaxyGymBackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMain() {
		try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
			ConfigurableApplicationContext mockContext = Mockito.mock(ConfigurableApplicationContext.class);
			mockedSpringApplication.when(() -> SpringApplication.run(GalaxyGymBackendApplication.class, new String[]{}))
					.thenReturn(mockContext);

			GalaxyGymBackendApplication.main(new String[]{});

			mockedSpringApplication.verify(() -> SpringApplication.run(GalaxyGymBackendApplication.class, new String[]{}));
		}
	}
}
