package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScoreController {
    @FXML private TableView<Result> table;
    @FXML private TableColumn<Result, String> wordColumn;
    @FXML private TableColumn<Result, String> chosenArtikelColumn;
    @FXML private TableColumn<Result, String> correctArtikelColumn;
    @FXML private Label score;

    public void displayScore(int correct, int total){
        if(total == 0){
            this.score.setText("No Score");
        }
        else{
            double score = ((double)correct/(double)total)*(double)100;
            score = Math.round(score*10000)/10000.0;
            this.score.setText("Success Percentage = "+score+'%');
        }
    }
    public void makeTable(ObservableList<Result> results){
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        chosenArtikelColumn.setCellValueFactory(new PropertyValueFactory<>("chosenArtikel"));
        correctArtikelColumn.setCellValueFactory(new PropertyValueFactory<>("correctArtikel"));

        table.setItems(results);
        table.getColumns().addAll(wordColumn, chosenArtikelColumn, correctArtikelColumn);
    }
}
