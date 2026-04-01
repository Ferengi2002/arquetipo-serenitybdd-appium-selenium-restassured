package tasks.sourcedemo;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import userinterfaces.saucedemo.InterfacesUI;
import utils.web.GeneradorAleatorio;

import java.util.List;

public class AgregarProductoAleatorio implements Task {

    public static AgregarProductoAleatorio alCarrito() {
        return Tasks.instrumented(AgregarProductoAleatorio.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Obtenemos los botones y el índice
        List<WebElementFacade> botonesAgregar = InterfacesUI.BTN_ADD_TO_CART.resolveAllFor(actor);
        int indiceAleatorio = GeneradorAleatorio.numeroEntre(0, botonesAgregar.size() - 1);

        // Creamos la mini-tarea para que aparezca en el reporte
        actor.attemptsTo(
                Task.where("{0} hace clic en el botón 'Add to cart' para agregar el producto",
                        a -> botonesAgregar.get(indiceAleatorio).click()
                )
        );
    }
}