#language: es
@all
Característica: Compra de productos en Demoblaze

  @demoqa
  Escenario: Acceso exitoso y selección dinámica de productos
    Dado que el usuario navega hacia saucedemo.com
    Cuando el usuario ingresa sus credenciales válidas para acceder al sistema
    Entonces validar que haya accedido correctamente al sistema y agregue productos al azar


  @democomprarproducto
  Escenario: Compra exitosa de productos seleccionados al azar
    Dado que el usuario ha accedido al sistema y seleccionado productos al azar
    Cuando completa el proceso de compra con sus datos personales
    Y verifica los detalles y el monto total en el resumen
    Entonces validar que la compra se haya realizado exitosamente