package src.type;
import src.symbol.SymbolTable;

public interface Type {
    String getName();
    short getItems();
    short getTam();
    int getParenId();
    SymbolTable getParentStruct();
}
