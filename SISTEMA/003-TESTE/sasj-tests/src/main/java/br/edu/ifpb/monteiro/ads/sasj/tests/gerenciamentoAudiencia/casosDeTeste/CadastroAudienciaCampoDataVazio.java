package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class CadastroAudienciaCampoDataVazio extends TestCase{
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
  public void testTentarCadastrarAudienciaComCampoDataVazio() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
        driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
        driver.findElement(By.id("campo-senha")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//a/span")).click();
        driver.findElement(By.id("campo-data")).sendKeys("");
        driver.findElement(By.id("campo-hora")).sendKeys("10:00");
        driver.findElement(By.id("campo-numero-processo")).sendKeys("222.222.2222-22222.2222.22222");
        driver.findElement(By.id("campo-nome-parte")).sendKeys("Jonas da Silva");
        driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
        driver.findElement(By.id("campo-oitivas")).clear();
        driver.findElement(By.id("campo-oitivas")).sendKeys("11");
        driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("Custódia");
        driver.findElement(By.id("campo-observacao")).sendKeys("");
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
}
