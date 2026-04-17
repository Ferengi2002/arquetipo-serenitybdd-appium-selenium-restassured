package tasks.sourcedemo;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.ensure.Ensure;
import userinterfaces.saucedemo.InterfacesUI;

import java.util.List;

public class VerificarResumenCompra implements Task {

    public static VerificarResumenCompra detalles() {
        return Tasks.instrumented(VerificarResumenCompra.class);
    }

    @Override
    @Step("{0} verifica que los productos en el resumen sean los mismos que eligió al azar")
    public <T extends Actor> void performAs(T actor) {

        // 1. EL SECRETO: El actor saca de su memoria la lista de productos aleatorios
        List<String> productosQueElegi = actor.recall("productosElegidos");

        // 2. Validamos que la colección de elementos en pantalla contenga esos mismos nombres
        actor.attemptsTo(
                Ensure.that(InterfacesUI.TITULO_PAGINA).hasText("Checkout: Overview"),

                // Ensure.thatTheCollectionOf saca el texto de todos los LBL_NOMBRES_PRODUCTOS
                // y revisa que coincidan con nuestra lista de la memoria
                Ensure.that(InterfacesUI.LBL_NOMBRES_PRODUCTOS)
                        .textValues()
                        .containsElementsFrom(productosQueElegi),

                // Opcional: Validar que aparezca el total final y el botón
                Ensure.that(InterfacesUI.LBL_TOTAL).isDisplayed(),
                Ensure.that(InterfacesUI.BTN_FINISH).isDisplayed()
        );
    }
}