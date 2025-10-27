package report;

import hashtable.TabelaHash;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/**
 * Classe responsável por exportar todos os dados do programa
 * em formato CSV, para posterior geração de gráficos e análises.
 *
 * Esta classe é independente e não altera o report.RelatorioComparativo.java.
 * Ela utiliza as tabelas hash e as métricas já calculadas no Main.
 *
 * Diretório padrão de saída: data/results/
 */
public class ExportadorCSV {

    private final String pastaResultados = "data/results/";

    /**
     * Construtor: garante que o diretório de saída exista.
     */
    public ExportadorCSV() {
        new File(pastaResultados).mkdirs();
    }

    /**
     * Exporta a distribuição de chaves (quantas chaves existem por posição).
     */
    public void exportarDistribuicao(TabelaHash tabela, String nomeArquivo) {
        String caminho = pastaResultados + nomeArquivo;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write("posicao,quantidade_chaves\n");
            for (int i = 0; i < tabela.getCapacidade(); i++) {
                int quantidade = tabela.getListaNaPosicao(i) == null
                        ? 0 : tabela.getListaNaPosicao(i).getTamanho();
                writer.write(i + "," + quantidade + "\n");
            }
            System.out.println("Distribuição exportada: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao exportar distribuição CSV: " + e.getMessage());
        }
    }

    /**
     * Exporta a clusterização (número de colisões/tamanho das listas por posição).
     */
    public void exportarClusterizacao(TabelaHash tabela, String nomeArquivo) {
        String caminho = pastaResultados + nomeArquivo;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write("posicao,tamanho_lista\n");
            for (int i = 0; i < tabela.getCapacidade(); i++) {
                int tamanhoLista = tabela.getListaNaPosicao(i) == null
                        ? 0 : tabela.getListaNaPosicao(i).getTamanho();
                writer.write(i + "," + tamanhoLista + "\n");
            }
            System.out.println("Clusterização exportada: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao exportar clusterização CSV: " + e.getMessage());
        }
    }

    /**
     * Exporta as métricas globais de comparação entre as funções hash.
     */
    public void exportarMetricasGlobais(
            TabelaHash tabela1, TabelaHash tabela2,
            double tempoInsercao1, double tempoBusca1,
            double tempoInsercao2, double tempoBusca2) {

        String caminho = pastaResultados + "hash_metrics.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write("funcao,colisoes,redimensionamentos,tempo_insercao_ms,tempo_busca_ms,fator_carga_final\n");

            writer.write(tabela1.getNomeFuncaoHash() + "," +
                    tabela1.getNumeroColisoes() + "," +
                    tabela1.getNumeroRedimensionamentos() + "," +
                    String.format("%.3f", tempoInsercao1) + "," +
                    String.format("%.3f", tempoBusca1) + "," +
                    String.format("%.2f", tabela1.getLoadFactor()) + "\n");

            writer.write(tabela2.getNomeFuncaoHash() + "," +
                    tabela2.getNumeroColisoes() + "," +
                    tabela2.getNumeroRedimensionamentos() + "," +
                    String.format("%.3f", tempoInsercao2) + "," +
                    String.format("%.3f", tempoBusca2) + "," +
                    String.format("%.2f", tabela2.getLoadFactor()) + "\n");

            System.out.println("Métricas globais exportadas: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao exportar métricas CSV: " + e.getMessage());
        }
    }

    /**
     * Exporta todos os CSVs de uma só vez (atalho).
     */
    public void exportarTudo(
            TabelaHash tabela1, TabelaHash tabela2,
            double tempoInsercao1, double tempoBusca1,
            double tempoInsercao2, double tempoBusca2) {

        exportarDistribuicao(tabela1, "distribuicao_hash1.csv");
        exportarDistribuicao(tabela2, "distribuicao_hash2.csv");

        exportarClusterizacao(tabela1, "clusterizacao_hash1.csv");
        exportarClusterizacao(tabela2, "clusterizacao_hash2.csv");

        exportarMetricasGlobais(tabela1, tabela2,
                tempoInsercao1, tempoBusca1,
                tempoInsercao2, tempoBusca2);
    }
}
