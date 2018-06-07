package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

public class CadastroConciliacaoCampoNumeroProcessoExistente extends TestCase {
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
  public void testCadastrarConciliacaoComNumeroDoProcessoExistente() throws Exception {
    driver.get(baseUrl + "/login");
    driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
    driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
    driver.findElement(By.id("campo-senha")).sendKeys("admin");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    
    driver.findElement(By.id("botao-novo-agendamento")).click();
    driver.findElement(By.id("campo-data")).sendKeys("17/04/2017");
    driver.findElement(By.id("campo-hora")).sendKeys("15:00");
    driver.findElement(By.id("campo-numero-processo")).sendKeys("0500449-55.2017.4.05.8203T");
    driver.findElement(By.id("campo-nome-parte")).sendKeys("GABRIELA DE OLIVEIRA BEZERRA");
    driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Concilia");
    driver.findElement(By.id("campo-oitivas")).clear();
    driver.findElement(By.id("campo-oitivas")).sendKeys("2");
    driver.findElement(By.id("campo-nome-conciliador")).sendKeys("Rayana");
    driver.findElement(By.id("botao-cadastrar-agendamento")).click();
    
    new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//simple-snack-bar")));
    
    driver.findElement(By.id("botao-novo-agendamento")).click();
    driver.findElement(By.id("campo-data")).sendKeys("18/04/2017");
    driver.findElement(By.id("campo-hora")).sendKeys("09:10");
    driver.findElement(By.id("campo-numero-processo")).sendKeys("0500449-55.2017.4.05.8203T");
    driver.findElement(By.id("campo-nome-parte")).sendKeys("GABRIELA");
    driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Concilia");
    driver.findElement(By.id("campo-oitivas")).clear();
    driver.findElement(By.id("campo-oitivas")).sendKeys("2");
    driver.findElement(By.id("campo-nome-conciliador")).sendKeys("Rayana");
    driver.findElement(By.id("botao-cadastrar-agendamento")).click();
    
    // new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//simple-snack-bar")));
    
    try {
        assertEquals("Conciliação cadastrada com sucesso", driver.findElement(By.xpath("//simple-snack-bar")).getText());

        driver.findElement(By.cssSelector("#mat-tab-label-2-1 > div.mat-tab-label-content")).click();
        
        driver.findElement(By.id("0500449-55.2017.4.05.8203T")).click();
        driver.findElement(By.id("botao-excluir-agendamento")).click();
        
        driver.findElement(By.id("0500449-55.2017.4.05.8203T")).click();
        driver.findElement(By.id("botao-excluir-agendamento")).click();
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
