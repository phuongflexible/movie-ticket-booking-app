package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.SeatGridViewAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.dao.PaymentMethodQuery;
import com.example.bookticketapp.dao.SeatQuery;
import com.example.bookticketapp.dao.TicketQuery;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.models.PaymentMethod;
import com.example.bookticketapp.models.Seat;
import com.example.bookticketapp.models.Showtime;
import com.example.bookticketapp.models.Ticket;
import com.example.bookticketapp.utils.DatetimeUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
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
    private Seat seat;
    private List<Seat> selectedSeats;
    private PaymentMethod method;
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

        seatAdapter = new SeatGridViewAdapter(this, R.layout.item_button_seat, seatList);
        gvSeats.setAdapter(seatAdapter);

        gvSeats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(BookingActivity.this, "click", Toast.LENGTH_SHORT).show();
                Seat seat = seatList.get(i);
                if (seat.isAvailable()) {   // nếu ghế còn trống
                    if (selectedSeats.contains(seat)) {     // nếu nằm trong list đã chọn rồi thì xóa
                        selectedSeats.remove(seat);
                    } else {
                        selectedSeats.add(seat);            // ngược lại thì thêm
                    }

                    // cập nhật textview ghế đã chọn
                    StringBuilder builder = new StringBuilder();
                    builder.append(seat.getSeatNumber()).append(" ");

                    txtSeats.setText("Ghế: " + builder);

                    updateSeatsAndTotal();
                }
            }
        });
    }

    private void updateSeatsAndTotal() {
        StringBuilder builder = new StringBuilder();

        for (Seat seat : selectedSeats) {
            builder.append(seat.getSeatNumber()).append(" ");
        }
        String seatsText = builder.toString();

        txtSeats.setText(selectedSeats.size() + " Ghế: " + seatsText);
        txtTotal.setText("Tổng cộng: " + calculateTotalPrice() + "đ");
    }

    private float calculateTotalPrice() {
        ticketQuery = new TicketQuery(this);
        float total = 0;

        for (Seat seat : selectedSeats) {
            Ticket ticket = new Ticket();
            ticket.setShowtimeId(showtime.getId());
            ticket.setSeatId(seat.getId());

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