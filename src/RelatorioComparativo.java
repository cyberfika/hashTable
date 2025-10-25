/**
 * Impressão de relatório comparativo no console.
 * Sem uso de coleções prontas (somente prints).
 */
public class RelatorioComparativo {

    public static void gerar(String func1, TabelaHash t1, double tempoIns1, double tempoBus1,
                             String func2, TabelaHash t2, double tempoIns2, double tempoBus2,
                             int totalNomes) {
        System.out.println("============================================================");
        System.out.println("    COMPARAÇÃO DE FUNÇÕES HASH - RA03 (PUCPR)");
        System.out.println("============================================================");
        System.out.println("CONFIGURAÇÃO:");
        System.out.println("  - Capacidade inicial: 32");
        System.out.println("  - Fator de carga: 0.75");
        System.out.println("  - Total de nomes inseridos: " + totalNomes);
        System.out.println("  - Método de colisão: Encadeamento (Lista Encadeada manual)\n");

        mostrar("TABELA HASH 1", func1, t1, tempoIns1, tempoBus1);
        mostrar("TABELA HASH 2", func2, t2, tempoIns2, tempoBus2);

        // NOVO: distribuição e clusterização
        int limite = Math.min(64, Math.min(t1.getCapacidade(), t2.getCapacidade()));

        System.out.println("============================================================");
        System.out.println("DISTRIBUIÇÃO DAS CHAVES (primeiras " + limite + " posições)");
        System.out.println("============================================================");
        imprimirDistribuicaoComparativa(t1, func1, t2, func2, limite);

        System.out.println("\n============================================================");
        System.out.println("ANÁLISE DE CLUSTERIZAÇÃO (Top 10 posições mais congestionadas)");
        System.out.println("============================================================");
        imprimirTopClusterizacao(t1, func1, 10);
        imprimirTopClusterizacao(t2, func2, 10);

        System.out.println("\n============================================================");
        System.out.println("CONCLUSÃO COMPARATIVA");
        System.out.println("============================================================");
        System.out.println("✓ MENOS colisões: " +
                (t1.getNumeroColisoes() <= t2.getNumeroColisoes() ? func1 : func2));
        System.out.println("✓ MENOS redimensionamentos: " +
                (t1.getNumeroRedimensionamentos() <= t2.getNumeroRedimensionamentos() ? func1 : func2));
        System.out.println();
    }


    private static void mostrar(String titulo, String nomeFuncao, TabelaHash tabela,
                                double tempoInsercao, double tempoBusca) {
        System.out.println("============================================================");
        System.out.println("RESULTADOS - " + titulo + " (" + nomeFuncao + ")");
        System.out.println("============================================================");
        System.out.printf("  Total de colisões: %d%n", tabela.getNumeroColisoes());
        System.out.printf("  Redimensionamentos: %d%n", tabela.getNumeroRedimensionamentos());
        System.out.printf("  Tempo de inserção: %.3f ms%n", tempoInsercao);
        System.out.printf("  Tempo de busca: %.3f ms%n", tempoBusca);
        System.out.printf("  Fator de carga final: %.2f%n%n", tabela.getLoadFactor());
    }

    /** Imprime distribuição comparativa lado a lado nas primeiras 'limite' posições. */
    private static void imprimirDistribuicaoComparativa(TabelaHash t1, String nome1,
                                                        TabelaHash t2, String nome2,
                                                        int limite) {
        int[] d1 = t1.getDistribuicaoArray();
        int[] d2 = t2.getDistribuicaoArray();

        System.out.printf("%-5s | %-6s | %-6s | %-24s | %-24s%n",
                "Pos", "Hash1", "Hash2", nome1, nome2);
        System.out.println("--------------------------------------------------------------------------");
        for (int i = 0; i < limite; i++) {
            String bar1 = barras(d1[i], 20);
            String bar2 = barras(d2[i], 20);
            System.out.printf("%-5d | %-6d | %-6d | %-24s | %-24s%n", i, d1[i], d2[i], bar1, bar2);
        }
    }

    /** Imprime top-N posições com maior clusterização (colisões por posição). */
    private static void imprimirTopClusterizacao(TabelaHash t, String nome, int topN) {
        int[] c = t.getClusterizacaoArray();
        // Como não podemos usar coleções, faremos seleção dos top-N por varredura repetida.
        System.out.println(nome + ":");
        for (int k = 0; k < topN; k++) {
            int maxIdx = -1, maxVal = -1;
            for (int i = 0; i < c.length; i++) {
                if (c[i] > maxVal) {
                    // Evitar reescolher o mesmo: marcamos como -2 depois de impresso
                    maxVal = c[i];
                    maxIdx = i;
                }
            }
            if (maxIdx < 0 || maxVal <= 0) break;
            System.out.printf("  Posição %d: %d colisões%n", maxIdx, maxVal);
            c[maxIdx] = -2; // marca como usado
        }
    }

    /** Barra ASCII simples com largura máxima. */
    private static String barras(int valor, int maxLargura) {
        if (valor <= 0) return "";
        int n = Math.min(valor, maxLargura);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append('█');
        return sb.toString();
    }
}
