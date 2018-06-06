package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class CadastroAudienciaSucesso extends TestCase{
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
  public void testCadastrarAudiNciaComSucesso() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
        driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
        driver.findElement(By.id("campo-senha")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//a/span")).click();
        driver.findElement(By.id("campo-data")).sendKeys("06/07/2018");
        driver.findElement(By.id("campo-hora")).sendKeys("10:00");
        driver.findElement(By.id("campo-numero-processo")).sendKeys("111.111.1111-11111.1111.11111");
        driver.findElement(By.id("campo-nome-parte")).sendKeys("Danilo e outros");
        driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
        driver.findElement(By.id("campo-oitivas")).clear();
        driver.findElement(By.id("campo-oitivas")).sendKeys("3");
        driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("Improbidade");
        driver.findElement(By.id("campo-duracao")).clear();
        driver.findElement(By.id("campo-duracao")).sendKeys("61");
        driver.findElement(By.id("campo-observacao")).sendKeys("Réu preso");
        driver.findElement(By.id("botao-cadastrar-agendamento")).click();
        try {
            assertEquals("Audiência cadastrada com sucesso", driver.findElement(By.xpath("//simple-snack-bar")).getText());
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
