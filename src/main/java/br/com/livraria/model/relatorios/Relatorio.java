package br.com.livraria.model.relatorios; // Define que esta classe pertence ao pacote 'br.com.livraria.model.relatorios'.

import br.com.livraria.model.negocio.Compra; // Importa o molde 'Compra'.
import br.com.livraria.model.produtos.Livro; // Importa o molde 'Livro'.
import br.com.livraria.model.usuarios.Cliente; // Importa o molde 'Cliente'.
import java.time.LocalDateTime; // Importa a ferramenta para trabalhar com data e hora.
import java.time.format.DateTimeFormatter; // Importa a ferramenta para formatar a data.
import java.util.*; // Importa várias ferramentas úteis para trabalhar com listas, mapas, etc.

public class Relatorio { // Inicia a declaração do molde 'Relatorio'.
    
    private final List<Compra> todasAsCompras; // Guarda a lista de todas as compras do sistema.
    private final List<Cliente> todosOsClientes; // Guarda a lista de todos os clientes do sistema.
    private final List<Livro> catalogoDeLivros; // Guarda a lista de todos os livros do catálogo.
    private final LocalDateTime dataRelatorio; // Guarda a data e hora em que o relatório foi criado.

    public Relatorio(List<Compra> todasAsCompras, List<Cliente> todosOsClientes, List<Livro> catalogo) { // Construtor.
        this.todasAsCompras = todasAsCompras; // Guarda a lista de compras recebida.
        this.todosOsClientes = todosOsClientes; // Guarda a lista de clientes recebida.
        this.catalogoDeLivros = catalogo; // Guarda a lista de livros recebida.
        this.dataRelatorio = LocalDateTime.now(); // Pega a data e hora atuais do sistema.
    }

    /**
     * Método principal que gera e exibe todas as seções do relatório.
     */
    public void gerarRelatorioCompleto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Cria um formatador de data.
        System.out.println("\n##################################################################");
        System.out.println("#                   RELATÓRIO GERAL DA LIVRARIA                  #");
        System.out.println("#                 Data de Emissão: " + dataRelatorio.format(formatter) + "           #");
        System.out.println("##################################################################");

        exibirListaCompletaLivros(); // Chama o método para mostrar todos os livros.
        exibirListaCompletaClientes(); // Chama o método para mostrar todos os clientes.
        pegarRelatorioMaisVendidos(); // Chama o método para mostrar os livros mais vendidos.
        gerarRelatorioMelhoresCompradores(); // Chama o método para mostrar os melhores compradores.
    }

    private void exibirListaCompletaLivros() { // Método que mostra todos os livros do catálogo.
        System.out.println("\n--- Lista de Livros no Catálogo (" + catalogoDeLivros.size() + " livros) ---"); // Título com a contagem total de livros.
        if(catalogoDeLivros.isEmpty()){ // Se não houver livros...
            System.out.println("Nenhum livro cadastrado no sistema."); // Avisa.
        } else { // Se houver...
            for (Livro livro : catalogoDeLivros) { // Loop que passa por cada livro.
                System.out.println("- " + livro.getTitulo() + " | Autor: " + livro.getAutor() + " | Estoque: " + livro.getEstoque()); // Mostra os detalhes.
            }
        }
        System.out.println("-----------------------------------------------------"); // Linha final.
    }

    private void exibirListaCompletaClientes() { // Método que mostra todos os clientes cadastrados.
        System.out.println("\n--- Lista de Clientes Cadastrados (" + todosOsClientes.size() + " clientes) ---"); // Título com a contagem total de clientes.
        if (todosOsClientes.isEmpty()) { // Se não houver clientes...
            System.out.println("Nenhum cliente cadastrado no sistema."); // Avisa.
        } else { // Se houver...
            for (Cliente cliente : todosOsClientes) { // Loop que passa por cada cliente.
                System.out.println("- " + cliente.getNome() + " | Email: " + cliente.getEmail()); // Mostra os detalhes.
            }
        }
        System.out.println("---------------------------------------------------------"); // Linha final.
    }

    private void pegarRelatorioMaisVendidos() { // Método que gera e mostra o relatório de livros mais vendidos.
        System.out.println("\n--- Ranking de Livros Mais Vendidos ---"); // Título.
        if (todasAsCompras.isEmpty()) { // Verifica se a lista de compras está vazia.
            System.out.println("Nenhuma compra foi registrada ainda para gerar o ranking."); // Se estiver, mostra esta mensagem.
            System.out.println("---------------------------------------");
            return; // Encerra o método.
        }

        Map<Livro, Integer> contagemDeVendas = new HashMap<>(); // Cria um "mapa" para contar as vendas.
        for (Compra compra : todasAsCompras) { // Loop por todas as compras.
            for (Livro livro : compra.getItensComprados()) { // Loop pelos livros de cada compra.
                contagemDeVendas.put(livro, contagemDeVendas.getOrDefault(livro, 0) + 1); // Adiciona o livro ao mapa, incrementando sua contagem.
            }
        }
        
        Map<Livro, Integer> sortedMap = new LinkedHashMap<>(); // Cria um novo mapa que vai guardar os livros em ordem.
        contagemDeVendas.entrySet().stream() // Transforma o mapa de contagem em um fluxo de dados.
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // Ordena os dados pela quantidade vendida (do maior para o menor).
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue())); // Coloca os dados ordenados no novo mapa.
        
        int rank = 1; // Cria uma variável para mostrar a posição no ranking.
        for (Map.Entry<Livro, Integer> entry : sortedMap.entrySet()) { // Loop por cada livro no mapa ordenado.
            System.out.println(rank + ". " + entry.getKey().getTitulo() + " - " + entry.getValue() + " cópias vendidas."); // Mostra a posição, o título e o total de vendas.
            rank++; // Aumenta o número do ranking para o próximo livro.
        }
        System.out.println("---------------------------------------"); // Linha final.
    }
    
    private void gerarRelatorioMelhoresCompradores() { // Método que gera o relatório de clientes que mais compraram.
        System.out.println("\n--- Ranking de Clientes (por nº de compras) ---"); // Título.
        if (todosOsClientes.isEmpty() || todasAsCompras.isEmpty()) { // Verifica se há clientes ou compras.
            System.out.println("Não há dados suficientes para gerar o ranking."); // Se não houver, avisa.
            System.out.println("-------------------------------------------------");
            return; // Encerra o método.
        }

        List<Cliente> clientesOrdenados = new ArrayList<>(todosOsClientes); // Cria uma cópia da lista de clientes.
        clientesOrdenados.sort(Comparator.comparingInt(c -> c.getHistoricoCompras().size())); // Ordena a lista pelo número de compras.
        Collections.reverse(clientesOrdenados); // Inverte a lista para que os que têm mais compras fiquem no começo.

        int rank = 1; // Cria uma variável para o ranking.
        boolean algumClienteComprou = false; // Variável para saber se encontramos pelo menos um cliente com compras.
        for (Cliente cliente : clientesOrdenados) { // Loop por cada cliente na lista ordenada.
             if (cliente.getHistoricoCompras().size() > 0) { // Só mostra o cliente se ele tiver feito pelo menos uma compra.
                 System.out.println(rank + ". " + cliente.getNome() + " - " + cliente.getHistoricoCompras().size() + " compras."); // Mostra a posição, o nome e o total de compras.
                 rank++; // Aumenta o número do ranking.
                 algumClienteComprou = true; // Marca que encontramos um comprador.
             }
        }
        if (!algumClienteComprou) { // Se o loop terminar e nenhum cliente tiver comprado nada...
            System.out.println("Nenhum cliente realizou compras ainda."); // Avisa.
        }
         System.out.println("-------------------------------------------------"); // Linha final.
    }
}