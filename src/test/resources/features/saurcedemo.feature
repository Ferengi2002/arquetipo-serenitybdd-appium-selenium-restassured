#language: es
@all
Característica: Compra de productos en SauceDemo

  @demoqa
  Escenario: Acceso exitoso y seleccion dinamica de productos
    Dado que el usuario navega hacia la pagina e inicia sesion con credenciales validas
    Cuando selecciona una cantidad aleatoria de productos para agregar al carrito
    Entonces el boton de cada producto seleccionado debe cambiar a "Remove"
    Y el contador del carrito debe actualizarse con la cantidad correcta

  @democomprarproducto
  Escenario: Compra exitosa de productos seleccionados al azar
    Dado que el usuario ha accedido al sistema y tiene productos aleatorios en el carrito
    Cuando completa el proceso de compra (checkout) con sus datos personales
    Y verifica los detalles y el monto total en la pagina de resumen
    Entonces el sistema debe mostrar un mensaje de compra exitosa