package tasks.sourcedemo;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import userinterfaces.saucedemo.InterfacesUI;

import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CompletarCheckout implements Task {

    public static CompletarCheckout conDatosPersonales() {
        return Tasks.instrumented(CompletarCheckout.class);
    }

    @Override
    @Step("{0} navega al carrito, valida la interfaz y completa sus datos de envío")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // 1. Clic en el carrito (JS para evitar el pop-up de Google arriba)
                JavaScriptClick.on(InterfacesUI.BTN_CARRITO),

                // 2. Esperar y Checkout (Clic normal con Scroll es más seguro aquí)
                WaitUntil.the(InterfacesUI.BTN_CHECKOUT, isVisible()).forNoMoreThan(Duration.ofSeconds(10)),
                Scroll.to(InterfacesUI.BTN_CHECKOUT),
                Click.on(InterfacesUI.BTN_CHECKOUT),

                // 3. Esperar el formulario
                WaitUntil.the(InterfacesUI.PUT_FIRST_NAME, isVisible()).forNoMoreThan(Duration.ofSeconds(10)),

                // 4. Llenar datos
                Enter.theValue("Carlos").into(InterfacesUI.PUT_FIRST_NAME),
                Enter.theValue("Automatizador").into(InterfacesUI.PUT_LAST_NAME),
                Enter.theValue("170101").into(InterfacesUI.PUT_POSTAL_CODE),

                // 5. EL CAMBIO CLAVE: Scroll y Clic NORMAL en el botón de continuar
                // Usamos isEnabled para asegurar que el formulario ya validó los campos
                Scroll.to(InterfacesUI.BTN_CONTINUE),
                WaitUntil.the(InterfacesUI.BTN_CONTINUE, isEnabled()).forNoMoreThan(Duration.ofSeconds(5)),
                Click.on(InterfacesUI.BTN_CONTINUE)
        );
    }
}