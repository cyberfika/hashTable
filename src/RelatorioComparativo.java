public class RelatorioComparativo {

    public static void gerar(String func1, TabelaHash t1, long tempoIns1, long tempoBus1,
                             String func2, TabelaHash t2, long tempoIns2, long tempoBus2,
                             int totalNomes) {

        System.out.println("============================================================");
        System.out.println("  COMPARAÇÃO DE FUNÇÕES HASH - TDE 03 (PUCPR)");
        System.out.println("============================================================");
        System.out.println("Total de nomes: " + totalNomes + "\n");

        mostrar("HASH 1 - " + func1, t1, tempoIns1, tempoBus1);
        mostrar("HASH 2 - " + func2, t2, tempoIns2, tempoBus2);

        System.out.println("============================================================");
        System.out.println("CONCLUSÃO:");
        System.out.println((t1.getNumeroColisoes() < t2.getNumeroColisoes() ? func1 : func2)
                + " apresentou menos colisões.");
        System.out.println("============================================================");
    }

    private static void mostrar(String titulo, TabelaHash tabela, long tIns, long tBus) {
        System.out.println(titulo);
        System.out.println("------------------------------------------------------------");
        System.out.println("Colisões: " + tabela.getNumeroColisoes());
        System.out.println("Redimensionamentos: " + tabela.getNumeroRedimensionamentos());
        System.out.println("Tempo inserção: " + tIns + " ms");
        System.out.println("Tempo busca: " + tBus + " ms");
        System.out.printf("Fator de carga final: %.2f%n%n", tabela.getLoadFactor());
    }
}
