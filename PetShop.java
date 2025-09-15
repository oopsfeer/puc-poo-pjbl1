import java.util.Scanner;
import java.util.ArrayList;

public class PetShop {

    private static ArrayList<Tutor> tutors = new ArrayList<>();

    public static void main(String[] args) {

        Scanner leitor = new Scanner(System.in); // leitura do teclado
        popularCadastro(); // dados iniciais

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
                        System.out.println(t);
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
            if (op.length() > 0) {
                char c = op.charAt(0);
                if (c == 'c' || c == 'i' || c == 'b' || c == 'e' || c == 'x') {
                    return String.valueOf(c);
                }
            }
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static Integer pedirCodigo(Scanner leitor, String prompt) {
        System.out.print(prompt);
        String s = leitor.nextLine().trim();
        if (!isNumero(s)) return null;
        return toInt(s); // conversão simples, sem exceptions
    }

    private static boolean isNumero(String s) {
        if (s == null || s.length() == 0) return false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch < '0' || ch > '9') return false;
        }
        return true;
    }

    private static int toInt(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            n = n * 10 + (s.charAt(i) - '0');
        }
        return n;
    }

    private static int[] lerDataValida(Scanner leitor) {
        while (true) {
            System.out.print("Data de nascimento (dd mm aaaa): ");
            String linha = leitor.nextLine().trim();
            String[] partes = linha.split("\\s+");
            if (partes.length != 3) {
                System.out.println("Formato inválido. Tente novamente.");
                continue;
            }

            if (!isNumero(partes[0]) || !isNumero(partes[1]) || !isNumero(partes[2])) {
                System.out.println("Use apenas números. Tente novamente.");
                continue;
            }

            int d = toInt(partes[0]);
            int m = toInt(partes[1]);
            int a = toInt(partes[2]);

            if (!validaData(d, m, a)) {
                System.out.println("Data inválida. Tente novamente.");
                continue;
            }
            return new int[]{ d, m, a };
        }
    }
    public static boolean validaData(int d, int m, int a) {
        if (a < 1900 || a > 2100) return false; // faixa simples
        if (m < 1 || m > 12) return false;

        int[] diasMes = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (m == 2 && isBissexto(a)) {
            if (d < 1 || d > 29) return false;
        } else {
            if (d < 1 || d > diasMes[m - 1]) return false;
        }
        return true;
    }

    private static boolean isBissexto(int a) {
        if (a % 400 == 0) return true;
        if (a % 100 == 0) return false;
        return (a % 4 == 0);
    }

    public static int geraCodigo() {
        if (tutors.size() == 0) return 1;
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
            if (nome.length() == 0) {
                System.out.println("Cadastro encerrado.");
                return;
            }

            int[] dma = lerDataValida(leitor);
            int d = dma[0], m = dma[1], a = dma[2];

            System.out.print("Endereço: ");
            String endereco = leitor.nextLine().trim();

            int codigo = geraCodigo();
            Tutor novo = new Tutor(nome, codigo, endereco, d, m, a);

            // pets
            while (true) {
                System.out.print("Nome do pet (vazio para encerrar pets): ");
                String nomePet = leitor.nextLine().trim();
                if (nomePet.length() == 0) break;

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
        for (int i = 0; i < tutors.size(); i++) {
            System.out.println(tutors.get(i));
        }
    }

    public static Tutor buscarPorCodigo(int cod) {
        for (int i = 0; i < tutors.size(); i++) {
            if (tutors.get(i).getCodigo() == cod) return tutors.get(i);
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
