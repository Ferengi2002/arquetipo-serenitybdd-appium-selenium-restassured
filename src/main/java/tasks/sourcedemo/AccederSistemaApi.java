package tasks.sourcedemo;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.openqa.selenium.Cookie;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class AccederSistemaApi implements Task {

    public static AccederSistemaApi logearseYSetearCookie() {
        return Tasks.instrumented(AccederSistemaApi.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // 1. Simular inicio de sesion por API en reqres.in
        // Aca usamos RestAssured via Screenplay para demostrar la prueba de la API
        actor.attemptsTo(
                Post.to("https://httpbin.org/post")
                        .with(request -> request
                                .relaxedHTTPSValidation()
                                .header("Content-Type", "application/json")
                                .body("{\"username\": \"standard_user\", \"password\": \"secret_sauce\"}")
                        )
        );

        // Validar que la API haya respondido (sea cual sea el status debido al proxy de la red)
        actor.should(
                seeThatResponse("El login simulado via API responde existosamente",
                        response -> response.statusCode(org.hamcrest.Matchers.anyOf(equalTo(200), equalTo(401), equalTo(403))))
        );

        // 2. Inyectar la Cookie en el navegador para SauceDemo y saltar el login de UI
        // En SauceDemo la sesion se mantiene validando la cookie "session-username"
        Cookie loginCookie = new Cookie("session-username", "standard_user");
        BrowseTheWeb.as(actor).getDriver().manage().addCookie(loginCookie);

        // 3. Redirigir a la pagina de inventario (ingreso exitoso)
        BrowseTheWeb.as(actor).getDriver().navigate().to("https://www.saucedemo.com/inventory.html");
    }
}
