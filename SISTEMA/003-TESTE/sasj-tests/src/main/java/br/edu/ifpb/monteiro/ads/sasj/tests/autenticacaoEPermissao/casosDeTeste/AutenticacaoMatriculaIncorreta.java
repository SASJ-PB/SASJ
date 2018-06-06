package br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class AutenticacaoMatriculaIncorreta extends TestCase{
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  
	System.setProperty("webdriver.gecko.driver", 
	  "./src/main/java/br/edu/ifpb/monteiro/ads/sasj/tests/libs/geckodriver");
	  
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:4200/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTentarAutenticarComMatriculaIncorreta() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("campo-matricula")).sendKeys("mm-1233");
        driver.findElement(By.id("campo-senha")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try {
            assertEquals("Matrícula ou senha inválida !", driver.findElement(By.xpath("//simple-snack-bar")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
