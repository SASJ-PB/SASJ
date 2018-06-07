package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class CadastroAudienciaCampoTipoAudienciaVazio extends TestCase{
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	
	System.setProperty("webdriver.gecko.driver", 
	  "./src/main/java/br/edu/ifpb/monteiro/ads/sasj/tests/libs/geckodriver");
	
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:4200";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTentarCadastrarAudienciaComCampoTipoAudienciaVazio() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
        driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
        driver.findElement(By.id("campo-senha")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        driver.findElement(By.id("botao-novo-agendamento")).click();
        driver.findElement(By.id("campo-data")).sendKeys("06/09/2018");
        driver.findElement(By.id("campo-hora")).sendKeys("09:00");
        driver.findElement(By.id("campo-numero-processo")).sendKeys("555.555.5555-55555.5555.55555");
        driver.findElement(By.id("campo-nome-parte")).sendKeys("Roberta");
        driver.findElement(By.id("campo-tipo-sessao")).click();
        driver.findElement(By.cssSelector("#mat-option-1 > span.mat-option-text")).click();
        driver.findElement(By.cssSelector("div.mat-select-value")).click();
        driver.findElement(By.cssSelector("span.mat-option-text")).click();
        driver.findElement(By.id("campo-oitivas")).clear();
        driver.findElement(By.id("campo-oitivas")).sendKeys("5");
        driver.findElement(By.id("campo-duracao")).clear();
        driver.findElement(By.id("campo-duracao")).sendKeys("100");
        driver.findElement(By.id("botao-cadastrar-agendamento")).click();
        try {
            assertEquals("tipoAudiencia é obrigatório(a)", driver.findElement(By.xpath("//simple-snack-bar")).getText());
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
