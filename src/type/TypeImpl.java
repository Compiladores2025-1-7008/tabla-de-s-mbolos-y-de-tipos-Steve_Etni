package src.type;

import src.symbol.SymbolTable;

/**
 * Implementación de un tipo de datos en el compilador.
 * Representa tanto tipos primitivos como estructurados.
 *
 * @author steve-quezada
 */
public class TypeImpl implements Type {

    private String name;

    private short items;

    private short tam;

    private int parentId;

    private SymbolTable parentStruct;

    /**
     * Constructor que inicializa un nuevo tipo con todos sus atributos.
     *
     * @param name         Nombre del tipo
     * @param items        Número de elementos (1 para no arrays)
     * @param tam          Tamaño en bytes del tipo
     * @param parentId     ID del tipo padre (-1 si no tiene)
     * @param parentStruct Tabla de símbolos para estructuras (null si no es estructura)
     */
    public TypeImpl(String name, short items, short tam, int parentId, SymbolTable parentStruct) {
        this.name = name;
        this.items = items;
        this.tam = tam;
        this.parentId = parentId;
        this.parentStruct = parentStruct;
    }

    /**
     * Obtiene el nombre del tipo.
     *
     * @return Nombre del tipo
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Obtiene el número de elementos para tipos array.
     *
     * @return Número de elementos, 1 para tipos no array
     */
    @Override
    public short getItems() {
        return items;
    }

    /**
     * Obtiene el tamaño total en bytes del tipo.
     *
     * @return Tamaño en bytes
     */
    @Override
    public short getTam() {
        return tam;
    }

    /**
     * Obtiene el ID del tipo padre para tipos derivados.
     *
     * @return ID del tipo padre, -1 si no tiene
     */
    @Override
    public int getParenId() {
        return parentId;
    }

    /**
     * Obtiene la tabla de símbolos asociada para tipos estructura.
     *
     * @return Tabla de símbolos de la estructura, null si no es estructura
     */
    @Override
    public SymbolTable getParentStruct() {
        return parentStruct;
    }
}