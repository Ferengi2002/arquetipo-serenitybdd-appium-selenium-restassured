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
import tasks.sourcedemo.ValidarInterfaz;
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
        theActorInTheSpotlight().attemptsTo(
                ValidarInterfaz.delInventario(),
                AgregarProductoAleatorio.alCarrito()
        );
    }


}
