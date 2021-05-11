package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import services.Service;
import classes.Trip;
import classes.Stop;

import java.util.List;
import java.util.Set;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * Graafinen käyttöliittymä
 */
public class GUI extends Application {

    private Stage stage;
    private Service s;

    /**
     * Käyttöliittymä suorittaa kaiken sovelluslogiikkaan liittyvän
     * toiminnallisuuden Service-luokan kautta
     *
     * @throws IOException
     * @throws SQLException
     */
    public GUI() throws IOException, SQLException {
        this.s = new Service(false);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = generalView();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Päänäkymä. Tarjoaa mahdollisuuden ohjelman sulkemiseen ja
     * eri toiminnallisuuksiin siirtymiseen.
     *
     * @return päänäkymäksi rakennetun Scene-olion
     */
    public Scene generalView() {
        VBox layout = defaultVBoxLO();

        Text title = new Text("Welcome to HSL-Gatherer");
        title.setStyle("-fx-font-weight: bold");

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setAlignment(Pos.BOTTOM_CENTER);

        Parent buttonsLayout = generalViewLO();

        layout.getChildren().addAll(title, buttonsLayout, exitButton);

        exitButton.setOnAction(e -> {
            javafx.application.Platform.exit();
        });

        return new Scene(layout);
    }

    /**
     * Päänäkymän napit ja niihin liittyvät toiminnallisuudet.
     * Päänäkymästä asetetaan Stage- oliolle kaikkien muiden näkymien
     * ja metodien palauttamat Scene-oliot.
     *
     * @return nappien asetteluun tarkoitetun Parent-olion
     */
    public Parent generalViewLO() {
        VBox buttonLO = defaultVBoxLO();

        Button addStopButton = new Button("Add stop");
        addStopButton.setPrefWidth(200);
        Button addRouteButton = new Button("Add route");
        addRouteButton.setPrefWidth(200);
        Button showStopsButton = new Button("Show saved stops and routes");
        showStopsButton.setPrefWidth(200);
        Button deleteButton = new Button("Delete stops or routes");
        deleteButton.setPrefWidth(200);

        addStopButton.setOnAction(e -> { stage.setScene(addStopScene()); });
        addRouteButton.setOnAction(e -> {
            try {
                stage.setScene(addRouteScene());
            } catch (SQLException exception) {
            }
        });
        showStopsButton.setOnAction(e -> {
            try {
                stage.setScene(showStopsScene());
            } catch (Exception exception) {
            }
        });
        deleteButton.setOnAction(e -> { stage.setScene(deleteMenuScene()); });

        buttonLO.getChildren().addAll(addStopButton, addRouteButton, showStopsButton, deleteButton);

        return buttonLO;
    }

    /**
     * Pysäkkien hakuun ja lisäämiseen tarkoitettu näkymä
     * sekä siihen liittyvät toiminnallisuudet.
     *
     * @return pysäkkihakua ja lisäämistä varten rakennettu Scene-olio
     */
    public Scene addStopScene() {
        VBox layout = defaultVBoxLO();

        Text title = new Text("SEARCH FOR STOPS");
        title.setStyle("-fx-font-weight: bold");
        Text instr = new Text("Type a stop name and click 'Search' (No numbers or special characters)");

        HBox searchRow = new HBox();
        searchRow.setSpacing(10);
        searchRow.setAlignment(Pos.TOP_CENTER);
        searchRow.setPadding(new Insets(20, 20, 20, 20));
        TextField param = new TextField();
        param.setPrefWidth(200);
        Button searchButton = new Button("Search");

        VBox stopLO = new VBox(5);
        stopLO.setAlignment(Pos.CENTER);
        stopLO.setPadding(new Insets(20, 20, 20, 20));

        searchButton.setOnAction(e -> {
            try {
                stopLO.getChildren().clear();
                List<Stop> stops = s.searchForStops(param.getText());

                if(stops.size() > 0) {
                    for (int i = 0; i < stops.size(); i++) {
                        HBox stopRow = new HBox(5);
                        stopRow.setAlignment(Pos.CENTER);

                        Text stop = new Text(stops.get(i).getName());
                        Button saveButton = new Button("Save");

                        saveButton.setOnAction(c -> {
                            try {
                                if(s.addStop(stop.getText())) {
                                    saveButton.setDisable(true);
                                    saveButton.setText("SAVED!");
                                } else {
                                    saveButton.setDisable(true);
                                    saveButton.setText("ALREADY SAVED!");
                                }
                            } catch (SQLException exception) {
                                Text noStops = new Text("Something went wrong");
                                stopLO.getChildren().add(noStops);
                            }
                        });

                        stopRow.getChildren().addAll(stop, saveButton);
                        stopLO.getChildren().add(stopRow);
                    }
                } else {
                    Text noStops = new Text("No stops found!");
                    stopLO.getChildren().add(noStops);
                }
            } catch (IOException exception) {
                Text noStops = new Text("No stops found!");
                stopLO.getChildren().add(noStops);
            }
        });

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(200);

        searchRow.getChildren().addAll(param, searchButton);

        layout.getChildren().addAll(title, instr, searchRow, stopLO, returnButton);

        returnButton.setOnMouseClicked(e -> { stage.setScene(generalView()); });

        return new Scene(layout);
    }

    /**
     * Linjojen lisäämiseen tarkoitettu näkymä sekä
     * siihen liittyvät toiminnallisuudet. Tässä näkymässä
     * näkee vain valittavat pysäkit, mistä linjan voi valita.
     *
     * @return valittavia pysäkkejä varten rakennettu Scene-olio
     *
     * @throws SQLException
     */
    public Scene addRouteScene() throws SQLException {
        VBox layout = defaultVBoxLO();

        Text title = new Text("SAVED STOPS");
        title.setStyle("-fx-font-weight: bold");
        Text instr = new Text("Choose stop to save routes");

        List<String> stops = s.getSavedStops();

        VBox stopsLO = new VBox(5);
        stopsLO.setPadding(new Insets(30, 30, 30, 30));
        stopsLO.setAlignment(Pos.CENTER);

        for (int i = 0; i < stops.size(); i++) {
            String stop = stops.get(i);
            Button stopButton = new Button(stop);
            stopButton.setPrefWidth(200);

            stopButton.setOnMouseClicked(e -> {
                try {
                    stage.setScene(addRouteSubScene(stop));
                } catch (IOException exception) {
                }
            });

            stopsLO.getChildren().add(stopButton);
        }

        if (stops.size() < 1) {
            stopsLO.getChildren().add(new Text("No saved stops yet.. Go add some"));
        }

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(200);

        returnButton.setOnMouseClicked(e -> { stage.setScene(generalView()); });

        layout.getChildren().addAll(title, instr, stopsLO, returnButton);

        return new Scene(layout);
    }

    /**
     * Linjojen lisäämiseen tarkoitettu näkymä sekä
     * siihen liittyvät toiminnallisuudet. Tässä näkymässä
     * voi lisätä linjoja addRouteScene()-metodissa valitulle
     * pysäkille.
     *
     * @param stop addRouteScene()-metodista valittu pysäkki
     *
     * @see ui.GUI#addRouteScene()
     *
     * @return valittavia linjoja varten rakennettu Scene-olio
     *
     * @throws IOException
     */
    public Scene addRouteSubScene(String stop) throws IOException {
        VBox layout = defaultVBoxLO();

        Text title = new Text("STOP: " + stop);
        title.setStyle("-fx-font-weight: bold");

        List<Trip> routes = s.searchForRoutes(stop);

        VBox routeLO = new VBox(10);
        routeLO.setAlignment(Pos.CENTER);
        routeLO.setPadding(new Insets(20, 20, 20, 20));

        if(routes.size() > 0) {
            for (int i = 0; i < routes.size(); i++) {
                Trip trip = routes.get(i);
                HBox routeRow = new HBox(5);
                routeRow.setAlignment(Pos.CENTER);

                Text route = new Text("SIGN: " + trip.getSign() + " || ROUTE: " + trip.getRoute());
                Button saveButton = new Button("Save");

                saveButton.setOnAction(e -> {
                    try {
                        if (s.addTrip(trip, stop)) {
                            saveButton.setDisable(true);
                            saveButton.setText("SAVED!");
                        } else {
                            saveButton.setDisable(true);
                            saveButton.setText("ALREADY SAVED!");
                        }
                    } catch (SQLException exception) {
                    }
                });

                routeRow.getChildren().addAll(route, saveButton);
                routeLO.getChildren().add(routeRow);
            }
        }

        Button returnButton = new Button("Return");

        returnButton.setOnAction(e -> {
            try {
                stage.setScene(addRouteScene());
            } catch (SQLException exception) {
            }
        });

        layout.getChildren().addAll(title, routeLO, returnButton);

        ScrollPane sc = new ScrollPane(layout);

        return new Scene(sc);
    }

    /**
     * Näyttää käyttäjän tallentamilta pysäkeiltä käyttäjän
     * tallentamien linjojen aikataulut ja päivittää ne
     * reaaliaikaisesti.
     *
     * @return reaaliaikaisen aikataulujen esittämistä varten
     * rakennettu Scene-olio
     *
     * @throws SQLException
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Scene showStopsScene() throws SQLException, IOException, ExecutionException, InterruptedException {
        VBox layout = defaultVBoxLO();
        layout.setPrefSize(700, 500);

        Text title = new Text("SAVED STOPS AND ROUTES");
        title.setStyle("-fx-font-weight: bold");
        Text instr = new Text("(Real time schedules)");

        List<String> stops = s.getSavedStops();
        Set<Trip> routes = s.getSavedRoutes();

        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(title, instr);

        if (stops.size() < 1) {
            Text noStops = new Text("No stops saved yet.. Go add some");
            Button returnButton = new Button("Return");
            returnButton.setPrefWidth(200);

            layout.getChildren().addAll(noStops, returnButton);

            returnButton.setOnMouseClicked(e -> { stage.setScene(generalView()); });

            return new Scene(layout);
        }

        Text routeLO = new Text();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(true) {
                    StringBuilder sb = new StringBuilder();

                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            for (String stop : stops) {

                                sb.append("\n\nSTOP: " + stop + "\n\n");

                                List<Trip> trips = null;

                                try {
                                    trips = s.searchForTrips(stop);
                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }

                                Trip prev = null;

                                int routeCount = 0;

                                for (Trip trip : trips) {
                                    if (routes.contains(trip)) {
                                        if (!trip.equals(prev)) {
                                            sb.append("\n");
                                        }
                                        sb.append(trip.toString() + "\n");
                                        prev = trip;
                                        routeCount++;
                                    }
                                }

                                if (routeCount == 0) {
                                    sb.append("\nNothing saved for " + stop + " yet..\n");
                                }

                                routeLO.setText(sb.toString());
                            }
                        }
                    });
                    Thread.sleep(5000);
                }
            }
        };

        Thread th = new Thread(task);
        th.start();

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(200);

        layout.getChildren().addAll(routeLO, returnButton);

        returnButton.setOnAction(e -> { stage.setScene(generalView()); });

        ScrollPane sc = new ScrollPane(layout);

        return new Scene(sc);
    }

    /**
     * Näkymä, jossa valitaan, että haluaako käyttäjä poistaa
     * tietokantaan tallennettuja pysäkkejä vai linjoja.
     * Nämä kaksi vaihtoehtoa muodostavat omat näkymänsä.
     *
     * @see ui.GUI#deleteStopScene()
     * @see ui.GUI#deleteRouteScene()
     *
     * @return poistamiseen liittyvää valikkoa varten rakennettu
     * Scene-olio
     */
    public Scene deleteMenuScene() {
        VBox layout = defaultVBoxLO();

        Text title = new Text("Delete routes or stops");
        title.setStyle("-fx-font-weight: bold");

        VBox buttonLO = new VBox(5);
        buttonLO.setAlignment(Pos.CENTER);
        buttonLO.setPadding(new Insets(40, 40, 40, 40));

        Button stopButton = new Button("Stop");
        stopButton.setPrefWidth(200);
        Button routeButton = new Button("Route");
        routeButton.setPrefWidth(200);
        buttonLO.getChildren().addAll(stopButton, routeButton);

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(200);

        stopButton.setOnAction(e -> {
            try {
                stage.setScene(deleteStopScene());
            } catch (SQLException exception) {
            }
        });
        routeButton.setOnAction(e -> {
            try {
                stage.setScene(deleteRouteScene());
            } catch (SQLException exception) {
            }
        });

        returnButton.setOnAction(e -> {
            stage.setScene(generalView());
        });


        layout.getChildren().addAll(title, buttonLO, returnButton);

        return new Scene(layout);
    }

    /**
     * Näkymässä voi poistaa tietokantaan
     * tallennettuja pysäkkejä. Pysäkin poistaminen poistaa myös
     * pysäkkiin liittyvät linjat. Näkymään siirrytään
     * deleteMenuScene()-metodin palauttaman näkymän kautta.
     *
     * @see ui.GUI#deleteMenuScene()
     *
     * @return pysäkkien poistamista varten rakennettu Scene-olio
     *
     * @throws SQLException
     */
    public Scene deleteStopScene() throws SQLException {
        VBox layout = defaultVBoxLO();

        Text title = new Text("Saved stops");
        title.setStyle("-fx-font-weight: bold");

        List<String> stops = s.getSavedStops();

        VBox buttonLO = new VBox(30);
        buttonLO.setAlignment(Pos.CENTER);
        buttonLO.setPadding(new Insets(30, 30, 30, 30));

        for(String stop: stops) {
            HBox stopRow = new HBox(10);
            stopRow.setAlignment(Pos.CENTER);

            Text stopText = new Text(stop);
            Button deleteButton = new Button("Delete");

            deleteButton.setOnAction(e -> {
                try {
                    if(s.deleteStop(stop)) {
                        stopRow.getChildren().clear();
                    }
                } catch (SQLException exception) {
                }
            });

            stopRow.getChildren().addAll(stopText, deleteButton);
            buttonLO.getChildren().add(stopRow);
        }

        if(stops.size() < 1) {
            buttonLO.getChildren().add(new Text("No saved stops.. Go add some"));
        }

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(200);

        returnButton.setOnAction(e -> { stage.setScene(deleteMenuScene()); });

        layout.getChildren().addAll(title, buttonLO, returnButton);

        return new Scene(layout);
    }

    /**
     * Näkymässä voi poistaa tietokantaan tallennettuja linjoja.
     * Näkymään siirrytään deleteMenuScene()-metodin palauttaman
     * näkymän kautta.
     *
     * @see ui.GUI#deleteMenuScene()
     *
     * @return linjojen poistamista varten rakennettu Scene-olio
     *
     * @throws SQLException
     */
    public Scene deleteRouteScene() throws SQLException {
        VBox layout = defaultVBoxLO();

        Text title = new Text("Saved routes");
        title.setStyle("-fx-font-weight: bold");

        Set<Trip> routes = s.getSavedRoutes();

        VBox buttonLO = new VBox(5);
        buttonLO.setAlignment(Pos.CENTER);
        buttonLO.setPadding(new Insets(30, 30, 30, 30));

        for(Trip route: routes) {
            HBox routeRow = new HBox(5);
            routeRow.setAlignment(Pos.CENTER);

            Text routeText = new Text("Sign: " + route.getSign() + " || " + "Route: " + route.getRoute());
            Button deleteButton = new Button("Delete");

            deleteButton.setOnAction(e -> {
                try {
                    if (s.deleteRoute(route)) {
                        routeRow.getChildren().clear();
                    }
                } catch (SQLException exception) {
                }
            });

            routeRow.getChildren().addAll(routeText, deleteButton);
            buttonLO.getChildren().add(routeRow);
        }

        if (routes.size() < 1) {
            buttonLO.getChildren().add(new Text("No saved routes.. Go add some"));
        }

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(200);

        returnButton.setOnAction(e -> {
            stage.setScene(deleteMenuScene());
        });

        layout.getChildren().addAll(title, buttonLO, returnButton);

        return new Scene(layout);
    }

    /**
     *  Tekee kaikissa näkymissä käytettävän VBoxin perusasettelun
     *
     * @return VBoxin, jossa on valmiina kaikissa näkymissä käytettävä komponentin perusasettelu
     */
    public VBox defaultVBoxLO() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(30, 30, 30, 30));
        layout.setPrefSize(500, 400);

        return layout;
    }

    public static void main(String[] args) {
        launch(args);
    }
}