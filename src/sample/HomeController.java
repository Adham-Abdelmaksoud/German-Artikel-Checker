package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML private Label wordLabel;
    @FXML private Label errorLabel;
    @FXML private RadioButton derButton;
    @FXML private RadioButton dasButton;
    @FXML private RadioButton dieButton;
    @FXML private Shape derEllipse;
    @FXML private Shape dasEllipse;
    @FXML private Shape dieEllipse;

    private ToggleGroup group;
    private String[] words;
    private int[] state;
    private int index;
    private int size;
    private ObservableList<Result> results;
    private int totalAnswers;
    private int correctAnswers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            DatabaseConnector connector = new DatabaseConnector();

            Statement statement1 = connector.getConnection().createStatement();
            ResultSet resultSet1 = statement1.executeQuery("select count(*) from artikeln;");
            size = 0;
            while(resultSet1.next()){
                size = resultSet1.getInt(1);
            }

            Statement statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select wort from artikeln order by wort;");
            words = new String[size];
            state = new int[size];
            results = FXCollections.observableArrayList();
            int i = 0;
            while(resultSet.next()){
                words[i] = resultSet.getString("wort");
                state[i] = 0;
                i++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //index = 0;
        totalAnswers = 0;
        correctAnswers = 0;
        index = (int)(Math.random()*size);
        wordLabel.setText(words[index]);
        errorLabel.setText("");

        group = new ToggleGroup();
        derButton.setToggleGroup(group);
        dasButton.setToggleGroup(group);
        dieButton.setToggleGroup(group);

        derEllipse.toBack();
        dasEllipse.toBack();
        dieEllipse.toBack();
    }

    public void skipClicked(){
        //index++;
        index = (int)(Math.random()*size);
        wordLabel.setText(words[index]);
        errorLabel.setText("");
        group.selectToggle(null);
        derEllipse.setStroke(Color.TRANSPARENT);
        dasEllipse.setStroke(Color.TRANSPARENT);
        dieEllipse.setStroke(Color.TRANSPARENT);
    }

    public void exitClicked(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Score.fxml"));
        Parent root = loader.load();
        ScoreController controller = loader.getController();
        controller.displayScore(correctAnswers, totalAnswers);
        controller.makeTable(results);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void answerChosen(ActionEvent event){
        try{
            String wort = wordLabel.getText();
            String artikel = "";
            DatabaseConnector connector = new DatabaseConnector();
            Statement statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select artikel from artikeln where wort="+'"'+wort+'"');
            while(resultSet.next()){
                artikel = resultSet.getString("artikel");
            }
            if(artikel.equals("Der")){
                if(group.getSelectedToggle().equals(derButton)){
                    if(state[index] == 0){
                        state[index] = 1;
                        correctAnswers++;
                        totalAnswers++;
                    }
                    skipClicked();
                }
                else{
                    if(state[index] == 0){
                        state[index] = -1;
                        totalAnswers++;
                        if(dasButton.isSelected()){
                            results.add(new Result(words[index], "Das", "Der"));
                        }
                        if(dieButton.isSelected()){
                            results.add(new Result(words[index], "Die", "Der"));
                        }
                    }
                    errorLabel.setText("WRONG ANSWER!!!");
                    derEllipse.setStroke(Color.RED);
                }
            }
            if(artikel.equals("Das")){
                if(group.getSelectedToggle().equals(dasButton)){
                    if(state[index] == 0){
                        state[index] = 1;
                        correctAnswers++;
                        totalAnswers++;
                    }
                    skipClicked();
                }
                else{
                    if(state[index] == 0){
                        state[index] = -1;
                        totalAnswers++;
                        if(derButton.isSelected()){
                            results.add(new Result(words[index], "Der", "Das"));
                        }
                        if(dieButton.isSelected()){
                            results.add(new Result(words[index], "Die", "Das"));
                        }
                    }
                    errorLabel.setText("WRONG ANSWER!!!");
                    dasEllipse.setStroke(Color.RED);
                }
            }
            if(artikel.equals("Die")){
                if(group.getSelectedToggle().equals(dieButton)){
                    if(state[index] == 0){
                        state[index] = 1;
                        correctAnswers++;
                        totalAnswers++;
                    }
                    skipClicked();
                }
                else{
                    if(state[index] == 0){
                        state[index] = -1;
                        totalAnswers++;
                        if(derButton.isSelected()){
                            results.add(new Result(words[index], "Der", "Die"));
                        }
                        if(dasButton.isSelected()){
                            results.add(new Result(words[index], "Das", "Die"));
                        }
                    }
                    errorLabel.setText("WRONG ANSWER!!!");
                    dieEllipse.setStroke(Color.RED);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
