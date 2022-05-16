# Proyecto 2 | Estructura de Datos | Minimax

---
## Ricardo Emiliano Apodaca Cardiel <span style="font-family:monospace">422029455

---

Este proyecto fue hecho en Java y [Kotlin](https://kotlinlang.org/ "Kotlin" ) con
[Gradle](https://gradle.org/ "Gradle").
---

### Construcción y Ejecución
Se recomienda [instalar Gradle](https://gradle.org/install/ "Instalar Gradle") y construir el
proyecto con:

```bash
gradle build
```

Se genera un `.jar` en `build/libs`, para ejecutar este:

```bash
java -jar build/libs/ProyectoEDD_Minimax-1.0.jar
```

Tambien es posible construir el proyecto y ejecutarlo a la vez con: 

```bash
gradle run
```
Aunque no es lo recomendado es posible.

Si se desea el ejecutable se obtiene con:
```bash
gradle install
```
y se encontrará en `build/install/ProyectoEDD_Minimax/bin`, 
el ejecutable de Linux/MacOS es `ProyectoEDD_Minimax` y en Windows es
`ProyectoEDD_Minimax.bat` .

>Para la interfaz por línea de comandos de 
[Gradle](https://docs.gradle.org/current/userguide/command_line_interface.html "Lina de Comando gradle")

---

Otra manera sin instalar Gradle es utilizando [gradlew](gradlew) y [gradlew.bat](gradlew.bat)
ya incluidos en el proyecto, únicamente sustituya `gradle` por `./gradlew` o
`.\gradlew.bat` dependiendo el caso.


***
Si se desea la documentación de proyecto:
```bash
gradle dokkaHtml
```
Esta se obtendrá en `build/dokka/html/index.html`. 
***
>[github](https://github.com/SlemimanPzz/ProyectoEDD_Minimax) del Proyecto

>Hasta el momento el proyecto no esta terminado ni al parecer funciona el `.\gradlew.bat`
> ni en Windows.