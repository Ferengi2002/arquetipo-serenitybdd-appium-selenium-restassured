package poc.stepdefinitios;

import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y; // <-- No olvides importar Y
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import tasks.sourcedemo.*;
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

    // ==========================================
    // STEP DEFINITIONS PARA @demoqa
    // ==========================================

    @Dado(value = "que el {actor} navega hacia saucedemo.com")
    public void accedeADemoqa1(Actor actor)
    {
        actor.wasAbleTo(NavigateTo.demoQaPage());
    }

    @Cuando("el usuario ingresa sus credenciales válidas para acceder al sistema")
    public void accederAlSistema() {
        theActorInTheSpotlight().attemptsTo(
                AccederSistema.logearse()
        );
    }

    @Entonces("validar que haya accedido correctamente al sistema y agregue productos al azar")
    public void validarAccesoYAgregarProductos() {
        Actor actor = theActorInTheSpotlight();

        // El código interno se queda EXACTAMENTE IGUAL, llamando a las tareas que ya hicimos:
        actor.attemptsTo(
                ValidarInterfaz.delInventario(),
                AgregarProductoAleatorio.alCarrito()
        );
    }

    // ==========================================
    // STEP DEFINITIONS PARA @democomprarproducto
    // ==========================================

    @Dado("que el {actor} ha accedido al sistema y seleccionado productos al azar")
    public void usuarioHaAccedidoYSeleccionadoProductos(Actor actor) {

        actor.wasAbleTo(
                NavigateTo.demoQaPage(),
                AccederSistema.logearse(),
                AgregarProductoAleatorio.alCarrito()
        );
    }

    @Cuando("completa el proceso de compra con sus datos personales")
    public void completaElProcesoDeCompra() {
        theActorInTheSpotlight().attemptsTo(
                CompletarCheckout.conDatosPersonales()
        );
    }

    @Y("verifica los detalles y el monto total en el resumen")
    public void verificaDetallesYMontoTotal() {
        theActorInTheSpotlight().attemptsTo(
                VerificarResumenCompra.detalles()
        );
    }

    @Entonces("validar que la compra se haya realizado exitosamente")
    public void validarCompraExitosa() {
        theActorInTheSpotlight().attemptsTo(
                FinalizarCompra.exitosamente()
        );
    }
}