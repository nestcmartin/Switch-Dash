package es.ucm.vdm.engine.utils;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {

    private final List<T> freeObjects_;
    private final PoolObjectFactory<T> factory_;
    private final int maxSize_;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory_ = factory;
        this.maxSize_ = maxSize;
        this.freeObjects_ = new ArrayList<T>(maxSize_);
    }

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

    public void free(T object) {
        if (freeObjects_.size() < maxSize_) {
            freeObjects_.add(object);
        }
    }

}