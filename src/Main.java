public class Main {
    public static void main(String[] args) {
        final int CAPACIDADE_INICIAL = 32;
        final double FATOR_CARGA = 0.75;
        final String CAMINHO_ARQUIVO = "data/female_names.txt";

        String[] nomes = LeitorArquivo.lerNomes(CAMINHO_ARQUIVO, 6000);
        int total = nomes.length;

        if (total == 0) {
            System.out.println("Arquivo de nomes não encontrado ou vazio: " + CAMINHO_ARQUIVO);
            return;
        }

        TabelaHash tabela1 = new TabelaHashMetodo1(CAPACIDADE_INICIAL, FATOR_CARGA);
        TabelaHash tabela2 = new TabelaHashMetodo2(CAPACIDADE_INICIAL, FATOR_CARGA);

        double tIns1 = MedidorPerformance.medirTempoInsercao(tabela1, nomes);
        double tIns2 = MedidorPerformance.medirTempoInsercao(tabela2, nomes);

        int qtdBusca = Math.min(100, total);
        String[] amostra = new String[qtdBusca];
        for (int i = 0; i < qtdBusca; i++) amostra[i] = nomes[i];

        double tBus1 = MedidorPerformance.medirTempoBusca(tabela1, amostra);
        double tBus2 = MedidorPerformance.medirTempoBusca(tabela2, amostra);

        RelatorioComparativo.gerar(
                tabela1.getNomeFuncaoHash(), tabela1, tIns1, tBus1,
                tabela2.getNomeFuncaoHash(), tabela2, tIns2, tBus2,
                total
        );

        // ============================================================
        // EXPORTAÇÃO DE RESULTADOS PARA CSV (USANDO NOVA CLASSE)
        // ============================================================
        ExportadorCSV exportador = new ExportadorCSV();
        exportador.exportarTudo(
                tabela1, tabela2,
                tIns1, tBus1,
                tIns2, tBus2
        );

    }
}
