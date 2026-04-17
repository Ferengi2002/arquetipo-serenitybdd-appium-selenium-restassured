package tasks.sourcedemo;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import userinterfaces.saucedemo.InterfacesUI;

public class FinalizarCompra implements Task {

    public static FinalizarCompra exitosamente() {
        return Tasks.instrumented(FinalizarCompra.class);
    }

    @Override
    @Step("{0} finaliza la orden y verifica el mensaje de éxito")
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                // 1. Clic en el botón Finish
                Click.on(InterfacesUI.BTN_FINISH),

                // 2. Validación de oro: Comprobar que salió el mensaje de éxito
                Ensure.that(InterfacesUI.LBL_MENSAJE_EXITO)
                        .hasText("Thank you for your order!"),

                // 3. (Opcional pero recomendado) Validar que aparezca el botón para volver
                Ensure.that(InterfacesUI.BTN_BACK_HOME).isDisplayed()
        );
    }
}