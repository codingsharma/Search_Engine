package com.example.search_engine;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MainClass extends Application {
    static Database_connection connect;
    @FXML
    private TextField text_field;
    @FXML
    private TextArea text_area;
    @FXML
    private Button search_button;
    @FXML
    private Button history_button;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("searchpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 450);
        stage.setTitle("Search Page!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        connect = new Database_connection();

//        ResultSet iterator = connect.executeQuery("select * from history");
//        while (iterator.next()){
//            String link_name = iterator.getString("link_name");
//            String link = iterator.getString("link");
//            System.out.println(link_name +"==="+link);
//        }

        launch();
    }
    public void searchbuttonclick(ActionEvent event) throws SQLException {
        // first enter the keyword into the history table

        String Keyword = text_field.getText();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        int respons = connect.executeUpdate("insert into search values ('"+Keyword+"','"+time+"');");
        assert (respons == 1);

        // search for the keyword

        String query = "Select link, link_name, time_stamp, (length(link) - length(replace(link, '" + Keyword + "', '')))/length('" + Keyword + "') as countofkeyword from history order by countofkeyword desc limit 5";

        ResultSet iterator = connect.executeQuery(query);

        ArrayList<SearchResult> searchResults = new ArrayList<>();

        while(iterator.next()){
            String link = iterator.getString("link");
            String link_name = iterator.getString("link_name");
            Timestamp time1 = iterator.getTimestamp("time_stamp");
            int count = iterator.getInt("countofkeyword");

            searchResults.add(new SearchResult(link, link_name, time1));
        }

        StringBuilder show = new StringBuilder();
        String space = "     ";
        String nextline = "\n";

        for(SearchResult current: searchResults){
            show.append(current.getLink_name()).append(space).append(current.getLink()).append(space).append(current.getTime()).append(nextline);
        }

        text_area.setText(show.toString());

    }
    public void historybuttonclick(ActionEvent event) throws SQLException {
        // show top 10 search history
        ResultSet iterator = connect.executeQuery("select * from search order by time_stamp desc limit 10;");

        ArrayList<HistoryResult> historyResults = new ArrayList<>();

        while (iterator.next()) {
            String Keyword = iterator.getString("keyword");
            Timestamp time = iterator.getTimestamp("time_stamp");

            System.out.println(Keyword + "===" + time);
            historyResults.add(new HistoryResult(Keyword, time));
        }
            // displaying in text area
            StringBuilder show = new StringBuilder();
            String space = "              ";
            String nextline = "\n";
            show.append("Keyword").append(space).append("Time").append(nextline).append(nextline);

            for(HistoryResult current: historyResults){
                show.append(current.getKeyword()).append(space).append(current.getTimestamp()).append(nextline);
            }

            text_area.setText(show.toString());

    }
}