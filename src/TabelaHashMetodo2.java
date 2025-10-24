/**
 * Implementação de Tabela Hash utilizando a função hash de Renato.
 */
public class TabelaHashMetodo2 extends TabelaHash {

    public TabelaHashMetodo2(int capacidadeInicial, double fatorCarga) {
        super(capacidadeInicial, fatorCarga);
    }

    @Override
    protected int calcularHash(String chave, int capacidade) {
        // ========================================
        // ATENÇÃO RENATO: IMPLEMENTAR AQUI
        // Sugestão: Função DJB2 ou FNV-1a
        // ========================================
        long hash = 5381;
        for (int i = 0; i < chave.length(); i++) {
            hash = ((hash << 5) + hash) + chave.charAt(i);
        }
        return (int) (Math.abs(hash) % capacidade);
    }
}
