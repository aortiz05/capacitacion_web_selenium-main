package com.bdd;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeSettingsTest {

    WebDriver driver;
    @Test //USO ANTIGUO - NO USAR
    public void testWithShadowOld() {
        getWebdriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Navega directamente a `chrome://settings`
            driver.get("chrome://settings");

            // Espera un momento si es necesario para que la página cargue
            Thread.sleep(1000);

            // Paso 1: Accede al primer ShadowRoot en `chrome://settings`
            WebElement shadowHost = driver.findElement(By.tagName("settings-ui"));
            WebElement shadowRoot1 = (WebElement) js.executeScript("return arguments[0].shadowRoot", shadowHost);

            // Paso 2: Accede al siguiente nivel de ShadowRoot donde está el campo de búsqueda
            WebElement root2 = shadowRoot1.findElement(By.cssSelector("cr-toolbar"));
            WebElement shadowRoot2 = (WebElement) js.executeScript("return arguments[0].shadowRoot", root2);

            // Paso 3: Accede al siguiente nivel de ShadowRoot para encontrar el campo de búsqueda
            WebElement root3 = shadowRoot2.findElement(By.cssSelector("cr-toolbar-search-field"));
            WebElement shadowRoot3 = (WebElement) js.executeScript("return arguments[0].shadowRoot", root3);

            // Localiza el campo de búsqueda dentro del último ShadowRoot
            WebElement search = shadowRoot3.findElement(By.cssSelector("input#searchInput"));

            // Escribe en el campo de búsqueda
            search.sendKeys("Ejemplo de búsqueda en configuración");

            // Verifica el texto ingresado en el campo de búsqueda
            System.out.println("Texto ingresado: " + search.getAttribute("value"));
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cierra el navegador
            driver.quit();
        }
    }

    @Test
    public  void testWithShadowCurrent() {
        // Configura el WebDriver (asegúrate de tener el chromedriver en tu sistema)
        getWebdriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Navega directamente a `chrome://settings`
            driver.get("chrome://settings");

            // Espera un momento para que la página cargue completamente
            Thread.sleep(1000);

            // Paso 1: Accede al primer ShadowRoot en `chrome://settings`
            WebElement root1 = driver.findElement(By.tagName("settings-ui"));
            Object shadowRoot1 = js.executeScript("return arguments[0].shadowRoot", root1);

            // Paso 2: Accede al siguiente nivel de ShadowRoot dentro de `settings-ui`
            Object root2 = js.executeScript("return arguments[0].querySelector('cr-toolbar').shadowRoot", shadowRoot1);

            // Paso 3: Accede al siguiente nivel de ShadowRoot dentro de `settings-main`
            Object root3 = js.executeScript("return arguments[0].querySelector('cr-toolbar-search-field').shadowRoot", root2);

            // Localiza el campo de búsqueda dentro del último ShadowRoot
            WebElement searchField = (WebElement) js.executeScript("return arguments[0].querySelector('input#searchInput')", root3);

            // Escribe en el campo de búsqueda
            searchField.sendKeys("Ejemplo de búsqueda en configuración");
            Thread.sleep(10000);
            // Verifica el texto ingresado en el campo de búsqueda
            System.out.println("Texto ingresado: " + searchField.getAttribute("value"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cierra el navegador
            driver.quit();
        }
    }

    public void getWebdriver(){
        // Configura el WebDriver (asegúrate de tener el chromedriver en tu sistema)
        String basePath = System.getProperty("user.dir");
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("webdriver.chrome.driver", basePath+"/driver/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", basePath+"/driver/chromedriver");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

}

