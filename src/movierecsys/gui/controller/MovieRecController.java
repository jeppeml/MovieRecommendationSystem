/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import movierecsys.be.Movie;
import movierecsys.bll.OwsLogicFacade;
import movierecsys.bll.OwsManager;

/**
 *
 * @author pgn
 */
public class MovieRecController implements Initializable
{

    /**
     * The TextField containing the URL of the targeted website.
     */
    @FXML
    private TextField txtMovieSearcjh;

    /**
     * The TextField containing the query word.
     */
    @FXML
    private ListView<Movie> lstMovies;
    
    private OwsLogicFacade owsfacade = new OwsManager();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       List<Movie> movies = owsfacade.searchMovies("din");
       lstMovies.getItems().clear();
       lstMovies.getItems().addAll(movies);
    }

}