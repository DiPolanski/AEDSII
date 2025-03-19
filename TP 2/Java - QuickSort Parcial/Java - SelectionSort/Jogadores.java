import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
    public static int totalComparacoes = 0;
    public static int totalMovimentacoes = 0;

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

    public static void imprimir(Jogadores[] jogador, int i) {
        try {
            System.out
                    .println("[" + jogador[i].getId() + " ## " + jogador[i].getNome() + " ## " + jogador[i].getAltura()
                            + " ## " + jogador[i].getPeso() + " ## " + jogador[i].getAnoNascimento() + " ## "
                            + jogador[i].getUniversidade() + " ## " + jogador[i].getCidadeNascimento() + " ## "
                            + jogador[i].getEstadoNascimento() + "]");
        } catch (NullPointerException e) {
        }
    }// imprime da maneira pedida no exercício

    public static void ler(String caminhoDoArquivoCSV, int qualId, Jogadores[] jogador) {
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

                    Jogadores jogador1 = new Jogadores(id, nome, altura, peso, universidade, anoNascimento,
                            cidadeNascimento, estadoNascimento);

                    jogador[linhaAtual - 1] = jogador1;
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

    public static Jogadores[] selecao(Jogadores[] jogadores) {
        int n = jogadores.length;
        for (int i = 0; i < (n - 1); i++) {
            if (jogadores[i] != null) {
                int menor = i;
                for (int j = (i + 1); j < n; j++) {
                    if (jogadores[j] != null) {
                        String nomeJogador1 = jogadores[menor].getNome();
                        String nomeJogador2 = jogadores[j].getNome();
                        if (nomeJogador1.compareTo(nomeJogador2) > 0) {
                            menor = j;
                            totalComparacoes++;
                        }
                    }
                }
                if (menor != i) {
                    Jogadores temp = jogadores[i];
                    totalMovimentacoes++;
                    jogadores[i] = jogadores[menor];
                    totalMovimentacoes++;
                    jogadores[menor] = temp;
                    totalMovimentacoes++;
                }
            }
        }
        return jogadores;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);

        String caminhoDoArquivoCSV = "/tmp/players.csv"; // nome do arquivo
        String qualId = ""; // inicia a a procura de id como vazia
        int maxJogadores = 3922;
        Jogadores[] jogadores = new Jogadores[maxJogadores];

        while (!qualId.equals("FIM")) {
            qualId = scanner.nextLine();

            if (!qualId.equals("FIM")) {
                int QualId = Integer.parseInt(qualId);
                ler(caminhoDoArquivoCSV, QualId, jogadores);
            }
        }

        jogadores = selecao(jogadores);

        for (int i = 0; i < jogadores.length - 1; i++) {
            imprimir(jogadores, i);
        }
        scanner.close();
        long totalTime = System.currentTimeMillis();

        String filename = "802736_selecao.txt";
        try {
            FileWriter outputFile = new FileWriter(filename);
            outputFile.write("802736\t");
            outputFile.write(totalComparacoes + "\t");
            outputFile.write(totalTime + "\t");
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}