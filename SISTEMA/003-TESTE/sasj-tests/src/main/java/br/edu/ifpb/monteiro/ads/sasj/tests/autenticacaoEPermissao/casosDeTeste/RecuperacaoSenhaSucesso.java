package br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class RecuperacaoSenhaSucesso extends TestCase{
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
  public void testTentarRecuperarSenhaComEmailValido() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.xpath("//a/span")).click();
        driver.findElement(By.id("mat-input-2")).sendKeys("admin@admin.com");
        driver.findElement(By.id("botao-enviar-link")).click();
        for (int second = 0;; second++) {
        	if (second >= 60) fail("timeout");
        	try { if (isElementPresent(By.xpath("//simple-snack-bar"))) break; } catch (Exception e) {}
        	Thread.sleep(1000);
        }

        try {
            assertEquals("E-mail de redefinição de senha enviado", driver.findElement(By.xpath("//simple-snack-bar")).getText());
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
  
  private boolean isElementPresent(By by) {
    try {
	  driver.findElement(by);
	  return true;
	} catch (NoSuchElementException e) {
	  return false;
	}
  }

}
