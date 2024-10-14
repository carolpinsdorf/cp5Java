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

public class MusicRepo implements _BaseRepo<Music>{
    // inicializar o logger
    protected Logger<Music> logger;
    public MusicRepo(){
        MusicRepoLogger(Music.class);
    }
    public void MusicRepoLogger(Class<Music> tClass){
        this.logger = new Log4jLogger<>(tClass);
    }

    // CRUD BÁSICO
    @Override
    public void Insert(Music music) {
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "INSERT INTO T_MUSIC (id_music, title_music, duration_music, fk_album) VALUES (default,?, ?, ?)";
            var stmt = conn.prepareStatement(query);

            stmt.setString(1, music.getTitle());
            stmt.setInt(2,music.getDuration());
            stmt.setInt(3, music.getAlbum().getId());
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            logger.logCreate(music);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Update(Music music, int id) {
        try{
            var conn = DataBaseConfig.getConnection();
            var query = "UPDATE T_MUSIC SET title_music = ?, duration_music = ?, fk_album = ? WHERE id_music = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setString(1, music.getTitle());
            stmt.setInt(2,music.getDuration());
            stmt.setInt(3, music.getAlbum().getId() );
            stmt.setInt(4, id);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            logger.logUpdate(music);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(int id) {
        try {
            var conn = DataBaseConfig.getConnection();
            var query = "DELELE FROM T_MUSIC WHERE id_music = ?";
            var stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            conn.close();
            stmt.close();

            logger.logDelete(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Optional<Music> GetByIid(int id) {
        Optional<Music> music = Optional.empty();
        try{
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT * FROM T_MUSIC WHERE id_music = ?";
            var stmt =  conn.prepareStatement(query);

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()){
                var _id = rs.getInt("id_music");
                var title = rs.getString("title_music");
                var duration = rs.getInt("duration_music");
                var fk_album = rs.getInt("fk_album");

                Optional<Album> albumOptional = AlbumRepo.GetByIid(fk_album);
                var album = albumOptional.orElse(null);

                music = Optional.of(new Music(_id,title, duration,album));
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return music;
    }

    @Override
    public List<Music> GetAll() {
        var musics = new ArrayList<Music>();

        try {
            var conn = DataBaseConfig.getConnection();
            var query = "SELECT * FROM T_MUSIC ORDER BY id_music";
            var stmt = conn.prepareStatement(query);
            var rs = stmt.executeQuery();

            while (rs.next()){
                var id  = rs.getInt("id_music");
                var title = rs.getString("title_music");
                var duration = rs.getInt("duration_music");
                var fk_album = rs.getInt("fk_album");

                Optional<Album> albumOptional = AlbumRepo.GetByIid(fk_album);
                var album = albumOptional.orElse(null);

                musics.add(new Music(id, title,duration, album));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musics;
    }
}
