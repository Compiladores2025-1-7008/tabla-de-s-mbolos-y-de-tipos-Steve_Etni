package src.main;

import java.io.*;
import java.util.*;

import src.symbol.Symbol;
import src.symbol.SymbolImpl;
import src.symbol.SymbolTable;
import src.symbol.SymbolTableImpl;
import src.symbol.SymbolTableStack;
import src.symbol.SymbolTableStackImpl;
import src.type.TypeTable;
import src.type.TypeTableImpl;
import src.util.InputReader;
import src.util.TablePrinter;
import src.util.Colors;

/**
 * Clase principal que implementa un analizador de código fuente para la gestión
 * de tablas de símbolos y tipos. Esta clase es el núcleo del sistema y se encarga
 * del procesamiento de código fuente, manejo de ámbitos y gestión de tipos.
 *
 * Funcionalidades principales:
 * - Lectura y procesamiento de archivos de código fuente
 * - Gestión de ámbitos global y local
 * - Procesamiento de estructuras
 * - Manejo de declaraciones de funciones y variables
 * - Interfaz de usuario interactiva
 * 
 * @author steve-quezada
 * @author etnicst
 */
public class Main {

    private static Map<String, Symbol> currentStructFields = null;
    private static String currentStructName = null;

    /**
     * Punto de entrada principal del programa. Inicializa las tablas necesarias
     * y presenta una interfaz de usuario para cargar y procesar archivos.
     *
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        TypeTable typeTable = new TypeTableImpl();
        SymbolTableStack symbolStack = new SymbolTableStackImpl();
        SymbolTable globalTable = new SymbolTableImpl(typeTable, null);
        symbolStack.push(globalTable);

        Scanner menuScanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println();
            Colors.println("┌───────────────────────┐", Colors.GRAY_DARK);
            Colors.println("│     Menú Principal    │", Colors.TEAL + Colors.HIGH_INTENSITY);
            Colors.println("├───────────────────────┤", Colors.GRAY_DARK);
            Colors.println("│ 1. Leer desde archivo │", Colors.WHITE);
            Colors.println("│ 2. Salir              │", Colors.WHITE);
            Colors.println("└───────────────────────┘", Colors.GRAY_DARK);
            Colors.println("  Seleccione una opción  ", Colors.TEAL);

            String input = menuScanner.nextLine().trim();

            try {
                int option = Integer.parseInt(input);
                switch (option) {
                    case 1:
                        Colors.println("┌───────────────────────┐", Colors.GRAY_DARK);
                        Colors.println("│  Nombre del archivo:  │", Colors.TEAL + Colors.HIGH_INTENSITY);
                        Colors.println("└───────────────────────┘", Colors.GRAY_DARK);
                        String filename = menuScanner.nextLine().trim();
                        Colors.print("\n" + "═".repeat(89), Colors.GRAY_DARK);
                        System.out.println();
                        try {
                            processInput(new InputReader(getResourcePath(filename)), typeTable, symbolStack,
                                    globalTable);
                            Colors.println("\n" + "═".repeat(89), Colors.GRAY_DARK);
                        } catch (IOException e) {
                            Colors.println("Error al leer el archivo: " + e.getMessage(), Colors.RED);
                        }
                        break;
                    case 2:
                        running = false;
                        Colors.println("┌───────────────────────┐", Colors.GRAY_DARK);
                        Colors.println("│  Programa finalizado. │", Colors.TEAL + Colors.HIGH_INTENSITY);
                        Colors.println("└───────────────────────┘", Colors.GRAY_DARK);
                        break;
                    default:
                        Colors.println("Error: Opción no válida. Por favor seleccione 1 o 2.",
                                Colors.RED);
                }
            } catch (NumberFormatException e) {
                Colors.println("Error: Por favor ingrese un número válido.",
                        Colors.RED);
            } catch (Exception e) {
                Colors.println("Error inesperado: " + e.getMessage(),
                        Colors.RED);
                menuScanner.nextLine();
            }
        }

        menuScanner.close();
    }

    /**
     * Procesa un archivo de entrada, reiniciando las tablas y procesando cada línea.
     * 
     * @param reader      Lector del archivo de entrada
     * @param typeTable   Tabla de tipos del compilador
     * @param symbolStack Pila de tablas de símbolos
     * @param globalTable Tabla de símbolos global
     * @throws IOException Si hay errores en la lectura del archivo
     */
    private static void processInput(InputReader reader, TypeTable typeTable,
            SymbolTableStack symbolStack,
            SymbolTable globalTable) throws IOException {
        StringBuilder input = new StringBuilder();
        String line;

        ((TypeTableImpl) typeTable).reset();
        symbolStack = new SymbolTableStackImpl();
        globalTable = new SymbolTableImpl(typeTable, null);
        symbolStack.push(globalTable);

        while ((line = reader.readLine()) != null) {
            input.append(line).append("\n");
        }

        reader.close();

        if (input.length() > 0) {

            processCode(input.toString(), typeTable, symbolStack, globalTable);

            printSymbolTable(globalTable, typeTable, symbolStack);
        }
    }

    /**
     * Obtiene la ruta completa de un archivo de recursos.
     *
     * @param filename Nombre del archivo
     * @return Ruta completa al archivo en el directorio de recursos
     */
    private static String getResourcePath(String filename) {
        return "src/resources/" + filename;
    }

    /**
     * Procesa el código fuente línea por línea, identificando y manejando diferentes
     * construcciones sintácticas como estructuras, funciones y variables.
     *
     * @param code        Código fuente completo
     * @param typeTable   Tabla de tipos
     * @param symbolStack Pila de tablas de símbolos
     * @param globalTable Tabla de símbolos global
     */
    private static void processCode(String code, TypeTable typeTable,
            SymbolTableStack symbolStack,
            SymbolTable globalTable) {
        String[] lines = code.split("\n");
        Map<String, Symbol> currentStructFields = null;
        String currentStructName = null;
        SymbolTable currentFunctionScope = null;
        StringBuilder structBuilder = new StringBuilder();
        StringBuilder functionBuilder = new StringBuilder();
        boolean inStruct = false;
        boolean inFunction = false;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("//"))
                continue;

            try {
                if (line.startsWith("struct")) {
                    handleStructStart(line, currentStructFields, structBuilder);
                    inStruct = true;
                } else if (inStruct) {
                    handleStructContent(line, typeTable, globalTable, currentStructFields,
                            structBuilder, currentStructName);
                    if (line.contains("}"))
                        inStruct = false;
                } else if (line.contains("(") && !line.contains(";")) {
                    handleFunctionStart(line, typeTable, globalTable, functionBuilder);
                    inFunction = true;
                    currentFunctionScope = new SymbolTableImpl(typeTable);
                } else if (inFunction) {
                    handleFunctionContent(line, typeTable, symbolStack, currentFunctionScope,
                            functionBuilder);
                    if (line.contains("}")) {
                        inFunction = false;
                        symbolStack.push(currentFunctionScope);
                    }
                } else if (line.contains(";")) {
                    handleGlobalDeclaration(line, typeTable, globalTable);
                }
            } catch (Exception e) {
                Colors.println("Error procesando línea: " + line, Colors.RED);
                Colors.println("Error: " + e.getMessage(), Colors.RED);
            }
        }
    }

    /**
     * Inicia el procesamiento de una declaración de estructura.
     *
     * @param line    Línea que contiene la declaración de la estructura
     * @param fields  Mapa para almacenar los campos
     * @param builder StringBuilder para acumular la definición
     */
    private static void handleStructStart(String line, Map<String, Symbol> fields,
            StringBuilder builder) {
        String structName = line.split("\\s+")[1];

        fields = new LinkedHashMap<>();
        builder.setLength(0);
        builder.append(line).append("\n");
        currentStructName = structName;
        currentStructFields = fields;
    }

    /**
     * Procesa el contenido de una estructura, incluyendo sus campos y tipos.
     */
    private static void handleStructContent(String line, TypeTable typeTable,
            SymbolTable globalTable,
            Map<String, Symbol> fields,
            StringBuilder builder,
            String structName) {
        line = line.trim();
        if (line.contains("}")) {
            builder.append(line);
            if (currentStructFields != null && currentStructName != null) {

                Map<String, Symbol> orderedFields = new LinkedHashMap<>(currentStructFields);
                int structTypeId = ((TypeTableImpl) typeTable).createStructType(
                        currentStructName, orderedFields);
                globalTable.insert(currentStructName,
                        new SymbolImpl(0, structTypeId, "struct"));
                currentStructFields = null;
                currentStructName = null;
            }
        } else if (line.contains(";")) {
            String[] parts = line.trim().split("\\s+|;");
            String fieldType = parts[0];
            String fieldName = parts[1];

            int typeId = typeTable.findTypeByName(fieldType);
            if (typeId < 0) {

                typeId = getTypeId(typeTable, fieldType);
            }

            if (currentStructFields != null) {
                currentStructFields.put(fieldName, new SymbolImpl(0, typeId, "Miembro"));
            }
            builder.append(line).append("\n");
        }
    }

    /**
     * Inicia el procesamiento de una declaración de función.
     */
    private static void handleFunctionStart(String line, TypeTable typeTable,
            SymbolTable globalTable,
            StringBuilder builder) {
        builder.setLength(0);
        builder.append(line).append("\n");
        processFunctionHeader(line, typeTable, globalTable);
    }

    /**
     * Procesa el contenido de una función, incluyendo variables locales.
     */
    private static void handleFunctionContent(String line, TypeTable typeTable,
            SymbolTableStack symbolStack,
            SymbolTable functionScope,
            StringBuilder builder) {
        if (line.contains("}")) {
            builder.append(line);
            processFunctionBody(builder.toString(), functionScope, typeTable, symbolStack);
        } else if (line.contains(";") && !line.startsWith("return")) {
            processLocalVariable(line, functionScope, typeTable);
            builder.append(line).append("\n");
        }
    }

    /**
     * Maneja declaraciones globales de variables y arrays.
     */
    private static void handleGlobalDeclaration(String line, TypeTable typeTable,
            SymbolTable globalTable) {
        if (line.contains("[")) {
            processGlobalArrayDeclaration(line, typeTable, globalTable);
        } else {
            processGlobalVariable(line, typeTable, globalTable);
        }
    }

    /**
     * Procesa la cabecera de una función, extrayendo tipo de retorno y parámetros.
     */
    private static void processFunctionHeader(String line, TypeTable typeTable,
            SymbolTable globalTable) {
        String[] parts = line.split("\\s+|\\(");
        String returnType = parts[0];
        String funcName = parts[1];

        List<Integer> paramTypes = new ArrayList<>();
        String paramStr = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
        if (!paramStr.trim().isEmpty()) {
            for (String param : paramStr.split(",")) {
                String[] paramParts = param.trim().split("\\s+");
                paramTypes.add(getTypeId(typeTable, paramParts[0]));
            }
        }

        globalTable.insertFunction(funcName, getTypeId(typeTable, returnType), paramTypes);
    }

    /**
     * Procesa una variable local dentro de una función.
     */
    private static void processLocalVariable(String line, SymbolTable scope,
            TypeTable typeTable) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 2)
            return;

        String varType = parts[0];
        String varName = parts[1].replace(";", "");

        int typeId = getTypeId(typeTable, varType);
        scope.insert(varName, new SymbolImpl(
                scope.getCurrentOffset(),
                typeId,
                "Variable"
        ));
    }

    /**
     * Procesa una variable global.
     */
    private static void processGlobalVariable(String line, TypeTable typeTable,
            SymbolTable globalTable) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 2)
            return;

        String varType = parts[0];
        String varName = parts[1].replace(";", "");

        int typeId = typeTable.findTypeByName(varType);
        if (typeId < 0) {
            typeId = getTypeId(typeTable, varType);
        }

        globalTable.insert(varName, new SymbolImpl(0, typeId, "Variable"));
    }

    /**
     * Procesa la declaración de un array global.
     */
    private static void processGlobalArrayDeclaration(String line, TypeTable typeTable,
            SymbolTable globalTable) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 2)
            return;

        String arrayType = parts[0];
        String arrayName = parts[1].replace(";", "");
        processArrayDeclaration(arrayType, arrayName, typeTable, globalTable);
    }

    /**
     * Obtiene o crea un identificador de tipo.
     *
     * @param typeTable Tabla de tipos
     * @param typeName  Nombre del tipo
     * @return ID del tipo encontrado o creado
     */
    private static int getTypeId(TypeTable typeTable, String typeName) {
    
        if (typeName.equals("return")) {
        }

        int existingId = typeTable.findTypeByName(typeName);
        if (existingId >= 0) {
            return existingId;
        }

        switch (typeName.toLowerCase()) {
            case "int":
                return Config.TypeIds.INT;
            case "float":
                return Config.TypeIds.FLOAT;
            case "void":
                return Config.TypeIds.VOID;
            default:
                return typeTable.addType(typeName, 1, -1);
        }
    }

    /**
     * Procesa la declaración de un array, extrayendo dimensiones y tipo base.
     */
    private static void processArrayDeclaration(String arrayType, String name,
            TypeTable typeTable, SymbolTable scope) {

        List<Integer> dimensions = new ArrayList<>();
        int start = arrayType.indexOf("[");
        while (start != -1) {
            int end = arrayType.indexOf("]", start);
            String dimStr = arrayType.substring(start + 1, end);
            dimensions.add(Integer.parseInt(dimStr));
            start = arrayType.indexOf("[", end);
        }

        String baseType = arrayType.substring(0, arrayType.indexOf("["));

        int arrayTypeId = ((TypeTableImpl) typeTable).createMultiDimArrayType(
                baseType, dimensions);

        scope.insert(name, new SymbolImpl(0, arrayTypeId, "Variable"));
    }

    /**
     * Imprime las tablas de símbolos y tipos con formato.
     */
    private static void printSymbolTable(SymbolTable globalTable, TypeTable typeTable,
            SymbolTableStack symbolStack) {
        TablePrinter.printTypeTable(typeTable);
        TablePrinter.printSymbolTables(symbolStack, typeTable);
    }

    /**
     * Procesa el cuerpo de una función, incluyendo variables locales y ámbito.
     */
    private static void processFunctionBody(String functionDef,
            SymbolTable functionScope,
            TypeTable typeTable,
            SymbolTableStack symbolStack) {
        String[] lines = functionDef.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.contains("(") || line.equals("{") ||
                    line.equals("}") || line.startsWith("return ")) {
                continue;
            }

            if (line.contains(";")) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 2) {
                    String varType = parts[0];
                    String varName = parts[1].replace(";", "");

                    if (varType.contains("[")) {
                        processArrayDeclaration(varType, varName, typeTable, functionScope);
                    } else {
                        int typeId = getTypeId(typeTable, varType);
                        functionScope.insert(varName, new SymbolImpl(0, typeId, "Variable"));
                    }
                }
            }
        }

        symbolStack.push(functionScope);
    }

}
