package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Leitor de arquivo TXT (sem coleções prontas).
 * Armazena dados em vetor fixo e depois recorta no tamanho exato.
 */
public class LeitorArquivo {

    public static String[] lerNomes(String caminho, int maxNomes) {
        String[] nomes = new String[maxNomes];
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null && contador < maxNomes) {
                String trim = linha.trim();
                if (!trim.isEmpty()) {
                    nomes[contador++] = trim;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo '" + caminho + "': " + e.getMessage());
        }

        String[] resultado = new String[contador];
        for (int i = 0; i < contador; i++) resultado[i] = nomes[i];
        return resultado;
    }
}
