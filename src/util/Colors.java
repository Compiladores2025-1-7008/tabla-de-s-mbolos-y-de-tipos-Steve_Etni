package src.util;

/**
 * Clase que se encarga de guardar <strong>las cadenas de 
 * formato</strong> para la terminal.
 *
 * @author Mindahrelfen
 * @author steve-quezada
 */
public class Colors {

    /**
     * Modifica el color de la fuente a negro.
     */
    public static final String BLACK = "\033[0;30m";

    /**
     * Modifica el color de la fuente a rojo.
     */
    public static final String RED = "\033[0;31m";

    /**
     * Modifica el color de la fuente a verde.
     */
    public static final String GREEN = "\033[0;32m";

    /**
     * Modifica el color de la fuente a amarillo.
     */
    public static final String YELLOW = "\033[0;33m";

    /**
     * Modifica el color de la fuente a azul.
     */
    public static final String BLUE = "\033[0;34m";

    /**
     * Modifica el color de la fuente a un tono azul.
     */
    public static final String BLUE_LIGHT = "\033[38;5;69m";

    /**
     * Modifica el color de la fuente a magenta.
     */
    public static final String MAGENTA = "\033[0;35m";

    /**
     * Modifica el color de la fuente a un tono magenta.
     */
    public static final String MAGENTA_LIGHT = "\033[38;5;177m";

    /**
     * Modifica el color de la fuente a cyan.
     */
    public static final String CYAN = "\033[0;36m";

    /**
     * Modifica el color de la fuente a púrpura.
     */
    public static final String PURPLE = "\033[0;35m";

    /**
     * Modifica el color de la fuente a blanco.
     */
    public static final String WHITE = "\033[0;37m";

    /**
     * Modifica el color de la fuente a rosa.
     */
    public static final String PINK = "\033[0;35m";

    /**
     * Modifica el color de la fuente a gris oscuro.
     */
    public static final String GRAY_DARK = "\033[38;5;240m";

    /**
     * Modifica el color de la fuente a gris claro.
     */
    public static final String GRAY_LIGHT = "\033[1;30m";

    /**
     * Modifica el color de la fuente a naranja.
     */
    public static final String ORANGE = "\033[38;5;208m";

    /**
     * Modifica el color de la fuente a teal.
     */
    public static final String TEAL = "\033[38;5;30m";

    /**
     * Modifica el color de la fuente a marrón.
     */
    public static final String BROWN = "\033[38;5;94m";

    /**
     * Modifica el color de la fuente a verde lima.
     */
    public static final String LIME = "\033[38;5;154m";

    /**
     * Modifica el color de la fuente a dorado.
     */
    public static final String GOLD = "\033[38;5;220m";

    /**
     * Modifica el color de la fuente a lavanda.
     */
    public static final String LAVENDER = "\033[38;5;183m";

    /**
     * Modifica el color de la fuente a turquesa.
     */
    public static final String TURQUOISE = "\033[38;5;45m";

    /**
     * Modifica el color de la fuente a coral.
     */
    public static final String CORAL = "\033[38;5;209m";

    /**
     * Modifica el color de la fuente a oliva.
     */
    public static final String OLIVE = "\033[38;5;100m";

    /**
     * Modifica el color de la fuente a beige.
     */
    public static final String BEIGE = "\033[38;5;230m";

    /**
     * Modifica el color de la fuente a índigo.
     */
    public static final String INDIGO = "\033[38;5;54m";

    /**
     * Modifica el color de la fuente a salmón.
     */
    public static final String SALMON = "\033[38;5;216m";

    /**
     * Modifica el color de la fuente a menta.
     */
    public static final String MINT = "\033[38;5;121m";

    /**
     * Modifica el color de la fuente a esmeralda.
     */
    public static final String EMERALD = "\033[38;5;48m";

    /**
     * Modifica el color de la fuente a chocolate.
     */
    public static final String CHOCOLATE = "\033[38;5;166m";

    /**
     * Modifica el color de la fuente a ciruela.
     */
    public static final String PLUM = "\033[38;5;96m";

    /**
     * Modifica el color de la fuente a aguamarina.
     */
    public static final String AQUAMARINE = "\033[38;5;86m";

    /**
     * Modifica el color de la fuente a azul marino.
     */
    public static final String NAVY = "\033[38;5;17m";

    /**
     * Modifica el color de la fuente a esmeralda brillante.
     */
    public static final String BRIGHT_EMERALD = "\033[38;5;10m";

    /**
     * Modifica el color de la fuente a gris acero.
     */
    public static final String STEEL_GRAY = "\033[38;5;102m";

    /**
     * Modifica el color de la fuente a marrón arena.
     */
    public static final String SAND = "\033[38;5;137m";

    /**
     * Modifica el color de la fuente a lavanda claro.
     */
    public static final String LIGHT_LAVENDER = "\033[38;5;189m";

    /**
     * Modifica el color de la fuente a verde bosque.
     */
    public static final String FOREST_GREEN = "\033[38;5;28m";

    /**
     * Modifica el color de la fuente a azul cielo.
     */
    public static final String SKY_BLUE = "\033[38;5;117m";

    /**
     * Modifica el color de la fuente a morado oscuro.
     */
    public static final String DARK_PURPLE = "\033[38;5;91m";

    /**
     * Modifica el color de la fuente a rosa claro.
     */
    public static final String LIGHT_PINK = "\033[38;5;217m";

    /**
     * Modifica el color de la fuente a azul hielo.
     */
    public static final String ICE_BLUE = "\033[38;5;123m";

    /**
     * Modifica el color de la fuente a gris pizarra.
     */
    public static final String SLATE_GRAY = "\033[38;5;66m";

    /**
     * Modifica el color de la fuente a verde musgo.
     */
    public static final String MOSS_GREEN = "\033[38;5;58m";

    /**
     * Modifica el color de la fuente a oro viejo.
     */
    public static final String OLD_GOLD = "\033[38;5;136m";

    /**
     * Elimina todos los formatos de la fuente, incluyendo el color.
     */
    public static final String RESTORE = "\033[0m";

    /**
     * Muestra a la fuente con un colo mas intenso.
     */
    public static final String HIGH_INTENSITY = "\033[1m";

    /**
     * Muestra a la fuente con un colo menos intenso.
     */
    public static final String LOW_INTENSITY = "\033[2m";

    /**
     * Agrega el formato italics a la fuente.
     */
    public static final String ITALICS = "\033[3m";

    /**
     * Subraya a la fuente.
     */
    public static final String UNDERLINE = "\033[4m";

    /**
     * Hace parpadear a la fuente.
     */
    public static final String BLINK = "\033[5m";

    /**
     * Hace parpadear muy rapido a la fuente.
     */
    public static final String FAST_BLINK = "\033[6m";

    /**
     * Intercambia el color de la fuente con el del fondo.
     */
    public static final String REVERSE = "\033[7m";

    /**
     * Vuelve invisible a la fuente.
     */
    public static final String INVISIBLE_TEXT = "\033[8m";

    /**
     * Modifica el color de fondo a negro.
     */
    public static final String BGD_BLACK = "\033[0;40m";

    /**
     * Modifica el color de fondo a rojo.
     */
    public static final String BGD_RED = "\033[0;41m";

    /**
     * Modifica el color de fondo a verde.
     */
    public static final String BGD_GREEN = "\033[0;42m";

    /**
     * Modifica el color de fondo a amarillo.
     */
    public static final String BGD_YELLOW = "\033[0;43m";

    /**
     * Modifica el color de fondo a azul.
     */
    public static final String BGD_BLUE = "\033[0;44m";

    /**
     * Modifica el color de fondo a magenta.
     */
    public static final String BGD_MAGENTA = "\033[0;45m";

    /**
     * Modifica el color de fondo a cyan.
     */
    public static final String BGD_CYAN = "\033[0;46m";

    /**
     * Modifica el color de fondo a blanco.
     */
    public static final String BGD_WHITE = "\033[0;47m";

    /**
     * Modifica el color de fondo a gris.
     */
    public static final String BGD_GRAY = "\033[0;100m";

    /**
     * Imprime la cadena <code>s</code> con el formato dado. 
     * Imprime con retorno de carro.
     *
     * @param s      Cadena a imprimir.
     * @param format Formato a aplicar a dicha cadena.
     */
    public static final void println(Object s, String format) {
        System.out.println(format + s + RESTORE);
    }

    /**
     * Imprime la cadena <code>s</code> con el formato dado. 
     * Imprime sin retorno de carro.
     *
     * @param s      Cadena a imprimir.
     * @param format Formato a aplicar a dicha cadena.
     */
    public static final void print(Object s, String format) {
        System.out.print(format + s + RESTORE);
    }
}