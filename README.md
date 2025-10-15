# Sistema de Venta de Boletos

Este es un pequeño sistema de escritorio hecho en **Java Swing** para simular la venta de boletos de autobús. Permite a un usuario comprar boletos para diferentes rutas y muestra estadísticas en tiempo real.

## ¿Qué hace el sistema?

* **Venta de Boletos:** El usuario puede ingresar su nombre, cédula y la cantidad de boletos que desea comprar para una de las rutas disponibles.
* **Cálculo Automático:** El precio se muestra al seleccionar la ruta y el total se calcula al realizar la compra.
* **Validaciones Clave:** El sistema no te deja avanzar si:
    * Dejas campos vacíos.
    * La cédula no tiene 10 dígitos.
    * Intentas comprar más de 5 boletos por persona o por compra.
    * No hay suficientes boletos disponibles para la ruta que elegiste.
* **Estadísticas en Vivo:** Hay una pestaña de "Estadísticas" que se actualiza automáticamente con cada compra, mostrando:
    * Boletos vendidos por ruta.
    * Boletos restantes por ruta.
    * Dinero recaudado por ruta y el total general.
* **Historial de Compras:** Se muestra un registro de todas las transacciones realizadas.

***

## ¿Cómo está construido?

El proyecto se divide en tres clases principales que trabajan juntas:

1.  **`Ventana.java`**
    * Es la cara del proyecto. Contiene todos los botones, campos de texto y etiquetas que ve el usuario.
    * Se encarga de capturar los datos del comprador y enviárselos al `GestorBoletos` para que los procese.
    * También recibe la información del gestor para actualizar las estadísticas y el historial.

2.  **`GestorBoletos.java`**
    * Aquí está toda la lógica del negocio. No tiene elementos visuales, solo se encarga de los cálculos y las reglas.
    * Usa una **`Stack<Boleto>` (Pila)** para guardar el historial de compras. Esto es útil porque la última compra realizada es la primera en salir, como una pila de platos.
    * Utiliza **`HashMap`s (Mapas)** para llevar la cuenta de los boletos vendidos y disponibles por ruta. Esto es súper eficiente para consultar estadísticas rápidamente sin tener que recorrer toda la pila de compras.
    * Contiene todas las **validaciones** para asegurarse de que cada compra sea válida.

3.  **`Boleto.java`**
    * Es una clase simple que actúa como una plantilla para cada venta.
    * Cada vez que se realiza una compra, se crea un nuevo objeto `Boleto` con toda la información: cédula, nombre, ruta, cantidad y el total pagado.

***

A continuación se presentan las pantallas de ejecución del programa:

***


<img width="1048" height="642" alt="image" src="https://github.com/user-attachments/assets/6c71f91b-a67f-4801-a03f-2e19574693eb" />


<img width="1043" height="631" alt="image" src="https://github.com/user-attachments/assets/4dc41f23-0053-4d5a-b217-943b16b92ab1" />


<img width="1042" height="629" alt="image" src="https://github.com/user-attachments/assets/4745cf1f-fd1d-40e9-bfc2-79f76bdf6c43" />


<img width="1053" height="639" alt="image" src="https://github.com/user-attachments/assets/0f79fc94-241c-4344-b1a9-ca8b9af7bccd" />


<img width="1045" height="632" alt="image" src="https://github.com/user-attachments/assets/8a0a661a-3176-4882-9355-a62dded6dcd4" />

<img width="1041" height="631" alt="image" src="https://github.com/user-attachments/assets/3f2c3731-d28a-4774-9d7e-6bdaecd7253e" />


<img width="1042" height="631" alt="image" src="https://github.com/user-attachments/assets/8cdc7281-fa9f-4170-8e5e-e88cea8a4eb4" />


<img width="1046" height="632" alt="image" src="https://github.com/user-attachments/assets/98aa9ef9-61bd-41ba-9488-83e14db94077" />

***

Autores: Carlos Alberto Angulo Pizarro, Richard Mauricio Soria Asanza

