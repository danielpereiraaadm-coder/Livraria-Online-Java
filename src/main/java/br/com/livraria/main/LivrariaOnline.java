package br.com.livraria.main; // Define que esta classe pertence ao pacote 'br.com.livraria.main'.

import br.com.livraria.model.negocio.Compra; // Importa a classe 'Compra' para poder usá-la aqui.
import br.com.livraria.model.produtos.Livro; // Importa a classe 'Livro'.
import br.com.livraria.model.relatorios.Relatorio; // Importa a classe 'Relatorio'.
import br.com.livraria.model.usuarios.Administrador; // Importa a classe 'Administrador'.
import br.com.livraria.model.usuarios.Cliente; // Importa a classe 'Cliente'.
import br.com.livraria.model.usuarios.Usuario; // Importa a classe 'Usuario'.
import java.util.ArrayList; // Importa a ferramenta para criar listas que podem crescer de tamanho.
import java.util.InputMismatchException; // Importa a ferramenta para lidar com erros de digitação.
import java.util.List; // Importa a interface 'List', que é o modelo para listas.
import java.util.Scanner; // Importa a ferramenta para ler o que o usuário digita no teclado.
import java.util.stream.Collectors; // Importa ferramentas para manipular listas de forma mais avançada.

public class LivrariaOnline { // Inicia a declaração da classe principal do nosso programa.

    private static final Scanner scanner = new Scanner(System.in); // Cria um 'leitor de teclado' que ficará disponível para todo o programa.
    private static final List<Livro> catalogo = new ArrayList<>(); // Cria a lista que vai guardar todos os livros da loja.
    private static final List<Usuario> usuarios = new ArrayList<>(); // Cria a lista que vai guardar todos os usuários.
    private static final List<Compra> todasAsCompras = new ArrayList<>(); // Cria a lista para guardar o histórico de todas as vendas.

    public static void main(String[] args) { // Método principal, onde o programa começa a ser executado.
        // --- Dados iniciais para o sistema não começar vazio ---
        catalogo.add(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 59.90, "Fantasia", 10));
        catalogo.add(new Livro("Duna", "Frank Herbert", 45.50, "Ficção Científica", 5));
        usuarios.add(new Administrador("Admin", "admin@email.com", "999.888.777-66"));
        usuarios.add(new Cliente("Ana Silva", "ana@email.com", "111.222.333-44"));

        while (true) { // Inicia um loop infinito que mantém o programa rodando.
            System.out.println("\n===== BEM-VINDO À LIVRARIA ONLINE ====="); // Mostra o título do menu principal.
            System.out.println("1. Fazer Login"); // Mostra a primeira opção.
            System.out.println("2. Sair do Sistema"); // Mostra a segunda opção.
            System.out.print("Escolha uma opção: "); // Pede para o usuário digitar sua escolha.
            int escolha = lerOpcao(); // Chama o método que lê o número digitado pelo usuário.

            switch (escolha) { // Verifica qual foi a opção escolhida.
                case 1 -> { // Se o usuário digitou 1...
                    System.out.print("\nDigite o seu e-mail para login: "); // Pede para o usuário digitar o e-mail.
                    String email = scanner.nextLine(); // Lê o e-mail que o usuário digitou.
                    Usuario usuarioLogado = Usuario.login(usuarios, email); // Tenta fazer o login.

                    if (usuarioLogado instanceof Administrador admin) { // Se o login retornou um Administrador...
                        menuAdmin(admin); // ...chama o menu do administrador.
                    } else if (usuarioLogado instanceof Cliente cliente) { // Se o login retornou um Cliente...
                        menuCliente(cliente); // ...chama o menu do cliente.
                    }
                }
                case 2 -> { // Se o usuário digitou 2...
                    System.out.println("Obrigado por utilizar o sistema. Até logo!"); // Mostra uma mensagem de despedida.
                    scanner.close(); // Fecha o leitor de teclado.
                    return; // Encerra o programa.
                }
                default -> System.out.println("Opção inválida. Por favor, tente novamente."); // Se digitou qualquer outro número.
            }
        }
    }

    private static void menuAdmin(Administrador admin) { // Método que controla o menu do administrador.
        while (true) { // Loop infinito para manter o menu do admin rodando.
            System.out.println("\n--- PAINEL DO ADMINISTRADOR ---");
            System.out.println("1. Cadastrar Novo Livro");
            System.out.println("2. Excluir Livro");
            System.out.println("3. Atualizar Estoque de Livro");
            System.out.println("4. Cadastrar Novo Cliente");
            System.out.println("5. Cadastrar Novo Administrador");
            System.out.println("6. Listar todos os Livros");
            System.out.println("7. Gerar Relatórios");
            System.out.println("8. Logout");
            System.out.print("Escolha uma opção: ");
            int escolha = lerOpcao();

            switch (escolha) {
                case 1 -> {
                    System.out.print("Digite o título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Digite o autor: ");
                    String autor = scanner.nextLine();
                    System.out.print("Digite a categoria: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Digite o preço: ");
                    double preco = scanner.nextDouble();
                    System.out.print("Digite a quantidade em estoque: ");
                    int estoque = scanner.nextInt();
                    scanner.nextLine();
                    admin.cadastrarLivro(catalogo, new Livro(titulo, autor, preco, categoria, estoque));
                }
                case 2 -> {
                    if (catalogo.isEmpty()) {
                        System.out.println("Não há livros para excluir.");
                        break;
                    }
                    listarLivros();
                    System.out.print("Digite o número do livro que deseja excluir: ");
                    int numLivro = lerOpcao();
                    if (numLivro > 0 && numLivro <= catalogo.size()) {
                        Livro livroParaExcluir = catalogo.get(numLivro - 1);
                        admin.removerLivro(catalogo, livroParaExcluir);
                    } else {
                        System.out.println("Número do livro inválido.");
                    }
                }
                case 3 -> {
                    listarLivros();
                    System.out.print("Digite o número do livro para atualizar o estoque: ");
                    int numLivro = lerOpcao();
                    if (numLivro > 0 && numLivro <= catalogo.size()) {
                        System.out.print("Digite a nova quantidade em estoque: ");
                        int novoEstoque = lerOpcao();
                        admin.atualizarEstoqueLivro(catalogo.get(numLivro - 1), novoEstoque);
                    } else {
                        System.out.println("Número do livro inválido.");
                    }
                }
                case 4 -> {
                    System.out.print("Digite o nome do cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o e-mail do cliente: ");
                    String email = scanner.nextLine();
                    System.out.print("Digite o CPF do cliente: ");
                    String cpf = scanner.nextLine();
                    usuarios.add(new Cliente(nome, email, cpf));
                    System.out.println("Cliente '" + nome + "' cadastrado com sucesso!");
                }
                case 5 -> {
                    System.out.print("Digite o nome do administrador: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o e-mail do administrador: ");
                    String email = scanner.nextLine();
                    System.out.print("Digite o CPF do administrador: ");
                    String cpf = scanner.nextLine();
                    usuarios.add(new Administrador(nome, email, cpf));
                    System.out.println("Administrador '" + nome + "' cadastrado com sucesso!");
                }
                case 6 -> listarLivros();
                case 7 -> { // Se escolheu 7 (Gerar Relatórios)...
                    List<Cliente> clientes = usuarios.stream() // Cria uma lista apenas com os clientes.
                        .filter(u -> u instanceof Cliente)
                        .map(u -> (Cliente) u)
                        .collect(Collectors.toList());
                    Relatorio relatorio = new Relatorio(todasAsCompras, clientes, catalogo); // Cria o objeto de relatório, agora enviando também o catálogo de livros.
                    relatorio.gerarRelatorioCompleto(); // Chama o método que agora imprime TUDO.
                }
                case 8 -> {
                    admin.logOut();
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuCliente(Cliente cliente) { // Método que controla o menu do cliente.
        while (true) { // Loop infinito para manter o menu do cliente rodando.
            System.out.println("\n--- MENU DO CLIENTE ---");
            System.out.println("1. Listar Livros Disponíveis");
            System.out.println("2. Adicionar Livro ao Carrinho");
            System.out.println("3. Remover Livro do Carrinho"); // <-- NOVA OPÇÃO
            System.out.println("4. Ver Carrinho");
            System.out.println("5. Finalizar Compra");
            System.out.println("6. Ver Histórico de Compras");
            System.out.println("7. Logout"); // <-- Ordem ajustada
            System.out.print("Escolha uma opção: ");
            int escolha = lerOpcao(); // Lê a escolha.

            switch (escolha) {
                case 1 -> listarLivros();
                case 2 -> {
                    listarLivros();
                    System.out.print("Digite o número do livro que deseja adicionar: ");
                    int numLivro = lerOpcao();
                    if (numLivro > 0 && numLivro <= catalogo.size()) {
                        System.out.print("Digite a quantidade: ");
                        int quantidade = lerOpcao();
                        cliente.adicionarAoCarrinho(catalogo.get(numLivro - 1), quantidade);
                    } else {
                        System.out.println("Número do livro inválido.");
                    }
                }
                case 3 -> { // <-- LÓGICA DA NOVA OPÇÃO DE REMOVER
                    if (cliente.getCarrinho().retornaItens().isEmpty()) { // Verifica se o carrinho está vazio.
                        System.out.println("O carrinho já está vazio."); // Se estiver, avisa.
                        break; // E volta para o menu.
                    }
                    cliente.getCarrinho().exibirCarrinho(); // Mostra o carrinho com os itens numerados.
                    System.out.print("Digite o número do item que deseja remover: "); // Pede para o usuário escolher o item.
                    int numItem = lerOpcao(); // Lê o número do item.
                    cliente.removerDoCarrinho(numItem); // Chama o novo método para remover o item pelo número.
                }
                case 4 -> cliente.getCarrinho().exibirCarrinho(); // Agora a opção 4 é ver o carrinho.
                case 5 -> { // Agora a opção 5 é finalizar a compra.
                    Compra novaCompra = cliente.efetuarPagamento();
                    if (novaCompra != null) {
                        todasAsCompras.add(novaCompra);
                    }
                }
                case 6 -> { // Agora a opção 6 é ver o histórico.
                    System.out.println("\n--- Histórico de Compras de " + cliente.getNome() + " ---");
                    if (cliente.getHistoricoCompras().isEmpty()) {
                        System.out.println("Ainda não realizou nenhuma compra.");
                    } else {
                        cliente.getHistoricoCompras().forEach(System.out::println);
                    }
                }
                case 7 -> { // Agora a opção 7 é o logout.
                    cliente.logOut();
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
    
    private static int lerOpcao() { // Método para ler um número do teclado de forma segura.
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            return opcao;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    private static void listarLivros() { // Método para mostrar a lista de livros.
        System.out.println("\n--- Catálogo de Livros Disponíveis ---");
        for (int i = 0; i < catalogo.size(); i++) {
            System.out.println((i + 1) + ". " + catalogo.get(i));
        }
        System.out.println("------------------------------------");
    }
}