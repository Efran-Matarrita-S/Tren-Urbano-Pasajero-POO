# 🚆 Tren Urbano de Pasajeros - Sistema de Tiquetes

Proyecto programado #1 del curso **Programación Orientada a Objetos**.

Este proyecto consiste en el desarrollo de una aplicación en **Java** para la gestión y venta de tiquetes de un tren urbano de pasajeros. La aplicación utiliza los principios de **Programación Orientada a Objetos (POO)**, manejo de archivos **XML** y ventanas gráficas con **Java Swing**.

---

## 📌 Objetivo del proyecto

Aplicar los conceptos de Programación Orientada a Objetos en Java para crear un sistema que permita administrar y vender tiquetes de tren, utilizando objetos, archivos XML y una interfaz gráfica.

---

## 🧩 Conceptos principales

El sistema trabaja con tres conceptos principales:

### 🎫 Tiquete

Representa una compra realizada por una persona.

**Contiene:**
- Identificación
- Nombre del comprador
- ID del precio asociado

---

### 🏷️ Tipo de Tiquete

Representa el tipo de tiquete disponible para la venta.

**Contiene:**
- Identificación
- Nombre del tipo
- Descripción
- Imagen

---

### 💰 Precio

Representa el precio vigente de un tipo de tiquete.

**Contiene:**
- Identificación
- Tipo de tiquete asociado
- Precio
- Fecha de vigencia

---

## 📁 Estructura del proyecto

```
Tren-Urbano-Pasajero-POO
│
├── Source Packages
│   ├── Conceptos
│   │   ├── Tiquete.java
│   │   ├── Tipo.java
│   │   └── Precio.java
│   │
│   ├── Util
│   │   └── XML_Admin.java
│   │
│   ├── Aplicacion
│   │   └── PruebaXML.java
│   │
│   └── Presentacion
│
├── Data
│   ├── tipos.xml
│   ├── precios.xml
│   └── tiquetes.xml
│
├── Imagenes
├── Diagramas
└── README.md
```

---

## 🗂️ Manejo de datos

El sistema utiliza archivos **XML** como mecanismo de carga y almacenamiento de datos.

**Ubicación:**
- `Data/tipos.xml`
- `Data/precios.xml`
- `Data/tiquetes.xml`

La clase encargada del manejo de XML es:

```
Util/XML_Admin.java
```

Se utiliza el analizador **DOM (Document Object Model)** para:

- Leer archivos XML
- Convertir datos a objetos Java
- Guardar objetos nuevamente en XML

---

## 🖥️ Funcionalidades del sistema

### 🛒 Kiosko

Permite al usuario comprar tiquetes.

**Funciones:**
- Mostrar tipos de tiquetes disponibles
- Mostrar imagen, descripción, precio y fecha
- Ingresar nombre del comprador
- Registrar compra
- Guardar el tiquete en XML

---

### 🛠️ Administrador

Permite gestionar toda la información del sistema.

**Funciones:**
- Consultar tiquetes vendidos
- Crear, modificar y eliminar tipos
- Crear, modificar y eliminar precios
- Visualizar datos cargados desde XML
- Guardar cambios en XML

---

## 🧪 Pruebas realizadas

Se implementó la clase:

```
Aplicacion/PruebaXML.java
```

**Permite verificar:**
- Lectura correcta de XML
- Conversión a objetos Java
- Relación entre Tipo y Precio

---

## 🛠️ Tecnologías utilizadas

- Java
- JDK 21
- NetBeans
- Java Swing
- XML
- DOM Parser
- Programación Orientada a Objetos (POO)

---

## 👥 Integrantes

- Nombre: Angie Mariela Alpizar Porrass
- Nombre: Efran Diego Matarrita

---

## 📌 Estado del proyecto

### ✅ Avances realizados

- Clases principales implementadas
- Estructura del proyecto creada
- Archivos XML iniciales creados
- Lectura y escritura de XML funcionando
- Prueba de carga de datos completada

---

### 🚧 Pendiente

- Implementación de ventanas Swing
- Desarrollo del Kiosko
- Desarrollo del Administrador
- Diagrama de clases en Draw.io
- Documento explicando uso de DOM
- Pruebas finales

---

## ✨ Notas

Este proyecto sigue una arquitectura basada en separación de responsabilidades:

- **Conceptos** → Modelos (POO)
- **Util** → Manejo de archivos
- **Aplicacion** → Lógica
- **Presentacion** → Interfaz gráfica
