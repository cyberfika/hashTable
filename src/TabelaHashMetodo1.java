/**
 * Implementação da Tabela Hash usando a função hash do Fernando.
 * Na entrega final, substituir o bloco de MODO_TESTE pela implementação oficial.
 */
public class TabelaHashMetodo1 extends TabelaHash {

    public TabelaHashMetodo1(int capacidadeInicial, double fatorCarga) {
        super(capacidadeInicial, fatorCarga);
    }

    @Override
    public String getNomeFuncaoHash() {
        if (Main.MODO_TESTE) return "TESTE - Hash Simples (Jafte)";
        return "PENDENTE - Fernando";
    }

    @Override
    protected int calcularHash(String chave, int capacidade) {
        if (Main.MODO_TESTE) {
            // ========================================
            // Hash temporário APENAS para testes locais.
            // NÃO ENTREGAR ASSIM.
            // ========================================
            int h = chave.length() * 31;
            return Math.abs(h % capacidade);
        }

        // ========================================
        // ATENÇÃO FERNANDO: IMPLEMENTAR AQUI
        // Sugestões:
        //  - Método da Multiplicação (Knuth)
        //  - Polynomial Rolling Hash
        // Exemplo (Multiplicação/Knuth):
        // int hash = chave.hashCode();
        // double A = 0.6180339887;
        // double prod = hash * A;
        // double frac = prod - Math.floor(prod);
        // return (int)(capacidade * frac);
        // ========================================
        throw new IllegalStateException(
                "ATENÇÃO FERNANDO: implementar a função hash (Multiplicação ou Polynomial Rolling Hash)."
        );
    }
}
