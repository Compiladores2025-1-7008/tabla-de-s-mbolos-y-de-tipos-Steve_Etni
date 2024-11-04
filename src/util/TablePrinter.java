package src.util;

import java.util.*;

import src.symbol.Symbol;
import src.symbol.SymbolTable;
import src.symbol.SymbolTableStack;
import src.type.Type;
import src.type.TypeTable;

/**
 * Clase que se encarga de la visualización formateada de las tablas de símbolos
 * y tipos en la terminal.
 *
 * @author steve-quezada
 */
public class TablePrinter {

    private static TypeTable currentTypeTable;

    /**
     * Clase interna que maneja el cálculo y formato de anchos de columnas para
     * las tablas.
     */
    private static class ColumnWidths {

        int[] widths;

        /**
         * Constructor que inicializa el array de anchos.
         *
         * @param columns Número de columnas en la tabla
         */
        ColumnWidths(int columns) {
            widths = new int[columns];
        }

        /**
         * Actualiza el ancho de una columna si el nuevo valor es más largo.
         *
         * @param column Índice de la columna a actualizar
         * @param value  Valor cuya longitud se compara
         */
        void updateWidth(int column, String value) {
            if (value != null) {
                widths[column] = Math.max(widths[column], value.length());
            }
        }

        /**
         * Formatea una fila de valores con el color especificado.
         *
         * @param values       Valores a formatear
         * @param contentColor Color a aplicar al contenido
         * @return String formateado con los valores y colores
         */
        String format(String[] values, String contentColor) {
            StringBuilder sb = new StringBuilder();
            sb.append(Colors.GRAY_DARK).append("│").append(contentColor).append(" ");

            for (int i = 0; i < values.length; i++) {
                String value = values[i] != null ? values[i] : "";
                sb.append(String.format("%-" + widths[i] + "s", value));
                if (i < values.length - 1) {
                    sb.append(Colors.GRAY_DARK).append(" │ ").append(contentColor);
                }
            }

            sb.append(Colors.GRAY_DARK).append(" │");
            return sb.toString();
        }

        /**
         * Genera una línea separadora horizontal para la tabla.
         *
         * @return String con la línea separadora formateada
         */
        String separator() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < widths.length; i++) {
                if (i == 0) {
                    sb.append("├");
                } else {
                    sb.append("┼");
                }
                sb.append("─".repeat(widths[i] + 2));
            }
            sb.append("┤");
            return sb.toString();
        }

        /**
         * Genera el borde superior de la tabla.
         *
         * @return String con el borde superior formateado
         */
        String topBorder() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < widths.length; i++) {
                if (i == 0) {
                    sb.append("┌");
                } else {
                    sb.append("┬");
                }
                sb.append("─".repeat(widths[i] + 2));
            }
            sb.append("┐");
            return sb.toString();
        }

        /**
         * Genera el borde inferior de la tabla.
         *
         * @return String con el borde inferior formateado
         */
        String bottomBorder() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < widths.length; i++) {
                if (i == 0) {
                    sb.append("└");
                } else {
                    sb.append("┴");
                }
                sb.append("─".repeat(widths[i] + 2));
            }
            sb.append("┘");
            return sb.toString();
        }

        /**
         * Formatea los encabezados de la tabla con sus respectivos colores.
         *
         * @param values Array de Strings con los nombres de los encabezados
         * @return String formateado con los encabezados y sus colores
         */
        String formatHeaders(String[] values) {
            StringBuilder sb = new StringBuilder();
            sb.append(Colors.GRAY_DARK).append("│ ");

            for (int i = 0; i < values.length; i++) {
                String value = values[i] != null ? values[i] : "";
                String color = getHeaderColor(value);
                sb.append(color).append(String.format("%-" + widths[i] + "s", value));
                if (i < values.length - 1) {
                    sb.append(Colors.GRAY_DARK).append(" │ ");
                }
            }

            sb.append(Colors.GRAY_DARK).append(" │");
            return sb.toString();
        }

        /**
         * Determina el color apropiado para cada tipo de encabezado.
         *
         * @param header Nombre del encabezado
         * @return String con el código de color correspondiente
         */
        private String getHeaderColor(String header) {
            switch (header) {
                case "Nombre":
                    return Colors.CYAN + Colors.HIGH_INTENSITY;
                case "Tipo ID":
                    return Colors.EMERALD + Colors.HIGH_INTENSITY;
                case "Tipo Retorno":
                    return Colors.GOLD + Colors.HIGH_INTENSITY;
                case "Categoría":
                    return Colors.MAGENTA + Colors.HIGH_INTENSITY;
                case "Ámbito":
                    return Colors.TEAL + Colors.HIGH_INTENSITY;
                case "Tipo Base":
                    return Colors.LIME + Colors.HIGH_INTENSITY;
                case "Descripción":
                    return Colors.LAVENDER + Colors.HIGH_INTENSITY;
                default:
                    return Colors.WHITE;
            }
        }
    }

    /**
     * Imprime la tabla de tipos con formato.
     * Muestra los tipos definidos con sus identificadores y descripciones.
     *
     * @param typeTable Tabla de tipos a imprimir
     */
    public static void printTypeTable(TypeTable typeTable) {
        currentTypeTable = typeTable;
        System.out.println();
        Colors.print("┌────────────────┐", Colors.GRAY_DARK);
        Colors.println("\n" + "│ Tabla de Tipos │", Colors.MOSS_GREEN + Colors.HIGH_INTENSITY);
        Colors.println("└────────────────┘", Colors.GRAY_DARK);

        List<String[]> rows = new ArrayList<>();
        ColumnWidths widths = new ColumnWidths(3);

        String[] headers = { "Tipo ID", "Tipo Base", "Descripción" };
        rows.add(headers);

        TreeMap<Integer, Type> usedTypes = new TreeMap<>();
        for (int i = 0; i < 20; i++) {
            Optional<Type> type = typeTable.getType(i);
            if (type.isPresent() && type.get().getName() != null && !type.get().getName().isEmpty()) {
                usedTypes.put(i, type.get());
            }
        }

        for (Map.Entry<Integer, Type> entry : usedTypes.entrySet()) {
            String[] row = new String[3];
            row[0] = String.valueOf(entry.getKey());
            row[1] = entry.getValue().getName();
            row[2] = getTypeDescription(entry.getValue());
            rows.add(row);
        }

        printFormattedTable(rows, widths);
        System.out.println();
    }

    /**
     * Genera una descripción detallada para un tipo específico.
     *
     * @param type Tipo a describir
     * @return Descripción del tipo como String
     */
    private static String getTypeDescription(Type type) {
        if (type.getParentStruct() != null) {
            String description = getDetailedStructDescription(type.getParentStruct());
            return description.isEmpty() ? "struct" : "struct { " + description + " }";
        } else if (type.getName().contains("[")) {
            return "array";
        } else {
            return "Tipo primitivo";
        }
    }
    
    /**
     * Genera una descripción detallada de los miembros de una estructura.
     *
     * @param structTable Tabla de símbolos que contiene los miembros de la estructura
     * @return String con la descripción formateada de los miembros
     */
    private static String getDetailedStructDescription(SymbolTable structTable) {
        if (structTable == null || structTable.getSymbols().isEmpty())
            return "";

        StringBuilder desc = new StringBuilder();
        boolean first = true;

        Map<String, Symbol> fields = new LinkedHashMap<>(structTable.getSymbols());
        for (Map.Entry<String, Symbol> entry : fields.entrySet()) {
            if (!first)
                desc.append("; ");
            String typeName = currentTypeTable.getName(entry.getValue().getType());
            if (typeName == null || typeName.isEmpty()) {
                typeName = String.valueOf(entry.getValue().getType());
            }
            desc.append(typeName).append(" ").append(entry.getKey());
            first = false;
        }
        return desc.toString();
    }

    /**
     * Imprime las tablas de símbolos del programa.
     * Muestra también las estructuras definidas con sus miembros.
     *
     * @param stack Pila de tablas de símbolos del programa
     * @param typeTable Tabla de tipos actual
     */
    public static void printSymbolTables(SymbolTableStack stack, TypeTable typeTable) {
        currentTypeTable = typeTable;

        Optional<SymbolTable> globalScope = stack.base();
        if (globalScope.isPresent()) {
            System.out.println();
            Colors.print("┌───────────────┐", Colors.GRAY_DARK);
            Colors.println("\n" + "│ Ámbito Global │", Colors.BLUE + Colors.HIGH_INTENSITY);
            Colors.println("└───────────────┘", Colors.GRAY_DARK);
            printGlobalScope(globalScope.get());

            Map<String, Symbol> orderedSymbols = new LinkedHashMap<>(globalScope.get().getSymbols());

            for (Map.Entry<String, Symbol> entry : orderedSymbols.entrySet()) {
                if ("struct".equals(entry.getValue().getCat())) {
                    System.out.println();
                    Colors.print("┌─────────────┐", Colors.GRAY_DARK);
                    Colors.println("\n" + "│ Estructura: │ " + entry.getKey(),
                            Colors.SLATE_GRAY + Colors.HIGH_INTENSITY);
                    Colors.println("└─────────────┘", Colors.GRAY_DARK);
                    printStructureMembers(typeTable.getParentStruct(entry.getValue().getType()));
                }
            }
        }

        System.out.println();
        Colors.print("┌──────────────────┐", Colors.GRAY_DARK);
        Colors.println("\n" + "│  Ámbito de Main  │", Colors.DARK_PURPLE + Colors.HIGH_INTENSITY);
        Colors.println("└──────────────────┘", Colors.GRAY_DARK);
        Optional<SymbolTable> mainScope = stack.peek();
        if (mainScope.isPresent() && !mainScope.equals(globalScope)) {
            printMainScope(mainScope.get());
        }
    }

    /**
     * Imprime los símbolos del ámbito global.
     * Incluye nombres, tipos, retornos y categorías de los símbolos.
     *
     * @param table Tabla de símbolos del ámbito global
     */
    private static void printGlobalScope(SymbolTable table) {
        List<String[]> rows = new ArrayList<>();
        ColumnWidths widths = new ColumnWidths(5);

        String[] headers = { "Nombre", "Tipo ID", "Tipo Retorno", "Categoría", "Ámbito" };
        rows.add(headers);

        TreeMap<String, Symbol> symbols = new TreeMap<>(table.getSymbols());
        for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
            String[] row = new String[5];
            Symbol sym = entry.getValue();
            row[0] = entry.getKey();
            row[1] = sym.getCat().equals("function") ? "-" : String.valueOf(sym.getType());
            row[2] = sym.getCat().equals("function") ? "int" : "-";
            row[3] = sym.getCat();
            row[4] = "Global";
            rows.add(row);
        }

        printFormattedTable(rows, widths);
    }

    /**
     * Imprime los miembros de una estructura.
     * Muestra el nombre, tipo y categoría de cada miembro.
     *
     * @param table Tabla de símbolos que representa la estructura
     */
    private static void printStructureMembers(SymbolTable table) {
        if (table == null)
            return;

        List<String[]> rows = new ArrayList<>();
        ColumnWidths widths = new ColumnWidths(3);

        String[] headers = { "Nombre", "Tipo ID", "Categoría" };
        rows.add(headers);

        Map<String, Symbol> orderedSymbols = new LinkedHashMap<>(table.getSymbols());
        for (Map.Entry<String, Symbol> entry : orderedSymbols.entrySet()) {
            String[] row = new String[3];
            row[0] = entry.getKey();
            row[1] = String.valueOf(entry.getValue().getType());
            row[2] = "Miembro";
            rows.add(row);
        }

        printFormattedTable(rows, widths);
    }

    /**
     * Imprime las variables locales del ámbito main.
     * Muestra el nombre, tipo y categoría de cada variable.
     *
     * @param table Tabla de símbolos del ámbito main
     */
    private static void printMainScope(SymbolTable table) {
        List<String[]> rows = new ArrayList<>();
        ColumnWidths widths = new ColumnWidths(3);

        String[] headers = { "Nombre", "Tipo ID", "Categoría" };
        rows.add(headers);

        TreeMap<String, Symbol> symbols = new TreeMap<>(table.getSymbols());
        for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
            if (!entry.getValue().getCat().equals("function")) {
                String[] row = new String[3];
                row[0] = entry.getKey();
                row[1] = String.valueOf(entry.getValue().getType());
                row[2] = "Variable";
                rows.add(row);
            }
        }

        printFormattedTable(rows, widths);
    }
    
    /**
     * Imprime una tabla formateada con bordes y colores.
     * Se encarga del formato visual de todas las tablas del programa.
     *
     * @param rows Lista de filas a imprimir, donde cada fila es un array de Strings
     * @param widths Objeto que maneja los anchos de las columnas
     */
    private static void printFormattedTable(List<String[]> rows, ColumnWidths widths) {

        for (String[] row : rows) {
            for (int j = 0; j < row.length; j++) {
                widths.updateWidth(j, row[j]);
            }
        }

        Colors.println(widths.topBorder(), Colors.GRAY_DARK);

        Colors.println(widths.formatHeaders(rows.get(0)), "");

        Colors.println(widths.separator(), Colors.GRAY_DARK);

        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            String category = row[row.length - 2];

            String color;
            switch (category) {
                case "function":
                    color = Colors.DARK_PURPLE;
                    break;
                case "struct":
                    color = Colors.SLATE_GRAY;
                    break;
                case "Variable":
                    color = Colors.WHITE;
                    break;
                case "Miembro":
                    color = Colors.GRAY_LIGHT;
                    break;
                default:
                    color = Colors.WHITE;
                    break;
            }

            Colors.println(widths.format(row, color), "");
        }

        Colors.println(widths.bottomBorder(), Colors.GRAY_DARK);
    }
}
