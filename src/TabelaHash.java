    /**
     * Classe abstrata base para Tabelas Hash:
     * - Encapsula encadeamento (ListaEncadeada[])
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

        /** Histograma ASCII simples (pode ser colado no LaTeX). */
        public void mostrarDistribuicao() {
            for (int i = 0; i < capacidade; i++) {
                System.out.printf("%4d | %3d | ", i, tabela[i].getTamanho());
                for (int j = 0; j < tabela[i].getTamanho(); j++) System.out.print("█");
                System.out.println();
            }
        }
    }
