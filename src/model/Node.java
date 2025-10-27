package model;

/**
 * Representa um nó da lista encadeada (chave + ponteiro).
 */
public class Node {
    private String chave;
    private Node proximo;

    public Node(String chave) {
        this.chave = chave;
        this.proximo = null;
    }

    public String getChave() {
        return chave;
    }

    public Node getProximo() {
        return proximo;
    }

    public void setProximo(Node proximo) {
        this.proximo = proximo;
    }
}

