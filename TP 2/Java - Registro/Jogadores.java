import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Jogadores {
    private int id;
    private String nome;
    private int altura; // em centimetros
    private int peso; // em KG
    private String universidade;
    private int anoNascimento;
    private String cidadeNascimento;
    private String estadoNascimento;

    public Jogadores() {
    }

    public Jogadores(int id, String nome, int altura, int peso, String universidade, int anoNascimento,
            String cidadeNascimento, String estadoNascimento) {
        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.universidade = universidade;
        this.anoNascimento = anoNascimento;
        this.cidadeNascimento = cidadeNascimento;
        this.estadoNascimento = estadoNascimento;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getAltura() {
        return altura;
    }

    public int getPeso() {
        return peso;
    }

    public String getUniversidade() {
        return universidade;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public String getCidadeNascimento() {
        return cidadeNascimento;
    }

    public String getEstadoNascimento() {
        return estadoNascimento;
    }

    public Jogadores clone() {
        return new Jogadores(id, nome, altura, peso, universidade, anoNascimento, cidadeNascimento, estadoNascimento);
    } // cria um clone para manipulação

    public void imprimir() {
        System.out.println("[" + id + " ## " + nome + " ## " + altura + " ## " + peso + " ## " + anoNascimento + " ## "
                + universidade + " ## " + cidadeNascimento + " ## " + estadoNascimento + "]");
    }// imprime da maneira pedida no exercício

    public static void ler(String caminhoDoArquivoCSV, int qualId) {
        try (BufferedReader leitorBuffer = new BufferedReader(new FileReader(caminhoDoArquivoCSV));) {
            int linhaAtual = 1; // começa na linha 1, pois linha 0 e cabeçalho
            String linha;

            while ((linha = leitorBuffer.readLine()) != null) {
                if (linhaAtual == qualId + 2) {
                    String[] colunas = linha.split(","); // divide a linha em colunas, como sendo separação a ","

                    int id = Integer.parseInt(colunas[0]);
                    String nome = colunas[1];
                    int altura = Integer.parseInt(colunas[2]);
                    int peso = Integer.parseInt(colunas[3]);
                    String universidade = "nao informado";
                    if (!colunas[4].isEmpty()) {
                        universidade = colunas[4];
                    }
                    int anoNascimento = Integer.parseInt(colunas[5]);
                    String cidadeNascimento;
                    if (colunas.length > 6) {
                        cidadeNascimento = colunas[6];
                    } else {
                        cidadeNascimento = "nao informado";
                    }
                    String estadoNascimento;
                    if (colunas.length > 7) {
                        estadoNascimento = colunas[7];
                    } else {
                        estadoNascimento = "nao informado";
                    }

                    Jogadores jogador = new Jogadores(id, nome, altura, peso, universidade, anoNascimento,
                            cidadeNascimento, estadoNascimento);

                    jogador.imprimir();
                }

                linhaAtual++;
            }

            leitorBuffer.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + caminhoDoArquivoCSV);
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {

        }
    } // leitura e cadastro dos dados

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String caminhoDoArquivoCSV = "/tmp/players.csv"; // nome do arquivo
        String qualId = ""; // inicia a a procura de id como vazia

        while (!qualId.equals("FIM")) {
            qualId = scanner.nextLine();

            if (!qualId.equals("FIM")) {
                int QualId = Integer.parseInt(qualId);
                ler(caminhoDoArquivoCSV, QualId);
            }

        }
        scanner.close();
    }
}