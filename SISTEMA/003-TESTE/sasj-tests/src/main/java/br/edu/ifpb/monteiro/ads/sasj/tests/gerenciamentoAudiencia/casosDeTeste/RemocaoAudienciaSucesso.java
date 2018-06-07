package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

public class RemocaoAudienciaSucesso extends TestCase{
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
  public void testRemoverAudienciaComSucesso() throws Exception {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
        driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
        driver.findElement(By.id("campo-senha")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        driver.findElement(By.id("botao-novo-agendamento")).click();
        driver.findElement(By.id("campo-data")).sendKeys("13/06/2018");
        driver.findElement(By.id("campo-hora")).sendKeys("20:00");
        driver.findElement(By.id("campo-numero-processo")).sendKeys("101.010.1010-10101.0101.01010");
        driver.findElement(By.id("campo-nome-parte")).sendKeys("LEILA VÂNIA SOUSA PORTELA");
        driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
        driver.findElement(By.id("campo-oitivas")).clear();
        driver.findElement(By.id("campo-oitivas")).sendKeys("2");
        driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("Instru");
        driver.findElement(By.id("botao-cadastrar-agendamento")).click();
        
        new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//simple-snack-bar")));
        
        driver.findElement(By.id("101.010.1010-10101.0101.01010")).click();
        driver.findElement(By.id("botao-excluir-agendamento")).click();
        try {
            assertEquals("Audiência excluída com sucesso", driver.findElement(By.xpath("//simple-snack-bar")).getText());
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
