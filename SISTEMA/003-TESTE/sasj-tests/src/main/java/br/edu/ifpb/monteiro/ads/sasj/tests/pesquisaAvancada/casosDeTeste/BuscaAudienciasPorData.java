package br.edu.ifpb.monteiro.ads.sasj.tests.pesquisaAvancada.casosDeTeste;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

public class BuscaAudienciasPorData extends TestCase {

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
	public void testBuscarAudienciasPorData() throws Exception {
		driver.get(baseUrl + "/login");
		driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
		driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
		driver.findElement(By.id("campo-senha")).sendKeys("admin");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		driver.findElement(By.id("botao-novo-agendamento")).click();
		driver.findElement(By.id("campo-data")).sendKeys("16/09/2018");
		driver.findElement(By.id("campo-hora")).sendKeys("10:00");
		driver.findElement(By.id("campo-numero-processo")).sendKeys("222.111.3333-99999.000.44444");
		driver.findElement(By.id("campo-nome-parte")).sendKeys("Carlos e outros");
		driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
		driver.findElement(By.id("campo-oitivas")).clear();
		driver.findElement(By.id("campo-oitivas")).sendKeys("3");
		driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("Leil");
		driver.findElement(By.id("campo-duracao")).clear();
		driver.findElement(By.id("campo-duracao")).sendKeys("181");
		driver.findElement(By.id("botao-cadastrar-agendamento")).click();
		
        new WebDriverWait(driver, 2); //.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//simple-snack-bar")))

        driver.findElement(By.id("botao-novo-agendamento")).click();
		driver.findElement(By.id("campo-data")).sendKeys("28/11/2018");
		driver.findElement(By.id("campo-hora")).sendKeys("11:00");
		driver.findElement(By.id("campo-numero-processo")).sendKeys("111.333.2222-00000.999.6666");
		driver.findElement(By.id("campo-nome-parte")).sendKeys("João e outros");
		driver.findElement(By.id("campo-tipo-sessao")).sendKeys("Audiência");
		driver.findElement(By.id("campo-oitivas")).clear();
		driver.findElement(By.id("campo-oitivas")).sendKeys("5");
		driver.findElement(By.id("campo-tipo-audiencia")).sendKeys("Videoconferência");
		driver.findElement(By.id("botao-cadastrar-agendamento")).click();
		
		driver.findElement(By.id("botaoPesquisaAvancada")).click();
		driver.findElement(By.id("checkData")).click();
		driver.findElement(By.id("campoDataA")).sendKeys("15/08/2018 11:00");
		driver.findElement(By.id("campoDataB")).sendKeys("16/09/2018 10:01");
		driver.findElement(By.id("botaoPesquisar")).click();
		
		try {
            assertTrue(isElementPresent(By.id("222.111.3333-99999.000.44444")));
		}
		catch (Error e) {
			verificationErrors.append(e.toString());
		}
				
		driver.findElement(By.id("botaoExibirTudo")).click();
		
		driver.findElement(By.id("222.111.3333-99999.000.44444")).click();
		driver.findElement(By.id("botao-excluir-agendamento")).click();
		
		driver.findElement(By.id("111.333.2222-00000.999.6666")).click();
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

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
}
