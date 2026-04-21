# 🚀 Serenity BDD Framework: AI-Powered Hybrid Automation
[![Serenity BDD](https://img.shields.io/badge/Serenity-BDD-brightgreen)](https://serenity-bdd.github.io/the-serenity-book/latest/index.html)
[![Java 17](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Appium](https://img.shields.io/badge/Appium-Mobile-blue)](http://appium.io/)
[![Gemini AI](https://img.shields.io/badge/AI-Gemini%202.5%20Flash-purple)](https://deepmind.google/technologies/gemini/)

Este es un ecosistema de automatización de nivel experto que integra **Web, Mobile (Android/iOS), API y una Capa de Inteligencia Artificial Autónoma**. Utiliza el patrón **Screenplay** para ofrecer pruebas altamente legibles, escalables y fáciles de mantener.

---

## 🌟 Características Principales

*   **🧠 AI SmartAgent (Self-Healing):** Integración directa con la API de Google Gemini (2.5 Flash). El framework es capaz de analizar el HTML en tiempo real para encontrar selectores (XPath) de forma autónoma si los elementos cambian o son dinámicos.
*   **🌐 Automatización Web:** Implementación robusta sobre SauceDemo con flujos de compra completos.
*   **📱 Soporte Multi-Plataforma:** Configuración lista para Android e iOS mediante Appium y Serenity environments.
*   **📡 API Testing:** Integración de RestAssured dentro del flujo Screenplay para validaciones de backend y pre-requisitos (como login vía API para acelerar pruebas UI).
*   **📊 Reportes Premium:** Generación automática de reportes Serenity BDD con capturas de pantalla, logs detallados y métricas de ejecución.
*   **🛡️ Manejo Dinámico de Datos:** Generadores de datos aleatorios y persistencia de estado entre pasos mediante `actor.remember()`.

---

## 🛠️ Stack Tecnológico

| Tecnología | Descripción |
| :--- | :--- |
| **Java 17** | Lenguaje de programación base. |
| **Serenity BDD** | Framework de pruebas y reportes. |
| **Gradle** | Gestor de dependencias y construcción. |
| **JUnit 5** | Motor de ejecución de pruebas. |
| **Selenium 4** | Automatización de navegadores Web. |
| **Appium 8** | Automatización de dispositivos móviles. |
| **RestAssured** | Pruebas de servicios REST. |
| **Google Gemini API** | Inteligencia Artificial para navegación inteligente. |

---

## 🏗️ Arquitectura (Pattern Screenplay)

El proyecto sigue rigurosamente el patrón Screenplay:

*   **Actors:** La entidad que realiza las acciones (ej: `el usuario`).
*   **Abilities:** Lo que el actor puede hacer (ej: `BrowseTheWeb`, `CallAnApi`).
*   **Tasks:** Tareas de alto nivel (ej: `AccederSistema`, `AgregarProductoAleatorio`).
*   **Interactions:** Acciones directas con la interfaz (ej: `SmartClick`, `Open`).
*   **Questions:** Validaciones del estado del sistema (ej: `VerificarResumenCompra`).
*   **User Interfaces:** Definición de localizadores y mapeo de elementos.

---

## 🧠 El Motor de IA: SmartAgent

El corazón innovador de este proyecto reside en el paquete `tasks.ai`. Se han implementado interacciones inteligentes que actúan como un **QA Engineer Virtual**:

1.  **SmartClick:** En lugar de fallar si un ID cambia, el agente extrae los elementos visibles del DOM, se los envía a **Gemini 2.5 Flash** junto con el objetivo (ej: *"clic en el botón de finalizar"*), y la IA devuelve el XPath óptimo en tiempo real.
2.  **SmartValidate:** Permite validar la existencia de elementos basados en descripciones semánticas en lugar de selectores rígidos.
3.  **Fallback Automático:** Si la API de IA no está disponible o la key falla, el sistema cuenta con un motor de respaldo basado en heurística y patrones comunes para evitar que la prueba se detenga.

---

## 🚀 Configuración y Ejecución

### 1. Pre-requisitos
*   Java JDK 17 o superior.
*   Gradle instalado (o usar el `gradlew` incluido).
*   Configurar variables de entorno (Driver de Edge/Chrome).

### 2. Variables de Entorno (.env)
Crea un archivo `.env` en la raíz del proyecto con tu API Key de Google Gemini:
```env
GEMINI_API_KEY=tu_api_key_aqui
```

### 3. Ejecución Rápida (PowerShell)
Hemos creado un script de automatización que gestiona la carga de variables y la generación de evidencias:
```powershell
./ejecutar_pruebas.ps1
```

### 4. Comandos Gradle
Para ejecutar manualmente apuntando a diferentes entornos:
```bash
# Ejecución Web en Edge
./gradlew clean test "-Denvironment=edge"

# Ejecución Mobile Android
./gradlew clean test "-Denvironment=android"

# Ejecución Mobile iOS
./gradlew clean test "-Denvironment=ios"
```

---

## 📂 Estructura del Proyecto

```text
src
 ├── main/java
 │    ├── actions/         # Acciones personalizadas
 │    ├── tasks/           # Tareas de negocio (AI, SauceDemo, Web)
 │    ├── userinterfaces/  # Mapeo de elementos (Page Objects dinámicos)
 │    └── utils/           # Clases de apoyo (Generadores, Manejo de IA)
 └── test
      ├── java/poc         # Runners y Step Definitions
      └── resources
           ├── features/   # Escenarios Gherkin (.feature)
           └── serenity.conf # Configuración centralizada
```

---

## 📈 Reportes de Ejecución
Tras cada ejecución, Serenity genera un reporte interactivo en:
`target/site/serenity/index.html`

El script `ejecutar_pruebas.ps1` también realiza un respaldo automático de estos reportes en la carpeta `/Evidencias/Ejecucion_TIMESTAMP/`.

---
