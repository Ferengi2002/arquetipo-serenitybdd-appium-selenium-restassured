package userinterfaces.saucedemo;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

import java.time.Duration;

public class InterfacesUI extends PageObject {

    // ==========================================
    // ELEMENTOS DE LOGIN E INVENTARIO (Los que ya tenías)
    // ==========================================
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

    // ==========================================
    // ELEMENTOS DEL FLUJO DE COMPRA (Nuevos)
    // ==========================================

    // --- Ícono del Carrito ---
    public static final Target BTN_CARRITO = Target
            .the("Icono del carrito de compras")
            .locatedBy("//a[@class='shopping_cart_link']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    // --- Página del Carrito ---
    public static final Target BTN_CHECKOUT = Target
            .the("Botón para ir al Checkout")
            .locatedBy("//button[@data-test='checkout']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target LBL_NOMBRES_PRODUCTOS = Target
            .the("Nombres de los productos en la lista")
            .locatedBy("//div[@data-test='inventory-item-name']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    // --- Página de Información del Cliente ---
    public static final Target TITULO_PAGINA = Target
            .the("Título de la página actual")
            .locatedBy("//span[@class='title']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target PUT_FIRST_NAME = Target
            .the("Input de Nombre")
            .locatedBy("//input[@data-test='firstName']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target PUT_LAST_NAME = Target
            .the("Input de Apellido")
            .locatedBy("//input[@data-test='lastName']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target PUT_POSTAL_CODE = Target
            .the("Input de Código Postal")
            .locatedBy("//input[@data-test='postalCode']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target BTN_CONTINUE = Target
            .the("Botón para continuar el checkout")
            .locatedBy("//input[@data-test='continue']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    // --- Página de Resumen (Overview) ---
    public static final Target LBL_PRECIOS_PRODUCTOS = Target
            .the("Precios individuales de los productos")
            .locatedBy("//div[@data-test='inventory-item-price']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target LBL_SUBTOTAL = Target
            .the("Etiqueta del Subtotal")
            .locatedBy("//div[@data-test='subtotal-label']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target LBL_TAX = Target
            .the("Etiqueta de Impuestos (Tax)")
            .locatedBy("//div[@data-test='tax-label']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target LBL_TOTAL = Target
            .the("Etiqueta del Monto Total")
            .locatedBy("//div[@class='summary_total_label']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target BTN_FINISH = Target
            .the("Botón para finalizar la compra")
            .locatedBy("//button[@class='btn btn_action btn_medium cart_button']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    // --- Página de Éxito ---
    public static final Target LBL_MENSAJE_EXITO = Target
            .the("Mensaje de agradecimiento por la compra")
            .locatedBy("//h2[@data-test='complete-header']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));

    public static final Target BTN_BACK_HOME = Target
            .the("Botón para regresar al inicio")
            .locatedBy("//button[@id='back-to-products']")
            .waitingForNoMoreThan(Duration.ofSeconds(10));
}