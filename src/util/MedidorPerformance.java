package util;

import hashtable.TabelaHash;

/**
 * Utilitário simples para medir tempos de inserção e busca (com nanoTime).
 * Retorna tempo em milissegundos com três casas decimais (double).
 */
public class MedidorPerformance {

    public static double medirTempoInsercao(TabelaHash tabela, String[] chaves) {
        long inicio = System.nanoTime();
        for (String chave : chaves) {
            tabela.inserir(chave);
        }
        long fim = System.nanoTime();
        return (fim - inicio) / 1_000_000.0; // ms com casas decimais
    }

    public static double medirTempoBusca(TabelaHash tabela, String[] chaves) {
        long inicio = System.nanoTime();
        for (String chave : chaves) {
            tabela.buscar(chave);
        }
        long fim = System.nanoTime();
        return (fim - inicio) / 1_000_000.0; // ms com casas decimais
    }
}

