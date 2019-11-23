package es.ucm.vdm.engine.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de objetos activos reutilizables.
 * @param <T> el tipo de los objetos a gestionar.
 */
public class Pool<T> {

    private final List<T> freeObjects_;
    private final PoolObjectFactory<T> factory_;
    private final int maxSize_;

    /**
     * Constructora de clase.
     * @param factory objeto encargado de crear nuevos objetos de tipo T.
     * @param maxSize número máximo de objetos que se van a gestionar.
     */
    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory_ = factory;
        this.maxSize_ = maxSize;
        this.freeObjects_ = new ArrayList<T>(maxSize_);
    }

    /**
     * Obtiene el primer objeto inactivo de entre todos los gestionados.
     * Si no hay objetos inactivos, se crea uno nuevo y se añade,
     * siempre y cuando no se haya superado la capacidad máxima.
     * @return una referencia al objeto T obtenido.
     */
    public T newObject() {
        T object = null;

        if (freeObjects_.isEmpty()) {
            object = factory_.createObject();
        }
        else {
            object = freeObjects_.remove(freeObjects_.size() - 1);
        }
        return object;
    }

    /**
     * Marca como inactivo el objeto especificado.
     * De forma efectiva, el objeto vuelve a estar disponible para el gestor.
     * @param object el objeto que debe desactivarse.
     */
    public void free(T object) {
        if (freeObjects_.size() < maxSize_) {
            freeObjects_.add(object);
        }
    }

}