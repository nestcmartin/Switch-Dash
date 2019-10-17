package ucm.vdm.pcengine;

import java.awt.*;
import ucm.vdm.engine.Image;

public class PCImage implements Image {

    java.awt.Image image_;

    public int getWidth()
    {
        return image_.getWidth(null);
    }

    public int getHeight()
    {
        return image_.getHeight(null);
    }
}
