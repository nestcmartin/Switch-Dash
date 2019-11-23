package es.ucm.vdm.engine.utils;

import java.util.HashMap;

import es.ucm.vdm.engine.Pixmap;

/**
 * Gestor de recursos de imagen.
 * Clase basada en el patrón de diseño Singleton.
 */
public class PixmapManager {

    private HashMap<String, Pixmap> pixmaps_ = new HashMap<>();
    private static final PixmapManager ourInstance = new PixmapManager();

    /**
     * Constructora de clase.
     */
    private PixmapManager() {}

    /**
     * Permite acceder a la instancia única de la clase.
     * @return la instancia única de la clase.
     */
    public static PixmapManager getInstance() {
        return ourInstance;
    }

    /**
     * Añade al diccionario de imágenes un nuevo archivo de imagen.
     * @param filename el nombre y extensión del archivo de imagen (clave).
     * @param pixmap el objeto de tipo Pixmap asociado al nombre de archivo proporcionado (valor).
     */
    public void addPixmap(String filename, Pixmap pixmap) {
        pixmaps_.put(filename, pixmap);
    }

    /**
     * Recupera un objeto de tipo Pixmap del diccioanrio de audio.
     * @param filename el nombre y extensión del archivo de imagen (clave).
     * @return el objeto de tipo Pixmap asociado al nombre de archivo proporcionado (valor).
     */
    public Pixmap getPixmap(String filename) {
        return pixmaps_.get(filename);
    }
}
