package es.ucm.vdm.engine;

/**
 * Interfaz del gestor de audio del motor de tecnología.
 */
public interface Audio {

    /**
     * Devuelve un objeto de tipo Sound.
     * @param filename el nombre y extensión del archivo de sonido fuente.
     * @return un objeto de tipo Sound.
     */
    Sound newSound(String filename);

}