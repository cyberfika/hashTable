public class MedidorPerformance {

    public static long medirTempoInsercao(TabelaHash tabela, String[] chaves) {
        long inicio = System.currentTimeMillis();
        for (String chave : chaves) {
            tabela.inserir(chave);
        }
        return System.currentTimeMillis() - inicio;
    }

    public static long medirTempoBusca(TabelaHash tabela, String[] chaves) {
        long inicio = System.currentTimeMillis();
        for (String chave : chaves) {
            tabela.buscar(chave);
        }
        return System.currentTimeMillis() - inicio;
    }
}
