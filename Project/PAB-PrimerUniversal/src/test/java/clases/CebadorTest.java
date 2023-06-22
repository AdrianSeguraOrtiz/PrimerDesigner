package clases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CebadorTest {

    @Test
    public void elCebadorDeUnFicheroConMuchosGapsDaError() {
        Lectura l = new Lectura();
        char[][] secuencia = l.leeFichero("FicheroMuchosGaps.fasta");
        Cebador c = new Cebador();
        assertThrows(RuntimeException.class, () -> c.compute(secuencia));
    }

    @Test
    public void elCebadorDeUnFicheroConTodoGapsMenos20NucleotidosDaLaComplementariaDeEllosMismos() {
        Lectura l = new Lectura();
        char[][] secuencia = l.leeFichero("TodoGapsMenos20NucleótidosAlineados.fasta");
        Cebador c = new Cebador();
        c.compute(secuencia);
        assertEquals("ACGCGTCATATATCTGCGT", c.getCebador());
    }
}
