package hashtable;

import model.ListaEncadeada;
import model.Node;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe abstrata base para Tabelas Hash:
 * - Encapsula encadeamento (model.ListaEncadeada[])
 * - Redimensionamento e rehashing
 * - Métricas: colisões, redimensionamentos, fator de carga
 * - Sem uso de estruturas prontas do Java
 */
public abstract class TabelaHash {

    private ListaEncadeada[] tabela;
    private int capacidade;
    private int tamanho;
    private double fatorCarga;
    private int colisoes;
    private int redimensionamentos;

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

    /** Nome da função hash (para o relatório). */
    public abstract String getNomeFuncaoHash();

    /** Cada classe concreta define sua função hash. */
    protected abstract int calcularHash(String chave, int capacidade);

    /** Inserção com encadeamento e contagem de colisões. */
    public void inserir(String chave) {
        if (deveRedimensionar()) redimensionar();

        int indice = calcularHash(chave, capacidade);
        if (indice < 0 || indice >= capacidade) {
            throw new IllegalStateException("Índice inválido gerado pela função hash: " + indice);
        }

        ListaEncadeada lista = tabela[indice];

        if (lista.buscar(chave)) return; // chave já existe, não duplica

        if (lista.getTamanho() > 0) colisoes++; // inserir em posição ocupada => colisão
        lista.inserir(chave);
        tamanho++;
    }

    /** Busca por chave. */
    public boolean buscar(String chave) {
        int indice = calcularHash(chave, capacidade);
        if (indice < 0 || indice >= capacidade) return false;
        return tabela[indice].buscar(chave);
    }

    /** Verifica se precisa redimensionar. */
    protected boolean deveRedimensionar() {
        double load = (double) tamanho / capacidade;
        return load >= fatorCarga;
    }

    /** Dobra a capacidade e realiza rehashing de todos os elementos. */
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
                if (novoIndice < 0 || novoIndice >= novaCapacidade) {
                    throw new IllegalStateException("Índice inválido em rehashing: " + novoIndice);
                }
                novaTabela[novoIndice].inserir(atual.getChave());
                atual = atual.getProximo();
            }
        }

        this.tabela = novaTabela;
        this.capacidade = novaCapacidade;
        this.redimensionamentos++;
    }

    /** Fator de carga atual (tamanho/capacidade). */
    public double getLoadFactor() {
        return (double) tamanho / capacidade;
    }

    public int getNumeroColisoes() { return colisoes; }
    public int getNumeroRedimensionamentos() { return redimensionamentos; }
    public int getCapacidade() { return capacidade; }
    public int getTamanho() { return tamanho; }

    /**
     * Distribuição por posição (contagem de elementos por índice).
     * Útil para relatórios ASCII e análise de clusterização.
     */
    public int[] getDistribuicaoArray() {
        int[] dist = new int[capacidade];
        for (int i = 0; i < capacidade; i++) {
            dist[i] = tabela[i].getTamanho();
        }
        return dist;
    }

    /**
     * Retorna um vetor de colisões por posição (clusterização).
     * Definição: para cada índice i, colisões_i = max(0, tamanhoLista_i - 1).
     * Isso representa quantas inserções colidiram naquela posição.
     */
    public int[] getClusterizacaoArray() {
        int[] dist = getDistribuicaoArray();
        int[] cluster = new int[capacidade];
        for (int i = 0; i < capacidade; i++) {
            int s = dist[i];
            cluster[i] = (s > 0) ? (s - 1) : 0;
        }
        return cluster;
    }


    /** Histograma ASCII simples (pode ser colado no LaTeX). */
    public void mostrarDistribuicao() {
        for (int i = 0; i < capacidade; i++) {
            System.out.printf("%4d | %3d | ", i, tabela[i].getTamanho());
            for (int j = 0; j < tabela[i].getTamanho(); j++) System.out.print("█");
            System.out.println();
        }
    }

    // >>> ADIÇÃO PARA EXPORTAÇÃO CSV - INÍCIO

    /**
     * Exporta a distribuição de chaves (quantidade de elementos por posição da tabela)
     * para um arquivo CSV.
     */
    public void exportarDistribuicaoCSV(String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write("posicao,quantidade_chaves\n");
            for (int i = 0; i < tabela.length; i++) {
                int quantidade = (tabela[i] == null) ? 0 : tabela[i].getTamanho();
                writer.write(i + "," + quantidade + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao exportar distribuicao CSV: " + e.getMessage());
        }
    }

    /**
     * Exporta a clusterização (tamanho das listas por posição) para um arquivo CSV.
     */
    public void exportarClusterizacaoCSV(String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write("posicao,tamanho_lista\n");
            for (int i = 0; i < tabela.length; i++) {
                int tamanhoLista = (tabela[i] == null) ? 0 : tabela[i].getTamanho();
                writer.write(i + "," + tamanhoLista + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao exportar clusterizacao CSV: " + e.getMessage());
        }
    }

    /**
     * Exporta o histórico do fator de carga e capacidade.
     * Este método deve ser chamado durante as inserções, armazenando dados cumulativos.
     */
    public void exportarFatorCargaCSV(String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.write(tamanho + "," + getLoadFactor() + "," + capacidade + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao exportar fator de carga CSV: " + e.getMessage());
        }
    }

    // <<< FIM DA ADIÇÃO


    // >>> ADIÇÃO PARA EXPORTADORCSV - INÍCIO
    /**
     * Retorna a lista encadeada armazenada na posição indicada da tabela.
     * Método de apoio para exportação de dados.
     */
    public ListaEncadeada getListaNaPosicao(int indice) {
        if (indice < 0 || indice >= tabela.length) {
            return null;
        }
        return tabela[indice];
    }

    // <<< FIM DA ADIÇÃO


}
