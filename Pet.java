public class Pet{

    // Atributos
    String nome;
    String tipo;
    String cor;

    // Construtor
    public Pet (String Nome, String Tipo, String Cor){
        nome = Nome;
        tipo = Tipo;
        cor = Cor;
    }


    // Getters
    public String getNome(){
        return nome;
    }

    public String getTipo(){
        return tipo;
    }

    public String getCor(){
        return cor;
    }

}