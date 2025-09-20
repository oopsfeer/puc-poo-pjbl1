import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class PetShop {

    // Lista que guarda todos os tutores cadastrados
    private static ArrayList<Tutor> tutors = new ArrayList<>();

    public static void main(String[] args) {

        Scanner leitor = new Scanner(System.in); // leitura do teclado
        popularCadastro(); // dados iniciais

        // laco principal
        while (true) {

            // entrada da opcao do menu
            String op = mostrarMenuEObterOpcao(leitor);

            // direcionamento a partir da escolha do menu
            switch (op) {

                // cadastrar tutor + pet(s)
                case "c":
                    cadastrarTutorEPets(leitor);
                    break;

                // imprimir cadastro
                case "i":
                    imprimirCadastro();
                    break;

                // buscar pets pelo codigo do tutor
                case "b":
                    Integer codBusca = pedirCodigo(leitor, "Informe o código do tutor: ");
                    if (codBusca == null) {
                        System.out.println("Código inválido.");
                        break;
                    }
                    Tutor t = buscarPorCodigo(codBusca);
                    if (t == null) {
                        System.out.println("Tutor nao encontrado.");
                    } 
                    else {
                        System.out.println("Tutor encontrado:");
                        System.out.println(t);
                    }
                    break;

                // excluir pets pelo codigo do tutor
                case "e":
                    excluirPorCodigo(leitor);
                    break;

                // encerrar
                case "x":
                    System.out.println("\nEncerrando. Até mais!");
                    leitor.close();
                    return;

            }
        }
    }

    // metodo que exibe o menu e retorna a opcao escolhida pelo usuario
    private static String mostrarMenuEObterOpcao(Scanner leitor) {
        while (true) {
            System.out.println();
            System.out.println("=== PETSHOP MENU ===");
            System.out.println("[c] Cadastrar tutor e pets");
            System.out.println("[i] Imprimir cadastro completo");
            System.out.println("[b] Buscar pets por código do tutor");
            System.out.println("[e] Excluir tutor ou pet");
            System.out.println("[x] Encerrar");
            System.out.print("Opcao: ");
            String op = leitor.nextLine().toLowerCase();
            if (op.length() == 1) {
                char c = op.charAt(0);
                if (c == 'c' || c == 'i' || c == 'b' || c == 'e' || c == 'x') {
                    return String.valueOf(c);
                }
            }
            System.out.println("Opcao inválida. Tente novamente.");
        }
    }

    // metodo que solicita um numero pro usuario, retorna o valor se ele foi digitado corretamente
    // ou null se a entrada nao for um valor numerico
    private static Integer pedirCodigo(Scanner leitor, String prompt) {
        System.out.print(prompt);
        String s = leitor.nextLine().trim();
        if (!isNumero(s)) 
            return null;
        return toInt(s); // conversao simples, sem exceptions
    }

    // metodo que verifica se a string contem apenas digitos
    private static boolean isNumero(String s) {
        if (s == null || s.length() == 0) 
            return false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch < '0' || ch > '9') 
                return false;
        }
        return true;
    }

    // metodo com uma logica manual para converter string para int
    private static int toInt(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) 
            n = n * 10 + (s.charAt(i) - '0');
        return n;
    }

    // metodo que retorna uma data de nascimento valida para o tutor
    private static int[] lerDataValida(Scanner leitor) {
        while (true) {
            System.out.print("Data de nascimento (dd mm aaaa): ");
            String linha = leitor.nextLine().trim();
            String[] partes = linha.split("\\s+");

            // se a pessoa nao digitar 3 valores separados por espaco reinicia o loop
            if (partes.length != 3) {
                System.out.println("Formato inválido. Tente novamente.");
                continue;
            }

            // verifica se as 3 entradas sao numeros; caso nao sejam, reinicia o loop
            if (!isNumero(partes[0]) || !isNumero(partes[1]) || !isNumero(partes[2])) {
                System.out.println("Use apenas números. Tente novamente.");
                continue;
            }

            // transforma a string para int
            int d = toInt(partes[0]);
            int m = toInt(partes[1]);
            int a = toInt(partes[2]);

            // testa se sao datas validas
            if (!validaData(d, m, a)) {
                System.out.println("Data inválida. Tente novamente.");
                continue;
            }
            return new int[]{ d, m, a };
        }
    }

    // metodo que verifica se a data entrada pelo usuario é uma data valida
    public static boolean validaData(int d, int m, int a) {
        
        if (a < 1900 || m < 1 || m > 12) 
            return false; // faixa simples

        int[] diasMes = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (m == 2 && isBissexto(a)) {
            if (d < 1 || d > 29) 
                return false;
        } 
        else {
            if (d < 1 || d > diasMes[m - 1]) 
                return false;
        }

        // teste se a data está no futuro
        int dAtual = LocalDate.now().getDayOfMonth();
        int mAtual = LocalDate.now().getMonthValue();
        int aAtual = LocalDate.now().getYear();        
        if (a > aAtual || (a == aAtual && m > mAtual) || (a == aAtual && m == mAtual && d > dAtual)) 
            return false;

        return true;
    }

    // verifica se um ano é bissexto
    private static boolean isBissexto(int a) {
        if (a % 400 == 0) 
            return true;
        if (a % 100 == 0) 
            return false;

        return (a % 4 == 0);
    }

    // metodo para gerar o codigo do tutor
    public static int geraCodigo() {
        if (tutors.size() == 0) 
            return 1;
        return tutors.get(tutors.size() - 1).getCodigo() + 1;
    }

    // metodo para popular o cadastro inicial
    public static void popularCadastro() {
        Tutor t1 = new Tutor("Ana Silva",  geraCodigo(), "Rua das Acácias, 123", 15, 5, 1990);
        t1.incluiPet("Bidu", "Cachorro", "Azul");
        t1.incluiPet("Mimi", "Gato", "Branco");
        tutors.add(t1);

        Tutor t2 = new Tutor("Bruno Souza", geraCodigo(), "Av. Central, 456", 2, 9, 1985);
        t2.incluiPet("Nina", "Gato", "Preto");
        tutors.add(t2);

        Tutor t3 = new Tutor("Carla Dias",  geraCodigo(), "Alameda Verde, 789", 28, 2, 1992);
        t3.incluiPet("Thor", "Cachorro", "Marrom");
        t3.incluiPet("Lola", "Pássaro", "Amarelo");
        t3.incluiPet("Pipoca", "Hamster", "Cinza");
        tutors.add(t3);
    }

    // metodo para cadastrar tutor e pets
    public static void cadastrarTutorEPets(Scanner leitor) {
        while (true) {
            System.out.print("Nome do tutor (vazio para encerrar cadastro): ");
            String nome = leitor.nextLine().trim();
            if (nome.length() == 0) {
                System.out.println("Cadastro encerrado.");
                return;
            }

            int[] dma = lerDataValida(leitor);
            int d = dma[0], m = dma[1], a = dma[2];

            // Verifica se o tutor tem mais de 18 anos
            int idade = Period.between(LocalDate.of(a, m, d), LocalDate.now()).getYears();
            if (idade<18){
                System.out.println("O tutor precisa ter mais que 18 anos! Cadastro encerrado.");
                System.out.println("Voltando para o menu principal...\n");
                return;
            }

            System.out.print("Endereco: ");
            String endereco = leitor.nextLine().trim();

            int codigo = geraCodigo();
            Tutor novo = new Tutor(nome, codigo, endereco, d, m, a);

            // pets
            while (true) {
                System.out.print("Nome do pet (vazio para encerrar pets): ");
                String nomePet = leitor.nextLine().trim();
                
                if (nomePet.length() == 0) 
                    break;

                System.out.print("Tipo do pet: ");
                String tipoPet = leitor.nextLine().trim();

                System.out.print("Cor do pet: ");
                String corPet = leitor.nextLine().trim();

                novo.incluiPet(nomePet, tipoPet, corPet);
            }

            tutors.add(novo);
            System.out.println("Tutor cadastrado com sucesso! Código: " + codigo);
        }
    }

    // metodo para imprimir o cadastro
    public static void imprimirCadastro() {
        System.out.println("\n--- CADASTRO DE TUTORES E PETS -------------------------------------------------\n");
        for (int i = 0; i < tutors.size(); i++) 
            System.out.println(tutors.get(i));
    }

    // metodo para buscar o tutor pelo codigo
    public static Tutor buscarPorCodigo(int cod) {
        for (int i = 0; i < tutors.size(); i++) {
            if (tutors.get(i).getCodigo() == cod) 
                return tutors.get(i);
        }        
        return null;
    }

    // metodo para excluir ou pet
    public static void excluirPorCodigo(Scanner leitor) {

        Tutor tutorEncontrado = null;

        // mostrar lista de tutores
        System.out.println("Tutores:");
        for (Tutor t : tutors) {
            System.out.println("\t" + t.getCodigo() + " - " + t.getNome());
        }

        // loop para solicitar um codigo valido para o usuario
        // 0 retorna para o menu
        while (true){

            Integer codExc = pedirCodigo(leitor, "Informe o código do tutor (0 volta para o menu): ");
            if (codExc == null) {
                System.out.println("Código inválido. Tente novamente.");
                continue;
            }

            if (codExc == 0){
                System.out.println("Voltando para o menu principal...");
                return;
            }

            for (Tutor t : tutors){
                if (t.getCodigo() == codExc){
                    tutorEncontrado = t;
                    break;
                }
            }

            if (tutorEncontrado == null){
                System.out.println("Tutor nao encontrado. Tente novamente.");
                continue;
            }
            break;
        }
        
        // submenu para escolher se exclui o tutor ou o pet
        System.out.println("O que deseja excluir?");
        System.out.println("[0] Voltar para o menu principal");
        System.out.println("[1] Tutor e todos os seus pets");
        System.out.println("[2] Apenas um pet deste tutor");

        // loop para selecionar uma opcao valida
        while (true){

            Integer opcao = pedirCodigo(leitor, "Escolha: (0 volta para o menu): ");

            if (opcao == null) {
                System.out.println("Código inválido. Tente novamente.");
                continue;
            }

            // retorna para o menu principal
            if (opcao == 0){
                System.out.println("Voltando para o menu principal...");
                return;
            }

            // remove o tutor
            if (opcao == 1){
                tutors.remove(tutorEncontrado);
                System.out.println("Tutor removido com sucesso!");
                return;
            }

            // remove os pets
            if (opcao == 2){
                if (tutorEncontrado.getPets().isEmpty()){
                    System.out.println("Esse tutor nao possui pets cadastrados.");
                    return;
                }
                // lista pets antes de excluir
                System.out.println("Pets deste tutor:");
                int idx = 1;
                for (Pet p : tutorEncontrado.getPets()) {
                    System.out.println("\t" + idx + " - " + p.getNome() + " (" + p.getTipo() + ")");
                    idx++;
                }

                // loop para solicitar um pet existente
                while (true){

                    Integer petIndex = pedirCodigo(leitor, "Digite o número do pet que deseja excluir (0 para retornar para o menu principal): ");
                    if (petIndex == null) {
                        System.out.println("Código inválido. Tente novamente.");
                        continue;
                    }

                    if (opcao == 0){
                        System.out.println("Voltando para o menu principal...");
                        return;
                    }

                    if (petIndex < 1 || petIndex > tutorEncontrado.getPets().size())
                        System.out.println("Número inválido! Tente novamente.");
                    else {
                        Pet removido = tutorEncontrado.getPets().remove(petIndex - 1);
                        System.out.println("Pet '" + removido.getNome() + "' removido com sucesso!");
                        return;
                    }
                }
            }

            System.out.println("Valor invalido. Tente novamente.");

        }
    }
}
