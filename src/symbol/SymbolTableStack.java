package src.symbol;
import java.util.Optional;

public interface SymbolTableStack{
    void push(SymbolTable table);
    SymbolTable pop();
    Optional<SymbolTable> peek();
    Optional<SymbolTable> base();
    Optional<SymbolTable> lookup(String id);

}