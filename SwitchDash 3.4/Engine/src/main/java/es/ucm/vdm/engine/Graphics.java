package es.ucm.vdm.engine;

import es.ucm.vdm.engine.utilities.Rect;

public interface Graphics {

    public Pixmap newPixmap(String fileName);

    public void clear(int color);

    public void drawPixmap(Pixmap pixmap, int x, int y);

    public void drawPixmap(Pixmap pixmap, Rect src, int x, int y);

    public void drawPixmap(Pixmap pixmap, Rect src, Rect dst);

    public int getWidth();
    public int getHeight();

}
