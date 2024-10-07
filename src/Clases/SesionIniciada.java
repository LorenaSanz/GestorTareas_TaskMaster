package Clases;

public class SesionIniciada {
    private static String logeado;

    public static void logeadoDni(String dni) {
    	logeado = dni;
    }

    public static String getLogeado() {
        return logeado;
    }

    public static void logout() {
    	logeado = null;
    }

    public static boolean isLogeado() {
        return logeado != null;
    }
    
}

