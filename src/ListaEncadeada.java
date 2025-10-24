/**
 * Implementação manual de lista encadeada simples (Singly Linked List).
 * Usada para tratamento de colisões (chaining).
 */
public class ListaEncadeada {
    private Node inicio;
    private int tamanho;

    public ListaEncadeada() {
        this.inicio = null;
        this.tamanho = 0;
    }

    /** Insere no final, evitando duplicatas. */
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

    /** Busca sequencial pela chave. */
    public boolean buscar(String chave) {
        Node atual = inicio;
        while (atual != null) {
            if (atual.getChave().equals(chave)) return true;
            atual = atual.getProximo();
        }
        return false;
    }

    public int getTamanho() {
        return tamanho;
    }

    /** Retorna o primeiro nó (para rehashing). */
    public Node getInicio() {
        return inicio;
    }
}
