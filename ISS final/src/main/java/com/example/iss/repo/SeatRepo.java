package com.example.iss.repo;

import com.example.iss.domain.Availability;
import com.example.iss.domain.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class SeatRepo implements SeatRepoInterface{
    private JdbcUtils dbUtils;
    public SeatRepo(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Seat seat) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Seat (price,column,row) VALUES (?, ?, ?)");
        ) {
            statement.setFloat(1, seat.getPrice());
            statement.setInt(2, seat.getColumn());
            statement.setInt(3, seat.getRow());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dbUtils.getConnection()) {
            // First SQL statement
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Seat WHERE id = ?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }

            // Second SQL statement
            try (PreparedStatement statement = connection.prepareStatement("UPDATE sqlite_sequence SET seq = (SELECT MAX(id) FROM Seat) WHERE name='Seat'")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Seat elem, Integer id) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Seat SET price = ?, column = ?, row = ? WHERE id = ?");
        ) {
            statement.setFloat(1, elem.getPrice());
            statement.setInt(2, elem.getColumn());
            statement.setInt(3, elem.getRow());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Seat findById(Integer id) {
        Seat seat = null;
        String query = "SELECT * FROM Seat WHERE id = ?"; // Assuming "Participant" is the table name
        try (
                Connection connection = dbUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    seat = new Seat();
                    seat.setId(resultSet.getInt("id"));
                    seat.setPrice(resultSet.getFloat("price"));
                    seat.setColumn(resultSet.getInt("column"));
                    seat.setRow(resultSet.getInt("row"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seat;
    }

    @Override
    public Iterable<Seat> findAll() {
        List<Seat> list = new ArrayList<>();
        String query = "SELECT * FROM Seat"; // Assuming "Participant" is the table name
        try (
                Connection connection = dbUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {
                Seat seat = new Seat();
                seat.setId(resultSet.getInt("id"));
                seat.setPrice(resultSet.getFloat("price"));
                seat.setColumn(resultSet.getInt("column"));
                seat.setRow(resultSet.getInt("row"));
                list.add(seat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Collection<Seat> getAll() {
        return null;
    }

    public boolean bookSeat(Seat seat){
        if(seat.getAvailability()== Availability.NotAvailable){
            return false;
        }
        else{
            seat.setAvailability(Availability.NotAvailable);
            return true;
        }
    }
}
