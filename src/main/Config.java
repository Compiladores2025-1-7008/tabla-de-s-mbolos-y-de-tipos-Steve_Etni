package src.main;

/**
 * Clase de configuración que define constantes relacionadas con
 * los tipos de datos del compilador.
 * 
 * @author etnicstv
 * @author steve-quezada
 */
public class Config {

    /**
     * Clase interna que define los identificadores numéricos
     * para los diferentes tipos de datos en el sistema.
     */
    public static class TypeIds {

        public static final int INT = 0;

        public static final int FLOAT = 1;

        public static final int VOID = 2;

        public static final int FIRST_ARRAY_TYPE = 4;

        public static final int FIRST_STRUCT_TYPE = 8;
    }

    /**
     * Clase interna que define los tamaños en bytes de los tipos 
     * primitivos del sistema.
     */
    public static class TypeSizes {
        public static final int INT_SIZE = 4;

        public static final int FLOAT_SIZE = 4;

        public static final int POINTER_SIZE = 4;
    }

    /**
     * Clase interna que define los nombres de los tipos primitivos 
     * predeterminados del sistema.
     */
    public static class DefaultTypes {

        public static final String INT = "int";

        public static final String FLOAT = "float";

        public static final String VOID = "void";
    }
}