package tasks.sourcedemo;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import org.openqa.selenium.By;
import userinterfaces.saucedemo.InterfacesUI;
import utils.web.GeneradorAleatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgregarProductoAleatorio implements Task {

    public static AgregarProductoAleatorio alCarrito() {
        return Tasks.instrumented(AgregarProductoAleatorio.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        List<WebElementFacade> botonesAgregar = InterfacesUI.BTN_ADD_TO_CART.resolveAllFor(actor);
        int cantidadProductos = GeneradorAleatorio.numeroEntre(1, botonesAgregar.size());
        Collections.shuffle(botonesAgregar);

        // NUEVO: Creamos una lista vacía para ir anotando los nombres de los productos que elegimos
        List<String> nombresProductosElegidos = new ArrayList<>();

        actor.attemptsTo(
                Task.where("{0} decide agregar " + cantidadProductos + " producto(s) aleatorio(s) al carrito")
        );

        for (int i = 0; i < cantidadProductos; i++) {

            WebElementFacade botonElegido = botonesAgregar.get(i);

            // NUEVO: Buscamos el nombre del producto que le pertenece a este botón en específico.
            // Subimos al contenedor padre (inventory_item) y buscamos el texto del título.
            String nombreProducto = botonElegido.findElement(By.xpath("./ancestor::div[contains(@class, 'inventory_item')]//div[contains(@class, 'inventory_item_name')]")).getText();

            // NUEVO: Lo agregamos a nuestra libretita de apuntes
            nombresProductosElegidos.add(nombreProducto);

            actor.attemptsTo(
                    // NUEVO: Mejoramos el mensaje para que el reporte diga EXACTAMENTE qué producto agregó
                    Task.where("{0} hace clic en el botón 'Add to cart' del producto: " + nombreProducto,
                            a -> botonElegido.click()
                    )
            );
        }
        actor.remember("productosElegidos", nombresProductosElegidos);
    }
}