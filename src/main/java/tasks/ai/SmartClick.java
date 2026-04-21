package tasks.ai;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SmartClick implements Interaction {

    private final String objetivo;

    public SmartClick(String objetivo) {
        this.objetivo = objetivo;
    }

    public static SmartClick on(String objetivo) {
        return Tasks.instrumented(SmartClick.class, objetivo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        System.out.println("==================================================================");
        System.out.println("SmartAgent analizando la pantalla para encontrar: " + objetivo);

        // --- INTENTO CON IA ---
        String xpath = intentarConIA(driver);

        // --- FALLBACK: si la IA no responde, generamos un XPath basado en el texto del objetivo ---
        if (xpath == null || xpath.isEmpty()) {
            xpath = generarFallbackXPath();
            System.out.println("[FALLBACK] La IA no esta disponible. Usando XPath de respaldo: " + xpath);
        }

        System.out.println("[RESULTADO] Ejecutando clic en: " + xpath);
        System.out.println("==================================================================");

        actor.attemptsTo(
                Click.on(By.xpath(xpath))
        );
    }

    private String intentarConIA(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String extractScript = "return Array.from(document.querySelectorAll(" +
                    "'button, a, input[type=\"submit\"], input[type=\"button\"], div.cart_button, div.btn_action'))" +
                    ".map(e => e.outerHTML).join('\\n');";
            String htmlElements = (String) js.executeScript(extractScript);

            if (htmlElements == null || htmlElements.isEmpty()) return null;
            if (htmlElements.length() > 6000) htmlElements = htmlElements.substring(0, 6000);

            return SmartAgentAPI.getXPathFromAI(objetivo, htmlElements);
        } catch (Exception e) {
            System.out.println("[SmartClick] Error al consultar la IA: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fallback: intenta construir un XPath a partir de palabras clave del objetivo.
     * Detecta patrones comunes como "Finish", "Continue", "Submit", etc.
     */
    private String generarFallbackXPath() {
        String objetivoLower = objetivo.toLowerCase();

        // Detectar palabras clave conocidas en el objetivo
        if (objetivoLower.contains("finish") || objetivoLower.contains("finalizar")) {
            return "//button[@data-test='finish']";
        }
        if (objetivoLower.contains("continue") || objetivoLower.contains("continuar")) {
            return "//input[@data-test='continue']";
        }
        if (objetivoLower.contains("checkout")) {
            return "//button[@data-test='checkout']";
        }
        if (objetivoLower.contains("cart") || objetivoLower.contains("carrito")) {
            return "//a[@class='shopping_cart_link']";
        }
        if (objetivoLower.contains("login") || objetivoLower.contains("acceder")) {
            return "//input[@id='login-button']";
        }

        // Fallback ultimo recurso: busca un boton cuyo texto contenga la ultima palabra del objetivo
        String[] palabras = objetivo.split(" ");
        String ultimaPalabra = palabras[palabras.length - 1].replace(",", "").replace(".", "");
        return "//button[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '"
                + ultimaPalabra.toUpperCase() + "')]";
    }
}
