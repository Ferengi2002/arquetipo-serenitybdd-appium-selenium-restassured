package tasks.sourcedemo;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil; // <--- IMPORT
import userinterfaces.saucedemo.InterfacesUI;

import java.util.List;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText; // <--- IMPORT

public class VerificarResumenCompra implements Task {

    public static VerificarResumenCompra detalles() {
        return Tasks.instrumented(VerificarResumenCompra.class);
    }

    @Override
    @Step("{0} verifica que los productos en el resumen sean los mismos que eligió al azar")
    public <T extends Actor> void performAs(T actor) {

        List<String> productosQueElegi = actor.recall("productosElegidos");

        actor.attemptsTo(
                // EL TOQUE FINAL: Esperar a que el título cambie a Overview
                WaitUntil.the(InterfacesUI.TITULO_PAGINA, containsText("Checkout: Overview")).forNoMoreThan(10).seconds(),

                Ensure.that(InterfacesUI.TITULO_PAGINA).hasText("Checkout: Overview"),

                Ensure.that(InterfacesUI.LBL_NOMBRES_PRODUCTOS)
                        .textValues()
                        .containsElementsFrom(productosQueElegi),

                Ensure.that(InterfacesUI.LBL_TOTAL).isDisplayed(),
                Ensure.that(InterfacesUI.BTN_FINISH).isDisplayed()
        );
    }
}