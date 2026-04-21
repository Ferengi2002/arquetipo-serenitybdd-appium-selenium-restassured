package poc.stepdefinitios;

import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import tasks.ai.SmartValidate;
import tasks.sourcedemo.*;
import tasks.web.NavigateTo;
import userinterfaces.saucedemo.InterfacesUI;

import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class saurcedemoStepDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    // ==========================================
    // ESCENARIO 1: @demoqa
    // "Acceso exitoso y seleccion dinamica de productos"
    // ==========================================

    // Con {actor} en la anotacion, Cucumber captura la palabra del feature en esa posicion
    // (en este caso 'usuario') y llama a OnStage.theActorCalled("usuario") automaticamente.
    // El .feature NO necesita cambiar — 'el usuario navega...' funciona tal cual.
    @Dado("que el {actor} navega hacia la pagina e inicia sesion con credenciales validas")
    public void navegaEIniciaSesion(Actor actor) {
        actor.whoCan(net.serenitybdd.screenplay.rest.abilities.CallAnApi.at(""));
        actor.wasAbleTo(
                NavigateTo.demoQaPage(),
                AccederSistemaApi.logearseYSetearCookie()
        );
    }

    @Cuando("selecciona una cantidad aleatoria de productos para agregar al carrito")
    public void seleccionaProductosAleatorios() {
        theActorInTheSpotlight().attemptsTo(
                AgregarProductoAleatorio.alCarrito()
        );
    }

    // MEJORA IA: En vez de hardcodear el XPath de "Remove", le pedimos a Gemini
    // que analice el DOM actual y encuentre cuantos botones de ese tipo existen.
    // Asi no importa si el texto exacto es "Remove", "Eliminar" o cambia en el futuro.
    @Entonces("el boton de cada producto seleccionado debe cambiar a {string}")
    public void validarBotonesCambiaronConIA(String descripcionBoton) {
        Actor actor = theActorInTheSpotlight();
        List<String> productosElegidos = actor.recall("productosElegidos");
        int cantidadEsperada = productosElegidos.size();

        actor.attemptsTo(
                SmartValidate.queExistan(cantidadEsperada,
                        "boton de tipo '" + descripcionBoton + "' que indica que un producto fue agregado al carrito")
        );
    }

    @Y("el contador del carrito debe actualizarse con la cantidad correcta")
    public void validarContadorCarrito() {
        Actor actor = theActorInTheSpotlight();
        List<String> productosElegidos = actor.recall("productosElegidos");
        int cantidadEsperada = productosElegidos.size();

        actor.attemptsTo(
                net.serenitybdd.screenplay.Task.where("{0} valida que el contador del carrito muestra " + cantidadEsperada,
                        a -> {
                            String textoContador = InterfacesUI.LBL_CONTADOR_CARRITO.resolveFor(a).getText();
                            int contadorActual = Integer.parseInt(textoContador.trim());
                            assertEquals(cantidadEsperada, contadorActual,
                                    "El contador del carrito muestra " + contadorActual + " pero se esperaban " + cantidadEsperada);
                        }
                )
        );
    }

    // ==========================================
    // ESCENARIO 2: @democomprarproducto
    // "Compra exitosa de productos seleccionados al azar"
    // ==========================================

    @Dado("que el {actor} ha accedido al sistema y tiene productos aleatorios en el carrito")
    public void usuarioConProductosEnElCarrito(Actor actor) {
        actor.whoCan(net.serenitybdd.screenplay.rest.abilities.CallAnApi.at(""));
        actor.wasAbleTo(
                NavigateTo.demoQaPage(),
                AccederSistemaApi.logearseYSetearCookie(),
                ValidarInterfaz.delInventario(),
                AgregarProductoAleatorio.alCarrito()
        );
    }

    @Cuando("completa el proceso de compra \\(checkout\\) con sus datos personales")
    public void completaElCheckout() {
        theActorInTheSpotlight().attemptsTo(
                CompletarCheckout.conDatosPersonales()
        );
    }

    @Y("verifica los detalles y el monto total en la pagina de resumen")
    public void verificaResumenDeCompra() {
        theActorInTheSpotlight().attemptsTo(
                VerificarResumenCompra.detalles()
        );
    }

    @Entonces("el sistema debe mostrar un mensaje de compra exitosa")
    public void validarMensajeDeCompraExitosa() {
        theActorInTheSpotlight().attemptsTo(
                FinalizarCompra.exitosamente()
        );
    }
}