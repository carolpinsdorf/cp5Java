package org.example.main;

import org.example.entities.Album;
import org.example.entities.Artist;
import org.example.entities.Music;
import org.example.infraestructure.DataBaseConfig;
import org.example.repository.AlbumRepo;
import org.example.repository.ArtistRepo;
import org.example.repository.MusicRepo;
import org.example.service.EntityValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // inicializando listas
        List<Artist> artists = new ArrayList<>();
        List<Album> albums = new ArrayList<>();
        List<Music> musics = new ArrayList<>();

        // inicializando repositorios
        ArtistRepo artistRepo = new ArtistRepo();
        AlbumRepo albumRepo = new AlbumRepo();
        MusicRepo musicRepo = new MusicRepo();

        // incializando validator
        EntityValidator validator = new EntityValidator();

        // incializando scanner
        Scanner scanner = new Scanner(System.in);
        int option = 0; // declarando variaveis

        // criando as tabelas
        DataBaseConfig.createDropTables();

        System.out.println("Sistema iniciando...");

        while (option != 7) {
            // menu de opcoes
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar um novo artista");
            System.out.println("2. Cadastrar um novo álbum");
            System.out.println("3. Cadastrar uma nova música");
            System.out.println("4. Listar todos os artistas, álbuns ou músicas");
            System.out.println("5. Buscar artista por gênero");
            System.out.println("6. Buscar álbum por ano");
            System.out.println("7. Sair da aplicação");
            System.out.print("Escolha uma opção: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // CADASTRAR NOVO ARTISTA
                    System.out.print("Nome do artista: ");
                    String artistName = scanner.nextLine();
                    if (validator.checkString(artistName)) {
                        System.out.println("Campo obrigatório!");
                    } else {
                        System.out.print("Gênero do artista: ");
                        String genre = scanner.nextLine();
                        if (validator.checkString(genre)) {
                            System.out.println("Campo obrigatório");
                        }else{
                            Artist artist = new Artist(0, artistName, genre, new ArrayList<>());
                            artists.add(artist); // Adiciona o artista à lista
                            artistRepo.Insert(artist); // Salva no banco de dados
                            var nm_artista = artist.getName();
                            System.out.println("Artista " + nm_artista +" cadastrado com sucesso!");
                            break;
                        }
                    }
                    break;
                case 2:
                    // CADASTRAR NOVO ALBUM
                    System.out.print("Nome do álbum: ");
                    String title = scanner.nextLine();
                    if (validator.checkString(title)) {
                        System.out.println("Campo obrigatório!");
                        break;
                    }else {
                        System.out.print("Ano de lançamento do álbum: ");
                        int year = scanner.nextInt();
                        scanner.nextLine(); // Consome a linha restante após nextInt()
                        if (validator.checkYear(year)) {
                            System.out.println("Ano inválido. O ano deve estar entre 1900 e o ano atual.");
                            break;
                        }else {

                            // associando album a um artista
                            System.out.print("ID do artista (para associar o álbum): ");
                            int artistIDForAlbum = scanner.nextInt();
                            scanner.nextLine(); // Consome a linha restante após nextInt()
                            // Verifica se o artista com o ID fornecido existe
                            Artist artistForAlbum = artistRepo.findById(artists, artistIDForAlbum);
                            if (artistForAlbum == null) {
                                System.out.println("Artista não encontrado. Certifique-se de que o ID está correto.");
                                break;
                            }else {
                                Album album = new Album(0,title,year,artistForAlbum,new ArrayList<>());
                                albumRepo.Insert(album);  // Salva o álbum no banco de dados
                                albums.add(album); // Adiciona o álbum à lista
                                var nm_album = album.getName();
                                System.out.println("Álbum " + nm_album + " cadastrado com sucesso!");
                                break;
                            }
                        }
                    }
                case 3:
                    // CADASTRAR NOVA MÚSICA
                    System.out.print("Título da música: ");
                    String musicTitle = scanner.nextLine();
                    if (validator.checkString(musicTitle)) {
                        System.out.println("Campo obrigatório!");
                        break;
                    }else {
                        System.out.print("Duração da música (em segundos): ");
                        int duration = scanner.nextInt();
                        scanner.nextLine(); // Consome a linha restante após nextInt()
                        if (validator.checkDuration(duration)) {

                            // Relacionando album com musica
                            System.out.print("ID do álbum (para associar a música): ");
                            int albumIDForMusic = scanner.nextInt();
                            scanner.nextLine(); // Consome a linha restante após nextInt()
                            // Verifica se o álbum com o ID fornecido existe
                            Optional<Album> albumForMusic = albumRepo.GetByIid(albumIDForMusic);
                            if (!albumForMusic.isPresent()) {
                                System.out.println("Álbum não encontrado. Certifique-se de que o ID está correto.");
                                break;
                            }else {
                                Album album = albumForMusic.get();
                                Music music = new Music(0, musicTitle, duration, album);
                                musicRepo.Insert(music);  // Salva a música no banco
                                musics.add(music); // adiciona a musica a lisra
                                var title_music = music.getTitle();
                                System.out.println("Música " + title_music + " cadastrada com sucesso!");
                                break;
                            }

                        } else {
                            System.out.println("Duração inválida");
                            break;
                        }
                    }
                case 4:
                    // Listar Artistas
                    System.out.println("\nArtistas:");
                    List<Artist> artistsList = artistRepo.GetAll();
                    if (artistsList.isEmpty()) {
                        System.out.println("Nenhum artista cadastrado.");
                    } else {
                        for (Artist artistList : artistsList) {
                            System.out.println("ID: " + artistList.getId() + ", Nome: " + artistList.getName() + ", Gênero: " + artistList.getGenre());
                        }
                    }
                    // Listar Álbuns
                    System.out.println("\nÁlbuns:");
                    List<Album> albumsList = albumRepo.GetAll();
                    if (albumsList.isEmpty()) {
                        System.out.println("Nenhum álbum cadastrado.");
                    } else {
                        for (Album albumList : albumsList) {
                            System.out.println("ID: " + albumList.getId() + ", Nome: " + albumList.getName() + ", Ano: " + albumList.getYearLaunched() +
                                    ", Artista ID: " + albumList.getArtist().getId());
                        }
                    }
                    // Listar Músicas
                    System.out.println("\nMúsicas:");
                    List<Music> musicsList = musicRepo.GetAll();
                    if (musicsList.isEmpty()) {
                        System.out.println("Nenhuma música cadastrada.");
                    } else {
                        for (Music musicList : musicsList) {
                            System.out.println("ID: " + musicList.getId() + ", Título: " + musicList.getTitle() + ", Duração: " + musicList.getDuration() +
                                    " segundos, Álbum ID: " + musicList.getAlbum().getId());
                        }
                    }
                    break;
                case 5:
                    // BUSCAR ARTISTA POR GÊNERO
                    System.out.print("Digite o gênero do artista que deseja buscar: ");
                    String genreToSearch = scanner.nextLine();

                    // Verifica se o gênero não está vazio
                    if (validator.checkString(genreToSearch)) {
                        System.out.println("Campo obrigatório! Por favor, insira um gênero.");
                        break;
                    } else {
                        // Busca os artistas pelo gênero
                        List<Artist> artistsByGenre = artistRepo.searchedByGenre(genreToSearch);

                        // Exibe os resultados da busca
                        if (artistsByGenre.isEmpty()) {
                            System.out.println("Nenhum artista encontrado para o gênero: " + genreToSearch);
                        } else {
                            System.out.println("\nArtistas encontrados para o gênero " + genreToSearch + ":");
                            for (Artist artistSearched : artistsByGenre) {
                                System.out.println("ID: " + artistSearched.getId() + ", Nome: " + artistSearched.getName() + ", Gênero: " + artistSearched.getGenre());
                            }
                        }
                    }
                    break;
                case 6:
                    System.out.print("Digite o ano do álbum que deseja buscar: ");
                    int yearToSearch = scanner.nextInt();

                    // Verifica se o ano é válido
                    if (validator.checkYear(yearToSearch)) {
                        System.out.println("Ano inválido. Por favor, insira um ano entre 1900 e o ano atual.");
                        break;
                    }else {
                        // Busca os álbuns pelo ano
                        List<Album> albumsByYear = albumRepo.searchByYear(yearToSearch);

                        // Exibe os resultados da busca
                        if (albumsByYear.isEmpty()) {
                            System.out.println("Nenhum álbum encontrado para o ano: " + yearToSearch);
                        } else {
                            System.out.println("\nÁlbuns encontrados para o ano " + yearToSearch + ":");
                            for (Album albumSearched : albumsByYear) {
                                System.out.println("ID: " + albumSearched.getId() + ", Nome: " + albumSearched.getName() + ", Ano: " +
                                        albumSearched.getYearLaunched() + ", Artista ID: " + albumSearched.getArtist().getId());
                            }
                        }
                    }
                    break;
                case 7:
                    System.out.println("Saindo da aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}

