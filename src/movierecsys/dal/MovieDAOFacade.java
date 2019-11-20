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
 * @author jeppjleemoritzled
 */
public interface MovieDAOFacade {
    
    public List<Movie> getAllMovies();
    
    public Movie createMovie(int releaseYear, String title);
    
    public void deleteMovie(Movie movie);
   
    public void updateMovie(Movie movie);
    
    public Movie getMovie(int id);
    
}
