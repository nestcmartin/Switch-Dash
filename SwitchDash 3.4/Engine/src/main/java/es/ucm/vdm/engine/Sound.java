package es.ucm.vdm.engine;

/**
 * Interfaz del wrapper de objetos de tipo sonido.
 */
public interface Sound {

    /**
     * Reproduce un sonido.
     * @param looping indica si el sonido debe reproducirse en bucle.
     */
    public void play(boolean looping);

    /**
     * Detiene la reproducción del sonido.
     */
    public void stop();

    /**
     * Pausa la reproducción de un sonido.
     */
    public void pause();

    /**
     * Determina si un sonido se reproduce en bucle o no.
     * @param looping indica si el sonido debe reproducirse en bucle.
     */
    void setLooping(boolean looping);

    /**
     * Determina el volumen de reproducción del sonido.
     * @param volume el volumen de reproducción deseado [0.0f-1.0f].
     */
    void setVolume(float volume);

    /**
     * @return true si el sonido se está reproduciendo, false en caso contrario.
     */
    public boolean isPlaying();

    /**
     * @return true si el sonido no se está reproduciendo, false en caso contrario.
     */
    public boolean isStopped();

    /**
     * @return true si el sonido se está reproduciendo en bucle, false en caso contrario.
     */
    public boolean isLooping();

    /**
     * Libera los recursos utilizados por el objeto Sound.
     */
    public void dispose();
}