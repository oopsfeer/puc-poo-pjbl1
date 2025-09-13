import java.util.Scanner;
import java.util.ArrayList;

public class PetShop {
    
    private static ArrayList<Tutor> tutors = new ArrayList<Tutor>();
    
    public static void main(String[] args) {

        // Insere registros iniciais no cadastro
        popularCadastro();

    }

    // Gera o código do tutor
    public static int geraCodigo(){
        if (tutors.size()==0)
            return 1;
        return tutors.get(tutors.size()-1).getCodigo()+1;
    }

    public static void popularCadastro(){

    }

    public static void imprimeTutores(){
        System.out.println("--- CADASTRO DE TUTORES E PETS -------------------------------------------------");
        for (Tutor T:tutors)
            System.out.println(T.toString());
    }
}
