package br.com.livraria.model.negocio; // Define que esta classe pertence ao pacote 'br.com.livraria.model.negocio'.

import br.com.livraria.model.produtos.Livro; // Importa o molde 'Livro'.
import br.com.livraria.model.usuarios.Cliente; // Importa o molde 'Cliente'.
import java.time.LocalDateTime; // Importa a ferramenta para trabalhar com data e hora.
import java.time.format.DateTimeFormatter; // Importa a ferramenta para formatar a data e hora em texto.
import java.util.List; // Importa a interface 'List'.

public class Compra { // Inicia a declaração do molde 'Compra'.
    private final Cliente cliente; // Guarda qual cliente fez a compra.
    private final List<Livro> listaItens; // Guarda a lista de livros que foram comprados.
    private final float valorTotal; // Guarda o valor total pago.
    private final LocalDateTime data; // Guarda a data e a hora exatas em que a compra foi feita.

    public Compra(Cliente cliente, List<Livro> itensComprados) { // Construtor, chamado ao criar uma nova compra.
        this.cliente = cliente; // Guarda o cliente recebido.
        this.listaItens = itensComprados; // Guarda a lista de itens recebida.
        this.data = LocalDateTime.now(); // Pega a data e hora atuais do sistema e guarda.
        this.valorTotal = calcularValorTotal(); // Chama o método para calcular o total e guarda o resultado.
    }
    
    private float calcularValorTotal() { // Método privado para calcular o total da compra.
        // Usa uma forma moderna do Java (stream) para somar o preço de cada livro na lista.
        return (float) listaItens.stream().mapToDouble(Livro::getPreco).sum();
    }
    
    public void imprimeNotaFiscal() { // Método para mostrar os detalhes da compra na tela.
        // Cria um formatador para que a data apareça no formato "dia/mês/ano hora:minuto:segundo".
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("\n--- NOTA FISCAL ---"); // Título.
        System.out.println("Cliente: " + cliente.getNome()); // Mostra o nome do cliente.
        System.out.println("Data: " + data.format(formatter)); // Mostra a data formatada.
        System.out.println("\nItens Comprados:"); // Título da lista de itens.
        for (Livro livro : listaItens) { // Loop que passa por cada livro comprado.
            // Mostra o título e o preço de cada livro.
            System.out.println("- " + livro.getTitulo() + " (R$ " + String.format("%.2f", livro.getPreco()) + ")");
        }
        System.out.println("\nValor Total: R$ " + String.format("%.2f", valorTotal)); // Mostra o valor total pago.
        System.out.println("-------------------"); // Linha final.
    }

    // Getters
    public Cliente getCliente() { return cliente; } // Devolve o cliente da compra.
    public List<Livro> getItensComprados() { return listaItens; } // Devolve a lista de livros comprados.
    public float getValorTotal() { return valorTotal; } // Devolve o valor total.

    @Override // Indica que estamos substituindo um método padrão.
    public String toString() { // Define como o objeto 'Compra' deve ser mostrado em texto (usado no histórico).
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Cria o formatador de data.
        // Monta e devolve um resumo da compra em uma única linha de texto.
        return "Compra de " + cliente.getNome() + " em " + data.format(formatter) +
               ", Valor: R$" + String.format("%.2f", valorTotal);
    }
}