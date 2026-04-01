package tasks.sourcedemo;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import userinterfaces.saucedemo.InterfacesUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidarInterfaz implements Task {

    public static ValidarInterfaz delInventario() {
        return Tasks.instrumented(ValidarInterfaz.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                // 1. Mini-tarea para la URL
                Task.where("{0} valida que la URL actual sea la del inventario",
                        a -> {
                            String urlActual = ThucydidesWebDriverSupport.getDriver().getCurrentUrl();
                            assertEquals("https://www.saucedemo.com/inventory.html", urlActual);
                        }
                ),

                // 2. Mini-tarea para el Logo
                Task.where("{0} valida que el logo de la empresa esté visible",
                        a -> {
                            boolean logoVisible = InterfacesUI.APP_LOGO.resolveFor(a).isVisible();
                            assertTrue(logoVisible, "El logo de la app no está visible");
                        }
                ),

                // 3. Mini-tarea para el Menú
                Task.where("{0} valida que el menú hamburguesa esté visible",
                        a -> {
                            boolean menuVisible = InterfacesUI.BTN_MENU.resolveFor(a).isVisible();
                            assertTrue(menuVisible, "El megamenu no está visible");
                        }
                )
        );
    }
}