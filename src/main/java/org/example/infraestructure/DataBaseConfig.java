package org.example.infraestructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConfig {
    //  declarar paramentros
    private static String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static String USER = "RM556898";
    private static String PASSWORD = "021298";

    // realizar conexão
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void createDropTables(){
        String dropTables = """
                DROP TABLE T_MUSIC;
                DROP TABLE T_ALBUM;
                DROP TABLE T_ARTIST;
                """;
        String createArtistTable = "CREATE TABLE T_ARTIST (" +
                "id_artist int GENERATED AS IDENTITY PRIMARY KEY, " +
                "nm_artist VARCHAR2(50) CONSTRAINT nn_nm_artist NOT NULL, " +
                "genre_artist VARCHAR2(50) CONSTRAINT nn_genre_artist NOT NULL)";

        String creatAlbumTable = "CREATE TABLE T_ALBUM (" +
                "id_album int GENERATED AS IDENTITY PRIMARY KEY," +
                "nm_album VARCHAR(100) CONSTRAINT  nn_nm_artist NOT NULL," +
                "year_album DATE CONSTRAINT nn_year_album NOT NULL," +
                "fk_artist int REFERENCES T_ARTIST (id_artist))";

        String createMusicTable = "CREATE TABLE T_MUSIC (" +
                "id_music int GENERATED AS IDENTITY PRIMARY KEY, " +
                        "title_music VARCHAR2(100) CONSTRAINT nn_title_music  NOT NULL, " +
                        "duration_music int CONSTRAINT  nn_duration NOT NULL, " +
                        "fk_album int REFERENCES T_ALBUM(id_album))";

        // Usando a classe DataBaseConfig para obter a conexão
        try (var conn = DataBaseConfig.getConnection();
             var stmt = conn.createStatement()) {

            //Dropa toda as tabelas para nao dar conflito
            stmt.executeUpdate(dropTables);

            // Criando a tabela T_ARTIST
            stmt.executeUpdate(createArtistTable);

            // Criando a tabela T_ALBUM
            stmt.executeUpdate(creatAlbumTable);

            // Criando a tabela T_MUSIC
            stmt.executeUpdate(createMusicTable);

            conn.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

