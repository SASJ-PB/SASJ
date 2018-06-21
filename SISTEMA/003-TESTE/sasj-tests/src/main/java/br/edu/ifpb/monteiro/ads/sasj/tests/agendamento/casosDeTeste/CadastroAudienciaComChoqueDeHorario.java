package br.edu.ifpb.monteiro.ads.sasj.tests.agendamento.casosDeTeste;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

public class CadastroAudienciaComChoqueDeHorario extends TestCase{

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
	  public void testCadastrarConciliacaoComHorarioIgualAudienciaSucesso() throws Exception {
	        driver.get(baseUrl + "/login");
	        driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
	        driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
	        driver.findElement(By.id("campo-senha")).sendKeys("admin");
	        driver.findElement(By.xpath("//button[@type='submit']")).click();
	        
	        driver.findElement(By.id("botao-novo-agendamento")).click();
	        driver.findElement(By.id("campo-data")).sendKeys("20/10/2018");
	        driver.findElement(By.id("campo-hora")).sendKeys("10:00");
	        driver.findElement(By.id("campo-numero-processo")).sendKeys("876787686299");
	        driver.findElement(By.id("campo-nome-parte")).sendKeys("Carlos Maciel");
	        driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
	        driver.findElement(By.id("campo-oitivas")).clear();
	        driver.findElement(By.id("campo-oitivas")).sendKeys("2");
	        driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("Outros");
	        driver.findElement(By.id("campo-duracao")).clear();
	        driver.findElement(By.id("campo-duracao")).sendKeys("40");
	        driver.findElement(By.id("botao-cadastrar-agendamento")).click();
	        
	        new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//simple-snack-bar")));

	        driver.findElement(By.id("botao-novo-agendamento")).click();
	        driver.findElement(By.id("campo-data")).sendKeys("20/10/2018");
	        driver.findElement(By.id("campo-hora")).sendKeys("10:10");
	        driver.findElement(By.id("campo-numero-processo")).sendKeys("876754356299");
	        driver.findElement(By.id("campo-nome-parte")).sendKeys("Joel Santos");
	        driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
	        driver.findElement(By.id("campo-oitivas")).clear();
	        driver.findElement(By.id("campo-oitivas")).sendKeys("5");
	        driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("PJE");
	    	driver.findElement(By.id("botao-cadastrar-agendamento")).click();
	        
	        try {
	            assertEquals("A data e hora da audiência conflita com um agendamento já cadastrado", 
	            		driver.findElement(By.xpath("//simple-snack-bar")).getText());
	        } catch (Error e) {
	            verificationErrors.append(e.toString());
	        }
	        
	        driver.findElement(By.id("botao-cancelar-agendamento")).click();
	        
	        driver.findElement(By.id("876787686299")).click();
	        driver.findElement(By.id("botao-excluir-agendamento")).click();
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
