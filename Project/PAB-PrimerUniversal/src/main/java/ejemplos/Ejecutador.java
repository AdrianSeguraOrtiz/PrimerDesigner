package ejemplos;

import clases.Cebador;
import clases.Lectura;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ejecutador {
    public void ejecuta(String fichero) {
        String nombreFicheroSalida = "CebadorUniversal" + fichero + ".txt";
        Lectura l = new Lectura();
        char[][] secuencia = l.leeFichero(fichero);
        Cebador c = new Cebador();
        c.compute(secuencia);
        File file = new File(nombreFicheroSalida);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(c.getResultadoCompleto().replace("\033[32m", "").replace("\033[31m", "").replace("\033[30m", ""));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(c.getResultadoCompleto());
        System.out.println("El resultado se ha guardado en el fichero " + nombreFicheroSalida);
    }
}
