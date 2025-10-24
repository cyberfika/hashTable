public class Main {

    // =========================================================
    // MODO DE TESTE
    // true  -> permite rodar o projeto com hashes simples (placeholder)
    // false -> exige as implementações de Fernando e Renato (lança exceção)
    // =========================================================
    public static final boolean MODO_TESTE = true;

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

        long tIns1 = MedidorPerformance.medirTempoInsercao(tabela1, nomes);
        long tIns2 = MedidorPerformance.medirTempoInsercao(tabela2, nomes);

        int qtdBusca = Math.min(100, total);
        String[] amostra = new String[qtdBusca];
        for (int i = 0; i < qtdBusca; i++) amostra[i] = nomes[i];

        long tBus1 = MedidorPerformance.medirTempoBusca(tabela1, amostra);
        long tBus2 = MedidorPerformance.medirTempoBusca(tabela2, amostra);

        if (MODO_TESTE) {
            System.out.println("\n[AVISO] Executando em MODO_TESTE: hashes simples de validação (NÃO ENTREGAR ASSIM).\n");
        }

        RelatorioComparativo.gerar(
                tabela1.getNomeFuncaoHash(), tabela1, tIns1, tBus1,
                tabela2.getNomeFuncaoHash(), tabela2, tIns2, tBus2,
                total
        );
    }
}
