import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class PruebasPhantomjsIT {
    private static WebDriver driver = null;

@Test
public void tituloIndexTest() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        assertEquals("Votacion mejor jugador liga ACB", driver.getTitle(),"El titulo no es correcto");
        driver.close();
        driver.quit();
    }

@Test
public void reiniciarVotosTest() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);

        driver.navigate().to("http://localhost:8080/Baloncesto/");
        driver.findElement(By.id("votosCero")).click();
        
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        driver.findElement(By.id("verVotos")).click();
        
        WebElement tablaVotos = driver.findElement(By.id("tablaVotos"));
        List<WebElement> filas = tablaVotos.findElements(By.tagName("tr"));
        for (int i = 1; i < filas.size(); i++) { 
            WebElement fila = filas.get(i);
            List<WebElement> columnas = fila.findElements(By.tagName("td"));
            String valorVotos = columnas.get(2).getText();
            assertEquals("0", valorVotos);
        }

        driver.close();
        driver.quit();
    }
}