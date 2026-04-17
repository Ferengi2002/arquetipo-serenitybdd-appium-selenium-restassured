package tasks.sourcedemo;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.JavaScriptClick; // <--- IMPORTANTE: Nuevo import para el JS Click
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import userinterfaces.saucedemo.InterfacesUI;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;

public class CompletarCheckout implements Task {

    public static CompletarCheckout conDatosPersonales() {
        return Tasks.instrumented(CompletarCheckout.class);
    }

    @Override
    @Step("{0} navega al carrito, valida la interfaz y completa sus datos de envío")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // 1. Ir al carrito usando JavaScript (Ignora la ventana de Google que está encima)
                JavaScriptClick.on(InterfacesUI.BTN_CARRITO), // <--- CAMBIO AQUÍ

                // 2. Esperar dinámicamente hasta que el título cambie a "Your Cart" (máximo 10 segundos)
                WaitUntil.the(InterfacesUI.TITULO_PAGINA, containsText("Your Cart")).forNoMoreThan(10).seconds(),

                // 3. Validaciones en la página del carrito
                Ensure.that(InterfacesUI.TITULO_PAGINA).hasText("Your Cart"),
                Ensure.that(InterfacesUI.BTN_CHECKOUT).isDisplayed(),

                // 4. Clic para ir al formulario (Aquí un clic normal suele bastar porque el pop-up ya no estorba abajo)
                Click.on(InterfacesUI.BTN_CHECKOUT),

                // 5. Esperar también aquí por si acaso la transición es rápida
                WaitUntil.the(InterfacesUI.TITULO_PAGINA, containsText("Checkout: Your Information")).forNoMoreThan(10).seconds(),

                // 6. Validaciones en la página de información
                Ensure.that(InterfacesUI.TITULO_PAGINA).hasText("Checkout: Your Information"),
                Ensure.that(InterfacesUI.PUT_FIRST_NAME).isDisplayed(),
                Ensure.that(InterfacesUI.PUT_LAST_NAME).isDisplayed(),
                Ensure.that(InterfacesUI.PUT_POSTAL_CODE).isDisplayed(),
                Ensure.that(InterfacesUI.BTN_CONTINUE).isDisplayed(),

                // 7. Llenar el formulario
                Enter.theValue("Carlos").into(InterfacesUI.PUT_FIRST_NAME),
                Enter.theValue("Automatizador").into(InterfacesUI.PUT_LAST_NAME),
                Enter.theValue("170101").into(InterfacesUI.PUT_POSTAL_CODE),

                // 8. Clic en continuar hacia el resumen
                Click.on(InterfacesUI.BTN_CONTINUE)
        );
    }
}