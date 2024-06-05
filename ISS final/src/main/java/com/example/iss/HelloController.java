package com.example.iss;

import com.example.iss.domain.Seat;
import com.example.iss.service.SeatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    public Button updateSeat;
    public TextField textField;
    public Button buyButton;
    private SeatService seatService;
    public javafx.scene.layout.GridPane gridPane;
    public Button AddRow;
    public Button DeleteRow;
    public Label SeatPrice;

    private Integer selectedId;

    private Label previousLabel;

    private boolean admin = true;

    public void setService(SeatService seatService) {
        this.seatService = seatService;
// Clear the GridPane
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();

        // Get the seat data
        Seat[][] seatMatrix = seatService.getSeatMatrix();

        // Add a row to the GridPane for each row in the seatMatrix
        for (int i = 1; i <= seatMatrix.length; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30); // Set the preferred height of the new row
            gridPane.getRowConstraints().add(rowConstraints);

            // Add a Label to the GridPane for each seat in the row
            for (int j = 1; j <= seatMatrix[i-1].length; j++) {
                Seat seat = seatMatrix[i-1][j-1];
                if (seat != null) {
                    Label label = new Label(""+seat.getId());
                    label.getStyleClass().add("label");
                    label.setStyle("-fx-background-color: black; -fx-text-fill: transparent;");
                    label.setPrefSize(25, 25); // Set the preferred width and height
                    gridPane.add(label, j-1, i-1);
                } else {
                    if(i == 1 && j == 1){
                        Label label = new Label("");
                        gridPane.add(label, j-1, i-1);
                    }
                    else{
                        if(i==1 && j!=1){
                            Label label = new Label(""+(j-1));
                            gridPane.add(label, j-1, i-1);
                        }
                        else if(j==1 && i!=1){
                            char rowLetter = (char) (i + 63); // Convert row number to ASCII letter starting from 'A'
                            Label label = new Label(String.valueOf(rowLetter));
                            gridPane.add(label, j-1, i-1);
                        }
                    }
                }
            }
        }

        // Add a click event handler to each Label in the GridPane
        for (int i = 1; i <= gridPane.getChildren().size(); i++) {
            if (gridPane.getChildren().get(i-1) instanceof Label) {
                Label label = (Label) gridPane.getChildren().get(i-1);
                label.setOnMouseClicked(this::handleLabelClick);
            }
        }
    }

    private void refresh(){
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();

        // Get the seat data
        Seat[][] seatMatrix = seatService.getSeatMatrix();

        // Add a row to the GridPane for each row in the seatMatrix
        for (int i = 1; i <= seatMatrix.length; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30); // Set the preferred height of the new row
            gridPane.getRowConstraints().add(rowConstraints);

            // Add a Label to the GridPane for each seat in the row
            for (int j = 1; j <= seatMatrix[i-1].length; j++) {
                Seat seat = seatMatrix[i-1][j-1];
                if (seat != null) {
                    Label label = new Label(""+seat.getId());
                    label.getStyleClass().add("label");
                    label.setStyle("-fx-background-color: black; -fx-text-fill: transparent;");
                    label.setPrefSize(25, 25); // Set the preferred width and height
                    gridPane.add(label, j-1, i-1);
                } else {
                    if(i == 1 && j == 1){
                        Label label = new Label("");
                        gridPane.add(label, j-1, i-1);
                    }
                    else{
                        if(i==1 && j!=1){
                            Label label = new Label(""+(j-1));
                            gridPane.add(label, j-1, i-1);
                        }
                        else if(j==1 && i!=1){
                            char rowLetter = (char) (i + 63); // Convert row number to ASCII letter starting from 'A'
                            Label label = new Label(String.valueOf(rowLetter));
                            gridPane.add(label, j-1, i-1);
                        }
                    }
                }
            }
        }

        // Add a click event handler to each Label in the GridPane
        for (int i = 1; i <= gridPane.getChildren().size(); i++) {
            if (gridPane.getChildren().get(i-1) instanceof Label) {
                Label label = (Label) gridPane.getChildren().get(i-1);
                label.setOnMouseClicked(this::handleLabelClick);
            }
        }
    }



    private void handleLabelClick(MouseEvent event) {
        // If there was a previously clicked label, change its color back to black
        if (previousLabel != null) {
            previousLabel.setStyle("-fx-background-color: black; -fx-text-fill: transparent;");
        }
        Label clickedLabel = (Label) event.getSource();
        String value = clickedLabel.getText();
        int id = Integer.parseInt(value);
        Seat seat = seatService.findSeatById(id);
        selectedId = seat.getId();

        SeatPrice.setText("Seat Price: " + seat.getPrice());

        // Change the background color of the clicked label to blue
        clickedLabel.setStyle("-fx-background-color: blue; -fx-text-fill: transparent;");

        previousLabel = clickedLabel;
    }

    public void onAddRowButtonClick(ActionEvent actionEvent) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(30); // Set the preferred height of the new row
        gridPane.getRowConstraints().add(rowConstraints);

        // Fill the new row with values (optional)
        int newRow = gridPane.getRowConstraints().size() - 1;
        for (int j = 1; j <= gridPane.getColumnConstraints().size(); j++) {
            // Add a new seat in the database for each column in the new row
            seatService.addSeat(10.0f, j, newRow);

            Integer id = seatService.findSeatByColumnAndRow(j, newRow);
            if (id != null) {
                Label label = new Label(""+id);
                gridPane.add(label, j, newRow);
            }
        }

        gridPane.getChildren().clear();
        refresh();
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
        Seat[][] seatMatrix = seatService.getSeatMatrix();
        if (seatMatrix.length > 0 && !gridPane.getRowConstraints().isEmpty()) {
            int lastRow = seatMatrix.length - 1;
            for (int j = 1; j <= gridPane.getColumnConstraints().size(); j++) {
                Integer id = seatService.findSeatByColumnAndRow(j, lastRow);
                seatService.deleteSeat(id);
            }

            // Remove the RowConstraints for the last row
            gridPane.getRowConstraints().remove(lastRow);

            gridPane.getChildren().clear();
            refresh();
        }
    }




    public void onUpdateButtonClick(ActionEvent actionEvent) {
        String price = textField.getText();
        if (price != null && selectedId != null) {
            Seat seat = seatService.findSeatById(selectedId);
            seat.setPrice(Float.parseFloat(price));
            seatService.updateSeat(seat, seat.getId());
            System.out.println(seat);

            // Update the text of the existing SeatPrice label
            SeatPrice.setText("Seat Price: " + seat.getPrice());
        }

    }

    public void onClientButtonClick(ActionEvent actionEvent) {
        if(admin){
            admin = false;
            AddRow.setVisible(false);
            DeleteRow.setVisible(false);
            updateSeat.setVisible(false);
            textField.setVisible(false);
            buyButton.setVisible(true);
        }
        else{
            admin = true;
            AddRow.setVisible(true);
            DeleteRow.setVisible(true);
            updateSeat.setVisible(true);
            textField.setVisible(true);
            buyButton.setVisible(false);
        }
    }

    public void onBuyButtonClick(ActionEvent actionEvent) {
        if(selectedId != null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss/transaction-view.fxml"));
                Parent root = fxmlLoader.load();
                TransactionView transactionView = fxmlLoader.getController();
                Seat seat = seatService.findSeatById(selectedId);
                transactionView.setService(seatService,seat);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please select a seat");

            alert.showAndWait();
        }

    }
}