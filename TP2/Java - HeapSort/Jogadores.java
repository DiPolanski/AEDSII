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

    public static Jogadores[] HeapSort(Jogadores[] jogadores) {
        int n = linhaAtual;
        try {
            // Construir o heap máximo (rearranjar o array)
            for (int i = n / 2 - 1; i >= 0; i--) {
                ConstruirHeap(jogadores, n, i);
            }
            // Extrair elementos um por um do heap
            for (int i = n - 1; i >= 0; i--) {
                // Mova a raiz atual para o final do array
                Jogadores temp = jogadores[0];
                jogadores[0] = jogadores[i];
                jogadores[i] = temp;

                totalMovimentacoes += 3;

                // Chame heapify na subárvore reduzida
                ConstruirHeap(jogadores, i, 0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return jogadores;
    }

    private static void ConstruirHeap(Jogadores[] jogadores, int n, int i) {
        int maior = i;
        int filhoEsq = 2 * i + 1;
        int filhoDir = 2 * i + 2;
        try {
            // Se o filho da esquerda for maior que o maior até agora
            if ((filhoEsq) < n && (jogadores[filhoEsq].altura > jogadores[maior].altura ||
                    jogadores[filhoEsq].altura == jogadores[maior].altura
                            && (jogadores[filhoEsq].nome.compareTo(jogadores[maior].nome) > 0))) {

                totalComparacoes += 5;
                maior = filhoEsq;
            }

            // Se o filho da direita for maior que o maior até agora
            if ((filhoDir) < n && (jogadores[filhoDir].altura > jogadores[maior].altura ||
                    jogadores[filhoDir].altura == jogadores[maior].altura
                            && (jogadores[filhoDir].nome.compareTo(jogadores[maior].nome) > 0))) {
                totalComparacoes += 5;
                maior = filhoDir;
            }

            // Se o maior for maior que filhoEsq e/ou filhoDir
            if (maior != i) {
                Jogadores swap = jogadores[i];
                jogadores[i] = jogadores[maior];
                jogadores[maior] = swap;

                totalMovimentacoes += 3;

                // Recursivamente heapify a subárvore afetada
                ConstruirHeap(jogadores, n, maior);
            }
        } catch (NullPointerException e) {
        }
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
        jogadores = HeapSort(jogadores);

        for (int i = 0; i < jogadores.length - 1; i++) {
            imprimir(jogadores, i);
        }
        scanner.close();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        String filename = "802736_heapsort.txt";

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