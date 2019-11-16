package es.ucm.vdm.engine.utils;

public abstract class Random {

    public static int randomInt(int min, int max) {
        java.util.Random rnd = new java.util.Random();
        return rnd.nextInt((max - min) + 1) + min;
    }
}
