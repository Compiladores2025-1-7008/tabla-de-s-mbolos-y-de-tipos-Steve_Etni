package src.type;
import java.util.*;

import src.main.Config;
import src.symbol.Symbol;
import src.symbol.SymbolImpl;
import src.symbol.SymbolTable;
import src.symbol.SymbolTableImpl;

/**
 * Implementación de la tabla de tipos que gestiona los diferentes tipos de datos
 * en el programa, incluyendo tipos primitivos, arrays y estructuras.
 *
 * @author etnicst
 * @author steve-quezada
 */
public class TypeTableImpl implements TypeTable {
    
    private List<TypeImpl> types;
    
    private Map<String, Integer> typeNameToId;

    /**
     * Constructor que inicializa la tabla de tipos y registra los tipos básicos.
     */
    public TypeTableImpl() {
        this.types = new ArrayList<>();
        this.typeNameToId = new LinkedHashMap<>();
        initializeBasicTypes();
    }

    /**
     * Inicializa los tipos básicos (int y float) en la tabla.
     */
    private void initializeBasicTypes() {
        while (types.size() <= Config.TypeIds.FIRST_STRUCT_TYPE) {
            types.add(null);
        }
        
        types.set(Config.TypeIds.INT, 
            new TypeImpl("int", (short)1, (short)Config.TypeSizes.INT_SIZE, -1, null));
        types.set(Config.TypeIds.FLOAT, 
            new TypeImpl("float", (short)1, (short)Config.TypeSizes.FLOAT_SIZE, -1, null));
        
        typeNameToId.put("int", Config.TypeIds.INT);
        typeNameToId.put("float", Config.TypeIds.FLOAT);
    }

    /**
     * Obtiene el tamaño en bytes de un tipo.
     *
     * @param id Identificador del tipo
     * @return Tamaño en bytes del tipo, 0 si el tipo no existe
     */
    @Override
    public int getTam(int id) {
        if (id < 0 || id >= types.size()) return 0;
        return types.get(id).getTam();
    }

    /**
     * Obtiene el número de elementos para tipos array.
     *
     * @param id Identificador del tipo
     * @return Número de elementos, 0 si el tipo no existe
     */
    @Override
    public int getItems(int id) {
        if (id < 0 || id >= types.size()) return 0;
        return types.get(id).getItems();
    }

    /**
     * Obtiene el nombre de un tipo.
     *
     * @param id Identificador del tipo
     * @return Nombre del tipo, cadena vacía si el tipo no existe
     */
    @Override
    public String getName(int id) {
        if (id < 0 || id >= types.size()) return "";
        return types.get(id).getName();
    }

    /**
     * Obtiene el identificador del tipo padre para tipos derivados.
     *
     * @param id Identificador del tipo
     * @return ID del tipo padre, -1 si no tiene padre o no existe
     */
    @Override
    public int getParenId(int id) {
        if (id < 0 || id >= types.size()) return -1;
        return types.get(id).getParenId();
    }

    /**
     * Obtiene la tabla de símbolos asociada a una estructura.
     *
     * @param id Identificador del tipo estructura
     * @return Tabla de símbolos de la estructura, null si no es una estructura
     */
    @Override
    public SymbolTable getParentStruct(int id) {
        if (id < 0 || id >= types.size()) return null;
        return types.get(id).getParentStruct();
    }

    /**
     * Obtiene un tipo por su identificador.
     * Devuelve un Optional que contiene el tipo si existe.
     *
     * @param id Identificador del tipo a buscar
     * @return Optional con el tipo si existe, Optional.empty() si no existe
     */
    @Override
    public Optional<Type> getType(int id) {
        if (id < 0 || id >= types.size() || types.get(id) == null) {
            return Optional.empty();
        }
        return Optional.of(types.get(id));
    }

    /**
     * Añade un nuevo tipo a la tabla con tamaño y padre especificados.
     * Si el tipo ya existe, retorna su ID existente.
     *
     * @param name Nombre del tipo a añadir
     * @param items Número de elementos para tipos array
     * @param parent ID del tipo padre para tipos derivados
     * @return ID del tipo añadido o existente
     */
    @Override
    public int addType(String name, int items, int parent) {
        if (typeNameToId.containsKey(name)) {
            return typeNameToId.get(name);
        }

        int baseSize = getBaseSize(name);
        TypeImpl type = new TypeImpl(name, (short)items, (short)(baseSize * items), parent, null);
        types.add(type);
        int id = types.size() - 1;
        typeNameToId.put(name, id);
        return id;
    }

    /**
     * Añade un nuevo tipo estructura a la tabla.
     * Útil para crear tipos estructurados con sus propios miembros.
     *
     * @param name Nombre de la estructura
     * @param parent Tabla de símbolos que contiene los miembros
     * @return ID del tipo estructura creado
     */
    @Override
    public int addType(String name, SymbolTable parent) {
        TypeImpl type = new TypeImpl(name, (short)1, (short)0, -1, parent);
        types.add(type);
        return types.size() - 1;
    }

    /**
     * Obtiene el tamaño base de un tipo primitivo.
     * Solo maneja int y float, retorna 0 para otros tipos.
     *
     * @param name Nombre del tipo
     * @return Tamaño en bytes del tipo primitivo
     */
    private int getBaseSize(String name) {
        if (Config.DefaultTypes.INT.equals(name)) {
            return Config.TypeSizes.INT_SIZE;
        } else if (Config.DefaultTypes.FLOAT.equals(name)) {
            return Config.TypeSizes.FLOAT_SIZE;
        }
        return 0;
    }

    /**
     * Crea un nuevo array con el tipo y tamaño especificados.
     *
     * @param baseTypeName Nombre del tipo base del array
     * @param size Tamaño del array
     * @return ID del tipo array creado, -1 si hay error
     */
    public int createArrayType(String baseTypeName, int size) {
        int baseTypeId = findTypeByName(baseTypeName);
        if (baseTypeId < 0) return -1;

        String arrayName = baseTypeName + "[" + size + "]";
        if (typeNameToId.containsKey(arrayName)) {
            return typeNameToId.get(arrayName);
        }

        int id = Config.TypeIds.FIRST_ARRAY_TYPE;
        while (id < Config.TypeIds.FIRST_STRUCT_TYPE && types.get(id) != null) {
            id++;
        }

        int baseSize = getTam(baseTypeId);
        TypeImpl type = new TypeImpl(arrayName, (short)size, 
                                   (short)(baseSize * size), 
                                   baseTypeId, null);
        
        while (types.size() <= id) {
            types.add(null);
        }
        types.set(id, type);
        typeNameToId.put(arrayName, id);
        return id;
    }

    /**
     * Crea un nuevo tipo registro/estructura.
     *
     * @param name Nombre del registro
     * @param parent Tabla de símbolos que contiene los campos
     * @return ID del tipo registro creado
     */
    public int createRegisterType(String name, SymbolTable parent) {
        return addType(name, parent);
    }

    /**
     * Busca un tipo por su nombre en la tabla de tipos.
     * Primero busca en el mapa de nombres a IDs para una búsqueda rápida.
     * Si no lo encuentra, realiza una búsqueda lineal en la lista de tipos.
     *
     * @param name Nombre del tipo a buscar
     * @return ID del tipo si se encuentra, -1 si no existe
     */
    @Override
    public int findTypeByName(String name) {
        
        if (typeNameToId.containsKey(name)) {
            return typeNameToId.get(name);
        }
        
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i) != null && name.equals(types.get(i).getName())) {
                typeNameToId.put(name, i);
                return i;
            }
        }
        return -1;
    }

    /**
     * Crea un tipo de array multidimensional.
     *
     * @param baseTypeName Nombre del tipo base
     * @param dimensions Lista de dimensiones del array
     * @return ID del tipo array creado
     */
    public int createMultiDimArrayType(String baseTypeName, List<Integer> dimensions) {
        int baseTypeId = findTypeByName(baseTypeName);
        if (baseTypeId < 0) {
            baseTypeId = addType(baseTypeName, 1, -1);
        }
        
        int totalSize = 1;
        StringBuilder arrayName = new StringBuilder(baseTypeName);
        for (Integer dim : dimensions) {
            totalSize *= dim;
            arrayName.append("[").append(dim).append("]");
        }
        
        return addType(arrayName.toString(), totalSize, baseTypeId);
    }

    /**
     * Crea un nuevo tipo estructura con sus campos.
     *
     * @param name Nombre de la estructura
     * @param fields Mapa de campos y sus tipos
     * @return ID del tipo estructura creado
     */
    public int createStructType(String name, Map<String, Symbol> fields) {
        
        Integer existingId = typeNameToId.get(name);
        if (existingId != null) {
            return existingId;
        }

        int id = Config.TypeIds.FIRST_STRUCT_TYPE;
        while (id < types.size() && types.get(id) != null) {
            id++;
        }

        SymbolTable structTable = new SymbolTableImpl(this);
        int offset = 0;

        Map<String, Symbol> orderedFields = new LinkedHashMap<>(fields);
        
        for (Map.Entry<String, Symbol> field : orderedFields.entrySet()) {
            Symbol fieldSymbol = new SymbolImpl(offset, field.getValue().getType(), "Miembro");
            structTable.insert(field.getKey(), fieldSymbol);
            offset += getTam(field.getValue().getType());
        }

        TypeImpl type = new TypeImpl(name, (short)1, (short)offset, -1, structTable);
        
        while (types.size() <= id) {
            types.add(null);
        }
        types.set(id, type);
        typeNameToId.put(name, id);
        
        return id;
    }

    /**
     * Obtiene el tamaño total de un tipo.
     *
     * @param typeId ID del tipo
     * @return Tamaño total en bytes
     */
    public int getTypeSize(int typeId) {
        if (typeId < 0 || typeId >= types.size()) return 0;
        Type type = types.get(typeId);
        return type.getTam();
    }

    /**
     * Reinicia la tabla de tipos a su estado inicial.
     * Mantiene solo los tipos básicos.
     */
    public void reset() {
        this.types.clear();
        this.typeNameToId.clear();
        initializeBasicTypes();
    }
}
