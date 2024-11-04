package src.util;

import java.io.*;

/**
 * Clase que maneja la lectura de archivos de entrada para el procesamiento
 * del código fuente. Proporciona una interfaz simplificada para leer archivos
 * línea por línea.
 *
 * @author steve-quezada
 */
public class InputReader {

    private BufferedReader fileReader;

    /**
     * Constructor que inicializa el lector de archivos.
     * Crea un nuevo BufferedReader para el archivo especificado.
     *
     * @param filename La ruta del archivo a leer
     * @throws IOException Si ocurre un error al abrir el archivo
     */
    public InputReader(String filename) throws IOException {
        this.fileReader = new BufferedReader(new FileReader(filename));
    }

    /**
     * Lee una línea del archivo de entrada.
     * Retorna null cuando se alcanza el final del archivo.
     *
     * @return La siguiente línea del archivo como String
     *         o null si se llegó al final
     * @throws IOException Si ocurre un error durante la lectura
     */
    public String readLine() throws IOException {
        return fileReader.readLine();
    }

    /**
     * Cierra el archivo de entrada y libera los recursos asociados.
     *
     * @throws IOException Si ocurre un error al cerrar el archivo
     */
    public void close() throws IOException {
        fileReader.close();
    }
}