public class Main {

    public static void main(String[] args) {
        final int CAPACIDADE_INICIAL = 32;
        final double FATOR_CARGA = 0.75;

        String[] nomes = LeitorArquivo.lerNomes("data/female_names.txt", 6000);
        int total = nomes.length;

        if (total == 0) {
            System.out.println("Arquivo de nomes não encontrado ou vazio: " + CAMINHO_ARQUIVO);
            return;
        }

        TabelaHash tabela1 = new TabelaHashMetodo1(CAPACIDADE_INICIAL, FATOR_CARGA);
        TabelaHash tabela2 = new TabelaHashMetodo2(CAPACIDADE_INICIAL, FATOR_CARGA);

        long tIns1 = MedidorPerformance.medirTempoInsercao(tabela1, nomes);
        long tIns2 = MedidorPerformance.medirTempoInsercao(tabela2, nomes);

        // seleciona 100 nomes para busca
        int qtdBusca = Math.min(100, total);
        String[] amostra = new String[qtdBusca];
        for (int i = 0; i < qtdBusca; i++) amostra[i] = nomes[i];

        long tBus1 = MedidorPerformance.medirTempoBusca(tabela1, amostra);
        long tBus2 = MedidorPerformance.medirTempoBusca(tabela2, amostra);

        RelatorioComparativo.gerar("Método da Multiplicação (Fernando)", tabela1, tIns1, tBus1,
                "DJB2 (Renato)", tabela2, tIns2, tBus2, total);
    }
}
