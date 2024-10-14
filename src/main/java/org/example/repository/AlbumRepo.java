package org.example.repository;

import org.example.entities.Album;
import org.example.entities.Artist;
import org.example.entities.Music;
import org.example.infraestructure.DataBaseConfig;
import org.example.utils.Log4jLogger;
import org.example.utils.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlbumRepo implements  _BaseRepo<Album> {

    // inicializar o logger
    protected Logger<Album> logger;
    public AlbumRepo(){
        AlbumRepoLogger(Album.class);
    }
    public void AlbumRepoLogger(Class<Album> tClass){
        this.logger = new Log4jLogger<>(tClass);
    }

    // MÉTODOS ESPECÍFICOS
    public List<Album> searchByYear(int year) {
        List<Album> albumList = new ArrayList<>();
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT * FROM T_ALBUM WHERE year_album = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setInt(1, year);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                var _id = rs.getInt("id_album");
                var name = rs.getString("nm_album");
                var yearLaunched = rs.getInt("year_album");
                var fk_artist = rs.getInt("fk_artist");

                Optional<Artist> artistOptional = ArtistRepo.GetByIid(fk_artist);
                var artist = artistOptional.orElse(null);

                albumList.add(new Album(_id, name, yearLaunched, artist, new ArrayList<>()));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albumList;
    }


    // CRUD BÁSICO
    @Override
    public void Insert(Album album) {
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "INSERT INTO T_ALBUM (id_album, nm_album, year_album, fk_artist) VALUES (default, ?, ?, ?)";
            var stmt = conn.prepareStatement(query);

            stmt.setString(1, album.getName());
            stmt.setInt(2, album.getYearLaunched());
            stmt.setInt(3, album.getArtist().getId());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            logger.logCreate(album);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Update(Album album, int id) {
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "UPDATE T_ALBUM SET name_album = ?, year_album = ?, fk_artist = ? WHERE id_album = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setString(1, album.getName());
            stmt.setInt(2, album.getYearLaunched());
            stmt.setInt(3, album.getArtist().getId());
            stmt.setInt(4, id);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            logger.logUpdate(album);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(int id) {
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "DELETE FROM T_ALBUM WHERE id_album = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            logger.logDelete(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  static Optional<Album> GetByIid(int id) {
        Optional<Album> album = Optional.empty();
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT * FROM T_ALBUM WHERE id_album = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                var _id = rs.getInt("id_album");
                var name = rs.getString("nm_album");
                var year = rs.getInt("year_album");
                var fk_artist = rs.getInt("fk_artist"); //

                Optional<Artist> artistOptional = ArtistRepo.GetByIid(fk_artist);
                var artist = artistOptional.orElse(null);

                album = Optional.of(new Album(_id, name, year, artist, new ArrayList<Music>()));
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return album;
    }

    public List<Album> GetAll() {
        List<Album> albums = new ArrayList<>();
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT * FROM T_ALBUM";
            var stmt = conn.prepareStatement(query);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                var _id = rs.getInt("id_album");
                var name = rs.getString("nm_album");
                var year = rs.getInt("year_album");
                var fk_artist = rs.getInt("fk_artist");

                Optional<Artist> artistOptional = ArtistRepo.GetByIid(fk_artist);
                var artist = artistOptional.orElse(null);

                albums.add(new Album(_id, name, year, artist, new ArrayList<Music>()));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albums;
    }
}
