package Data;

import java.io.*;

/*
 * Controlador de Persistencia
 */


public class CtrlDatabase {

    private final DatabaseF IO = new DatabaseF();


    private CtrlDatabase() {
    }


    public static CtrlDatabase getInstance() {
        return CtrlDatabaseHolder.INSTANCE;
    }

    private static class CtrlDatabaseHolder {

        private static final CtrlDatabase INSTANCE = new CtrlDatabase();
    }

    public String readV() throws IOException {
        return IO.getRouteV();
    }

    public String readPre() throws IOException  {
        return IO.getRoutePre();
    }

    public String readR() throws IOException  {
        return IO.getRouteR();
    }

    public String readTest() throws IOException  {
        return IO.getRouteTest();
    }


}
