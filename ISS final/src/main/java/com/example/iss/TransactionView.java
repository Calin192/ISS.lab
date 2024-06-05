package com.example.iss;

import com.example.iss.domain.Seat;
import com.example.iss.service.SeatService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TransactionView {
    public Label pretLabel;
    public TextField cardNumberField;
    public TextField expDateField;
    public TextField cvcField;
    public TextField nameOnCardField;
    public Button proceedButton;
    private SeatService seatService;
    private Seat seat;

    public void setService(SeatService seatService, Seat seat) {
        this.seatService = seatService;
        this.seat = seat;
        System.out.println(seat);
        setPretLabel();
    }

    public void setPretLabel() {
        pretLabel.setText("Pret: " + seat.getPrice());
    }


    public void onProceedButton(ActionEvent actionEvent) {
        boolean ok = seatService.bookSeat(seat);
        if(ok == true && cardNumberField.getText().length() == 16 && expDateField.getText().length() == 5 && cvcField.getText().length() == 3 && nameOnCardField.getText().length() > 0) {
            System.out.println("Transaction reusita");
        } else {
            System.out.println("Transaction esuata");
        }
    }
}
