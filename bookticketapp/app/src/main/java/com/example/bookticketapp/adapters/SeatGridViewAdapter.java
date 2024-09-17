package com.example.bookticketapp.adapters;

        import android.content.Context;
        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.Toast;

        import com.example.bookticketapp.R;
        import com.example.bookticketapp.events.SeatsChangeListener;
        import com.example.bookticketapp.models.Seat;

        import java.util.List;

public class SeatGridViewAdapter extends BaseAdapter {
    private Context context;
    private int layoutItem;
    private List<Seat> seatList;
    private boolean[] selectedArray;
    private SeatsChangeListener listener;

    public SeatGridViewAdapter(Context context, int layoutItem, List<Seat> seatList) {
        this.context = context;
        this.layoutItem = layoutItem;
        this.seatList = seatList;
        this.selectedArray = new boolean[seatList.size()];
    }

    public void setSeatsChangeListener(SeatsChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return seatList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layoutItem, null);

        Seat seat = seatList.get(i);
        Button btnItemSeat = view.findViewById(R.id.btnItemSeat);

        btnItemSeat.setText(seat.getSeatNumber());

        int color_mint = context.getResources().getColor(R.color.mint);          // màu ghế trống
        int color_darkgray = context.getResources().getColor(R.color.darkGray);  // màu ghế đã bán
        int color_green = context.getResources().getColor(R.color.green);        // màu ghế đang chọn

        if (seat.isAvailable()) {       // Ghế trống
            btnItemSeat.setBackgroundColor(color_mint);
        } else {                        // Ghế đã bán
            btnItemSeat.setBackgroundColor(color_darkgray);
        }

        btnItemSeat.setOnClickListener(v -> {
            if (seat.isAvailable()) {     // nếu ghế còn trống
                if (selectedArray[i] == false) {     // nếu chưa được chọn
                    selectedArray[i] = true;         // chuyển thành ghế đang được chọn
                    btnItemSeat.setBackgroundColor(color_green);
                } else {
                    selectedArray[i] = false;
                    btnItemSeat.setBackgroundColor(color_mint);
                }

                if (listener != null) {
                    listener.onSeatSelectionChanged(seat.getId());
                }
            }
        });

        return view;
    }
}
