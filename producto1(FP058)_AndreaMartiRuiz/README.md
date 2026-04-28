## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

# Online Store — FASE 1

## 1. Objetivo
Diseñar el **modelo estático** de una tienda online para gestión interna (aplicación de escritorio en Java, ejecución por consola en fases posteriores).  
En esta fase se elaboran los **diagramas UML** a partir de los requisitos funcionales del enunciado.

---

## 2. Alcance de la FASE 1
En esta fase se incluyen:

- **Diagrama de clases (UML)**: entidades del dominio, atributos, operaciones de negocio y relaciones (multiplicidades y herencia).
- **Diagrama de casos de uso (UML)**: actor principal y funcionalidades del menú (agrupadas por áreas de gestión).

> Nota: No se implementa código ni se aplica MVC/DAO/BD en esta fase.

---

## 3. Modelo del dominio (Diagrama de clases)

### 3.1 Entidades principales
- **Cliente (abstracta)**  
  Atributos: `nombre`, `domicilio`, `nif`, `email` *(email como identificador)*.  
  Subtipos:
  - **ClienteEstándar**: no paga cuota, sin descuento de envío.
  - **ClientePremium**: cuota anual de 30€ y **20% de descuento** en gastos de envío.

- **Articulo**  
  Atributos: `codigo` *(alfanumérico, identificador)*, `descripcion`, `precioVenta`, `gastosEnvio`, `tiempoPreparacionMin`.

- **Pedido**  
  Atributos: `numeroPedido`, `cliente`, `articulo`, `unidades`, `fechaHora`.  
  Restricciones:
  - Cada pedido contiene **un único artículo** (con varias unidades).
  - Un pedido solo puede eliminarse/cancelarse si **no ha superado** el tiempo de preparación del artículo desde su fecha/hora.

### 3.2 Relaciones y multiplicidades (resumen)
- **Herencia**:
  - `ClienteEstandar --|> Cliente`
  - `ClientePremium  --|> Cliente`

- **Cliente — Pedido**:
  - Un pedido pertenece a **1** cliente.
  - Un cliente puede tener **0..*** pedidos.

- **Articulo — Pedido**:
  - Un pedido incluye **1** artículo.
  - Un artículo puede estar en **0..*** pedidos.

---

## 4. Funcionalidades (Diagrama de casos de uso)

### 4.1 Límite del sistema
El límite del sistema es la aplicación **Online Store (gestión interna)**.

### 4.2 Actor
- **Empleado/Administrador**: usuario que interactúa con el sistema mediante el menú.
- Los **clientes no son actores** en esta aplicación, ya que no interactúan directamente con el sistema (es una herramienta de gestión interna).

### 4.3 Casos de uso (según requisitos funcionales)
Se agrupan en tres áreas:

#### Gestión de Artículos
- Añadir Artículo
- Mostrar Artículos

#### Gestión de Clientes
- Añadir Cliente
- Mostrar Clientes
- Mostrar Clientes Estándar
- Mostrar Clientes Premium

#### Gestión de Pedidos
- Añadir Pedido *(si el cliente no existe, se solicita y se añade; el artículo debe existir)*
- Eliminar Pedido *(solo si no está enviado según tiempo de preparación)*
- Mostrar pedidos pendientes de envío *(con filtro por cliente)*
- Mostrar pedidos enviados *(con filtro por cliente)*

---

## 5. Entregables
- Diagrama UML de **clases**
- Diagrama UML de **casos de uso**
- (Este documento) README con explicación de la FASE 1

---

## 6. Notas para fases posteriores (fuera de alcance de FASE 1)
- FASE 2: Implementación en Java aplicando **MVC**, consola, estructuras dinámicas y gestión de excepciones.
- FASE 3: Persistencia con **JDBC + MySQL**, patrón **DAO**, transacciones y procedimientos almacenados.
- FASE 4: Persistencia mediante **ORM**.
- FASE 5: Interfaz gráfica con **JavaFX**.

# Producto 4 - Programación Orientada a Objetos con acceso a BBDD

## Descripción

Este proyecto corresponde al producto 4 de la asignatura de Programación Orientada a Objetos con acceso a Bases de Datos.

En esta fase se ha adaptado la aplicación de escritorio desarrollada en productos anteriores para trabajar con persistencia ORM mediante **JPA**, utilizando **Hibernate** como implementación y **MySQL** como sistema gestor de base de datos.

La aplicación mantiene la estructura **MVC** y permite gestionar clientes, pedidos y artículos.

## Objetivo del producto

El objetivo principal de este producto ha sido sustituir la persistencia anterior basada en JDBC por una solución ORM con JPA, manteniendo el funcionamiento general del proyecto y su organización interna.

## Tecnologías utilizadas

- Java
- MySQL
- JPA
- Hibernate
- Maven
- Patrón MVC

## Funcionalidades principales

La aplicación permite realizar las siguientes operaciones:

### Clientes
- Alta de cliente estándar
- Alta de cliente premium
- Listado de clientes
- Modificación de clientes
- Eliminación de clientes

### Pedidos
- Alta de pedidos
- Listado de pedidos
- Modificación de pedidos
- Eliminación de pedidos

### Artículos
- Mostrar artículos disponibles

## Estructura del proyecto

El proyecto está organizado en varios paquetes:

- `andreamr.modelo`: clases del modelo y entidades JPA
- `andreamr.vista`: parte de interacción con el usuario
- `andreamr.controlador`: conexión entre vista y modelo
- `andreamr.dao`: interfaces DAO
- `andreamr.dao.jpa`: implementaciones DAO con JPA
- `andreamr.dao.mysql`: implementaciones antiguas con JDBC, conservadas como referencia
- `andreamr.util`: clases auxiliares como `JPAUtil`

## Persistencia ORM

En este producto se ha realizado el mapeo ORM de las clases principales del proyecto:

- `Articulo`
- `Cliente`
- `ClienteEstandar`
- `ClientePremium`
- `Pedido`

También se han definido las relaciones necesarias entre entidades, por ejemplo la relación entre pedidos, clientes y artículos.

## Configuración

La configuración de la persistencia se encuentra en:

`src/META-INF/persistence.xml`

En ese archivo se indica:
- la conexión con MySQL
- el usuario y contraseña
- la unidad de persistencia
- las propiedades de Hibernate

## Ejecución

Para compilar el proyecto con Maven:

```bash
mvn clean compile