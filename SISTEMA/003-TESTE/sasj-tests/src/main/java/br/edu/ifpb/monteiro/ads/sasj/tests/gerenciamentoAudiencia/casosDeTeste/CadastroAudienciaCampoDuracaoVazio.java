package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class CadastroAudienciaCampoDuracaoVazio extends TestCase{
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
  public void testTentarCadastrarAudienciaComCampoTempoDuracaoVazio() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
        driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
        driver.findElement(By.id("campo-senha")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        driver.findElement(By.id("botao-novo-agendamento")).click();
        driver.findElement(By.id("campo-data")).sendKeys("09/03/2018");
        driver.findElement(By.id("campo-hora")).sendKeys("10:30");
        driver.findElement(By.id("campo-numero-processo")).sendKeys("777.777.7777-77777.7777.77777");
        driver.findElement(By.id("campo-nome-parte")).sendKeys("LEONARDO ARRUDA VENTURA E OUTRO");
        driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
        driver.findElement(By.id("campo-oitivas")).clear();
        driver.findElement(By.id("campo-oitivas")).sendKeys("5");
        driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("Penal");
        driver.findElement(By.id("campo-duracao")).clear();
        driver.findElement(By.id("campo-duracao")).sendKeys("");
        driver.findElement(By.id("campo-observacao")).sendKeys("Videoconferência com João Pessoa (3ª Vara)");
        driver.findElement(By.id("botao-cadastrar-agendamento")).click();
        try {
            assertEquals("Duração estimada é obrigatório(a)", driver.findElement(By.xpath("//simple-snack-bar")).getText());
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
