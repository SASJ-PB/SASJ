package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste;

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

public class CadastroUsuarioMatriculaDuplicada extends TestCase {
	
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
	public void testCadastrarUsuarioComMatriculaDuplicada() throws Exception {
		driver.get(baseUrl + "/login");
		driver.findElement(By.id("campo-matricula")).sendKeys("mm-1234");
		driver.findElement(By.xpath("//mat-form-field[2]/div/div/div")).click();
		driver.findElement(By.id("campo-senha")).sendKeys("admin");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		driver.findElement(By.id("botaoMenu")).click();
		driver.findElement(By.id("botaoUsuarios")).click();
		driver.findElement(By.id("botao-novo-usuario")).click();

		driver.findElement(By.id("campoNome")).sendKeys("Lucas Meireles");
		driver.findElement(By.id("campoMatricula")).sendKeys("lm-9999");
		driver.findElement(By.id("campoCargo")).sendKeys("Téc.");
		driver.findElement(By.id("campoEmail")).sendKeys("lucas@mail");
		driver.findElement(By.id("campoTipoUsuario")).sendKeys("Padrão");

		driver.findElement(By.id("botaoCadastrarUsuario")).click();
		
		new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//simple-snack-bar")));

		driver.findElement(By.id("botaoUsuarios")).click();
		driver.findElement(By.id("botao-novo-usuario")).click();

		driver.findElement(By.id("campoNome")).sendKeys("Lucas Mendes");
		driver.findElement(By.id("campoMatricula")).sendKeys("lm-1010");
		driver.findElement(By.id("campoCargo")).sendKeys("Téc.");
		driver.findElement(By.id("campoEmail")).sendKeys("lucas@mail");
		driver.findElement(By.id("campoTipoUsuario")).sendKeys("Padrão");
		
		driver.findElement(By.id("botaoCadastrarUsuario")).click();
		
		try {
		    assertEquals("Este e-mail já está associado a uma conta", 
		    		driver.findElement(By.xpath("//simple-snack-bar")).getText());
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
