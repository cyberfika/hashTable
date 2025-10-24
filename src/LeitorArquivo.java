import java.io.*;

/**
 * Leitor simples de arquivo de texto, sem uso de coleções.
 * Armazena os nomes em um vetor fixo de tamanho máximo.
 */
public class LeitorArquivo {
    public static String[] lerNomes(String caminho, int maxNomes) {
        String[] nomes = new String[maxNomes];
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null && contador < maxNomes) {
                if (!linha.trim().isEmpty()) {
                    nomes[contador++] = linha.trim();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo '" + caminho + "': " + e.getMessage());
        }

        // redimensiona array exato
        String[] resultado = new String[contador];
        for (int i = 0; i < contador; i++) resultado[i] = nomes[i];
        return resultado;
    }
}
