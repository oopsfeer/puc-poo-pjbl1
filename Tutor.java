import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class Tutor {
    
    // Atributos do tutor
    private String nome;
    private String endereco;
    private LocalDate dataNascimento;
    private int codigo;
    private ArrayList<Pet> pets = new ArrayList<Pet>();

    // Construtor
    public Tutor(String Nome, int Codigo, String Endereco, int Dia, int Mes, int Ano){
        nome = Nome;
        codigo = Codigo;
        endereco = Endereco;

        // Validação data
        if (!validaData(Dia, Mes, Ano)){
            System.exit(1);
        }
        dataNascimento = LocalDate.of(Ano, Mes, Dia);
    }

    // Validação data
    private boolean validaData(int Dia, int Mes, int Ano){
        
        int diaAtual = LocalDate.now().getDayOfMonth();
        int mesAtual = LocalDate.now().getMonthValue();
        int anoAtual = LocalDate.now().getYear();

        // Validacao se o ano e valido
        if ((Ano<1900) || (Ano>anoAtual)){
            System.out.println("Data invalida! Programa encerrado.");
            return false;
        }

        // Validacao se o mes e valido
        if ((Mes<1) || (Mes>12)){
            System.out.println("Data invalida! Programa encerrado.");
            return false;
        }

        // Validacao se o dia e valido
        if (Dia<1){
            System.out.println("Data invalida! Programa encerrado.");
            return false;
        }
        // Validacao do mes de fevereiro
        if (Mes == 2){
            // Verificacao se o ano e bissexto
            if ((Ano % 4 == 0 && Ano % 100 != 0) || (Ano % 400 == 0)){
                if (Dia>29){
                    System.out.println("Data invalida! Programa encerrado.");
                    return false;
                }
            }
            else {
                if (Dia>28){
                    System.out.println("Data invalida! Programa encerrado.");
                    return false;
                }
            }
        }
        else{
            if (Mes == 4 || Mes == 6 || Mes == 9 || Mes == 11){
                if (Dia>30){
                    System.out.println("Data invalida! Programa encerrado.");
                    return false;
                }
            }
            else {
                if (Dia>31){
                    System.out.println("Data invalida! Programa encerrado.");
                    return false;
                }
            }
        }

        // Verifica se a data esta no futuro
        if (Ano > anoAtual || (Ano == anoAtual && Mes > mesAtual) || (Ano == anoAtual && Mes == mesAtual && Dia > diaAtual)) {
            System.out.println("Data inválida (futuro)! Programa encerrado.");
            return false;
        }
        
        // Se a data passar em todos os testes
        // Verificamos se a pessoa tem mais que 18 anos
        int idade = Period.between(LocalDate.of(Ano, Mes, Dia), LocalDate.of(anoAtual, mesAtual, diaAtual)).getYears();
        if (idade<18){
            System.out.println("O tutor precisa ter mais que 18 anos! Programa encerrado.");
            return false;
        }

        return true;
    }

    // Getters
    public int getCodigo(){
        return codigo;
    }

    public String getNome(){
        return nome;
    }

    public String getEndereco(){
        return endereco;
    }

    public int getIdade(){
        LocalDate dataSistema = LocalDate.now();
        int idade = Period.between(dataNascimento, dataSistema).getYears();
        return idade;
    }

    public String getNascimento(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(formato);
    }

    // Setters
    public void incluiPet(String nome, String tipo, String cor){
        Pet pet = new Pet(nome, tipo, cor);
        pets.add(pet);
    }

    // Geração da string com todos os dados do tutor para posterior impressao
    public String toString(){
        String texto = String.format("Codigo tutor: %d%n", codigo);
        texto += String.format("\tNome...........: %s%n", nome);
        texto += String.format("\tData nascimento: %s (%d anos)%n", getNascimento(), getIdade());
        texto += String.format("\tEndereço.......: %s%n", endereco);
        texto += String.format("\tRelação de Pets: %n");
        
        if (pets.size()==0)
            texto += String.format("\t\t- Nenhum Pet cadastrado.");
        else{
            for (Pet pet : pets)
                texto += String.format("\t\t- Nome do pet: %s; Tipo: %s; Cor:%s.%n", pet.getNome(), pet.getTipo(), pet.getCor());
        }

        return texto;
    }

}
