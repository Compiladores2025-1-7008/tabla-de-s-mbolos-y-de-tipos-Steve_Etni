package src.symbol;

import java.util.*;

/**
 * Implementación de pila de tablas de símbolos, gestiona la jerarquía de ámbitos.
 * Esta clase mantiene una pila donde la cima representa el ámbito actual y
 * la base representa el ámbito global.
 * 
 * @author etnicst
 */
public class SymbolTableStackImpl implements SymbolTableStack {

    private Stack<SymbolTable> stack;

    /**
     * Constructor que crea una nueva pila de tablas de símbolos vacía.
     */
    public SymbolTableStackImpl() {
        this.stack = new Stack<>();
    }

    /**
     * Empuja una nueva tabla de símbolos en la cima de la pila.
     *
     * @param table La tabla de símbolos a insertar en la pila
     */
    @Override
    public void push(SymbolTable table) {
        stack.push(table);
    }

    /**
     * Elimina y devuelve la tabla de símbolos en la cima de la pila.
     *
     * @return La tabla de símbolos eliminada, o null si la pila está vacía
     */
    @Override
    public SymbolTable pop() {
        if (stack.isEmpty())
            return null;
        return stack.pop();
    }

    /**
     * Devuelve la tabla de símbolos en la cima de la pila sin eliminarla.
     *
     * @return Un Optional conteniendo la tabla de símbolos en la cima,
     *         o vacío si la pila está vacía
     */
    @Override
    public Optional<SymbolTable> peek() {
        if (stack.isEmpty())
            return Optional.empty();
        return Optional.of(stack.peek());
    }

    /**
     * Devuelve la tabla de símbolos en la base de la pila (ámbito global).
     *
     * @return Un Optional conteniendo la tabla de símbolos en la base,
     *         o vacío si la pila está vacía
     */
    @Override
    public Optional<SymbolTable> base() {
        if (stack.isEmpty())
            return Optional.empty();
        return Optional.of(stack.firstElement());
    }

    /**
     * Busca un símbolo por identificador en el ámbito actual y en el ámbito global. 
     * Implementa las reglas de ámbito donde las declaraciones locales ocultan las
     * declaraciones globales.
     *
     * @param id El identificador a buscar
     * @return Un Optional conteniendo la tabla de símbolos con el identificador,
     *         o vacío si no se encontró
     */
    @Override
    public Optional<SymbolTable> lookup(String id) {
        if (stack.isEmpty())
            return Optional.empty();

        Optional<Symbol> symbol = stack.peek().lookup(id);
        if (symbol.isPresent()) {
            return Optional.of(stack.peek());
        }

        symbol = stack.firstElement().lookup(id);
        if (symbol.isPresent()) {
            return Optional.of(stack.firstElement());
        }

        return Optional.empty();
    }
}