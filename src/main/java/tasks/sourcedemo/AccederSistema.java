package tasks.sourcedemo;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Scroll;
import userinterfaces.saucedemo.InterfacesUI;
import userinterfaces.web.CategoriasUI;
import userinterfaces.web.HeaderUI;
import userinterfaces.web.ProductosUI;
import utils.web.ControlAlarmas;
import utils.web.GeneradorAleatorio;

import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class AccederSistema implements Task {

    public static AccederSistema logearse(){
        return Tasks.instrumented(AccederSistema.class);
    }

    @Override
    public <T extends Actor> void performAs(T Actor){
                Actor.attemptsTo(
                        Enter.theValue("standard_user").into(InterfacesUI.PUT_USER),
                        Enter.theValue("secret_sauce").into(InterfacesUI.PUT_PASSWORD),
                        Click.on(InterfacesUI.BTN_LOGIN)
                );
    }
}