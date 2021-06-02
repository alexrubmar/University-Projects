package Data;

import java.io.*;



public class DatabaseF {

    public String getRouteV () throws IOException {
        String path = new java.io.File(".").getCanonicalPath();
        //String ruta = (path + "/EXE/JocsDeProves/ReglesValidades/");
        String ruta = (path + "/../EXE/JocsDeProves/ReglesValidades/");
        return ruta;
    }

    public String getRoutePre () throws IOException {
        String path = new java.io.File(".").getCanonicalPath();
        //String ruta = (path + "/EXE/JocsDeProves/DadesPreprocessadesTest/");
        String ruta = (path + "/../EXE/JocsDeProves/DadesPreprocessadesTest/");
        return ruta;
    }

    public String getRouteR () throws IOException {
        String path = new java.io.File(".").getCanonicalPath();
        //String ruta = (path + "/EXE/JocsDeProves/ReglesD'Associacio/");
        String ruta = (path + "/../EXE/JocsDeProves/ReglesD'Associacio/");
        return ruta;
    }

    public String getRouteTest() throws IOException {
        String path = new java.io.File(".").getCanonicalPath();
        //String ruta = (path + "/EXE/JocsDeProves/DadesPerTest/");
        String ruta = (path + "/../EXE/JocsDeProves/DadesPerTest/");
        return ruta;
    }
}
