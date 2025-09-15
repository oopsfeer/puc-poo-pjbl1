import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

public class PetShop {

    private static ArrayList<Tutor> tutors = new ArrayList<>();

    public static void main(String[] args) {

        Scanner leitor = new Scanner(System.in); //Para ler do teclado

        popularCadastro(); //Popula com dados iniciais

        while (true) {
            String op = mostrarMenuEObterOpcao(leitor);
            switch (op) {
                case "c":
                    cadastrarTutorEPets(leitor);
                    break;
                case "i":
                    imprimirCadastro();
                    break;
                case "b":
                    Integer codBusca = pedirCodigo(leitor, "Informe o código do tutor: ");
                    if (codBusca == null) {
                        System.out.println("Código inválido.");
                        break;
                    }
                    Tutor t = buscarPorCodigo(codBusca);
                    if (t == null) {
                        System.out.println("Tutor não encontrado.");
                    } else {
                        System.out.println("Tutor encontrado:");
                        System.out.println(t.toString());
                    }
                    break;
                case "e":
                    Integer codExc = pedirCodigo(leitor, "Informe o código do tutor para excluir: ");
                    if (codExc == null) {
                        System.out.println("Código inválido.");
                        break;
                    }
                    if (excluirPorCodigo(codExc)) {
                        System.out.println("Tutor removido com sucesso.");
                    } else {
                        System.out.println("Tutor não encontrado.");
                    }
                    break;
                case "x":
                    System.out.println("Encerrando. Até mais!");
                    leitor.close();
                    return;
                default:
                    // nunca cai aqui por causa do tratamento do menu
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static String mostrarMenuEObterOpcao(Scanner leitor) {
        while (true) {
            System.out.println();
            System.out.println("=== PETSHOP MENU ===");
            System.out.println("[c] Cadastrar tutor e pets");
            System.out.println("[i] Imprimir cadastro completo");
            System.out.println("[b] Buscar pets por código do tutor");
            System.out.println("[e] Excluir tutor por código");
            System.out.println("[x] Encerrar");
            System.out.print("Opção: ");
            String op = leitor.nextLine().trim().toLowerCase();
            if (!op.isEmpty()) {
                char c = op.charAt(0);
                if ("cibex".indexOf(c) >= 0) return String.valueOf(c);
            }
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static Integer pedirCodigo(Scanner leitor, String prompt) {
        System.out.print(prompt);
        return lerInteiroOuNull(leitor.nextLine());
    }

    private static Integer lerInteiroOuNull(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return null; }
    }

    private static int[] lerDataValida(Scanner leitor) {
        while (true) {
            System.out.print("Data de nascimento (dd mm aaaa): ");
            String[] partes = leitor.nextLine().trim().split("\\s+");
            if (partes.length != 3) {
                System.out.println("Formato inválido. Tente novamente.");
                continue;
            }
            Integer d = lerInteiroOuNull(partes[0]);
            Integer m = lerInteiroOuNull(partes[1]);
            Integer a = lerInteiroOuNull(partes[2]);
            if (d == null || m == null || a == null || !validaData(d, m, a)) {
                System.out.println("Data inválida. Tente novamente.");
                continue;
            }
            return new int[]{d, m, a};
        }
    }

    public static boolean validaData(int d, int m, int a) {
        try {
            LocalDate data = LocalDate.of(a, m, d);
            return !data.isAfter(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    public static int geraCodigo() {
        if (tutors.isEmpty()) return 1;
        return tutors.get(tutors.size() - 1).getCodigo() + 1;
    }

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

    public static void cadastrarTutorEPets(Scanner leitor) {
        while (true) {
            System.out.print("Nome do tutor (vazio para encerrar cadastro): ");
            String nome = leitor.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Cadastro encerrado.");
                return;
            }

            int[] dma = lerDataValida(leitor);
            int d = dma[0], m = dma[1], a = dma[2];

            System.out.print("Endereço: ");
            String endereco = leitor.nextLine().trim();

            int codigo = geraCodigo();
            Tutor novo = new Tutor(nome, codigo, endereco, d, m, a);

            // loop de pets
            while (true) {
                System.out.print("Nome do pet (vazio para encerrar pets): ");
                String nomePet = leitor.nextLine().trim();
                if (nomePet.isEmpty()) break;

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

    public static void imprimirCadastro() {
        System.out.println("--- CADASTRO DE TUTORES E PETS -------------------------------------------------");
        for (Tutor t : tutors) {
            System.out.println(t.toString());
        }
    }

    public static Tutor buscarPorCodigo(int cod) {
        for (Tutor t : tutors) {
            if (t.getCodigo() == cod) return t;
        }
        return null;
    }

    public static boolean excluirPorCodigo(int cod) {
        for (int i = 0; i < tutors.size(); i++) {
            if (tutors.get(i).getCodigo() == cod) {
                tutors.remove(i);
                return true;
            }
        }
        return false;
    }
}
