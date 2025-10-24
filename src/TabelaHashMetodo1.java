/**
 * Implementação de Tabela Hash utilizando a função hash de Fernando.
 */
public class TabelaHashMetodo1 extends TabelaHash {

    public TabelaHashMetodo1(int capacidadeInicial, double fatorCarga) {
        super(capacidadeInicial, fatorCarga);
    }

    @Override
    protected int calcularHash(String chave, int capacidade) {
        // ========================================
        // ATENÇÃO FERNANDO: IMPLEMENTAR AQUI
        // Sugestão: Método da Multiplicação (Knuth)
        // ========================================
        int hash = chave.hashCode();
        double A = 0.6180339887; // constante recomendada por Knuth
        double prod = hash * A;
        double frac = prod - Math.floor(prod);
        return (int) (capacidade * frac);
    }
}
