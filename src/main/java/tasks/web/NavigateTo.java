package tasks.web;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateTo {

    public static Performable demoQaPage() {
        return Task.where("{0} navega hacia la pagina principal de demoqa",
                Open.browserOn().the(GoHomePage.class));
    }

}
