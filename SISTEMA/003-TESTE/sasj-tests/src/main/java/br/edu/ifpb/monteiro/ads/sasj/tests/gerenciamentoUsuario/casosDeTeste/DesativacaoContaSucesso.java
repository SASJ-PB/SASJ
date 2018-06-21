package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.TestCase;

public class DesativacaoContaSucesso extends TestCase {
	
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
	public void testDesativarContaComSucesso() throws Exception {
		driver.get(baseUrl + "/login");
		driver.findElement(By.id("campo-matricula")).sendKeys("MM-2000");
		driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
		driver.findElement(By.id("campo-senha")).sendKeys("admin2");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		driver.findElement(By.id("botaoUsuario")).click();
		driver.findElement(By.id("botaoPerfil")).click();
		driver.findElement(By.id("botaoDesativarConta")).click();
		driver.findElement(By.id("confirmarDesativacao")).click();
		
		try {
            assertFalse(isElementPresent(By.xpath("//input[id='campo-matricula']")));
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
