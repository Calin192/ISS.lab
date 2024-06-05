package com.example.iss.service;

import com.example.iss.domain.Availability;
import com.example.iss.domain.Seat;
import com.example.iss.repo.SeatRepo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SeatService {
    private SeatRepo seatRepo;

    public SeatService(SeatRepo seatRepo) {
        this.seatRepo = seatRepo;
    }

    public void addSeat(float price, int column, int row) {
        seatRepo.add(new Seat(price, column, row, Availability.Available));
    }

    public void deleteSeat(Integer id) {
        seatRepo.delete(id);
    }

    public void updateSeat(Seat seat, Integer id) {
        seatRepo.update(seat, id);
    }

    public Integer findSeatByColumnAndRow(int column, int row) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(seat -> seat.getColumn() == column && seat.getRow() == row)
                .map(Seat::getId)
                .findFirst()
                .orElse(null);
    }

    public Seat findSeatById(Integer id) {
        return seatRepo.findById(id);
    }

    public Iterable<Seat> findAll() {
        return seatRepo.findAll();
    }

    public Seat[][] getSeatMatrix() {
        List<Seat> seats = StreamSupport.stream(findAll().spliterator(), false).collect(Collectors.toList());

        int maxRow = seats.stream().mapToInt(Seat::getRow).max().orElse(0);
        int maxColumn = seats.stream().mapToInt(Seat::getColumn).max().orElse(0);

        Seat[][] seatMatrix = new Seat[maxRow + 1][maxColumn + 1];

        for (Seat seat : seats) {
            seatMatrix[seat.getRow()][seat.getColumn()] = seat;
        }

        return seatMatrix;
    }

    public boolean bookSeat(Seat seat){
            return seatRepo.bookSeat(seat);
    }
}
