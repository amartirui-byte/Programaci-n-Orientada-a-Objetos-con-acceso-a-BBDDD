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