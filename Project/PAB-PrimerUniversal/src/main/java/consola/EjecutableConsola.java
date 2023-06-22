package consola;

import clases.Cebador;
import clases.Lectura;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EjecutableConsola {
    public static void main(String[] args) {
        String nombreFicheroEntrada = args[0];
        String nombreFicheroSalida = "CebadorUniversal" + nombreFicheroEntrada + ".txt";
        Lectura l = new Lectura();
        char[][] secuencia = l.leeFichero(nombreFicheroEntrada);
        Cebador c = new Cebador();
        c.compute(secuencia);
        String res = c.getResultadoCompleto().replace("\033[32m", "").replace("\033[31m", "").replace("\033[30m", "");
        File file = new File(nombreFicheroSalida);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(res);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        System.out.println("El resultado se ha guardado en el fichero " + nombreFicheroSalida);
    }
}
