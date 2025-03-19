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

    public static void QuickSortParcial(Jogadores[] jogadores, int esq, int dir, int k) {
        if (esq < dir) {
            int i = esq, j = dir;

            Jogadores pivo = jogadores[(esq + dir) / 2];

            while (i <= j) {
                while (jogadores[i].estadoNascimento != null
                        && (jogadores[i].estadoNascimento.compareTo(pivo.estadoNascimento) < 0
                                || (jogadores[i].estadoNascimento.equals(pivo.estadoNascimento)
                                        && jogadores[i].nome.compareTo(pivo.nome) < 0))) {
                    i++;
                }
                while (jogadores[j].estadoNascimento != null
                        && (jogadores[j].estadoNascimento.compareTo(pivo.estadoNascimento) > 0
                                || (jogadores[j].estadoNascimento.equals(pivo.estadoNascimento)
                                        && jogadores[j].nome.compareTo(pivo.nome) > 0))) {
                    j--;
                }
                if (i <= j) {
                    Jogadores tmp = jogadores[i];
                    jogadores[i] = jogadores[j];
                    jogadores[j] = tmp;
                    i++;
                    j--;
                }
            }

            QuickSortParcial(jogadores, esq, j, k);
            QuickSortParcial(jogadores, i, dir, k);
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);

        String caminhoDoArquivoCSV = "/tmp/players.csv"; // nome do arquivo
        String qualId; // inicia a procura de id como vazia
        int maxJogadores = 50000;
        Jogadores[] jogadores = new Jogadores[maxJogadores];

        // Inicialize o array com objetos Jogadores
        for (int i = 0; i < maxJogadores; i++) {
            jogadores[i] = new Jogadores();
        }

        int numJogadores = 0; // Adicione esta variável para rastrear o número de jogadores lidos

        while (!(qualId = scanner.nextLine()).equals("FIM")) {
            ler(caminhoDoArquivoCSV, qualId, jogadores, numJogadores); // Passe numJogadores como argumento
            numJogadores++; // Incremente o número de jogadores após a leitura
        }

        int k = 10;
        QuickSortParcial(jogadores, 0, numJogadores - 1, k); // Ordene apenas os jogadores válidos

        for (int i = 0; i < k && i < numJogadores; i++) {
            imprimir(jogadores, i);
        }
        scanner.close();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        
    }
}
