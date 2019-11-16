package es.ucm.vdm.logic;

import java.util.HashMap;

import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.utilities.Pair;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Sprite;

public class FontMapper {

    private static final FontMapper ourInstance = new FontMapper();
    public static FontMapper getInstance() {
        return ourInstance;
    }

    private Pixmap fontPixmap_;
    private HashMap<String, Pair> font_ = new HashMap<>();

    public Pair getFrameLocation(String c) {
        return (font_.get(c));
    }

    public Sprite getSprite(String c) {
        Sprite sprite = new Sprite(fontPixmap_, 7, 15);
        sprite.setFrameRow((int)font_.get(c).second());
        sprite.setFrameCol((int)font_.get(c).first());
        return sprite;
    }

    private FontMapper() {
        fontPixmap_ = PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.SCORE_FONT.ordinal()]);

        font_.put("A", new Pair(0, 0));
        font_.put("B", new Pair(1, 0));
        font_.put("C", new Pair(2, 0));
        font_.put("D", new Pair(3, 0));
        font_.put("E", new Pair(4, 0));
        font_.put("F", new Pair(5, 0));
        font_.put("G", new Pair(6, 0));
        font_.put("H", new Pair(7, 0));
        font_.put("I", new Pair(8, 0));
        font_.put("J", new Pair(9, 0));
        font_.put("K", new Pair(10, 0));
        font_.put("L", new Pair(11, 0));
        font_.put("M", new Pair(12, 0));
        font_.put("N", new Pair(13, 0));
        font_.put("O", new Pair(14, 0));
        font_.put("P", new Pair(0, 1));
        font_.put("Q", new Pair(1, 1));
        font_.put("R", new Pair(2, 1));
        font_.put("S", new Pair(3, 1));
        font_.put("T", new Pair(4, 1));
        font_.put("U", new Pair(5, 1));
        font_.put("V", new Pair(6, 1));
        font_.put("W", new Pair(7, 1));
        font_.put("X", new Pair(8, 1));
        font_.put("Y", new Pair(9, 1));
        font_.put("Z", new Pair(10, 1));
        font_.put("a", new Pair(11, 1));
        font_.put("b", new Pair(12, 1));
        font_.put("c", new Pair(13, 1));
        font_.put("d", new Pair(14, 1));
        font_.put("e", new Pair(0, 2));
        font_.put("f", new Pair(1, 2));
        font_.put("g", new Pair(2, 2));
        font_.put("h", new Pair(3, 2));
        font_.put("i", new Pair(4, 2));
        font_.put("j", new Pair(5, 2));
        font_.put("k", new Pair(6, 2));
        font_.put("l", new Pair(7, 2));
        font_.put("m", new Pair(8, 2));
        font_.put("n", new Pair(9, 2));
        font_.put("o", new Pair(10, 2));
        font_.put("p", new Pair(11, 2));
        font_.put("q", new Pair(12, 2));
        font_.put("r", new Pair(13, 2));
        font_.put("s", new Pair(14, 2));
        font_.put("t", new Pair(0, 3));
        font_.put("u", new Pair(1, 3));
        font_.put("v", new Pair(2, 3));
        font_.put("w", new Pair(3, 3));
        font_.put("x", new Pair(4, 3));
        font_.put("y", new Pair(5, 3));
        font_.put("z", new Pair(6, 3));
        font_.put("0", new Pair(7, 3));
        font_.put("1", new Pair(8, 3));
        font_.put("2", new Pair(9, 3));
        font_.put("3", new Pair(10, 3));
        font_.put("4", new Pair(11, 3));
        font_.put("5", new Pair(12, 3));
        font_.put("6", new Pair(13, 3));
        font_.put("7", new Pair(14, 3));
        font_.put("8", new Pair(0, 4));
        font_.put("9", new Pair(1, 4));
        font_.put(".", new Pair(2, 4));
        font_.put(",", new Pair(3, 4));
        font_.put(";", new Pair(4, 4));
        font_.put("?", new Pair(5, 4));
        font_.put("!", new Pair(6, 4));
        font_.put("-", new Pair(7, 4));
        font_.put("_", new Pair(8, 4));
        font_.put("~", new Pair(9, 4));
        font_.put("#", new Pair(10, 4));
        font_.put("\"", new Pair(11, 4));
        font_.put("'", new Pair(12, 4));
        font_.put("&", new Pair(13, 4));
        font_.put("(", new Pair(14, 4));
        font_.put(")", new Pair(0, 5));
        font_.put("[", new Pair(1, 5));
        font_.put("]", new Pair(2, 5));
        font_.put("|", new Pair(3, 5));
        font_.put("`", new Pair(4, 5));
        font_.put("\\", new Pair(5, 5));
        font_.put("/", new Pair(6, 5));
        font_.put("@", new Pair(7, 5));
        font_.put("+", new Pair(8, 5));
        font_.put("=", new Pair(9, 5));
        font_.put("*", new Pair(10, 5));
        font_.put("$", new Pair(11, 5));
        font_.put("<", new Pair(12, 5));
        font_.put(">", new Pair(13, 5));
        font_.put("%", new Pair(14, 5));
        font_.put(":", new Pair(0, 6));
        font_.put("^", new Pair(1, 6));
        font_.put(" ", new Pair(2, 6));

    }
}
