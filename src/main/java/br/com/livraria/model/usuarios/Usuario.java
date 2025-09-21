package br.com.livraria.model.usuarios; // Define que esta classe pertence ao pacote 'br.com.livraria.model.usuarios'.

import java.util.List; // Importa a interface 'List' para poder usar listas.

/**
 * Molde base para qualquer usuário do sistema.
 * É 'abstrata' porque não criamos um 'Usuario' genérico, apenas Clientes e Administradores.
 */
public abstract class Usuario { // Inicia a declaração do molde 'Usuario'.
    protected String nome; // Guarda o nome do usuário. 'protected' significa que as classes filhas (Cliente, Admin) podem acessar.
    protected String e_mail; // Guarda o e-mail do usuário, usado para login.
    protected String cpf; // Guarda o CPF do usuário.

    public Usuario(String nome, String email, String cpf) { // Construtor, chamado quando um novo usuário é criado.
        this.nome = nome; // Pega o nome recebido e guarda no atributo 'nome'.
        this.e_mail = email; // Pega o e-mail recebido e guarda no atributo 'e_mail'.
        this.cpf = cpf; // Pega o CPF recebido e guarda no atributo 'cpf'.
    }

    /**
     * Tenta autenticar um usuário.
     * @param todosOsUsuarios A lista de todos os usuários para procurar.
     * @param emailDigitado O e-mail que o usuário tentou usar para entrar.
     * @return O usuário encontrado ou 'null' se não encontrar ninguém com aquele e-mail.
     */
    public static Usuario login(List<Usuario> todosOsUsuarios, String emailDigitado) { // Método para fazer o login.
        for (Usuario usuario : todosOsUsuarios) { // Loop que verifica cada usuário na lista.
            if (usuario.getEmail().equalsIgnoreCase(emailDigitado)) { // Compara o e-mail do usuário da lista com o e-mail digitado (sem diferenciar maiúsculas/minúsculas).
                System.out.println("Login bem-sucedido! Bem-vindo(a), " + usuario.getNome() + "."); // Se encontrou, mostra mensagem de boas-vindas.
                return usuario; // Retorna o objeto do usuário que foi encontrado.
            }
        }
        System.out.println("E-mail não encontrado. Tente novamente."); // Se o loop terminar e não encontrar ninguém, mostra erro.
        return null; // Retorna 'null' para indicar que o login falhou.
    }

    /**
     * Mostra uma mensagem de logout.
     */
    public void logOut() { // Método para sair do sistema.
        System.out.println("A fazer logout..."); // Apenas mostra uma mensagem na tela.
    }

    // Métodos 'Getters' (para pegar/ler um valor) e 'Setters' (para definir/alterar um valor).
    public String getNome() { return nome; } // Devolve o nome do usuário.
    public void setNome(String nome) { this.nome = nome; } // Permite alterar o nome do usuário.
    public String getEmail() { return e_mail; } // Devolve o e-mail do usuário.
    public void setEmail(String email) { this.e_mail = email; } // Permite alterar o e-mail do usuário.
    public String getCpf() { return cpf; } // Devolve o CPF do usuário.
    public void setCpf(String cpf) { this.cpf = cpf; } // Permite alterar o CPF do usuário.
    
    public void exibirDetalhes() { // Método para mostrar as informações do usuário.
        System.out.println("Nome: " + nome); // Mostra o nome.
        System.out.println("Email: " + e_mail); // Mostra o e-mail.
        System.out.println("CPF: " + cpf); // Mostra o CPF.
    }
}