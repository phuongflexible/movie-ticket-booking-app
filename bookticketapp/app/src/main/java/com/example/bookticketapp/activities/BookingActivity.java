package com.example.bookticketapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.SeatGridViewAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.dao.PaymentMethodQuery;
import com.example.bookticketapp.dao.SeatQuery;
import com.example.bookticketapp.dao.TicketQuery;
import com.example.bookticketapp.events.SeatsChangeListener;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.models.PaymentMethod;
import com.example.bookticketapp.models.Seat;
import com.example.bookticketapp.models.Showtime;
import com.example.bookticketapp.models.Ticket;
import com.example.bookticketapp.utils.DatetimeUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements SeatsChangeListener {
    private GridView gvSeats;
    private Spinner spnMethod;
    private SeatGridViewAdapter seatAdapter;
    private ArrayAdapter methodSpinnerAdapter;
    private List<Seat> seatList;
    private List<PaymentMethod> methodList;
    private TextView txtTitle, txtCinema, txtRoom, txtShowtime, txtSeats, txtTotal;
    private Button btnPay;
    private Showtime showtime;
    private Movie movie;
    private Cinema cinema;
    private List<Integer> selectedSeatIds;
    private MovieQuery movieQuery;
    private CinemaQuery cinemaQuery;
    private SeatQuery seatQuery;
    private TicketQuery ticketQuery;
    private PaymentMethodQuery methodQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        findViewByIds();
        initData();
        initSeats();
        initPaymentMethod();
    }

    private void findViewByIds() {
        gvSeats = findViewById(R.id.gvSeats);
        spnMethod = findViewById(R.id.spinnerPaymentMethod);
        txtTitle = findViewById(R.id.txtTitle_booking);
        txtCinema = findViewById(R.id.txtCinema_booking);
        txtRoom = findViewById(R.id.txtRoom_booking);
        txtShowtime = findViewById(R.id.txtShowtime_booking);
        txtSeats = findViewById(R.id.txtSeats_booking);
        txtTotal = findViewById(R.id.txtTotal);
    }

    private void initData() {
        movieQuery = new MovieQuery(this);
        cinemaQuery = new CinemaQuery(this);

        showtime = (Showtime) getIntent().getSerializableExtra("showtime");
        movie = movieQuery.getMovieById(showtime.getMovieId());
        cinema = cinemaQuery.getCinemaById(showtime.getCinemaId());

        String showtimeString = DatetimeUtils.timeToString(showtime.getShowtime());
        String showDateString = DatetimeUtils.dateToString(showtime.getShowDate());

        txtTitle.setText(movie.getTitle());
        txtCinema.setText("Rạp: " + cinema.getName());
        txtRoom.setText("Phòng chiếu: R01");
        txtShowtime.setText("Suất chiếu: " + showtimeString + " - " + showDateString);
        txtSeats.setText("0 Ghế");
        txtTotal.setText("Tổng cộng: 0đ");
    }

    private void initSeats() {
        seatQuery = new SeatQuery(this);
        seatList = seatQuery.getSeatsByRoomId(1);
        selectedSeatIds = new ArrayList<>();

        seatAdapter = new SeatGridViewAdapter(this, R.layout.item_button_seat, seatList);
        seatAdapter.setSeatsChangeListener(this);
        gvSeats.setAdapter(seatAdapter);
    }

    @Override
    public void onSeatSelectionChanged(int seatId) {
        Seat seat = seatQuery.getSeatById(seatId);
        if (seat.isAvailable()) {   // nếu ghế còn trống
            if (selectedSeatIds.contains(seatId)) {     // nếu nằm trong list đã chọn rồi thì xóa
                selectedSeatIds.remove(Integer.valueOf(seatId));
            } else {
                selectedSeatIds.add(seatId);            // ngược lại thì thêm
            }

            updateSeatsAndTotal();
        }
    }

    private void updateSeatsAndTotal() {
        StringBuilder builder = new StringBuilder();

        for (Integer seatId : selectedSeatIds) {
            Seat seat = seatQuery.getSeatById(seatId);
            builder.append(seat.getSeatNumber()).append(" ");   // mã số ghế + " "
        }
        String seatsText = builder.toString();

        txtSeats.setText(selectedSeatIds.size() + " Ghế: " + seatsText);
        txtTotal.setText("Tổng cộng: " + calculateTotalPrice() + "đ");
    }

    private float calculateTotalPrice() {
        ticketQuery = new TicketQuery(this);
        float total = 0;

        for (Integer seadId : selectedSeatIds) {
            Ticket ticket = new Ticket();
            ticket.setShowtimeId(showtime.getId());
            ticket.setSeatId(seadId);

            // Lấy giá vé từ bảng Ticket
            Ticket ticketFromDb = ticketQuery.getTicketBySeatId(ticket.getSeatId());
            if (ticketFromDb != null) {
                total += ticketFromDb.getPrice();
            }
        }
        return total;
    }

    private void initPaymentMethod() {
        methodQuery = new PaymentMethodQuery(this);
        methodList = methodQuery.getAllMethods();

        // chuyển trang string để gán cho spinner
        List<String> methodsString = new ArrayList<>();
        for (PaymentMethod method : methodList) {
            methodsString.add(method.getName());
        }

        methodSpinnerAdapter = new ArrayAdapter(this, R.layout.item_method_spinner, methodsString);
        spnMethod.setAdapter(methodSpinnerAdapter);
    }
}