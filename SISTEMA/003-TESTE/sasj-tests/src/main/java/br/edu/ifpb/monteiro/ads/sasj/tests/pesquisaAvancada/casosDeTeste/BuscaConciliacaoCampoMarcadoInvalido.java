package br.edu.ifpb.monteiro.ads.sasj.tests.pesquisaAvancada.casosDeTeste;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class BuscaConciliacaoCampoMarcadoInvalido extends TestCase {

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
	public void testBuscarConciliacaoComCampoMarcadoInavalido() throws Exception {
		driver.get(baseUrl + "/login");
		driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
		driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
		driver.findElement(By.id("campo-senha")).sendKeys("admin");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		driver.findElement(By.cssSelector("#mat-tab-label-0-1 > div.mat-tab-label-content")).click();
		//								   #mat-tab-label-1-1 > div.mat-tab-label-content
		driver.findElement(By.id("botaoPesquisaAvancada")).click();
		driver.findElement(By.id("checkConciliador")).click();
		driver.findElement(By.id("botaoPesquisar")).click();

		try {
		    assertEquals("Preencha corretamente os campos escolhidos ou desmarque aqueles que deseja ignorar", driver.findElement(By.xpath("//simple-snack-bar")).getText());
		}
		catch (Error e) {
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
