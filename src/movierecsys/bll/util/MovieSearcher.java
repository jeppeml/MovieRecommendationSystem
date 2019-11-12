/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.bll.util;

import java.util.ArrayList;
import java.util.List;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class MovieSearcher {

    public List<Movie> search(List<Movie> searchBase, String query) {
        // Case insensitive
        // Partial search *sha*
        List<Movie> filtered = new ArrayList<>();

        if (query.isEmpty()) {
            return searchBase;
        }

        for (Movie movie : searchBase) {
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(movie);
            }
        }

        return filtered;
    }

}
