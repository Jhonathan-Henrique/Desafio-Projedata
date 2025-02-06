import java.math.BigDecimal; //biblioteca que posso usar pontos flutuantes
import java.math.RoundingMode; // Define diferentes modos de arrendondamento
import java.time.LocalDate; // classe que faz parte da nova API de data e hora do Java introduzida no Java 8
import java.time.format.DateTimeFormatter; //Essa classe é usada para formatar e analisar (parse) datas e horas em Java
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    String nome;
    LocalDate dataNascimento;

    //construtor

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }
}

//Funcionario extends Pessoa: Isso significa que Funcionario é uma subclasse de Pessoa, ou seja, 
//ela herda tudo o que está dentro da classe Pessoa, mas pode adicionar mais coisas.

class Funcionario extends Pessoa {

    //BigDecimal salario: Aqui, é definida uma variável salario, que armazena o salário do funcionário. 
    //O tipo BigDecimal é utilizado para lidar com números de ponto flutuante de forma precisa, especialmente quando lidamos com valores financeiros.
    //String funcao: Define uma variável funcao, que vai armazenar a função ou cargo do funcionário (como "Operador", "Gerente", etc.).

    BigDecimal salario;
    String funcao;

    //public Funcionario(...): Isso define o construtor da classe Funcionario. Ele recebe quatro parâmetros: nome, dataNascimento, salario e funcao.

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }
}

public class Principal {

    //public class Principal: Essa linha define uma classe chamada Principal, que é onde o código vai ser executado. O nome da classe pode ser qualquer um, 
    //mas tradicionalmente a classe que contém o método main é chamada de Principal ou Main.


    public static void main(String[] args) {

        //public static void main(String[] args): Esse é o método principal, o ponto de entrada do programa. 
        //O código que está dentro deste método é o que será executado quando você rodar o programa.

        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(

            //List<Funcionario>: Aqui, você está criando uma lista (ou array list) para armazenar objetos do tipo Funcionario. 
            //O tipo Funcionario é uma classe que você já viu antes, e essa lista vai conter todos os funcionários.

            //new ArrayList<>(): Isso cria uma nova lista usando a implementação ArrayList, que é uma classe de lista que pode crescer 
            //dinamicamente conforme novos itens são adicionados. 
            //O ArrayList é uma das implementações da interface List.

            //Arrays.asList(...): Este método cria uma lista a partir de uma sequência de itens fornecidos. 
            //Dentro dos parênteses, você está criando uma lista de objetos Funcionario.

            
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
            ));
            
            /*new Funcionario(...): Isso cria um novo objeto da classe Funcionario. Para cada funcionário, você passa os valores necessários para o construtor de Funcionario, que são:

                Nome: O nome do funcionário (exemplo: "Maria", "João", etc.).
                Data de nascimento: Criada usando LocalDate.of(), que recebe o ano, mês e dia como parâmetros (exemplo: LocalDate.of(2000, 10, 18)).
                Salário: O salário é criado usando new BigDecimal(), que recebe o valor do salário como um String (exemplo: new BigDecimal("2009.44")).
                Função: A função do funcionário é passada como uma string (exemplo: "Operador", "Gerente", etc.). */

        // Remover João
        funcionarios.removeIf(f -> f.nome.equals("João"));
        
        // Imprimir funcionários formatados
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        funcionarios.forEach(f -> System.out.println(f.nome + ", " + f.dataNascimento.format(formatter) + ", " + f.salario.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",") + ", " + f.funcao));
        
        // Aumento de 10% nos salários
        funcionarios.forEach(f -> f.salario = f.salario.multiply(new BigDecimal("1.1")));
        
        // Agrupar por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream().collect(Collectors.groupingBy(f -> f.funcao));
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println("  " + f.nome));
        });
        
        // Aniversariantes em outubro e dezembro
        funcionarios.stream()
            .filter(f -> f.dataNascimento.getMonthValue() == 10 || f.dataNascimento.getMonthValue() == 12)
            .forEach(f -> System.out.println("Aniversariante: " + f.nome));
        
        // Funcionário mais velho
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(f -> f.dataNascimento));
        System.out.println("Funcionário mais velho: " + maisVelho.nome + ", Idade: " + (LocalDate.now().getYear() - maisVelho.dataNascimento.getYear()));
        
        // Ordem alfabética
        funcionarios.sort(Comparator.comparing(f -> f.nome));
        funcionarios.forEach(f -> System.out.println(f.nome));
        
        // Total dos salários
        BigDecimal totalSalarios = funcionarios.stream().map(f -> f.salario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + totalSalarios.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","));
        
        // Salários em relação ao mínimo
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> System.out.println(f.nome + " ganha " + f.salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP) + " salários mínimos"));
    }
}