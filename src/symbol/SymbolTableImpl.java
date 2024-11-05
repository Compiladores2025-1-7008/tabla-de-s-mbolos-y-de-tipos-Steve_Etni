package src.symbol;

import java.util.*;
import src.type.TypeTable;

/**
 * Implementación de una tabla de símbolos que gestiona los identificadores
 * y sus atributos en un ámbito específico del programa.
 *
 * @author etnicst
 */
public class SymbolTableImpl implements SymbolTable {
    
    private Map<String, List<Symbol>> symbolsByName;

    private Map<String, Symbol> symbols;

    private SymbolTable parent;

    private int currentOffset;

    private TypeTable typeTable;

    /**
     * Constructor que inicializa una tabla de símbolos con una tabla de tipos
     * y una referencia opcional a una tabla padre.
     *
     * @param typeTable Tabla de tipos del compilador
     * @param parent    Tabla de símbolos padre (null si es ámbito global)
     */
    public SymbolTableImpl(TypeTable typeTable, SymbolTable parent) {
        this.symbolsByName = new HashMap<>();
        this.symbols = new LinkedHashMap<>();
        this.parent = parent;
        this.currentOffset = 0;
        this.typeTable = typeTable;
    }

    /**
     * Constructor simplificado que crea una tabla sin padre.
     *
     * @param typeTable Tabla de tipos del compilador
     */
    public SymbolTableImpl(TypeTable typeTable) {
        this(typeTable, null);
    }

    /**
     * Busca un símbolo por su identificador en esta tabla y en las tablas padre.
     *
     * @param id Identificador a buscar
     * @return Optional conteniendo el símbolo si se encuentra
     */
    @Override
    public Optional<Symbol> lookup(String id) {
        Symbol sym = symbols.get(id);
        if (sym != null) {
            return Optional.of(sym);
        }
        return parent != null ? parent.lookup(id) : Optional.empty();
    }

    /**
     * Inserta un nuevo símbolo en la tabla.
     *
     * @param id  Identificador del símbolo
     * @param sym Símbolo a insertar
     */
    @Override
    public void insert(String id, Symbol sym) {
        symbols.put(id, sym);
        currentOffset += calculateSize(sym);
    }

    /**
     * Obtiene todos los símbolos de la tabla.
     *
     * @return Mapa de símbolos
     */
    @Override
    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    /**
     * Calcula el tamaño en bytes que ocupa un símbolo.
     *
     * @param sym Símbolo del cual calcular el tamaño
     * @return Tamaño en bytes
     */
    private int calculateSize(Symbol sym) {
        int typeId = sym.getType();

        if (sym.getCat().equals("function")) {
            return 4;
        }

        return typeTable.getTam(typeId);
    }

    /**
     * Inserta una función en la tabla con soporte para sobrecarga.
     *
     * @param id         Nombre de la función
     * @param returnType Tipo de retorno
     * @param paramTypes Lista de tipos de parámetros
     */
    @Override
    public void insertFunction(String id, int returnType, List<Integer> paramTypes) {
        SymbolImpl sym = new SymbolImpl(currentOffset, returnType, "function");
        paramTypes.forEach(sym::addArgument);

        symbolsByName.computeIfAbsent(id, k -> new ArrayList<>()).add(sym);
        symbols.put(generateUniqueFunctionId(id, paramTypes), sym);
    }

    /**
     * Genera un identificador único para una función basado en su nombre y
     * parámetros.
     *
     * @param baseName   Nombre base de la función
     * @param paramTypes Lista de tipos de parámetros
     * @return Identificador único
     */
    private String generateUniqueFunctionId(String baseName, List<Integer> paramTypes) {
        StringBuilder sb = new StringBuilder(baseName);
        for (Integer param : paramTypes) {
            sb.append("_").append(param);
        }
        return sb.toString();
    }

    /**
     * Inserta una estructura en la tabla.
     *
     * @param id     Nombre de la estructura
     * @param typeId ID del tipo de la estructura
     */
    public void insertStruct(String id, int typeId) {
        SymbolImpl sym = new SymbolImpl(currentOffset, typeId, "struct");
        symbols.put(id, sym);
    }

    /**
     * Obtiene el desplazamiento actual de la memoria.
     *
     * @return Desplazamiento actual en bytes
     */
    @Override
    public int getCurrentOffset() {
        return currentOffset;
    }
}