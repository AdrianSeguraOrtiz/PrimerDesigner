package clases;

import java.util.ArrayList;
import java.util.HashMap;

public class Cebador {
    private String cebador;
    private String resultadoCompleto;

    public void compute(char[][] sec) {
        char[] secuenciaConsenso = new char[sec[0].length];
        double[] fiabilidad = new double[sec[0].length];
        calculaSecConsensoYFiabilidad(sec, secuenciaConsenso, fiabilidad);
        int[] posInPosFinTemp = calculaPosMaxFiabilidad(secuenciaConsenso, fiabilidad);
        int posInicial = posInPosFinTemp[0];
        int posFinal = posInPosFinTemp[1];
        int temperatura = posInPosFinTemp[2];
        String molde = String.valueOf(secuenciaConsenso).substring(posInicial, posFinal);
        cebador = calculaInvCom(molde);
        String hibridaciones = calculaHibridaciones(sec, posInicial, posFinal, molde);
        String StringFiabilidades = "";
        for (double v : fiabilidad) {
            StringFiabilidades += " " + v;
        }
        resultadoCompleto = "Secuencia consenso: 5'- " + String.valueOf(secuenciaConsenso) + " - 3' \n"
                + "Fiabilidades: " + StringFiabilidades + "\n"
                + "Molde Secuencia Consenso: 5'- " + molde + " - 3'" + "  Posición: " + posInicial + " - " + posFinal + "\n"
                + "Cebador universal: 5'- " + cebador + " -3'"
                + "   Longitud: " + (posFinal - posInicial) + "   Temperatura: " + temperatura + "ºC" + "\n"
                + "Hibridaciones del cebador en cada secuencia: \n"
                + hibridaciones;
    }

    private String calculaHibridaciones(char[][] sec, int posInicial, int posFinal, String molde) {
        String res = "";
        String green = "\033[32m";
        String rojo = "\033[31m";
        String negro = "\033[30m";
        for (int i = 0; i < sec.length; i++) {
            res = res + negro + "Sec " + (i + 1) + ": 5'- ";
            for (int j = posInicial; j < posFinal; j++) {
                if (sec[i][j] == molde.toCharArray()[j - posInicial]) {
                    res = res + green + sec[i][j];
                } else {
                    res = res + rojo + sec[i][j];
                }
            }
            res = res + negro + " -3' \n";
        }
        return res;
    }

    private String calculaInvCom(String molde) {
        String res = "";
        HashMap<Character, Character> complementaria = new HashMap<Character, Character>();
        complementaria.put('A', 'T');
        complementaria.put('T', 'A');
        complementaria.put('C', 'G');
        complementaria.put('G', 'C');
        for (int i = molde.length() - 1; i >= 0; i--) {
            res = res + complementaria.get(molde.substring(i, i + 1).toCharArray()[0]);
        }
        return res;
    }

    public int[] calculaPosMaxFiabilidad(char[] secuenciaConsenso, double[] fiabilidad) {
        int[] res = new int[3];
        double maxAlcanzado = 0;
        double valorZonaActual = 0;
        int tempActual = 0;
        HashMap<Character, Integer> temperaturas = new HashMap<Character, Integer>();
        temperaturas.clear();
        temperaturas.put('A', 2);
        temperaturas.put('T', 2);
        temperaturas.put('G', 4);
        temperaturas.put('C', 4);
        temperaturas.put('-', 0);
        boolean gap = false;
        boolean hibridacionTresPrimaCebador = true;
        int j = 0;
        for (int i = 0; i < fiabilidad.length; i++) {
            gap = false;
            hibridacionTresPrimaCebador = true;
            valorZonaActual = 0;
            tempActual = 0;
            j = 0;
            while (!gap && hibridacionTresPrimaCebador && j < 23 && tempActual < 60 && i < fiabilidad.length - 23) {
                if (secuenciaConsenso[i + j] == '-') {
                    gap = true;
                }
                if (j < 6 && fiabilidad[i + j] < 1) {
                    hibridacionTresPrimaCebador = false;
                }
                tempActual += temperaturas.get(secuenciaConsenso[i + j]);
                valorZonaActual += fiabilidad[i + j];
                j++;
                if (!gap && hibridacionTresPrimaCebador && valorZonaActual / j > maxAlcanzado && j > 17 && tempActual > 55) {
                    maxAlcanzado = valorZonaActual / j;
                    res[0] = i;
                    res[1] = i + j;
                    res[2] = tempActual;
                }
            }
        }
        if (res[1] == 0) {
            throw new RuntimeException("No es posible la construcción de un primer universal");
        }
        return res;
    }

    private void calculaSecConsensoYFiabilidad(char[][] sec, char[] secuenciaConsenso, double[] fiabilidad) {
        HashMap<Character, Double> mapa = new HashMap<Character, Double>();
        for (int col = 0; col < sec[0].length; col++) {
            mapa.clear();
            mapa.put('A', (double) 0);
            mapa.put('T', (double) 0);
            mapa.put('G', (double) 0);
            mapa.put('C', (double) 0);
            mapa.put('-', (double) 0);
            for (int fila = 0; fila < sec.length; fila++) {
                mapa.replace(sec[fila][col], mapa.get(sec[fila][col]) + 1);
                if (fila == sec.length - 1) {
                    double max = 0;
                    boolean empate = false;
                    ArrayList<Character> empatadas = new ArrayList<Character>();
                    for (char n : new char[]{'-', 'A', 'T', 'G', 'C'}) {
                        if (mapa.get(n) > max) {
                            empate = false;
                            empatadas.clear();
                            max = mapa.get(n);
                            secuenciaConsenso[col] = n;
                            fiabilidad[col] = max / sec.length;
                        } else if (mapa.get(n) == max) {
                            empate = true;
                            empatadas.add(n);
                        }
                    }
                    if (empate) {
                        for (char n : new char[]{'-', 'A', 'T', 'G', 'C'}) {
                            if (empatadas.contains(n)) {
                                secuenciaConsenso[col] = n;
                            }
                        }
                    }
                }
            }
        }
    }

    public String getResultadoCompleto() {
        return resultadoCompleto;
    }

    public String getCebador() {
        return cebador;
    }
}
