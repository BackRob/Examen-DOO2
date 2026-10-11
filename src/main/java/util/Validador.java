package util;

public class Validador {

    // Comprueba que el texto no sea null ni esté vacío (o solo con espacios)
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    // Variante con largo máximo (útil para respetar el tamaño de las columnas en la BD)
    public static boolean esTextoValido(String texto, int largoMaximo) {
        return esTextoValido(texto) && texto.trim().length() <= largoMaximo;
    }

    // Solo revisa el formato: 7 u 8 números, guion, y un dígito o K
    public static boolean esFormatoRutValido(String rut) {
        return rut != null && rut.trim().matches("^\\d{7,8}-[\\dkK]$");
    }

    // Revisa formato + que el dígito verificador sea correcto (módulo 11)
    public static boolean esRutValido(String rut) {
        if (!esFormatoRutValido(rut)) return false;

        String[] partes = rut.trim().split("-");
        String cuerpo = partes[0];
        char dvIngresado = Character.toUpperCase(partes[1].charAt(0));

        int suma = 0;
        int multiplicador = 2;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * multiplicador;
            multiplicador = (multiplicador == 7) ? 2 : multiplicador + 1;
        }

        int resto = 11 - (suma % 11);
        char dvCalculado = (resto == 11) ? '0' : (resto == 10) ? 'K' : (char) ('0' + resto);

        return dvIngresado == dvCalculado;
    }
}