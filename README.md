<div align="center">
<a href="https://classroom.github.com/a/NBafOLw1">
<img src="https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg" alt="Review Assignment Due Date">
</a>
<a href="https://classroom.github.com/online_ide?assignment_repo_id=16894960&assignment_repo_type=AssignmentRepo">
<img src="https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg" alt="Open in Visual Studio Code">
</a>
    <br>
    <br>
</div>

<div align="center">
  <img width="200" src="https://www.fciencias.unam.mx/sites/default/files/logoFC_2.png" alt="Logo FC">
  <h1>Compiladores 2025-1</h1>
  <h3>Prácticas de Laboratorio</h3>
  <p>
    <strong>Profesora:</strong> Ariel Adara Mercado Martínez <br>
    <strong>Ayudante:</strong> Janeth Pablo Martínez <br>
    <strong>Ayud. Lab.:</strong> Carlos Gerardo Acosta Hernández <br>
    <br>
    <strong>Alumnos:</strong>
    <br>
    Etni Sarai Castro Sierra <br> 
    Kevin Steve Quezada Ordoñez <br> 
  </p>
</div>

# Tabla de Símbolos y Tipos

Implementación de una tabla de símbolos y sistema de tipos para un compilador desarrollado en Java.

## Características

- Gestión de tablas de símbolos con ámbitos anidados
- Sistema de tipos que soporta:
  - Tipos primitivos (int, float)
  - Arrays (uni y multidimensionales)
  - Estructuras
  - Funciones con parámetros
- Interfaz de línea de comandos interactiva
- Visualización de tablas con formato y colores
- Procesamiento de archivos de entrada

## Requisitos Previos

- Java Development Kit (JDK) 8 o superior
- Apache Ant

## Estructura del Proyecto

```
C:
│   build.xml
│   README.md
│   
└───src
    ├───main
    │       Config.java
    │       Main.java
    │       
    ├───resources
    │       1.txt
    │       2.txt
    │       3.txt
    │       4.txt
    │       5.txt
    │       
    ├───symbol
    │       Symbol.java
    │       SymbolImpl.java
    │       SymbolTable.java
    │       SymbolTableImpl.java
    │       SymbolTableStack.java
    │       SymbolTableStackImpl.java
    │       
    ├───type
    │       Type.java
    │       TypeImpl.java
    │       TypeParent.java
    │       TypeTable.java
    │       TypeTableImpl.java
    │       
    └───util
            Colors.java
            InputReader.java
            TablePrinter.java

```

## Componentes del Proyecto

- `main/` - Clases principales
  - `Main.java` - Punto de entrada que implementa:
    - Interfaz de usuario interactiva
    - Procesamiento de código fuente
    - Gestión de declaraciones y ámbitos
  - `Config.java` - Constantes de configuración para:
    - IDs de tipos predefinidos
    - Tamaños de tipos primitivos
    - Nombres de tipos por defecto
    
- `symbol/` - Implementación de tabla de símbolos
  - `Symbol.java` - Interfaz de símbolo que define las operaciones básicas sobre símbolos (getDir, getType, getCat, getArgs)
  - `SymbolImpl.java` - Implementación concreta de un símbolo con dirección, tipo, categoría y lista de argumentos
  - `SymbolTable.java` - Interfaz de tabla de símbolos que define operaciones de búsqueda e inserción
  - `SymbolTableImpl.java` - Implementación de tabla de símbolos con soporte para variables, funciones y estructuras
  - `SymbolTableStack.java` - Interfaz para gestión de ámbitos anidados
  - `SymbolTableStackImpl.java` - Implementación de pila de ámbitos que maneja el anidamiento de tablas

- `type/` - Implementación del sistema de tipos
  - `Type.java` - Interfaz que define las operaciones básicas sobre tipos
  - `TypeTable.java` - Interfaz para la gestión de tipos del compilador
  - `TypeImpl.java` - Implementación de un tipo con nombre, tamaño y propiedades
  - `TypeTableImpl.java` - Implementación completa del sistema de tipos que soporta:
    - Tipos primitivos (int, float)
    - Arrays uni y multidimensionales
    - Estructuras con campos
    - Tipos derivados

- `util/` - Clases utilitarias
  - `Colors.java` - Biblioteca de constantes y métodos para colorear la salida en terminal
  - `InputReader.java` - Manejo de lectura de archivos de entrada con BufferedReader
  - `TablePrinter.java` - Formateador sofisticado para visualización de tablas con:
    - Bordes Unicode personalizados
    - Colores por tipo de contenido
    - Alineación automática de columnas
    - Manejo de encabezados

## Flujo General de los Comandos

### Ejecutar el proyecto:
Este comando compilará el proyecto y luego lo ejecutará:

```bash
$ ant run
```

### Limpiar el directorio de compilación:
Este comando eliminará todos los archivos generados en el proceso de compilación:

```bash
$ ant clean
```

## Uso

1. Al iniciar, seleccione la opción 1 para leer desde archivo
2. Ingrese el nombre del archivo (ejemplo: "1.txt")
3. El programa mostrará:
   - Tabla de Tipos: Muestra todos los tipos definidos
   - Ámbito Global: Muestra variables y funciones globales
   - Estructuras: Muestra los campos de las estructuras definidas
   - Ámbito Main: Muestra variables locales


