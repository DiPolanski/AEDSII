import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

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
    private static Jogadores[] jogadores; // Array de jogadores

    public static void setJogadores(Jogadores[] array) {
        jogadores = array;
    }

    public static Jogadores[] getJogadores() {
        return jogadores;
    }

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

    public static void ler(String caminhoDoArquivoCSV, String qualId, Jogadores[] jogador, int numJogadores) {
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

                    if (numJogadores >= 0 && numJogadores < jogador.length) {
                        jogador[numJogadores] = jogador1;
                    }

                }

            }
            leitorBuffer.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void MergeSort(int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;
            MergeSort(esquerda, meio);
            MergeSort(meio + 1, direita);
            Intercalar(esquerda, meio, direita);
        }
    }

    public static void Intercalar(int esquerda, int meio, int direita) {
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;

        Jogadores[] esquerdaArray = new Jogadores[n1];
        Jogadores[] direitaArray = new Jogadores[n2];
        try {
            for (int i = 0; i < n1; i++) {
                esquerdaArray[i] = jogadores[esquerda + i];
            }
            for (int i = 0; i < n2; i++) {
                direitaArray[i] = jogadores[meio + 1 + i];
            }

            int i = 0, j = 0;
            int k = esquerda;

            while (i < n1 && j < n2) {
                if (compararUniversidades(esquerdaArray[i], direitaArray[j]) <= 0) {
                    jogadores[k] = esquerdaArray[i];
                    i++;
                } else {
                    jogadores[k] = direitaArray[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                jogadores[k] = esquerdaArray[i];
                i++;
                k++;
            }

            while (j < n2) {
                jogadores[k] = direitaArray[j];
                j++;
                k++;
            }
        } catch (NullPointerException e) {

        }
    }

    public static int compararUniversidades(Jogadores jogador1, Jogadores jogador2) {
        try {
            int diferenca = jogador1.getUniversidade().compareTo(jogador2.getUniversidade());

            if (diferenca == 0) {
                diferenca = jogador1.getNome().compareTo(jogador2.getNome());
            }

            return diferenca;

        } catch (NullPointerException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);

        String caminhoDoArquivoCSV = "/tmp/players.csv"; // nome do arquivo
        String qualId; // inicia a procura de id como vazia
        int maxJogadores = 50000;
        Jogadores[] jogadores = new Jogadores[maxJogadores];
        int numJogadores = 0; // Número de jogadores lidos

        setJogadores(jogadores); // Definir o array de jogadores na classe Jogadores

        while (!(qualId = scanner.nextLine()).equals("FIM")) {
            ler(caminhoDoArquivoCSV, qualId, jogadores, numJogadores); // Passa numJogadores como argumento
            numJogadores++; // Incrementa o índice do próximo jogador após a leitura
        }

        MergeSort(0, jogadores.length - 1); // Ordena apenas os jogadores lidos

        for (int i = 0; i < numJogadores; i++) {
            imprimir(getJogadores(), i); // Acesso ao array de jogadores sem passá-lo como parâmetro
        }
        scanner.close();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        String filename = "802736_mergesort.txt";

        try {
            FileWriter outputFile = new FileWriter(filename);
            outputFile.write("802736/t");
            outputFile.write(totalComparacoes + "/t");
            outputFile.write(totalMovimentacoes + "\t");
            outputFile.write(totalTime + "\t");
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}