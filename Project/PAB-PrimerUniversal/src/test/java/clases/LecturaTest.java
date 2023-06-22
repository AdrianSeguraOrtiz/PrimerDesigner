package clases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LecturaTest {
    @Test
    public void laLecturaDeUnFicheroCon1SecuenciaDaError() {
        Lectura l = new Lectura();
        assertThrows(RuntimeException.class, () -> l.leeFichero("Fichero1secuencia.fasta"));
    }

    @Test
    public void laLecturaDeUnFicheroSinCabecerasDaError() {
        Lectura l = new Lectura();
        assertThrows(RuntimeException.class, () -> l.leeFichero("FicheroSinCabeceras.fasta"));
    }

    @Test
    public void laLecturaDeUnGFicheroSinAlinearDaError() {
        Lectura l = new Lectura();
        assertThrows(RuntimeException.class, () -> l.leeFichero("FicheroSinAlinear.fasta"));
    }
}
