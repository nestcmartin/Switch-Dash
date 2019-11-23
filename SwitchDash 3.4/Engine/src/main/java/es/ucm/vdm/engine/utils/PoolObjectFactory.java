package es.ucm.vdm.engine.utils;

/**
 * Interfaz de factoría de objetos de tipo T.
 * @param <T> el tipo de los objetos a crear.
 */
public interface PoolObjectFactory<T> {

    /**
     * Crea un nuevo objeto de tipo T.
     * @return el objeto de tipo T creado.
     */
    public T createObject();

}