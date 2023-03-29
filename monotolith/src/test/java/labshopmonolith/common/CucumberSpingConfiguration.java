package labshopmonolith.common;

import io.cucumber.spring.CucumberContextConfiguration;
import labshopmonolith.MonotolithApplication;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = { MonotolithApplication.class })
public class CucumberSpingConfiguration {}
