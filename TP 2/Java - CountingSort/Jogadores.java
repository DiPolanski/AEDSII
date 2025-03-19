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

    public static void CountingSort(Jogadores[] jogadores, int n) {
        int maior = getMaior(jogadores, n);

        // Criar arrays para contar as alturas e os índices originais
        int[] count = new int[maior + 1];
        int[] indicesOriginais = new int[n];
        Jogadores[] ordenado = new Jogadores[n];

        // Inicializar o array de contagem
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
            totalComparacoes++;
        }

        // Contar as alturas
        for (int i = 0; i < n; i++) {
            count[jogadores[i].altura]++;
            totalComparacoes++;
            totalMovimentacoes++;
        }

        // Calcular a posição correta de cada jogador
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
            totalComparacoes++;
            totalMovimentacoes++;
        }

        // Preencher o array ordenado, considerando desempate pelo nome
        for (int i = n - 1; i >= 0; i--) {
            int altura = jogadores[i].altura;
            int posicao = count[altura] - 1;
            ordenado[posicao] = jogadores[i];
            indicesOriginais[posicao] = i;
            count[altura]--;

            totalComparacoes++;
            totalMovimentacoes += 4;
        }

        // Copiar de volta para o array original, considerando desempate pelo nome
        for (int i = 0; i < n; i++) {
            jogadores[i] = ordenado[i].clone();
            totalMovimentacoes++;
        }

        // Realizar o desempate pelo nome
        for (int i = 1; i < n; i++) {
            int j = i;
            while (j > 0 && jogadores[j].altura == jogadores[j - 1].altura) {
                // Se as alturas são iguais, compare pelo nome
                if (jogadores[j].nome.compareTo(jogadores[j - 1].nome) < 0) {
                    // Trocar os jogadores de posição
                    Jogadores temp = jogadores[j];
                    jogadores[j] = jogadores[j - 1];
                    jogadores[j - 1] = temp;

                    totalComparacoes++;
                    totalMovimentacoes += 6;
                    // Atualizar os índices originais
                    int tempIndex = indicesOriginais[j];
                    indicesOriginais[j] = indicesOriginais[j - 1];
                    indicesOriginais[j - 1] = tempIndex;

                    j--;
                } else {
                    break;
                }
            }
        }
    }

    public static int getMaior(Jogadores[] jogadores, int n) {
        int maior = jogadores[0].altura;

        for (int i = 0; i < n; i++) {
            if (maior < jogadores[i].altura) {
                maior = jogadores[i].altura;
                totalMovimentacoes++;
            }
        }
        return maior;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);

        String caminhoDoArquivoCSV = "/tmp/players.csv"; // nome do arquivo
        String qualId; // inicia a procura de id como vazia
        int maxJogadores = 50000;
        Jogadores[] jogadores = new Jogadores[maxJogadores];
        int numJogadores = 0; // Número de jogadores lidos

        while (!(qualId = scanner.nextLine()).equals("FIM")) {
            ler(caminhoDoArquivoCSV, qualId, jogadores, numJogadores); // Passa numJogadores como argumento
            numJogadores++; // Incrementa o índice do próximo jogador após a leitura
        }

        CountingSort(jogadores, numJogadores); // Ordena apenas os jogadores lidos

        for (int i = 0; i < numJogadores; i++) {
            imprimir(jogadores, i);
        }
        scanner.close();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        String filename = "802736_coutingsort.txt";

        try {
            FileWriter outputFile = new FileWriter(filename);
            outputFile.write("802736\t");
            outputFile.write(totalComparacoes + "\t");
            outputFile.write(totalMovimentacoes + "\t");
            outputFile.write(totalTime + "\t");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}