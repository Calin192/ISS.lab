package com.example.iss;

import com.example.iss.domain.Seat;
import com.example.iss.repo.SeatRepo;
import com.example.iss.service.SeatService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/example/iss/stuffcss/admin.css").toExternalForm());
        stage.setTitle("Hello!");

        stage.setScene(scene);
        stage.show();


        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(props);
        SeatRepo seatRepo = new SeatRepo(props);


        SeatService service = new SeatService(seatRepo);

        System.out.println(service.findSeatByColumnAndRow(2,3));


        HelloController controller = fxmlLoader.getController();
        controller.setService(service);
    }

    public static void main(String[] args) {
        launch();
    }
}