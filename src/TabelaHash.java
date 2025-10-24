/**
 * Classe abstrata base para implementação de Tabelas Hash.
 * NÃO utiliza estruturas prontas do Java.
 * Implementa:
 *  - Encadeamento manual
 *  - Redimensionamento e rehashing
 *  - Métricas de desempenho
 */
public abstract class TabelaHash {

    protected ListaEncadeada[] tabela;
    protected int capacidade;
    protected int tamanho;
    protected double fatorCarga;
    protected int colisoes;
    protected int redimensionamentos;

    public TabelaHash(int capacidadeInicial, double fatorCarga) {
        this.capacidade = capacidadeInicial;
        this.fatorCarga = fatorCarga;
        this.tabela = new ListaEncadeada[capacidade];
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ListaEncadeada();
        }
        this.tamanho = 0;
        this.colisoes = 0;
        this.redimensionamentos = 0;
    }

    /** Método abstrato — cada classe concreta define sua função hash. */
    protected abstract int calcularHash(String chave, int capacidade);

    /** Inserção manual com tratamento de colisões. */
    public void inserir(String chave) {
        if (deveRedimensionar()) redimensionar();

        int indice = calcularHash(chave, capacidade);
        ListaEncadeada lista = tabela[indice];

        if (lista.buscar(chave)) return; // já existe

        if (lista.getTamanho() > 0) colisoes++;
        lista.inserir(chave);
        tamanho++;
    }

    /** Busca uma chave na tabela. */
    public boolean buscar(String chave) {
        int indice = calcularHash(chave, capacidade);
        return tabela[indice].buscar(chave);
    }

    /** Verifica se precisa redimensionar. */
    protected boolean deveRedimensionar() {
        double load = (double) tamanho / capacidade;
        return load >= fatorCarga;
    }

    /** Dobra a capacidade e realiza rehashing manual. */
    protected void redimensionar() {
        int novaCapacidade = capacidade * 2;
        ListaEncadeada[] novaTabela = new ListaEncadeada[novaCapacidade];
        for (int i = 0; i < novaCapacidade; i++) {
            novaTabela[i] = new ListaEncadeada();
        }

        for (int i = 0; i < capacidade; i++) {
            Node atual = tabela[i].getInicio();
            while (atual != null) {
                int novoIndice = calcularHash(atual.getChave(), novaCapacidade);
                novaTabela[novoIndice].inserir(atual.getChave());
                atual = atual.getProximo();
            }
        }

        this.tabela = novaTabela;
        this.capacidade = novaCapacidade;
        this.redimensionamentos++;
    }

    public int getNumeroColisoes() { return colisoes; }
    public int getNumeroRedimensionamentos() { return redimensionamentos; }
    public int getCapacidade() { return capacidade; }
    public int getTamanho() { return tamanho; }

    /** Retorna o fator de carga atual. */
    public double getLoadFactor() {
        return (double) tamanho / capacidade;
    }

    /** Mostra uma distribuição simples (para relatório ASCII). */
    public void mostrarDistribuicao() {
        for (int i = 0; i < capacidade; i++) {
            System.out.printf("%4d | %2d | ", i, tabela[i].getTamanho());
            for (int j = 0; j < tabela[i].getTamanho(); j++) System.out.print("█");
            System.out.println();
        }
    }
}
