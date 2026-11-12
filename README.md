# Taller: Clean Architecture y Arquitectura Hexagonal
**Asignatura:** Ingeniería de Software 2 (ISW-2)  
**Estudiante:** Laura Sofía Ramírez Pulido

---

## 1. Parte 1: Investigación
*1.1 Cockburn (2005) parte de un problema que ve en las aplicaciones de su época. ¿Cuál es ese problema? 
¿Qué es un puerto y qué es un adaptador en su propuesta? ¿Por qué dice que hay una simetría entre el 
lado del usuario y el lado de la base de datos? 

Cockburn identifica que  " Uno de los mayores problemas de las aplicaciones de software a lo largo de los años ha sido la infiltración de la lógica empresarial en el código de la interfaz de usuario. " (Cockburn, A. (2005)), en otras palabras, en muchas aplicaciones la lógica del negocio se encuentra mezclada con el código de la interfaz  y con los componentes encargados de acceder a la base de datos. Esto genera problemas porque cuando se quiere cambiar la interfaz o la base de datos se pueden afectar las funcionalidades principales del sistema. Para evitar esta problemática o solucionarla Cockburn propone  que " La arquitectura hexagonal, o de puertos y adaptadores, resuelve estos problemas aprovechando la simetría de la situación: una aplicación interna se comunica a través de varios puertos con dispositivos externos. " (Cockburn, A. (2005)). 

Un puerto según Cockburn es " El término "puerto y adaptadores" hace referencia a la función de cada componente del diagrama. Un puerto identifica una comunicación específica. Normalmente, cada puerto requiere varios adaptadores para diversas tecnologías que se conectan a él " (Cockburn, A. (2005)) .Es decir, el puerto es una interfaz que define la manera en la que la aplicación se comunica con otros elementos externos y un adaptador es el componente que permite conectar el puerto con una tecnología especifica.  

Según Cockburn " Tanto los problemas del lado del usuario como los del servidor se deben al mismo error de diseño y programación: la confusión entre la lógica de negocio y la interacción con entidades externas. La asimetría que se debe aprovechar no reside entre la parte izquierda y la derecha de la aplicación, sino entre el interior y el exterior de la misma. La regla fundamental es que el código interno no debe filtrarse al exterior " (Cockburn, A. (2005)) ". Es decir, que hay una simetría entre el lado del usuario y el lado de la base de datos ya que estos dos son considerados elementos externos a la lógica inicial del sistema. A pesar de que son diferentes porque uno almacena y el otro ingresa información ninguno debe ni puede controlar las reglas del negocio. 

 
1.2. Martin (2012) dibuja Clean Architecture como círculos concéntricos. ¿Qué va en cada círculo? Martin enuncia una sola regla para todo el diagrama, la regla de dependencia (The Dependency Rule): ¿qué dice esa regla sobre la dirección en que puede apuntar el código, y qué prohíbe? 

Robert C. Martin afirma que  " Los círculos concéntricos representan diferentes áreas del software. En general, cuanto más se avanza en el círculo, mayor es el nivel del software. Los círculos exteriores representan mecanismos. Los círculos interiores representan políticas. " (Martin, R. C. (2012) ). Principalmente existen 4 círculos aunque pueden necesitarse más, estos son: 

Entidades: "Las entidades encapsulan las reglas de negocio de toda la empresa . Una entidad puede ser un objeto con métodos o un conjunto de estructuras de datos y funciones. Lo importante es que las entidades puedan ser utilizadas por diversas aplicaciones dentro de la empresa."(Martin, R. C. (2012) ).Esto quiere decir que las entidades contienen las reglas de negocio más importantes y generales el sistema. 

Casos de uso:"Estos casos de uso coordinan el flujo de datos hacia y desde las entidades, y dirigen a dichas entidades para que utilicen sus reglas de negocio corporativas con el fin de alcanzar los objetivos del caso de uso."(Martin, R. C. (2012) ). Esto quiere decir que los casos de uso contienen las acciones y procesos específicos que puede realizar la aplicación. 

Adaptadores de interfaz:"El software de esta capa consiste en un conjunto de adaptadores que convierten los datos del formato más conveniente para los casos de uso y las entidades, al formato más conveniente para alguna entidad externa, como la base de datos o la web."(Martin, R. C. (2012) ).Esto quiere decir que los adaptadores de interfaz convierten la información para que pueda comunicarse entre los casos de uso y los elementos externos. 

Marcos de referencia y factores determinantes: "Esta capa es donde se ubican todos los detalles. La web es un detalle. La base de datos es un detalle. Mantenemos estas cosas en el exterior, donde pueden causar poco daño."(Martin, R. C. (2012) ).Esto quiere decir que los marcos de referencia y factores determinantes corresponden a los elementos externos del sistema. 

" La regla de dependencia siempre se aplica. Las dependencias del código fuente siempre apuntan hacia adentro. A medida que te acercas, el nivel de abstracción aumenta. El círculo más externo representa detalles concretos de bajo nivel. A medida que te acercas, el software se vuelve más abstracto y encapsula políticas de nivel superior. El círculo más interno es el más general. " (Martin, R. C. (2012) ). Según lo anterior esta regla establece que las dependencias del código siempre deben dirigirse hacia el interior , es decir, las capas externas pueden depender de las internas pero las internas no pueden depender de las externas. 

 
1.3. Según la regla anterior, el caso de uso no puede depender de la base de datos. Pero el caso de uso necesita guardar datos. ¿Cómo resuelve Clean Architecture esa contradicción? Expliquen el mecanismo con un ejemplo (Martin lo trata en el mismo artículo, y también sirve el principio de inversión de dependencias que vieron en SOLID). 

" Independiente de la base de datos. Puedes reemplazar Oracle o SQL Server por Mongo, BigTable, CouchDB o cualquier otra opción. Tus reglas de negocio no están ligadas a la base de datos. " (Martin, R. C. (2012) ). Clean Architecture  soluciona esta situación mediante el principio de inversión de dependencias . En lugar de que el caso de uso dependa directamente de una base de datos , lo que se hace es que se crea una interfaz que define las operaciones necesarias para guardar y consultar información. 

" Por ejemplo, muchos marcos de bases de datos devuelven un formato de datos conveniente en respuesta a una consulta. Podríamos llamarlo Estructura de Filas. No queremos pasar esa estructura de filas internamente a través de un límite. Eso violaría la Regla de Dependencia, ya que obligaría a un círculo interno a conocer algo sobre un círculo externo. " (Martin, R. C. (2012) ). La idea principal de este ejemplo es que el caso de uso no debe recibir ni manejar directamente estructuras propias de la base de datos. 

 
1.4. Comparen la arquitectura hexagonal (Cockburn, 2005), la de cebolla (Palermo, 2008) y Clean Architecture (Martin, 2012). ¿Qué tienen en común las tres? ¿Qué cambia de una a otra: el vocabulario, el número de capas, ¿algo más? 

Las tres arquitecturas tienen como objetivo principal separar la lógica del negocio de los componentes externos de una aplicación, como las interfaces gráficas, las bases de datos y los frameworks. También buscan reducir el acoplamiento entre los componentes para facilitar el mantenimiento, las pruebas y los cambios tecnológicos. Sin embargo, cada una presenta una forma diferente de explicar y organizar esta separación: 

Arquitectura hexagonal (Cockburn, 2005): se basa en los puertos y adaptadores. Su objetivo es que la aplicación pueda comunicarse con diferentes elementos externos sin depender directamente de ellos. Se enfoca en la separación entre el interior de la aplicación y su exterior. 

Arquitectura de cebolla (Palermo, 2008): organiza el sistema en capas alrededor de un núcleo central llamado modelo de dominio. Su regla principal es que las dependencias siempre deben dirigirse hacia el centro. Las interfaces se encuentran en las capas internas y sus implementaciones en las capas externas. 

Clean Architecture (Martin, 2012): también utiliza una organización por capas y círculos concéntricos. Diferencia claramente las entidades, los casos de uso, los adaptadores de interfaz y los frameworks y controladores externos. Su regla principal es que las dependencias deben apuntar hacia el interior. 

En conclusión, las tres comparten la idea de proteger las reglas del negocio de los detalles tecnológicos. Lo que cambia principalmente es el vocabulario, la representación y la manera de organizar los componentes. No se trata simplemente de tener más o menos carpetas, sino de mantener correctamente la dirección de las dependencias y la separación de responsabilidades (Cockburn, 2005; Palermo, 2008; Martin, 2012). 

 
1.5. Su API es pequeña: unos pocos endpoints y una regla de validación. ¿Se justifica aplicarle Clean Architecture? Argumenten con al menos un costo (trade-off) y un beneficio concreto, y tomen una posición. No hay una única respuesta correcta: se califica el argumento. 

Considero que aplicar Clean Architecture a una API pequeña, como nuestra API de avistamientos, puede tener beneficios, aunque también representa algunos costos. Uno de los principales beneficios es que permite organizar mejor el código y separar las responsabilidades. Por ejemplo, podemos tener los controladores encargados de recibir las solicitudes, los casos de uso encargados de ejecutar las funcionalidades y los componentes de persistencia encargados de guardar la información. Esto facilita realizar pruebas y modificar una parte del sistema sin afectar necesariamente las demás. 

Otro beneficio es que la aplicación puede crecer en el futuro. Si se agregan nuevas funcionalidades, como consultar avistamientos por ubicación, actualizar registros o implementar nuevos métodos de almacenamiento, será más fácil mantener el código organizado. 

Sin embargo, también existen algunos costos. Al ser una API pequeña, implementar todas las capas puede aumentar la cantidad de clases, interfaces y archivos. Esto requiere más tiempo de desarrollo y puede hacer que algunas operaciones sencillas parezcan más complicadas de lo necesario. 

Mi posición es que sí vale la pena aplicar Clean Architecture en este taller, pero de una manera sencilla y sin crear capas innecesarias. Aunque nuestra API no tiene muchas funcionalidades, considero que es una buena oportunidad para poder aprender a separar las responsabilidades y evitar que toda la lógica quede concentrada en los controladores.  *.

---

## 2. Parte 2: Repositorio y Rama
* **Link del repositorio:** https://github.com/lsramirezp-tech/Avistamiento_software.git
* **Rama utilizada:** `main`

---

## 3. Arquitectura Elegida y Estructura
* **Arquitectura seleccionada:** Clean Architecture.
* **Estructura de carpetas final:**
```text
src/main/java/co/edu/unbosque/avistamientos/avistamientos_api/
├── domain/
│   ├── model/
│   │   └── Avistamiento.java
│   └── repository/
│       └── AvistamientoRepository.java
├── usecase/
│   └── ValidadorDeAvistamiento.java
└── infrastructure/
    ├── web/
    │   └── AvistamientoController.java
    └── persistence/
        └── (Implementaciones o adaptadores de base de datos)
