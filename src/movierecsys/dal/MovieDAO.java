/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import movierecsys.be.Movie;

/**
 *
 * @author pgn, jle
 */
public class MovieDAO
{
    public static void main(String[] args) {
        System.out.println("Hello world!");
        
        MovieDAO dao = new MovieDAO();
        //dao.createMovie(2019, "Joker");
        Movie movie3 = dao.getMovie(3);
        System.out.println("Movie: " + movie3.getTitle());
        movie3.setTitle("Character");
        
        //dao.deleteMovie(movie2);
        dao.updateMovie(movie3);
        
        System.out.println("Movie: " + dao.getMovie(3));
        
    }

    private static final String MOVIE_SOURCE = "data/movie_titles.txt";

    private void clearAll() throws IOException {
        // Ereases the content of the file without deleting it.
        RandomAccessFile raf = new RandomAccessFile(MOVIE_SOURCE, "rw");
        raf.setLength(0);
    }
    
    private void saveAll(List<Movie> movies) throws IOException {
        clearAll(); // deletes contents of file
        
        try (BufferedWriter bw
                = new BufferedWriter(
                        new FileWriter(MOVIE_SOURCE, true))) {
            for (Movie movie : movies) {

                bw.append(movie.getId() + "," + movie.getYear() + "," + movie.getTitle());
                bw.newLine();
            }
        }
    }
    
    /**
     * Gets a list of all movies in the persistence storage.
     *
     * @return List of movies.
     * @throws java.io.FileNotFoundException
     */
    public List<Movie> getAllMovies() throws FileNotFoundException, IOException
    {
        List<Movie> allMovies = new ArrayList<>();
        File file = new File(MOVIE_SOURCE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                try
                {
                    Movie mov = stringArrayToMovie(line);
                    allMovies.add(mov);
                } catch (Exception ex)
                {
                    //Do nothing we simply do not accept malformed lines of data.
                    //In a perfect world you should at least log the incident.
                }
            }
        }
        return allMovies;
    }

    /**
     * Reads a movie from a , s
     *
     * @param t
     * @return
     * @throws NumberFormatException
     */
    private Movie stringArrayToMovie(String t)
    {
        String[] arrMovie = t.split(",");

        int id = Integer.parseInt(arrMovie[0]);
        int year = Integer.parseInt(arrMovie[1]);
        String title = arrMovie[2];

        Movie mov = new Movie(id, year, title);
        return mov;
    }

    /**
     * Creates a movie in the persistence storage.
     *
     * @param releaseYear The release year of the movie
     * @param title The title of the movie
     * @return The object representation of the movie added to the persistence
     * storage.
     */
    public Movie createMovie(int releaseYear, String title)
    {
        int id = getNextAvailableId();
        try (BufferedWriter bw
                = new BufferedWriter(
                        new FileWriter(MOVIE_SOURCE, true))) {
            bw.append(id + "," + releaseYear + "," + title);
            bw.newLine();
        } catch (IOException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Deletes a movie from the persistence storage.
     *
     * @param movie The movie to delete.
     */
    public void deleteMovie(Movie movie)
    {
        try {
            List<Movie> allMovies = getAllMovies();
            Movie movieToRemove = null;
            for (Movie m : allMovies) {
                if(movie.getId()==m.getId())
                    movieToRemove = m;
            }
            
            if(movieToRemove!=null)
                allMovies.remove(movieToRemove);
            
            saveAll(allMovies);
            
        } catch (IOException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Updates the movie in the persistence storage to reflect the values in the
     * given Movie object.
     *
     * @param movie The updated movie.
     */
    public void updateMovie(Movie movie)
    {
        try {
            List<Movie> allMovies = getAllMovies();
            Movie movieToUpdate = null;
            for (Movie m : allMovies) {
                if(movie.getId()==m.getId())
                    movieToUpdate = m;
            }
            
            if(movieToUpdate!=null){
                movieToUpdate.setTitle(movie.getTitle());
                movieToUpdate.setYear(movie.getYear());
            }
            saveAll(allMovies);
            
        } catch (IOException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets a the movie with the given ID.
     *
     * @param id ID of the movie.
     * @return A Movie object.
     */
    public Movie getMovie(int id)
    {
        try {
            List<Movie> movies = getAllMovies();
            
            for (Movie movie : movies) {
                if(id==movie.getId())
                    return movie;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Movie not found
    }

    private int getNextAvailableId() {
        try {
            List<Movie> movies = getAllMovies();
            int largestId = -1;
            
            for (Movie movie : movies) {
                if(largestId<movie.getId())
                    largestId = movie.getId();
            }
            
            return largestId + 1;
            
        } catch (IOException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; // Something is rotten
    }

}
