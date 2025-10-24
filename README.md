
# 🧠 Implementação Manual de Tabelas Hash em Java

**Disciplina:** Resolução de Problemas Estruturados em Computação  
**Curso:** Bacharelado em Sistemas de Informação — PUCPR  
**Professora:** Marina de Lara  
**Autores:**
- **Fernando Alonso Piroga da Silva** – Implementação da Função Hash 1 (Método da Multiplicação)
- **Jafte Carneiro Fagundes da Silva** – Coordenação, Estrutura Base, Relatórios e Main  
- **Renato Pestana Gouveia** – Implementação da Função Hash 2 (DJB2)

---

## 📘 1. Introdução

Este projeto acadêmico tem como objetivo **comparar a eficiência de duas funções hash** distintas, implementadas de forma **100% manual em Java**, **sem o uso de estruturas de dados prontas** da linguagem (como `ArrayList`, `LinkedList`, `HashMap`, etc.).

A aplicação foi construída sob os princípios da **Programação Orientada a Objetos (POO)**, aplicando **abstração, encapsulamento, herança e polimorfismo** para criar uma arquitetura modular, testável e extensível.

---

## 🧩 2. Estrutura do Projeto

```

projeto-hash/
├── data/
│   └── female_names.txt          # Arquivo de entrada com 5000 nomes
├── src/
│   ├── Main.java                 # Classe principal (menu, execução e relatório)
│   ├── Node.java                 # Nó da lista encadeada
│   ├── ListaEncadeada.java       # Implementação manual do encadeamento
│   ├── TabelaHash.java           # Classe abstrata (base comum)
│   ├── TabelaHashMetodo1.java    # Implementação de Fernando
│   ├── TabelaHashMetodo2.java    # Implementação de Renato
│   ├── LeitorArquivo.java        # Utilitário para leitura de arquivo
│   ├── MedidorPerformance.java   # Cronometragem de inserções/buscas
│   └── RelatorioComparativo.java # Relatório final comparativo
└── README.md

```

---

## ⚙️ 3. Configuração do Projeto

### Requisitos
- **Java JDK 17** ou superior
- Terminal de execução (CLI)
- Arquivo `female_names.txt` contendo uma lista de nomes (mínimo 1000 registros)

### Compilação e Execução

No terminal (a partir da raiz do projeto):

```bash
# Compilar todos os arquivos
javac src/*.java -d bin

# Executar o programa
java -cp bin Main
````

---

## 🧠 4. Descrição das Classes

### 🏗️ **Node.java**

Estrutura base de um nó para lista encadeada, contendo:

* `String chave`
* Ponteiro `Node proximo`

Usada para armazenar as chaves (nomes) e lidar com colisões.

---

### 🔗 **ListaEncadeada.java**

Implementação **manual** de uma lista encadeada simples:

* Métodos: `inserir(String)`, `buscar(String)`, `getTamanho()`
* Sem uso de `LinkedList` do Java
* Base para o **encadeamento (chaining)** da Tabela Hash

---

### 🧮 **TabelaHash.java (Abstrata)**

Classe **abstrata e polimórfica**, responsável por:

* Armazenar o vetor principal de **listas encadeadas**
* Controlar métricas: colisões, redimensionamentos, fator de carga
* Métodos:

    * `inserir(String chave)`
    * `buscar(String chave)`
    * `redimensionar()`
    * `getNumeroColisoes()`
    * `getNumeroRedimensionamentos()`
    * `getLoadFactor()`
    * `mostrarDistribuicao()`
* Método **abstrato** `calcularHash(String chave, int capacidade)` — sobrescrito nas subclasses

---

### ⚡ **TabelaHashMetodo1.java (Fernando)**

Implementa o **Método da Multiplicação (Knuth)**:

```java
int hash = chave.hashCode();
double A = 0.6180339887;
double prod = hash * A;
double frac = prod - Math.floor(prod);
return (int) (capacidade * frac);
```

---

### 🔥 **TabelaHashMetodo2.java (Renato)**

Implementa o **algoritmo DJB2**, eficiente e amplamente utilizado:

```java
long hash = 5381;
for (int i = 0; i < chave.length(); i++) {
    hash = ((hash << 5) + hash) + chave.charAt(i);
}
return (int) (Math.abs(hash) % capacidade);
```

---

### 📚 **LeitorArquivo.java**

Leitura manual de arquivo `.txt`:

* Usa `BufferedReader`
* Armazena os nomes em um vetor fixo (`String[]`)
* Não utiliza `ArrayList` ou coleções

---

### ⏱️ **MedidorPerformance.java**

Classe auxiliar para medir tempos de execução:

* `medirTempoInsercao(TabelaHash tabela, String[] chaves)`
* `medirTempoBusca(TabelaHash tabela, String[] chaves)`

Retorna o tempo em **milissegundos**.

---

### 📊 **RelatorioComparativo.java**

Gera o **relatório textual completo** com:

* Totais de colisões
* Redimensionamentos
* Tempo de inserção e busca
* Fator de carga final
* Conclusão comparativa automática

Exemplo de saída:

```
============================================================
  COMPARAÇÃO DE FUNÇÕES HASH - TDE 03 (PUCPR)
============================================================
Total de nomes: 5000

HASH 1 - Método da Multiplicação
------------------------------------------------------------
Colisões: 4892
Redimensionamentos: 7
Tempo inserção: 18 ms
Tempo busca: 2 ms
Fator de carga final: 1.22

HASH 2 - DJB2
------------------------------------------------------------
Colisões: 4756
Redimensionamentos: 7
Tempo inserção: 16 ms
Tempo busca: 1 ms
Fator de carga final: 1.22

============================================================
CONCLUSÃO:
DJB2 apresentou menos colisões.
============================================================
```

---

## 🧩 5. Conceitos Aplicados de POO

| Conceito                   | Aplicação no Projeto                                             |
| -------------------------- | ---------------------------------------------------------------- |
| **Encapsulamento**         | Todos os atributos são privados e acessados via métodos públicos |
| **Abstração**              | `TabelaHash` define a estrutura comum de todas as tabelas        |
| **Herança**                | `TabelaHashMetodo1` e `TabelaHashMetodo2` herdam de `TabelaHash` |
| **Polimorfismo**           | O método `calcularHash()` é sobrescrito em cada implementação    |
| **Composição**             | A Tabela Hash contém listas encadeadas compostas por `Node`      |
| **Tratamento de exceções** | Leitura de arquivo e redimensionamento com verificações seguras  |

---

## 🧮 6. Funcionamento Interno

### 🔹 Inserção

1. Calcula o índice da chave com `calcularHash()`
2. Verifica se já existe na lista encadeada
3. Insere no final da lista
4. Incrementa contadores e verifica **fator de carga**
5. Se necessário, chama `redimensionar()`

### 🔹 Colisão

* Implementada via **encadeamento**
* Cada posição da tabela contém uma **ListaEncadeada** manual

### 🔹 Redimensionamento

* Ocorre quando `tamanho / capacidade ≥ fatorCarga`
* Dobra a capacidade (`capacidade * 2`)
* Reinsere **todos os elementos (rehashing)**

---

## 📈 7. Métricas de Eficiência

Durante a execução, o programa mede:

| Métrica                  | Descrição                                        |
| ------------------------ | ------------------------------------------------ |
| **Colisões**             | Quantas inserções caíram em posições já ocupadas |
| **Redimensionamentos**   | Quantas vezes a tabela dobrou de tamanho         |
| **Tempo de Inserção**    | Tempo total para inserir todos os nomes          |
| **Tempo de Busca**       | Tempo para buscar 100 nomes aleatórios           |
| **Fator de Carga Final** | `tamanho / capacidade` após todas as inserções   |
| **Distribuição**         | Quantidade de chaves por posição da tabela       |

---

## 🧪 8. Testes e Validação

Os testes consistem em:

1. Inserir 5000 nomes na Tabela Hash 1 e 2.
2. Medir:

    * Colisões
    * Redimensionamentos
    * Tempo de inserção
3. Buscar 100 nomes aleatórios.
4. Gerar relatório final automático.

---

## ⚖️ 9. Critérios de Avaliação (PUCPR)

| Critério                           | Peso        | Implementado              |
| ---------------------------------- | ----------- | ------------------------- |
| **Funções hash distintas**         | Alto        | ✅                         |
| **Tratamento de colisões**         | Alto        | ✅ Encadeamento            |
| **Redimensionamento e rehashing**  | Crítico     | ✅ Automático              |
| **Testes de eficiência completos** | Obrigatório | ✅ Todos                   |
| **Orientação a Objetos**           | Fundamental | ✅ Padrões aplicados       |
| **Relatório completo (LaTeX)**     | Obrigatório | ✅ Geração de dados pronta |
| **Sem estruturas prontas do Java** | Crítico     | ✅ 100% manual             |

---

## 🧠 10. Conclusões e Aprendizados

O projeto proporcionou uma visão profunda sobre:

* Implementação interna de tabelas hash
* Cálculo e impacto das funções hash
* Importância da escolha do método de dispersão
* Diferença entre eficiência de hash functions
* Aplicação de **POO em estruturas de dados reais**

Além disso, o exercício reforça a importância de:

* **Projetar hierarquias de classes reutilizáveis**
* **Reduzir acoplamento entre componentes**
* **Garantir modularidade e extensibilidade**

---

## 📚 11. Referências

* Cormen, T. H. *Introduction to Algorithms*. MIT Press, 2009.
* Knuth, D. E. *The Art of Computer Programming, Vol. 3: Sorting and Searching*. Addison-Wesley, 1998.
* Java Language Specification, Oracle Docs (JLS 17).


---

## 🏁 12. Licença

Este projeto foi desenvolvido exclusivamente para fins acadêmicos na PUCPR.
É proibida a reprodução total ou parcial sem autorização dos autores.

---

> **“Entender como construir uma estrutura de dados é mais valioso do que apenas usá-la.”**
> — Donald Knuth
