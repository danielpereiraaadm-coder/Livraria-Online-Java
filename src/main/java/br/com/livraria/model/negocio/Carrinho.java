package br.com.livraria.model.negocio; // Define que esta classe pertence ao pacote 'br.com.livraria.model.negocio'.

import br.com.livraria.model.produtos.Livro; // Importa o molde 'Livro'.
import java.util.ArrayList; // Importa a ferramenta para criar listas.
import java.util.List; // Importa a interface 'List'.

public class Carrinho { // Inicia a declaração do molde 'Carrinho'.
    private final List<Livro> listaItens; // Guarda a lista de livros que foram colocados no carrinho.

    public Carrinho() { // Construtor, chamado ao criar um novo carrinho.
        this.listaItens = new ArrayList<>(); // Cria uma nova lista vazia para guardar os itens.
    }

    public void adicionarItem(Livro livro) { // Método para adicionar um livro na lista.
        this.listaItens.add(livro); // Usa o método 'add' da lista para incluir o livro.
    }

    public void removerItem(Livro livro) { // Método para remover um livro da lista.
        this.listaItens.remove(livro); // Usa o método 'remove' da lista para tirar o livro.
    }

    public float calcularTotal() { // Método que calcula o preço total dos itens no carrinho.
        float total = 0.0f; // Cria uma variável para somar o total, começando com zero.
        for (Livro item : listaItens) { // Loop que passa por cada livro dentro do carrinho.
            total += item.getPreco(); // Pega o preço do livro atual e soma ao 'total'.
        }
        return total; // Devolve o resultado final da soma.
    }
    
    public void limparCarrinho() { // Método para esvaziar o carrinho.
        this.listaItens.clear(); // Usa o método 'clear' da lista para remover todos os itens.
    }
    
    public List<Livro> retornaItens() { // Método que devolve a lista de itens.
        return listaItens; // Retorna a lista completa.
    }
    
    public void exibirCarrinho() { // Método para mostrar o conteúdo do carrinho na tela.
        if (listaItens.isEmpty()) { // Verifica se a lista de itens está vazia.
            System.out.println("O carrinho está vazio."); // Se estiver, mostra esta mensagem.
        } else { // Se não estiver vazio...
            System.out.println("\n--- Itens no Carrinho ---"); // Mostra um título.
            for (Livro livro : listaItens) { // Loop que passa por cada livro na lista.
                // Mostra o título e o preço formatado do livro.
                System.out.println("- " + livro.getTitulo() + " (R$ " + String.format("%.2f", livro.getPreco()) + ")");
            }
            System.out.println("Total: R$ " + String.format("%.2f", calcularTotal())); // Mostra o valor total a ser pago.
            System.out.println("-------------------------"); // Mostra uma linha final.
        }
    }
}