/**
 * Impressão de relatório comparativo no console.
 * Sem uso de coleções prontas (somente prints).
 */
public class RelatorioComparativo {

    public static void gerar(String func1, TabelaHash t1, long tempoIns1, long tempoBus1,
                             String func2, TabelaHash t2, long tempoIns2, long tempoBus2,
                             int totalNomes) {

        System.out.println("============================================================");
        System.out.println("    COMPARAÇÃO DE FUNÇÕES HASH - TDE 03 (PUCPR)");
        System.out.println("============================================================");
        System.out.println("CONFIGURAÇÃO:");
        System.out.println("  - Capacidade inicial: 32");
        System.out.println("  - Fator de carga: 0.75");
        System.out.println("  - Total de nomes inseridos: " + totalNomes);
        System.out.println("  - Método de colisão: Encadeamento (Lista Encadeada manual)\n");

        mostrar("TABELA HASH 1", func1, t1, tempoIns1, tempoBus1);
        mostrar("TABELA HASH 2", func2, t2, tempoIns2, tempoBus2);

        System.out.println("============================================================");
        System.out.println("CONCLUSÃO COMPARATIVA");
        System.out.println("============================================================");
        System.out.println("✓ MENOS colisões: " +
                (t1.getNumeroColisoes() <= t2.getNumeroColisoes() ? func1 : func2));
        System.out.println("✓ MENOS redimensionamentos: " +
                (t1.getNumeroRedimensionamentos() <= t2.getNumeroRedimensionamentos() ? func1 : func2));
        System.out.println();
    }

    private static void mostrar(String titulo, String nomeFuncao, TabelaHash tabela,
                                long tempoInsercao, long tempoBusca) {
        System.out.println("============================================================");
        System.out.println("RESULTADOS - " + titulo + " (" + nomeFuncao + ")");
        System.out.println("============================================================");
        System.out.printf("  Total de colisões: %d%n", tabela.getNumeroColisoes());
        System.out.printf("  Redimensionamentos: %d%n", tabela.getNumeroRedimensionamentos());
        System.out.printf("  Tempo de inserção: %d ms%n", tempoInsercao);
        System.out.printf("  Tempo de busca: %d ms%n", tempoBusca);
        System.out.printf("  Fator de carga final: %.2f%n%n", tabela.getLoadFactor());
    }
}
