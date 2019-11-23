package es.ucm.vdm.engine.utils;

/**
 * Utilidad para generar números aleatorios.
 */
public abstract class Random {

    /**
     * Genera un número entero aleatorio dentro de un rango.
     * @param min el valor mínimo del rango (inclusivo).
     * @param max el valor máximo del rango (inclusivo).
     * @return el número entero aleatorio obtenido.
     */
    public static int randomInt(int min, int max) {
        java.util.Random rnd = new java.util.Random();
        return rnd.nextInt((max - min) + 1) + min;
    }
}
