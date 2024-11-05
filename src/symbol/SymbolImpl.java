package src.symbol;

import java.util.ArrayList;

/**
 * Implementación de un símbolo que representa una variable, función o estructura
 * en el programa. Almacena información sobre su ubicación, tipo y categoría.
 * 
 * @author etnicst
 */
public class SymbolImpl implements Symbol {
    
    private int dir;

    private int type;

    private String cat;

    private ArrayList<Integer> args;

    /**
     * Constructor que inicializa un nuevo símbolo.
     *
     * @param dir  Dirección o desplazamiento en memoria
     * @param type Identificador del tipo de datos
     * @param cat  Categoría del símbolo
     */
    public SymbolImpl(int dir, int type, String cat) {
        this.dir = dir;
        this.type = type;
        this.cat = cat;
        this.args = new ArrayList<>();
    }

    /**
     * Obtiene la dirección o desplazamiento en memoria del símbolo.
     *
     * @return Dirección en memoria
     */
    @Override
    public int getDir() {
        return dir;
    }

    /**
     * Obtiene el identificador del tipo de datos del símbolo.
     *
     * @return ID del tipo de datos
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * Obtiene la categoría del símbolo.
     *
     * @return Categoría del símbolo
     */
    @Override
    public String getCat() {
        return cat;
    }

    /**
     * Obtiene la lista de tipos de los argumentos.
     *
     * @return Lista de IDs de tipos de argumentos
     */
    @Override
    public ArrayList<Integer> getArgs() {
        return args;
    }

    /**
     * Añade un nuevo tipo de argumento a la lista.
     *
     * @param typeId ID del tipo de argumento a añadir
     */
    public void addArgument(int typeId) {
        args.add(typeId);
    }

    /**
     * Verifica si el símbolo representa una función.
     *
     * @return true si es una función, false en caso contrario
     */
    public boolean isFunction() {
        return "function".equals(cat);
    }
}