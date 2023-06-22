package clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lectura {
    public char[][] leeFichero(String fichero) {
        List<String> listaRes = new ArrayList<String>();
        String res_i = "";
        try {
            Scanner sc = new Scanner(new File(fichero));
            sc.nextLine();
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                if (linea.startsWith(">")) {
                    listaRes.add(res_i);
                    res_i = "";
                } else {
                    res_i += linea;
                }
            }
            listaRes.add(res_i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (listaRes.size() < 2) {
            throw new RuntimeException("El fichero introducido no es correcto");
        }
        char[][] res = new char[listaRes.size()][listaRes.get(0).length()];
        for (int i = 0; i < listaRes.size(); i++) {
            if (i < listaRes.size() - 1 && listaRes.get(i).length() != listaRes.get(i + 1).length()) {
                throw new RuntimeException("El fichero introducido tiene secuencias de distinta longitud");
            }
            for (int j = 0; j < listaRes.get(i).length(); j++) {
                res[i][j] = listaRes.get(i).charAt(j);
            }
        }
        return res;
    }
}
