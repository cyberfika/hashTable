/**
 * Implementação manual de uma lista encadeada simples.
 * Utilizada para o tratamento de colisões (encadeamento).
 */
public class ListaEncadeada {
    private Node inicio;
    private int tamanho;

    public ListaEncadeada() {
        this.inicio = null;
        this.tamanho = 0;
    }

    /** Insere uma nova chave no final da lista, se ainda não existir. */
    public void inserir(String chave) {
        if (buscar(chave)) return;

        Node novo = new Node(chave);
        if (inicio == null) {
            inicio = novo;
        } else {
            Node atual = inicio;
            while (atual.getProximo() != null) {
                atual = atual.getProximo();
            }
            atual.setProximo(novo);
        }
        tamanho++;
    }

    /** Busca uma chave na lista encadeada. */
    public boolean buscar(String chave) {
        Node atual = inicio;
        while (atual != null) {
            if (atual.getChave().equals(chave)) return true;
            atual = atual.getProximo();
        }
        return false;
    }

    /** Retorna o tamanho atual da lista. */
    public int getTamanho() {
        return tamanho;
    }

    /** Retorna o primeiro nó (usado para rehashing). */
    public Node getInicio() {
        return inicio;
    }
}
