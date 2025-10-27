package hashtable;

/**
 * Implementação da Tabela Hash usando algoritmo DJB2.
 */
public class TabelaHashMetodo2 extends TabelaHash {

    public TabelaHashMetodo2(int capacidadeInicial, double fatorCarga) {
        super(capacidadeInicial, fatorCarga);
    }

    @Override
    public String getNomeFuncaoHash() {
        return "Método 2: DJB2 (Daniel J. Bernstein)";
    }

    @Override
    protected int calcularHash(String chave, int capacidade) {
        // Implementação do algoritmo DJB2

        /*
        * Os valores iniciais podem ser:
        * - 5381: É um número primo que Bernstein escolheu empiricamente por produzir boas distribuições
        * - 33: Foi escolhido porque permite otimização com shift de bits e produzindo boa dispersão
        */

        if (chave == null || chave.isEmpty()) {
            return 0;
        }

        long hash = 5381;

        for (int i = 0; i < chave.length(); i++) {
            char c = chave.charAt(i);
            // hash * 33 + c (usando shift de bits para otimização)
            hash = ((hash << 5) + hash) + c;
        }

        // Garante que o resultado seja positivo e dentro do range [0, capacidade-1]
        return (int)((hash & 0x7FFFFFFF) % capacidade);
    }
}
