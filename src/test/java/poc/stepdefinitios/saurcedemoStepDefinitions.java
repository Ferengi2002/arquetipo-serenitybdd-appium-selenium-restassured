package poc.stepdefinitios;

import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import tasks.sourcedemo.AccederSistema;
import tasks.sourcedemo.AgregarProductoAleatorio;
import tasks.web.NavigateTo;
import userinterfaces.saucedemo.InterfacesUI;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class saurcedemoStepDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("que el {actor} navega hacia saucedemo.com")
    public void accedeADemoqa1(Actor actor)
    {
        actor.wasAbleTo(NavigateTo.demoQaPage());
    }

    @Cuando("acceder al sistema")
    public void accederAlSistema() {
        theActorInTheSpotlight().attemptsTo(
                AccederSistema.logearse()
        );
    }

    @Entonces("validar que haya accedido correctamente al sistema")
    public void validarQueHayaAccedidoCorrectamenteAlSistema() {
        Actor actor = theActorInTheSpotlight();

        // 1. Validar la URL actual (usando el driver de Serenity)
        String urlActual = ThucydidesWebDriverSupport.getDriver().getCurrentUrl();
        assertEquals("https://www.saucedemo.com/inventory.html", urlActual);

        // 2. Validar el logo de la empresa usando la estructura que pediste
        // Usamos .isVisible() en lugar de .getText() porque un logo normalmente no tiene texto visible
        boolean logoVisible = InterfacesUI.APP_LOGO.resolveFor(actor).isVisible();
        assertTrue(logoVisible, "El logo de la app no está visible");

        // 3. Validar el megamenu usando la misma estructura
        boolean menuVisible = InterfacesUI.BTN_MENU.resolveFor(actor).isVisible();
        assertTrue(menuVisible, "El megamenu no está visible");

        // 4. Finalmente, llamamos a la tarea que creamos arriba para comprar 1 producto aleatorio
        actor.attemptsTo(
                AgregarProductoAleatorio.alCarrito()
        );
    }


}
