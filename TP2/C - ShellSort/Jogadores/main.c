#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

struct Player {
    int id, height, weight, birthYear;
    char name[100], university[100], birthCity[100], birthState[100];
};

typedef struct Player Player;

void printPlayer(Player player) {
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n",
           player.id, player.name, player.height, player.weight,
           player.birthYear, player.university,
           player.birthCity, player.birthState);
}

Player createPlayer(int id, char *name, int height, int weight, char *university, int birthYear, char *birthCity, char *birthState) {
    Player player;
    player.id = id;
    strcpy(player.name, name);
    player.height = height;
    player.weight = weight;
    strcpy(player.university, university);
    player.birthYear = birthYear;
    strcpy(player.birthCity, birthCity);
    strcpy(player.birthState, birthState);
    return player;
}

Player *findPlayerById(int targetId, FILE *file) {
    char line[1024];
    while (fgets(line, sizeof(line), file) != NULL) {
        int id;
        char name[100] = "nao informado", university[100] = "nao informado", birthCity[100] = "nao informado", birthState[100] = "nao informado";
        int height = 0, weight = 0, birthYear = 0;

        sscanf(line, "%d,%99[^,],%d,%d,%99[^,],%d,%99[^,],%99[^\n]",
               &id, name, &height, &weight, university, &birthYear, birthCity, birthState);

        if (id == targetId) {
            Player *player = malloc(sizeof(Player));
            player->id = id;
            strcpy(player->name, name);
            player->height = height;
            player->weight = weight;
            strcpy(player->university, university);
            player->birthYear = birthYear;
            strcpy(player->birthCity, birthCity);
            strcpy(player->birthState, birthState);
            return player;
        }
    }
    return NULL; // Retorna NULL se o jogador não for encontrado
}

int comparePlayers(struct Player a, struct Player b) {
    if (a.weight < b.weight) {
        return -1;
    } else if (a.weight > b.weight) {
        return 1;
    } else {
        // Em caso de peso igual, compare pelo nome
        return strcmp(a.name, b.name);
    }
}

void shellSort(struct Player players[], int n, int *comparisons, int *movements) {
    int h = 1;
    while (h < n / 3) {
        h = 3 * h + 1;
    }

    while (h >= 1) {
        for (int i = h; i < n; i++) {
            struct Player currentPlayer = players[i];
            int j = i;

            while (j >= h && comparePlayers(currentPlayer, players[j - h]) < 0) {
                (*comparisons)++; // Incrementa a contagem de comparações
                players[j] = players[j - h];
                (*movements) += 3; // Incrementa a contagem de movimentações (3 atributos são movidos)
                j -= h;
            }

            players[j] = currentPlayer;
        }
        h /= 3;
    }
}

int main() {
    const char *filename = "/tmp/players.csv";
    FILE *file = fopen(filename, "r");

    if (file == NULL) {
        printf("Arquivo não encontrado: %s\n", filename);
        return 1;
    }

    char line[1024];
    fgets(line, sizeof(line), file); // Ignorar a primeira linha (cabeçalho)

    Player *foundPlayers = malloc(sizeof(Player));
    int numFoundPlayers = 0;

    int comparisons = 0;  // Contador de comparações
    int movements = 0; // Contador de movimentações

    clock_t start = clock(); // Inicia a contagem de tempo

    while (1) {
        char input[100];
        scanf("%s", input);

        if (strcmp(input, "FIM") == 0) {
            break;
        }

        int targetId = atoi(input);

        Player *playerFound = findPlayerById(targetId, file);

        if (playerFound != NULL) {
            numFoundPlayers++;
            foundPlayers = realloc(foundPlayers, numFoundPlayers * sizeof(Player));
            foundPlayers[numFoundPlayers - 1] = *playerFound;
            free(playerFound);

        }
        fseek(file, 0, SEEK_SET); // Retornar ao início do arquivo
        fgets(line, sizeof(line), file); // Ignorar a primeira linha (cabeçalho) novamente
    }

    // Chamando a função de ordenação shellSort e calculando comparações e movimentações
    shellSort(foundPlayers, numFoundPlayers, &comparisons, &movements);

    // Imprimir os jogadores ordenados
    for (int i = 0; i < numFoundPlayers; i++) {
        printPlayer(foundPlayers[i]);
    }

    clock_t end = clock(); // Encerra a contagem de tempo
    double timeMicros = (double)(end - start) * 1e6 / CLOCKS_PER_SEC;

    fclose(file);
    free(foundPlayers);

    // Escrevendo as métricas no arquivo
    FILE *outputFile = fopen("802736_shellsort.txt", "w");
    fprintf(outputFile, "802736\t");
    fprintf(outputFile, "Comparacoes: %d\t", comparisons);
    fprintf(outputFile, "Movimentacoes: %d\t", movements);
    fprintf(outputFile, "Tempo em microssegundos: %ld\t", (long)timeMicros);
    fclose(outputFile);

    return 0;
}
