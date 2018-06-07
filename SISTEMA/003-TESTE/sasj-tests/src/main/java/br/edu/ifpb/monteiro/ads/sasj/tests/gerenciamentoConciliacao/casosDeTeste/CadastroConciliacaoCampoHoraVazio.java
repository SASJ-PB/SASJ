package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class CadastroConciliacaoCampoHoraVazio extends TestCase{
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
  public void testTentarCadastrarConciliacaoComCampoHoraVazio() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
        driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
        driver.findElement(By.id("campo-senha")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.id("botao-novo-agendamento")).click();
        driver.findElement(By.id("campo-data")).sendKeys("29/03/2017");
        driver.findElement(By.id("campo-hora")).sendKeys("");
        driver.findElement(By.id("campo-numero-processo")).sendKeys("0502223-57.2016.4.05.8203S");
        driver.findElement(By.id("campo-nome-parte")).sendKeys("MARIA JOSÉ NUNES DE LIMA FERREIRA");
        driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Concilia");
        driver.findElement(By.id("campo-oitivas")).clear();
        driver.findElement(By.id("campo-oitivas")).sendKeys("2");
        driver.findElement(By.id("campo-nome-conciliador")).sendKeys("Francimária");
        driver.findElement(By.id("botao-cadastrar-agendamento")).click();
        try {
            assertEquals("Você esqueceu de informar a hora", driver.findElement(By.xpath("//simple-snack-bar")).getText());
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
