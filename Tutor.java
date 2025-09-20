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
        dataNascimento = LocalDate.of(Ano, Mes, Dia);
        
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

    public ArrayList<Pet> getPets(){
        return pets;
    }

    // Setters
    public void incluiPet(String nome, String tipo, String cor){
        Pet pet = new Pet(nome, tipo, cor);
        pets.add(pet);
    }

    // Geracao da string com todos os dados do tutor para posterior impressao
    public String toString(){
        String texto = String.format("Codigo tutor: %d\n", codigo);
        texto += String.format("\tNome...........: %s\n", nome);
        texto += String.format("\tData nascimento: %s (%d anos)\n", getNascimento(), getIdade());
        texto += String.format("\tEndereço.......: %s\n", endereco);
        texto += String.format("\tRelacao de Pets: \n");
        
        if (pets.size()==0)
            texto += String.format("\t\t- Nenhum Pet cadastrado.");
        else{
            for (Pet pet : pets)
                texto += String.format("\t\t- Nome do pet: %s; Tipo: %s; Cor:%s.\n", pet.getNome(), pet.getTipo(), pet.getCor());
        }

        return texto;
    }
}
