package br.com.livraria.model.usuarios; // Define que esta classe pertence ao pacote 'br.com.livraria.model.usuarios'.

import br.com.livraria.model.produtos.Livro; // Importa o molde 'Livro'.
import java.util.List; // Importa a interface 'List'.

/**
 * Representa um Administrador, que herda as características de um Usuário.
 */
public class Administrador extends Usuario { // Inicia a declaração do molde 'Administrador', que é uma extensão de 'Usuario'.

    public Administrador(String nome, String email, String cpf) { // Construtor, chamado quando um novo admin é criado.
        super(nome, email, cpf); // Chama o construtor do 'Usuario' para guardar nome, email e cpf.
    }
    
    public void cadastrarLivro(List<Livro> catalogo, Livro livro) { // Método para o admin cadastrar um livro.
        Livro.cadastraLivro(catalogo, livro); // A tarefa é repassada para o método estático da classe 'Livro'.
    }
    
    public void removerLivro(List<Livro> catalogo, Livro livro) { // Método para o admin remover um livro.
        Livro.excluiLivro(catalogo, livro); // Repassa a tarefa para o método estático da classe 'Livro'.
    }

    public void atualizarEstoqueLivro(Livro livro, int novoEstoque) { // Método para o admin atualizar o estoque.
        Livro.atualizaLivro(livro, novoEstoque); // Repassa a tarefa para o método estático da classe 'Livro'.
    }
    
    @Override // Indica que estamos substituindo um método da classe pai.
    public void exibirDetalhes() { // Método para mostrar os detalhes específicos do admin.
        System.out.println("--- Detalhes do Administrador ---"); // Título.
        super.exibirDetalhes(); // Chama o método original do 'Usuario' para mostrar nome, email e cpf.
        System.out.println("Cargo: Gestor de Catálogo e Relatórios"); // Adiciona a informação do cargo.
        System.out.println("---------------------------------"); // Linha final.
    }
}