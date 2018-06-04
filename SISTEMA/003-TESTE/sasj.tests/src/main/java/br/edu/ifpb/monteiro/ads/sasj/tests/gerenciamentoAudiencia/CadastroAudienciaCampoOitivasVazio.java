package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CadastroAudienciaCampoOitivasVazio {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.gecko.driver", "./src/main/java/br/edu/ifpb/monteiro/ads/sasj/tests/libs/geckodriver");
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:4200/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTentarCadastrarAudienciaComCampoOitivasVazio() throws Exception {
    driver.get(baseUrl + "/login");
    driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
    driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
    driver.findElement(By.id("campo-senha")).sendKeys("admin");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//a/span")).click();
    driver.findElement(By.id("campo-data")).sendKeys("01/12/2017");
    driver.findElement(By.id("campo-hora")).sendKeys("09:30");
    driver.findElement(By.id("campo-numero-processo")).sendKeys("666.666.6666-66666.6666.66666");
    driver.findElement(By.id("campo-nome-parte")).sendKeys("Frederico");
    driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
    driver.findElement(By.id("campo-oitivas")).clear();
    driver.findElement(By.id("campo-oitivas")).sendKeys("");
    driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("Outros");
    driver.findElement(By.id("campo-observacao")).sendKeys("Baixado pelo TRF para novo interrogatório dos réus");
    driver.findElement(By.id("botao-cadastrar-agendamento")).click();
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

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
