import java.util.Scanner;
import java.io.BufferedReader;
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

    public static int linhaAtual = 1;

    public static void ler(String caminhoDoArquivoCSV, String qualId, Jogadores[] jogador) {
        try {
            FileReader arq = new FileReader(caminhoDoArquivoCSV);
            BufferedReader leitorBuffer = new BufferedReader(arq);
            // linhaAtual como variavel global, senao ela reiniciaria o valor dela toda hora
            // para 1
            // int linhaAtual = 1; // começa na linha 1, pois linha 0 e cabeçalho
            String linha;
            String[] colunas;
            while ((linha = leitorBuffer.readLine()) != null) {
                // precisa por o -1, senao ele nao vai alocar espaço para os nao tem informação
                colunas = linha.split(",", -1); // divide a linha em colunas, como sendo separação a ","
                if (qualId.equals(colunas[0])) {

                    int id = Integer.parseInt(colunas[0]);
                    String nome = colunas[1];
                    int altura = Integer.parseInt(colunas[2]);
                    int peso = Integer.parseInt(colunas[3]);
                    String universidade = (colunas[4].isEmpty() ? "nao informado" : colunas[4]);
                    int anoNascimento = Integer.parseInt(colunas[5]);
                    String cidadeNascimento = (colunas[6].isEmpty() ? "nao informado" : colunas[6]);
                    String estadoNascimento = (colunas[7].isEmpty() ? "nao informado" : colunas[7]);

                    Jogadores jogador1 = new Jogadores(id, nome, altura, peso, universidade, anoNascimento,
                            cidadeNascimento, estadoNascimento);

                    jogador[linhaAtual - 1] = jogador1;
                    linhaAtual++;

                }

            }
            leitorBuffer.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static Jogadores[] insercao(Jogadores[] jogadores) {
        int n = linhaAtual;
        try {
            for (int i = 1; i < n; i++) {
                Jogadores chave = jogadores[i];
                int j = i - 1;
                totalComparacoes++;
                while ((j >= 0) && (jogadores[j].anoNascimento > chave.anoNascimento
                        || jogadores[j].anoNascimento == chave.anoNascimento
                                && jogadores[j].nome.compareTo(chave.nome) > 0)) {

                    jogadores[j + 1] = jogadores[j];
                    j--;
                    totalMovimentacoes++;
                }

                jogadores[j + 1] = chave;
                totalMovimentacoes++;
            }

        } catch (NullPointerException e) {
        }
        return jogadores;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);

        String caminhoDoArquivoCSV = "/tmp/players.csv"; // nome do arquivo
        String qualId; // inicia a a procura de id como vazia
        int maxJogadores = 50000;
        Jogadores[] jogadores = new Jogadores[maxJogadores];

        while (!(qualId = scanner.nextLine()).equals("FIM")) {
            // qualId = scanner.nextLine(); // coloquei no proprio while
            ler(caminhoDoArquivoCSV, qualId, jogadores);
        }
        jogadores = insercao(jogadores);

        for (int i = 0; i < jogadores.length - 1; i++) {
            imprimir(jogadores, i);
        }
        scanner.close();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        String filename = "802736_insercao.txt";

        try {
            FileWriter outputFile = new FileWriter(filename);
            outputFile.write("802736\t");
            outputFile.write(totalComparacoes + "\t");
            outputFile.write(totalMovimentacoes + "\t");
            outputFile.write(totalTime + "\t");
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}