package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AtualizacaoConciliacaoSucesso {
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
  public void testAtualizarConciliacaoComSucesso() throws Exception {
    driver.get(baseUrl + "/login");
    driver.findElement(By.id("mat-input-0")).sendKeys("mm-1234");
    driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
    driver.findElement(By.id("mat-input-1")).sendKeys("admin");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//a/span")).click();
    driver.findElement(By.id("campo-data")).sendKeys("23/05/2017");
    driver.findElement(By.id("campo-hora")).sendKeys("14:00");
    driver.findElement(By.id("campo-numero-processo")).sendKeys("0500612-35.2017.4.05.8203T");
    driver.findElement(By.id("campo-nome-parte")).sendKeys("GREISYELE DOS SANTOS ARAUJO");
    driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Concilia");
    driver.findElement(By.id("campo-oitivas")).clear();
    driver.findElement(By.id("campo-oitivas")).sendKeys("2");
    driver.findElement(By.id("campo-nome-conciliador")).sendKeys("Ewerton");
    driver.findElement(By.id("botao-cadastrar-agendamento")).click();
    driver.findElement(By.cssSelector("#mat-tab-label-1-1 > div.mat-tab-label-content")).click();
    driver.findElement(By.xpath("//mat-tab-body[@id='mat-tab-content-1-1']/div/app-agendamento-tabela/div[2]/table/tbody/tr[4]/td[4]/button")).click();
    driver.findElement(By.id("campo-duracao")).clear();
    driver.findElement(By.id("campo-duracao")).sendKeys("15");
    driver.findElement(By.id("campo-nome-conciliador")).clear();
    driver.findElement(By.id("campo-nome-conciliador")).sendKeys("");
    driver.findElement(By.id("campo-nome-conciliador")).sendKeys("Ewerton Ribeiro");
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
