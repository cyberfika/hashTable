/**
 * Implementação da Tabela Hash usando algoritmo Polynomial Rolling Hash
 */
public class TabelaHashMetodo1 extends TabelaHash {

    public TabelaHashMetodo1(int capacidadeInicial, double fatorCarga) {
        super(capacidadeInicial, fatorCarga);
    }

    @Override
    public String getNomeFuncaoHash() { return "Método 1: Polynomial Rolling Hash"; }

    @Override
    protected int calcularHash(String chave, int capacidade) {
        // pode ser 31 ou 53
        final int p = 31;
        // número primo grande para evitar overflow
        final int M = 1_000_000_009;

        long hash = 0;
        // p^0 inicialmente
        long pPow = 1;

        for (int i = 0; i < chave.length(); i++) {
            char c = chave.charAt(i);

            int valor = (c - 'a' + 1);

            // soma ponderada
            hash = (hash + valor * pPow) % M;

            // atualiza próxima potência
            pPow = (pPow * p) % M;
        }

        // retorna o valor ajustado ao tamanho da tabela
        return (int)(hash % capacidade);
    }
}
