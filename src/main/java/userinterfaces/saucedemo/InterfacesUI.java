package userinterfaces.saucedemo;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InterfacesUI extends PageObject {

    //public static final Target CATEGORIAS = Target.the("categorías disponibles").locatedBy("//div[@class='list-group']/a[@class='list-group-item' and @id='itemc']");
    public static final Target BTN_LOGIN = Target
            .the("Btn de Login")
            .locatedBy("//input[@id='login-button']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target PUT_USER = Target
            .the("Input de Usuario")
            .locatedBy("//input[@id='user-name']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target PUT_PASSWORD = Target
            .the("Input de Contraseña")
            .locatedBy("//input[@id='password']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target APP_LOGO = Target
            .the("Logo de la app")
            .locatedBy("//div[@class='app_logo']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target BTN_MENU = Target
            .the("Botón del Mega Menu")
            .locatedBy("//button[@id='react-burger-menu-btn']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target BTN_ADD_TO_CART = Target
            .the("Botones de agregar al carrito")
            .locatedBy("//button[text()='Add to cart']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

}


/*

Esta parte es importante para la parte de tomar toda una lista y hacerla randomica

    @FindBy(xpath = "//div[@class='list-group']/a[@class='list-group-item' and @id='itemc']")
    public List<WebElement> categoriasVisibles;

    public List<String> obtenerCategorias(){
        return categoriasVisibles.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
 */