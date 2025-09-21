package br.com.livraria.model.produtos; // Define que esta classe pertence ao pacote 'br.com.livraria.model.produtos'.

import java.util.List; // Importa a interface 'List'.
import java.util.Objects; // Importa uma ferramenta do Java para ajudar a comparar objetos.

public class Livro { // Inicia a declaração do molde 'Livro'.
    private String titulo; // Guarda o título do livro. 'private' significa que só pode ser acessado de dentro desta classe.
    private String autor; // Guarda o autor do livro.
    private float preco; // Guarda o preço do livro.
    private String categoria; // Guarda a categoria (ex: Fantasia, Ficção).
    private int estoque; // Guarda quantos exemplares deste livro estão disponíveis.

    public Livro(String titulo, String autor, double preco, String categoria, int estoque) { // Construtor, chamado ao criar um novo livro.
        this.titulo = titulo; // Guarda o título recebido no atributo 'titulo'.
        this.autor = autor; // Guarda o autor recebido no atributo 'autor'.
        this.preco = (float) preco; // Guarda o preço, convertendo de 'double' para 'float'.
        this.categoria = categoria; // Guarda a categoria.
        this.estoque = estoque; // Guarda a quantidade em estoque.
    }

    // --- Métodos de Gestão do Catálogo ---
    // 'static' significa que o método pertence à classe Livro em geral, e não a um livro específico.

    public static void cadastraLivro(List<Livro> catalogo, Livro livro) { // Método para adicionar um livro na lista do catálogo.
        catalogo.add(livro); // Adiciona o livro recebido à lista.
        System.out.println("Livro '" + livro.getTitulo() + "' cadastrado com sucesso."); // Mostra uma mensagem de confirmação.
    }
    
    public static void excluiLivro(List<Livro> catalogo, Livro livro) { // Método para remover um livro da lista.
        if(catalogo.remove(livro)) { // Tenta remover o livro da lista. Se conseguir, o resultado é 'true'.
            System.out.println("Livro '" + livro.getTitulo() + "' removido com sucesso."); // Se conseguiu, mostra mensagem de sucesso.
        } else { // Se não conseguiu encontrar o livro para remover...
            System.out.println("Livro não encontrado no catálogo."); // Mostra uma mensagem de erro.
        }
    }

    public static void atualizaLivro(Livro livro, int novoEstoque) { // Método para atualizar a quantidade em estoque.
        System.out.println("Estoque do livro '" + livro.getTitulo() + "' atualizado para " + novoEstoque + " unidades."); // Mostra uma mensagem informativa.
        livro.setEstoque(novoEstoque); // Chama o método 'setEstoque' do livro específico para alterar seu valor.
    }

    // --- Fim dos Métodos de Gestão ---

    public void darBaixaEstoque(int quantidadeComprada) { // Método para diminuir o estoque de um livro específico após a venda.
        if (this.estoque >= quantidadeComprada) { // Verifica se há estoque suficiente.
            this.estoque -= quantidadeComprada; // Subtrai a quantidade vendida do estoque atual.
        }
    }
    
    // Getters e Setters
    public String getTitulo() { return titulo; } // Devolve o título.
    public void setTitulo(String titulo) { this.titulo = titulo; } // Permite alterar o título.
    public String getAutor() { return autor; } // Devolve o autor.
    public void setAutor(String autor) { this.autor = autor; } // Permite alterar o autor.
    public float getPreco() { return preco; } // Devolve o preço.
    public void setPreco(float preco) { this.preco = preco; } // Permite alterar o preço.
    public String getCategoria() { return categoria; } // Devolve a categoria.
    public void setCategoria(String categoria) { this.categoria = categoria; } // Permite alterar a categoria.
    public int getEstoque() { return estoque; } // Devolve a quantidade em estoque.
    public void setEstoque(int estoque) { this.estoque = estoque; } // Permite alterar a quantidade em estoque.

    @Override // Indica que estamos substituindo um método padrão do Java.
    public String toString() { // Define como o objeto 'Livro' deve ser mostrado em formato de texto.
        return "Livro '" + titulo + "' por " + autor + // Monta o texto com título e autor.
                " (R$" + String.format("%.2f", preco) + // Adiciona o preço formatado com duas casas decimais.
                ") - Estoque: " + estoque; // Adiciona a informação do estoque.
    }
    
    @Override // Indica que estamos substituindo outro método padrão.
    public boolean equals(Object o) { // Define como o Java deve comparar dois livros para saber se são "iguais".
        if (this == o) return true; // Se forem o mesmo objeto na memória, são iguais.
        if (o == null || getClass() != o.getClass()) return false; // Se o outro objeto não for um Livro, são diferentes.
        Livro livro = (Livro) o; // Converte o objeto genérico 'o' para o tipo 'Livro'.
        // Consideramos dois livros iguais se o título e o autor forem os mesmos.
        return Objects.equals(titulo, livro.titulo) && Objects.equals(autor, livro.autor);
    }

    @Override // Indica que estamos substituindo outro método padrão.
    public int hashCode() { // Cria um "código de identidade" numérico para o livro.
        return Objects.hash(titulo, autor); // O código é gerado com base no título e no autor.
    }
}