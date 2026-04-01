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
        // 1. Capturamos TODOS los botones de "Add to cart" que hay en la pantalla
        List<WebElementFacade> botonesAgregar = InterfacesUI.BTN_ADD_TO_CART.resolveAllFor(actor);

        // 2. Usamos tu generador para sacar un número entre 0 y el total de botones menos 1
        int indiceAleatorio = GeneradorAleatorio.numeroEntre(0, botonesAgregar.size() - 1);

        // 3. Hacemos clic en el botón que salió sorteado
        botonesAgregar.get(indiceAleatorio).click();
    }
}