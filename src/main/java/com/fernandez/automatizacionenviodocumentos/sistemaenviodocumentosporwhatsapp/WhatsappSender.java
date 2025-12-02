/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fernandez.automatizacionenviodocumentos.sistemaenviodocumentosporwhatsapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.awt.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 *
 * @author andro
 */
public class WhatsappSender {
    
    private WebDriver driver;
    
    public WhatsappSender(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
    }
    
    public void sendMessage(String phoneNumber, String clientName, File pdfFile) throws Exception {

        String url = "https://web.whatsapp.com/send?phone=" + phoneNumber;
        driver.get(url);

        // Esperar que cargue el chat
        Thread.sleep(8000);

        // 1. Enviar mensaje de texto
        String mensaje = "Hola, buen día " + clientName + ", adjuntamos tu comprobante.";

        /*WebElement messageBox = driver.findElement(
        By.xpath("//div[@contenteditable='true' and contains(@class,'selectable-text')]"));*/
        
        WebElement messageBox = new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div[aria-placeholder='Escribe un mensaje']")
        ));

        messageBox.sendKeys(mensaje);
        messageBox.sendKeys(Keys.ENTER);

        Thread.sleep(2000);

        // 2. Click en el botón de adjuntar
        try{
            WebElement attachButton = driver.findElement(By.cssSelector("span[data-icon='clip']"));
           attachButton.click();
           System.out.println("Se procede a clickear en el icono de clip");
        }
        catch(Exception e){
            System.out.println("no funciono con span...intentando con button");
            try{
                WebElement attachButton = driver.findElement(By.cssSelector("button[aria-label=['Adjuntar']"));
                attachButton.click();
            }
            catch(Exception e1){
                System.out.println("no funciono con div");
            }
        }
       

        Thread.sleep(1000);

        // 3. Input oculto para seleccionar archivo
        
        //WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
        WebElement fileInput = driver.findElement(By.cssSelector("li[tabindex='0'][role='button']"));
        fileInput.sendKeys(pdfFile.getAbsolutePath());
        

        Thread.sleep(3000);

        // 4. Click en botón enviar del PDF
        try{
            WebElement sendButton = driver.findElement(By.cssSelector("span[data-icon='send']"));
            sendButton.click();
        }
        catch(Exception e){
            System.out.println("no funciono con span...intentando con div[Enviar]");
            try{
                WebElement sendButton = driver.findElement(By.cssSelector("div[aria-label='Enviar']"));
                sendButton.click();
                System.out.println("se logro clickear enviar!");
            }
            catch(Exception e1){
                System.out.println("no se logro clickear sobre enviar!");
            }
            
        }
        

        Thread.sleep(3000);
    }
    
    public void close() {
        driver.quit();
    }
}
