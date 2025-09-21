package br.com.livraria.model.usuarios; // Define que esta classe pertence ao pacote 'br.com.livraria.model.usuarios'.

import br.com.livraria.model.negocio.Carrinho; // Importa o molde 'Carrinho'.
import br.com.livraria.model.negocio.Compra; // Importa o molde 'Compra'.
import br.com.livraria.model.produtos.Livro; // Importa o molde 'Livro'.
import java.util.ArrayList; // Importa a ferramenta para criar listas.
import java.util.List; // Importa a interface 'List'.

/**
 * Representa um Cliente, que herda as características básicas de um Usuário.
 */
public class Cliente extends Usuario { // Inicia a declaração do molde 'Cliente', que é uma extensão de 'Usuario'.

    private final Carrinho carrinho; // Um cliente tem um carrinho de compras.
    private final List<Compra> historicoCompras; // Um cliente tem um histórico de compras.

    public Cliente(String nome, String email, String cpf) { // Construtor, chamado quando um novo cliente é criado.
        super(nome, email, cpf); // Primeiro, chama o construtor do 'Usuario' para guardar nome, email e cpf.
        this.carrinho = new Carrinho(); // Cria um novo carrinho vazio para este cliente.
        this.historicoCompras = new ArrayList<>(); // Cria uma nova lista vazia para o histórico de compras.
    }
    
    public void adicionarAoCarrinho(Livro livro, int quantidade) { // Método para o cliente adicionar um item ao carrinho.
        if (livro.getEstoque() >= quantidade) { // Verifica se a quantidade em estoque do livro é suficiente.
            for (int i = 0; i < quantidade; i++) { // Inicia um loop para adicionar o livro 'quantidade' vezes.
                carrinho.adicionarItem(livro); // Adiciona uma unidade do livro ao carrinho.
            }
            System.out.println(quantidade + "x '" + livro.getTitulo() + "' adicionado ao carrinho."); // Mostra uma mensagem de sucesso.
        } else { // Se o estoque não for suficiente...
            System.out.println("Estoque insuficiente para '" + livro.getTitulo() + "'. Disponível: " + livro.getEstoque()); // Mostra uma mensagem de erro.
        }
    }

    /**
     * Remove um item do carrinho com base no número da sua posição na lista.
     * @param numeroItem O número do item a ser removido (começando em 1).
     */
    public void removerDoCarrinho(int numeroItem) { // Método para o cliente remover um item pelo número.
        List<Livro> itens = carrinho.retornaItens(); // Pega a lista de livros que está no carrinho.
        if (numeroItem > 0 && numeroItem <= itens.size()) { // Verifica se o número digitado é válido.
            Livro livroRemovido = itens.remove(numeroItem - 1); // Remove o item da lista (usa numeroItem - 1 porque listas começam em 0).
            System.out.println("'" + livroRemovido.getTitulo() + "' removido do carrinho."); // Mostra uma mensagem de confirmação.
        } else { // Se o número não for válido...
            System.out.println("Número do item inválido."); // Mostra uma mensagem de erro.
        }
    }
    
    public Compra efetuarPagamento() { // Método para o cliente finalizar a compra.
        if (carrinho.retornaItens().isEmpty()) { // Verifica se o carrinho está vazio.
            System.out.println("Carrinho está vazio. Não é possível efetuar a compra."); // Se estiver, mostra uma mensagem de erro.
            return null; // E retorna 'null' para indicar que a compra falhou.
        }

        Compra novaCompra = new Compra(this, new ArrayList<>(carrinho.retornaItens())); // Cria um novo objeto 'Compra'.
        this.historicoCompras.add(novaCompra); // Adiciona esta nova compra ao histórico do cliente.
        
        for (Livro livro : novaCompra.getItensComprados()) { // Loop para cada livro que foi comprado.
            livro.darBaixaEstoque(1); // Diminui em 1 a quantidade em estoque daquele livro.
        }
        
        carrinho.limparCarrinho(); // Esvazia o carrinho do cliente.
        System.out.println("Compra finalizada com sucesso!"); // Mostra uma mensagem de sucesso.
        novaCompra.imprimeNotaFiscal(); // Chama o método para imprimir a nota fiscal da compra.
        return novaCompra; // Retorna o objeto da compra que foi realizada.
    }

    // Getters
    public Carrinho getCarrinho() { return carrinho; } // Devolve o carrinho do cliente.
    public List<Compra> getHistoricoCompras() { return historicoCompras; } // Devolve a lista de compras antigas.
    
    @Override // Indica que estamos substituindo um método que veio da classe pai ('Usuario').
    public void exibirDetalhes() { // Método para mostrar os detalhes específicos do cliente.
        System.out.println("--- Detalhes do Cliente ---");
        super.exibirDetalhes(); // Chama o método original do 'Usuario' para mostrar nome, email e cpf.
        System.out.println("Total de compras: " + historicoCompras.size()); // Mostra quantas compras o cliente já fez.
        System.out.println("---------------------------");
    }
}