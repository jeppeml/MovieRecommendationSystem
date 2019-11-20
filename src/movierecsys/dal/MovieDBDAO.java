/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import movierecsys.be.Movie;

/**
 *
 * @author jeppjleemoritzled
 */
public class MovieDBDAO implements MovieDAOFacade {

    private SQLServerDataSource ds;

    public static void main(String[] args) {
        MovieDBDAO dao = new MovieDBDAO();
        MovieDAO fileDAO = new MovieDAO();

        List<Movie> movies = fileDAO.getAllMovies();

        for (Movie movie : movies) {
            // dao.createMovie(movie.getId(), movie.getYear(), movie.getTitle());
        }

    }

    public MovieDBDAO() {
        ds = new SQLServerDataSource();
        ds.setDatabaseName("CSe19B_40_MRS");
        ds.setUser("CSe19B_40");
        ds.setPassword("CSe19B_40");
        ds.setServerName("10.176.111.31");
        ds.setPortNumber(1433);
    }

    private int getNextAvailableId() {
        try (Connection con = ds.getConnection()) {
            String sql = "SELECT TOP (1) id FROM Movies ORDER BY id DESC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("id") + 1;
            }

        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public Movie createMovie(int releaseYear, String title) {
        int id = getNextAvailableId();

        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO Movies (id, year, title) VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, releaseYear);
            pstmt.setString(3, title);

            pstmt.executeUpdate();

            return new Movie(id, releaseYear, title);

        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Movie> getAllMovies() {
        try (Connection con = ds.getConnection()) {
            
            String sql = "SELECT * FROM Movies";
            Statement stmt = con.createStatement();
            List<Movie> movies = new ArrayList();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int year = rs.getInt("year");
                String title = rs.getString("title");
                
                Movie m = new Movie(id, year, title);
                movies.add(m);
            }
            return movies;
            
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteMovie(Movie movie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateMovie(Movie movie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Movie getMovie(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
