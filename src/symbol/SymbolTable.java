package src.symbol;
import java.util.Optional;
import java.util.Map;
import java.util.List;

public interface SymbolTable {
    Optional<Symbol> lookup(String id);
    void insert(String id, Symbol sym);
    Map<String, Symbol> getSymbols();
    void insertFunction(String id, int returnType, List<Integer> paramTypes);
    int getCurrentOffset();
    void insertStruct(String name, int typeId);
}
