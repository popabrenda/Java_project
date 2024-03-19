package com.example.lab4_fx;

import domeniu.Inchiriere;
import domeniu.Masina;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.*;
import service.InchiriereService;
import service.MasinaService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws Exception
    {

        IRepository<Masina> repositoryMasina = new MasiniRepositoryDB();
        MasinaService masinaService = new MasinaService(repositoryMasina);

        IRepository<Inchiriere> repositoryInchiriere = new InchirieriRepositoryDB();
        InchiriereService inchiriereService = new InchiriereService(repositoryInchiriere);


        VBox root = new VBox();
        Scene scene = new Scene(root, 800, 700);



        //afisare masini
        ObservableList<Masina> masini = FXCollections.observableArrayList(masinaService.getAll());
        ListView<Masina> listViewMasini = new ListView<Masina>(masini);
        root.getChildren().add(listViewMasini);


        //afisare inchirieri
        ObservableList<Inchiriere> inchirieri = FXCollections.observableArrayList(inchiriereService.getAll());
        ListView<Inchiriere> listViewInchirieri = new ListView<Inchiriere>(inchirieri);
        root.getChildren().add(listViewInchirieri);


        //gridpane pentru adaugare masina
        GridPane gridPane = new GridPane();

        //denumire campuri de completat
        Label labelId = new Label("Id: ");
        Label labelMarca = new Label("Marca: ");
        Label labelModel = new Label("Model: ");

        //initializare campuri de completat
        TextField textFieldId = new TextField();
        TextField textFieldMarca = new TextField();
        TextField textFieldModel = new TextField();

        //denumire butoane pentru crud masina
        Button buttonAdauga = new Button("Adaugă");
        Button buttonModifica = new Button("Modifică");
        Button buttonSterge = new Button("Șterge");

        //adaugare masina
        gridPane.add(labelId, 1, 0);
        gridPane.add(labelMarca, 1, 1);
        gridPane.add(labelModel, 1, 2);

        //campuri de completat
        gridPane.add(textFieldId, 2, 0);
        gridPane.add(textFieldMarca, 2, 1);
        gridPane.add(textFieldModel, 2, 2);

        //butoane pentru crud masina
        gridPane.add(buttonAdauga, 3, 3);
        gridPane.add(buttonModifica, 4, 3);
        gridPane.add(buttonSterge, 5, 3);
        root.getChildren().add(gridPane);

        //actiune buton adaugare masina
        buttonAdauga.setOnAction(event -> {
            try {
                masinaService.add(Integer.parseInt(textFieldId.getText()), textFieldMarca.getText(), textFieldModel.getText());
                masini.setAll(masinaService.getAll());
            } catch (Exception e) {
                Alert erorPopup = new Alert(Alert.AlertType.ERROR);
                erorPopup.setTitle("Eroare");
                erorPopup.setHeaderText("Eroare la adaugare");
                erorPopup.setContentText(e.getMessage());
                erorPopup.showAndWait();
            }
        });
//        DuplicateEntityException | RepositoryException
        //actiune buton modificare masina
        buttonModifica.setOnAction(event -> {
            try {
                IRepository<Masina> dbrepositoryMasina = new MasiniRepositoryDB();

                ((MasiniRepositoryDB) dbrepositoryMasina).openConnection();

                repositoryMasina.setAll(dbrepositoryMasina.getAll());

                masinaService.update(Integer.parseInt(textFieldId.getText()), textFieldMarca.getText(), textFieldModel.getText());
                masini.setAll(masinaService.getAll());
            } catch (Exception e) {
                Alert erorPopup = new Alert(Alert.AlertType.ERROR);
                erorPopup.setTitle("Eroare");
                erorPopup.setHeaderText("Eroare la modificare");
                erorPopup.setContentText(e.getMessage());
                erorPopup.show();
            }
        });

        //actiune buton stergere masina
        buttonSterge.setOnAction(event -> {
            try {
                masinaService.remove(Integer.parseInt(textFieldId.getText()));
                masini.setAll(masinaService.getAll());
            } catch (Exception e) {
                Alert erorPopup = new Alert(Alert.AlertType.ERROR);
                erorPopup.setTitle("Eroare");
                erorPopup.setHeaderText("Eroare la stergere");
                erorPopup.setContentText(e.getMessage());
                erorPopup.show();
            }
        });

        GridPane gridPaneInchirieri = new GridPane();

        //denumire campuri de completat
        Label labelIdInchiriere = new Label("Id inchiriere: ");
        Label labelIdMasina = new Label("Id masina: ");
        Label labelDataInceput = new Label("Data Inceput: ");
        Label labelDataSfarsit = new Label("Data Sfarsit: ");

        //initializare campuri de completat
        TextField textFieldIdInchiriere = new TextField();
        TextField textFieldIdMasina = new TextField();
        TextField textFieldDataInceput = new TextField();
        TextField textFieldDataSfarsit = new TextField();

        //denumire butoane pentru crud inchiriere
        Button buttonAdaugaInchiriere = new Button("Adaugă");
        Button buttonModificaInchiriere = new Button("Modifică");
        Button buttonStergeInchiriere = new Button("Șterge");

        //adaugare inchiriere
        gridPaneInchirieri.add(labelIdInchiriere, 0, 0);
        gridPaneInchirieri.add(labelIdMasina, 0, 1);
        gridPaneInchirieri.add(labelDataInceput, 0, 2);
        gridPaneInchirieri.add(labelDataSfarsit, 0, 3);

        //campuri de completat
        gridPaneInchirieri.add(textFieldIdInchiriere, 1, 0);
        gridPaneInchirieri.add(textFieldIdMasina, 1, 1);
        gridPaneInchirieri.add(textFieldDataInceput, 1, 2);
        gridPaneInchirieri.add(textFieldDataSfarsit, 1, 3);

        //butoane pentru crud inchiriere
        gridPaneInchirieri.add(buttonAdaugaInchiriere, 2, 6);
        gridPaneInchirieri.add(buttonModificaInchiriere, 3, 6);
        gridPaneInchirieri.add(buttonStergeInchiriere, 4, 6);
        root.getChildren().add(gridPaneInchirieri);

        //actiune buton adaugare inchiriere
        buttonAdaugaInchiriere.setOnAction(event -> {
            try {

                IRepository<domeniu.Masina> dbrepositoryMasina = new MasiniRepositoryDB();

                ((MasiniRepositoryDB) dbrepositoryMasina).openConnection();

                repositoryMasina.setAll(dbrepositoryMasina.getAll());

                Masina masina = masinaService.readMasina(Integer.parseInt(textFieldIdMasina.getText()));

                inchiriereService.add(Integer.parseInt(textFieldIdInchiriere.getText()), new Masina(Integer.parseInt(textFieldIdMasina.getText()), masina.getMarca(), masina.getModel()), LocalDate.parse(textFieldDataInceput.getText()), LocalDate.parse(textFieldDataSfarsit.getText()));
                inchirieri.setAll(inchiriereService.getAll());


            } catch (Exception e) {
                Alert erorPopup = new Alert(Alert.AlertType.ERROR);
                erorPopup.setTitle("Eroare");
                erorPopup.setHeaderText("Eroare la adaugare");
                erorPopup.setContentText(e.getMessage());
                erorPopup.showAndWait();
            }
        });

        //actiune buton modificare inchiriere
        buttonModificaInchiriere.setOnAction(event -> {
            try {
                IRepository<domeniu.Masina> dbrepositoryMasina = new MasiniRepositoryDB();
                IRepository<domeniu.Inchiriere> dbrepositoryInchiriere = new InchirieriRepositoryDB();


                ((InchirieriRepositoryDB) dbrepositoryInchiriere).openConnection();

                ((MasiniRepositoryDB) dbrepositoryMasina).openConnection();

                repositoryMasina.setAll(dbrepositoryMasina.getAll());
                repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

                Masina masina = masinaService.readMasina(Integer.parseInt(textFieldIdMasina.getText()));

                inchiriereService.update(Integer.parseInt(textFieldIdInchiriere.getText()),LocalDate.parse(textFieldDataInceput.getText()), LocalDate.parse(textFieldDataSfarsit.getText()));
                inchirieri.setAll(inchiriereService.getAll());
            } catch (Exception e) {
                Alert erorPopup = new Alert(Alert.AlertType.ERROR);
                erorPopup.setTitle("Eroare");
                erorPopup.setHeaderText("Eroare la modificare");
                erorPopup.setContentText(e.getMessage());
                erorPopup.showAndWait();
            }
        });

        //actiune buton stergere inchiriere
        buttonStergeInchiriere.setOnAction(event ->
        {
            try {
                IRepository<Inchiriere> dbrepositoryInchiriere = new InchirieriRepositoryDB();
                ((InchirieriRepositoryDB) dbrepositoryInchiriere).openConnection();

                repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

                inchiriereService.remove(Integer.parseInt(textFieldIdInchiriere.getText()));
                inchirieri.setAll(inchiriereService.getAll());
            } catch (Exception e) {
                Alert erorPopup = new Alert(Alert.AlertType.ERROR);
                erorPopup.setTitle("Eroare");
                erorPopup.setHeaderText("Eroare la stergere");
                erorPopup.setContentText(e.getMessage());
                erorPopup.showAndWait();
            }



        });


        TextArea resultArea = new TextArea();
        resultArea.setPrefWidth(600);
        resultArea.setPrefHeight(400);
        resultArea.setEditable(false);



        Button buttonAfisareMasiniCuNrInchirieri = new Button("Afisare masini cu nr inchirieri");
        buttonAfisareMasiniCuNrInchirieri.setOnAction(event -> {

            StringBuilder resultText = new StringBuilder();


            InchirieriRepositoryDB dbrepositoryInchiriere = new InchirieriRepositoryDB();

            dbrepositoryInchiriere.openConnection();

            repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

            Map<Masina, Long> masiniCuNrInchirieri = inchirieri.stream()
                    .collect(Collectors.groupingBy(Inchiriere::getMasina, Collectors.counting()));
            masiniCuNrInchirieri.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().getMarca() + ", " + entry.getKey().getModel(),
                            Map.Entry::getValue, Long::sum)) // combinarea valorilor duplicate
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> {
                        String masina = entry.getKey();
                        Long numarInchirieri = entry.getValue();
                        resultText.append(masina).append(" - Numar total de inchirieri: ").append(numarInchirieri).append("\n");
                    });

            resultArea.setText(resultText.toString());
        });


        Button buttonNumarulDeInchirieriEfectuateInFiecareLuna = new Button("Numarul de inchirieri efectuate in fiecare luna");

        buttonNumarulDeInchirieriEfectuateInFiecareLuna.setOnAction(event -> {
            StringBuilder resultText = new StringBuilder();
            InchirieriRepositoryDB dbrepositoryInchiriere = new InchirieriRepositoryDB();

            dbrepositoryInchiriere.openConnection();

            repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

            Map<Integer, Long> numarulDeInchirieriEfectuateInFiecareLuna = inchirieri.stream()
                    .collect(Collectors.groupingBy(inchiriere -> inchiriere.getDataInceput().getMonthValue(), Collectors.counting()));
            numarulDeInchirieriEfectuateInFiecareLuna.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey(),
                            Map.Entry::getValue, Long::sum))
                    .entrySet().stream()
                    .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                    .forEach(entry -> {
                        Integer luna = entry.getKey();
                        Long numarInchirieri = entry.getValue();
                        resultText.append("Luna ").append(luna).append(" - Numar total de inchirieri: ").append(numarInchirieri).append("\n");
                    });

            resultArea.setText(resultText.toString());
        });

        Button buttonNumarulDeZileInCareFiecareMasinaAInchiriata = new Button("Numarul de zile in care fiecare masina a fost inchiriata");

        buttonNumarulDeZileInCareFiecareMasinaAInchiriata.setOnAction(event ->
        {
            InchirieriRepositoryDB dbrepositoryInchiriere = new InchirieriRepositoryDB();
            dbrepositoryInchiriere.openConnection();
            repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());


            Map<Masina, Long> masiniCuTimpInchiriat = inchirieri.stream()
                    .collect(Collectors.groupingBy(
                            Inchiriere::getMasina,
                            Collectors.summingLong(inchiriere ->
                                    inchiriere.getDataSfarsit().toEpochDay() - inchiriere.getDataInceput().toEpochDay())
                    ));

            StringBuilder resultText = new StringBuilder();
            masiniCuTimpInchiriat.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().getMarca() + ", " + entry.getKey().getModel(),
                            Map.Entry::getValue, Long::sum)) // combinarea valorilor duplicate
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> {
                        String masina = entry.getKey();
                        Long timpInchiriat = entry.getValue();
                        resultText.append(masina).append(" - Timp total de inchiriere: ").append(timpInchiriat).append(" zile\n");
                    });

            resultArea.setText(resultText.toString());
        });

        GridPane gridPaneMasini = new GridPane();
        gridPaneMasini.add(buttonAfisareMasiniCuNrInchirieri, 1, 0);
        gridPaneMasini.add(buttonNumarulDeInchirieriEfectuateInFiecareLuna, 1, 1);
        gridPaneMasini.add(buttonNumarulDeZileInCareFiecareMasinaAInchiriata, 1, 2);
        root.getChildren().add(gridPaneMasini);
        root.getChildren().addAll(resultArea);


        stage.setTitle("Administrare Închirieri Mașini");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
