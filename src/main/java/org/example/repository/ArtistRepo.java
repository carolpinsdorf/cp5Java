package org.example.repository;

import org.example.entities.Album;
import org.example.entities.Artist;
import org.example.infraestructure.DataBaseConfig;
import org.example.utils.Log4jLogger;
import org.example.utils.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArtistRepo implements  _BaseRepo<Artist> {

    // inicializar o logger
    protected Logger<Artist> logger;
    public ArtistRepo(){
        ArtistRepoLogger(Artist.class);
    }
    public void ArtistRepoLogger(Class<Artist> tClass){
        this.logger = new Log4jLogger<>(tClass);
    }

    // MÉTODOS ESPECÍFICOS
    public List<Artist> searchedByGenre(String genre) {
        List<Artist> artistList = new ArrayList<>();
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT * FROM T_ARTIST WHERE genre_artist = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setString(1, genre);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                var _id = rs.getInt("id_artist");
                var name = rs.getString("nm_artist");
                var genreFromDb = rs.getString("genre_artist"); // Pode ser útil se precisar comparar
                artistList.add(new Artist(_id, name, genreFromDb, new ArrayList<Album>()));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artistList;
    }


    public Artist findById(List<Artist> artists, int id) {
        Artist artist = null;
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT id_artist, nm_artist, genre_artist FROM T_ARTIST WHERE id_artist = ?";
            var stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                // Cria o objeto Artist com os dados do banco
                artist = new Artist(rs.getInt("id_artist"), rs.getString("nm_artist"), rs.getString("genre_artist"), new ArrayList<>());
            }

            // Fechar recursos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artist; // Retorna o  artista encontrado ou null se não encontrado
    }

    // CRUD BÁSICO
    @Override
    public void Insert(Artist artist) {
        try {
            // REGISTRAR DRIVER
            var conn = DataBaseConfig.getConnection();
            // CRIAR A QUERY
            var query =
                    "INSERT INTO T_ARTIST (id_artist,nm_artist,genre_artist) VALUES (default,?,?)";
            // DEFINIR STATEMENT
            var stmt = conn.prepareStatement(query);
            // SETTTAR VALORES DA QUERY
            stmt.setString(1, artist.getName());
            stmt.setString(2, artist.getGenre());
            // EXECUTAR QUERY
            stmt.executeUpdate();
            // ENCERAR STATEMENT
            stmt.close();
            // ENCERRAR CONEXAO
            conn.close();

            logger.logCreate(artist);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Update(Artist artist, int id) {
        try{
            var conn = DataBaseConfig.getConnection();
            var query = "UPDATE T_ARTIST SET nm_artist = ?, genre_artist = ? WHERE id_artist = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setString(1, artist.getName());
            stmt.setString(2, artist.getGenre());
            stmt.setInt(3,id);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            logger.logUpdate(artist);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(int id) {
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "DELETE FROM T_ARTIST WHERE id_artist = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setInt(1,id);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            logger.logDelete(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Optional<Artist> GetByIid(int id) {
        Optional<Artist> artist = Optional.empty();
        try{
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT * FROM T_ARTIST WHERE id_artist = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()){
                var _id  = rs.getInt("id_artist");
                var name = rs.getString("nm_artist");
                var genre = rs.getString("genre_artist");
                artist = Optional.of(new Artist(_id, name, genre,  new ArrayList<Album>()));
            }

            rs.close();
            conn.close();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println("Erro ao buscar artista com ID: "+ id);
            e.printStackTrace();
        }
        return artist;
    }

    @Override
    public List<Artist> GetAll() {
        var artists = new ArrayList<Artist>();
        try{
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT * FROM T_ARTIST ORDER BY id_artist";
            var stmt = conn.prepareStatement(query);
            var rs = stmt.executeQuery();

            while(rs.next()){
                var id = rs.getInt("id_artist");
                var name = rs.getString("nm_artist");
                var genre = rs.getString("genre_artist");
                artists.add(new Artist(id,name,genre, new ArrayList<Album>()));
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return artists;
    }
}
