/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class FileReaderTester {

    /**
     * Example method. This is the code I used to create the users.txt files.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileReaderTester test = new FileReaderTester();
        test.testGetAllMovies();
        test.testGetOneMovie();
    }

    public void testGetAllMovies() {

        MovieDAO movieDao = new MovieDAO();
        List<Movie> allMovs = movieDao.getAllMovies();
        for (Movie allMov : allMovs) {
            System.out.println(allMov.getTitle());
        }
        System.out.println("Movie count: " + allMovs.size());

    }

    public void testGetOneMovie() {
        MovieDAO movieDao = new MovieDAO();
        System.out.println("Looking for movie id 45");
        System.out.println("Movie 45:" + movieDao.getMovie(45));
    }
}
