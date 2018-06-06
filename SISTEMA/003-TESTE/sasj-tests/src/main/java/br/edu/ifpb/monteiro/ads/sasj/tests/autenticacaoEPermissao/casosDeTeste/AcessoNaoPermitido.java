package br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class AcessoNaoPermitido extends TestCase{
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
  public void testTentarAcessarPaginaSemPermissao() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("campo-matricula")).sendKeys("mm-123");
        driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
        driver.findElement(By.id("campo-senha")).sendKeys("padrao");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get(baseUrl + "/usuarios");
        try {
            assertEquals("Acesso negado!", driver.findElement(By.xpath("//app-nao-autorizado/div/h1")).getText());
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
