package poc.stepdefinitios;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import tasks.web.Eligiendo;
import tasks.web.NavigateTo;

import static net.serenitybdd.screenplay.GivenWhenThen.when;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class BuyProductsStepDefinitions {

    @ParameterType(".*")
    public Actor actor(String actorName) {
        return OnStage.theActorCalled(actorName);
    }

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("que el {actor} accede al sitio Demoblaze")
    public void accedeAlSistema(Actor actor) {
        // actor.wasAbleTo(NavigateTo.demoBlazePage());
    }

    @Cuando("agrega 2 productos ramdom de cualquier categoria al carrito")
    public void agregarProductosAleatoriosAlCarrito() {
        when(theActorInTheSpotlight()).attemptsTo(
                Eligiendo.productosDeCualquierCategoria()
        );
    }

    /*
    @Y("visualiza el carrito de compras")
    public void visualizarCarritoDeCompras() {
        theActorInTheSpotlight().attemptsTo(
                VerCarrito.deCompras()
        );
    }

    @Y("completa el formulario de compra con los datos del JSON y finaliza la compro")
    public void completarFormularioDeCompra() throws IOException, ParseException {
        JSONObject js = DatosParametrizados.leerJson();
        theActorInTheSpotlight().attemptsTo(
                CompletarPedido.conDatosCliente((String) js.get("nombre"),(String)js.get("pais") , (String)js.get("ciudad"), (String) js.get("tarjeta"), (String) js.get("mes"), (String) js.get("anio"))
        );
    }


    @Entonces("debe ver un mensaje de confirmacion con el ID de la orden y el monto total")
    public void deberiaVerUnMensajeConfirmacionConIdDeOrdenYMontoTotal() throws IOException, ParseException {
        JSONObject js = DatosParametrizados.leerJson();
        theActorInTheSpotlight().attemptsTo(
                FinalizarCompra.ahora((String) js.get("nombre"))
        );
    }

     */
}
