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
            System.out.println("Data invalida! Programa encerrado.");
            System.exit(1);
        }
        dataNascimento = LocalDate.of(Ano, Mes, Dia);
    }

    // Validação data
    // *******************
    private boolean validaData(int Dia, int Mes, int Ano){
        
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

    // Geração da string com todos os dados do tutor para posterior impressão
    public String toString(){

    }

}
