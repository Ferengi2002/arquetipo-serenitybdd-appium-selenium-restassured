package tasks.ai;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmartValidate implements Interaction {

    private final String descripcionElemento;
    private final int cantidadEsperada;

    public SmartValidate(String descripcionElemento, int cantidadEsperada) {
        this.descripcionElemento = descripcionElemento;
        this.cantidadEsperada = cantidadEsperada;
    }

    /**
     * Le pide a Gemini que encuentre el XPath del elemento descrito
     * y valida que aparezca la cantidad esperada en pantalla.
     * Si la IA no esta disponible, activa un FALLBACK: busca botones
     * cuyo texto visible coincida con la primera palabra en comillas de la descripcion.
     */
    public static SmartValidate queExistan(int cantidad, String descripcionElemento) {
        return Tasks.instrumented(SmartValidate.class, descripcionElemento, cantidad);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        System.out.println("==================================================================");
        System.out.println("SmartValidate: buscando en pantalla -> " + descripcionElemento);
        System.out.println("Cantidad esperada: " + cantidadEsperada);

        // --- INTENTO CON IA ---
        String xpath = intentarConIA(driver);

        // --- FALLBACK: si la IA no respondio, extraemos el texto entre comillas ---
        if (xpath == null || xpath.isEmpty()) {
            xpath = generarFallbackXPath();
            System.out.println("[FALLBACK] La IA no esta disponible. Usando XPath de respaldo: " + xpath);
        }

        // Contamos los elementos encontrados con el XPath
        List<WebElement> elementosEncontrados = driver.findElements(By.xpath(xpath));
        int cantidadReal = elementosEncontrados.size();

        System.out.println("[RESULTADO] Elementos encontrados: " + cantidadReal + " | Esperados: " + cantidadEsperada);
        System.out.println("==================================================================");

        assertEquals(
                cantidadEsperada,
                cantidadReal,
                "SmartValidate FALLO: Se esperaban " + cantidadEsperada
                        + " elemento(s) de [" + descripcionElemento
                        + "], pero se encontraron " + cantidadReal
        );
    }

    private String intentarConIA(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String extractScript = "return Array.from(document.querySelectorAll('button, input[type=\"button\"], input[type=\"submit\"]'))"
                    + ".map(e => e.outerHTML).join('\\n');";
            String htmlElements = (String) js.executeScript(extractScript);

            if (htmlElements == null || htmlElements.isEmpty()) return null;
            if (htmlElements.length() > 6000) htmlElements = htmlElements.substring(0, 6000);

            return SmartAgentAPI.getXPathFromAI(descripcionElemento, htmlElements);
        } catch (Exception e) {
            System.out.println("[SmartValidate] Error al consultar la IA: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fallback: extrae el texto entre comillas simples de la descripcion.
     * Ej: "boton de tipo 'Remove' que indica..." -> //button[text()='Remove']
     * Si no hay comillas, busca cualquier boton que contenga la descripcion.
     */
    private String generarFallbackXPath() {
        int inicio = descripcionElemento.indexOf("'");
        int fin = descripcionElemento.lastIndexOf("'");

        if (inicio != -1 && fin != -1 && inicio != fin) {
            String textoBoton = descripcionElemento.substring(inicio + 1, fin);
            return "//button[text()='" + textoBoton + "']";
        }

        // Fallback generico: buscar cualquier boton en la pagina
        return "//button";
    }
}
