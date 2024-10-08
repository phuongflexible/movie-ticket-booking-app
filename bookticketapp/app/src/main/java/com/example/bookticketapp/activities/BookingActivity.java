package com.example.bookticketapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.SeatGridViewAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.dao.PaymentMethodQuery;
import com.example.bookticketapp.dao.ReceiptQuery;
import com.example.bookticketapp.dao.SeatQuery;
import com.example.bookticketapp.dao.TicketQuery;
import com.example.bookticketapp.events.SeatsChangeListener;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.models.PaymentMethod;
import com.example.bookticketapp.models.Seat;
import com.example.bookticketapp.models.Showtime;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.SessionManager;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
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
    private Button btnBook;
    private Showtime showtime;
    private Movie movie;
    private Cinema cinema;
    private List<Integer> selectedSeatIds;
    private MovieQuery movieQuery;
    private CinemaQuery cinemaQuery;
    private SeatQuery seatQuery;
    private TicketQuery ticketQuery;
    private ReceiptQuery receiptQuery;
    private PaymentMethodQuery methodQuery;
    private float ticketPrice = 70000;
    private String seatsString;
    private Float totalPrice;
    private int methodId;
    private SessionManager sessionManager;
    private static final String PAYPAL_CLIENT_ID = "AXs1CXoY2nar9LYGRiuWZfJZJuulWtqwFhhYAX1vNPgLCMDu6grMCYmK1DmcRvHzrHQTrvRClltMc8xI";
    private static final int PAYPAL_REQUEST_CODE = 123;

    // PayPal Configuration
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        sessionManager = new SessionManager(this);

        findViewByIds();
        initData();
        initSeats();
        initPaymentMethod();

        spnMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PaymentMethod method = methodList.get(i);
                methodId = method.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Khởi động PayPal Service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.isLoggedIn()) {
                    showBookingDialog();
                } else {
                    showLoginDialog();
                }
            }
        });
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
        btnBook = findViewById(R.id.btnBook);
    }

    private void initData() {
        seatQuery = new SeatQuery(this);
        movieQuery = new MovieQuery(this);
        cinemaQuery = new CinemaQuery(this);
        ticketQuery = new TicketQuery(this);
        receiptQuery = new ReceiptQuery(this);
        methodQuery = new PaymentMethodQuery(this);

        showtime = (Showtime) getIntent().getSerializableExtra("showtime");
        movie = movieQuery.getMovieById(showtime.getMovieId());
        cinema = cinemaQuery.getCinemaById(showtime.getCinemaId());

        String showtimeString = DatetimeUtils.timeToString(showtime.getShowtime());
        String showDateString = DatetimeUtils.dateToString(showtime.getShowDate());

        txtTitle.setText(movie.getTitle());
        txtCinema.setText(cinema.getName());
        txtRoom.setText("R01");
        txtShowtime.setText(showtimeString + " - " + showDateString);
        txtSeats.setText("0 Ghế");
        txtTotal.setText("Tổng: 0đ");
    }

    private void initSeats() {
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

            // chọn ít nhất 1 ghế mới có thể đặt vé
            if (selectedSeatIds.size() > 0) {
                btnBook.setClickable(true);
                btnBook.setBackgroundColor(getResources().getColor(R.color.darkOrange));
            } else {
                btnBook.setClickable(false);
                btnBook.setBackgroundColor(getResources().getColor(R.color.gray));
            }

            updateSeatsAndTotal();
        }
    }

    private void updateSeatsAndTotal() {
        StringBuilder builder = new StringBuilder();
        totalPrice = (float) 0;

        for (Integer seatId : selectedSeatIds) {
            Seat seat = seatQuery.getSeatById(seatId);
            builder.append(seat.getSeatNumber()).append(" ");   // mã số ghế + " "

            totalPrice += ticketPrice;   // tính tổng tiền
        }
        seatsString = builder.toString();

        txtSeats.setText(selectedSeatIds.size() + " Ghế: " + seatsString);
        String formatString = String.format("%,.0f", totalPrice);
        txtTotal.setText("Tổng: " + formatString + "đ");
    }

    private void initPaymentMethod() {
        methodList = methodQuery.getAllMethods();

        // chuyển sang string để gán cho spinner
        List<String> methodsString = new ArrayList<>();
        for (PaymentMethod method : methodList) {
            methodsString.add(method.getName());
        }

        methodSpinnerAdapter = new ArrayAdapter(this, R.layout.item_spinner, methodsString);
        spnMethod.setAdapter(methodSpinnerAdapter);
    }

    private void showLoginDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);

        TextView txtLogin = dialog.findViewById(R.id.txtLogin_booking);
        Button btnCancel = dialog.findViewById(R.id.btn_Cancel_login);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingActivity.this, LoginActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showBookingDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_booking_confirmation);

        TextView txtTitleConfirm = dialog.findViewById(R.id.txtTitle_confirm);
        TextView txtShowtimeConfirm = dialog.findViewById(R.id.txtShowtime_confirm);
        TextView txtCinemaConfirm = dialog.findViewById(R.id.txtCinema_confirm);
        TextView txtRoomSeatConfirm = dialog.findViewById(R.id.txtRoomSeat_confirm);
        TextView txtTotalConfirm = dialog.findViewById(R.id.txtTotal_confirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel_confirm);
        Button btnPay = dialog.findViewById(R.id.btnPay);

        txtTitleConfirm.setText(txtTitle.getText());
        txtShowtimeConfirm.setText(txtShowtime.getText());
        txtCinemaConfirm.setText(txtCinema.getText());
        txtRoomSeatConfirm.setText(txtRoom.getText() + " - Ghế: " + seatsString);
        txtTotalConfirm.setText(txtTotal.getText());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = sessionManager.getUserId();   // lấy user id đang đăng nhập

                // tạo hóa đơn mới
                int receiptId = (int) receiptQuery.addReceipt(totalPrice, methodId, userId);

                if (receiptId != -1) {
                    // thêm các vé đã đặt vào 1 hóa đơn
                    for (Integer seatId : selectedSeatIds) {
                        Boolean resultTicket = ticketQuery.addTicket(showtime.getId(), seatId, ticketPrice, receiptId);
                    }
                    // cập nhật trạng thái các ghế đã đặt
                    seatQuery.updateSeatsAvailability(selectedSeatIds, false);
                    Toast.makeText(BookingActivity.this, "Đặt vé thành công!", Toast.LENGTH_SHORT).show();

                    moveToHistoryFragment();

                } else {
                    Toast.makeText(BookingActivity.this, "Đã xảy ra lỗi khi đặt vé!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                };
            }
        });

        dialog.show();
    }

    private void moveToHistoryFragment() {
        Intent intent = new Intent(BookingActivity.this, MainActivity.class);
        intent.putExtra("showHistoryFragment", true);
        startActivity(intent);
    }
}