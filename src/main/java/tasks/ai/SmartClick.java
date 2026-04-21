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

        // Extraemos todos los elementos clickeables (botones, enlaces y submits) del DOM
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String extractScript = "return Array.from(document.querySelectorAll('button, a, input[type=\"submit\"], input[type=\"button\"], div.cart_button, div.btn_action')).map(e => e.outerHTML).join('\\n');";
        String htmlElements = (String) js.executeScript(extractScript);

        // Limitamos el texto por si es muy largo para la API
        if (htmlElements.length() > 6000) {
            htmlElements = htmlElements.substring(0, 6000);
        }

        // Le preguntamos a la IA (Gemini) cuál es el XPath correcto
        String xpath = SmartAgentAPI.getXPathFromAI(objetivo, htmlElements);

        if (xpath != null && !xpath.isEmpty()) {
            System.out.println("Ejecutando clic dinámico en el XPath devuelto...");
            System.out.println("==================================================================");
            
            // Hacemos el clic inyectando el XPath que la IA encontró
            actor.attemptsTo(
                    Click.on(By.xpath(xpath))
            );
        } else {
            throw new RuntimeException("La IA no pudo determinar el XPath para el objetivo: " + objetivo);
        }
    }
}
