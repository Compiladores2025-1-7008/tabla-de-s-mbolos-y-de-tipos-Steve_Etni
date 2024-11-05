package src.type;
import java.util.Optional;

import src.symbol.SymbolTable;

public interface TypeTable {
    int getTam(int id);        
    int getItems(int id);
    String getName(int id);
    int getParenId(int id);
    SymbolTable getParentStruct(int id);
    Optional<Type> getType(int id);
    
    int addType(String name, int items, int parent);
    int addType(String name, SymbolTable parent);   
    int findTypeByName(String name);
}
