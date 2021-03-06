package com.barottomartin;

public class Properties extends java.util.Properties {

    private static Properties instance = null;

    private Properties() {
        setProperty("width", "80");
        setProperty("height", "60");
        setProperty("cellPixelSize", "10");
        setProperty("robotRadius", "5");
        setProperty("navigationStrategy", "Random");
    }

    public static Properties getInstance(){
        if (Properties.instance == null){
            Properties.instance = new Properties();
        }
        return Properties.instance;
    }
}
