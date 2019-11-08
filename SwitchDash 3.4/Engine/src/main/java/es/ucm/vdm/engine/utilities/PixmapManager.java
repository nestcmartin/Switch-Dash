package es.ucm.vdm.engine.utilities;

import java.util.HashMap;

import es.ucm.vdm.engine.Pixmap;

public class PixmapManager {

    private HashMap<String, Pixmap> pixmaps_ = new HashMap<>();

    private static final PixmapManager ourInstance = new PixmapManager();

    public static PixmapManager getInstance() {
        return ourInstance;
    }

    private PixmapManager() {
    }

    public void addPixmap(String filename, Pixmap pixmap) {
        pixmaps_.put(filename, pixmap);
    }

    public Pixmap getPixmap(String filename) {
        return pixmaps_.get(filename);
    }
}
