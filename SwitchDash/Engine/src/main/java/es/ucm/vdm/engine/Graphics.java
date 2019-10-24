package es.ucm.vdm.engine;

public interface Graphics {


    public Pixmap newPixmap(String fileName);

    public void clear(int color);

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcW, int srcH);
    public void drawPixmap(Pixmap pixmap, int x, int y);

    public int getWidth();
    public int getHeight();

}

