/**
 * Implementação da Tabela Hash usando a função hash do Renato.
 * Na entrega final, substituir o bloco de MODO_TESTE pela implementação oficial.
 */
public class TabelaHashMetodo2 extends TabelaHash {

    public TabelaHashMetodo2(int capacidadeInicial, double fatorCarga) {
        super(capacidadeInicial, fatorCarga);
    }

    @Override
    public String getNomeFuncaoHash() {
        if (Main.MODO_TESTE) return "TESTE - Hash Simples 2 (Jafte)";
        return "PENDENTE - Renato";
    }

    @Override
    protected int calcularHash(String chave, int capacidade) {
        if (Main.MODO_TESTE) {
            // ========================================
            // Hash temporário APENAS para testes locais.
            // NÃO ENTREGAR ASSIM.
            // ========================================
            int h = chave.hashCode();
            return Math.abs(h % capacidade);
        }

        // ========================================
        // ATENÇÃO RENATO: IMPLEMENTAR AQUI
        // Sugestões:
        //  - DJB2
        //  - FNV-1a
        // Exemplo (DJB2):
        // long hash = 5381;
        // for (int i = 0; i < chave.length(); i++) {
        //     hash = ((hash << 5) + hash) + chave.charAt(i);
        // }
        // return (int)(Math.abs(hash) % capacidade);
        // ========================================
        throw new IllegalStateException(
                "ATENÇÃO RENATO: implementar a função hash (DJB2 ou FNV-1a)."
        );
    }
}
