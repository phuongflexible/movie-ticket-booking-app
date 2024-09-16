package com.example.bookticketapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.models.ReceiptHistory;
import com.example.bookticketapp.utils.ImageUtils;

import java.util.List;

public class HistoryListviewAdapter extends BaseAdapter {
    Context context;
    List<ReceiptHistory> historyList;

    public HistoryListviewAdapter(Context context, List<ReceiptHistory> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgMovie;
        TextView txtMovieTitle, txtShowTime, txtCinema, txtRoom, txtSeatNumbers,
                txtTotalPrice, txtCreatedTime, txtPaymentTime, txtPaymentMethod;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_history_lisview, null);

            holder.imgMovie = view.findViewById(R.id.imgMovie_history);
            holder.txtMovieTitle = view.findViewById(R.id.txtMovieTitle_history);
            holder.txtShowTime = view.findViewById(R.id.txtShowTime_history);
            holder.txtCinema = view.findViewById(R.id.txtCinema_history);
            holder.txtRoom = view.findViewById(R.id.txtRoom_history);
            holder.txtSeatNumbers = view.findViewById(R.id.txtSeatNumbers_history);
            holder.txtTotalPrice = view.findViewById(R.id.txtTotalPrice_history);
            holder.txtCreatedTime = view.findViewById(R.id.txtCreatedTime_history);
            holder.txtPaymentTime = view.findViewById(R.id.txtPaymentTime_history);
            holder.txtPaymentMethod = view.findViewById(R.id.txtPaymentMethod_history);

            view.setTag(holder);
        } else {
                holder = (ViewHolder) view.getTag();
        }

        ReceiptHistory history = historyList.get(i);

        holder.txtMovieTitle.setText(history.getMovieTitle());
        holder.txtShowTime.setText("Suất chiếu: " + history.getShowTime() + " - " + history.getShowDate());
        holder.txtCinema.setText("Rạp: " + history.getCinema());
        holder.txtRoom.setText("Phòng chiếu: " + history.getRoom());
        holder.txtSeatNumbers.setText("Ghế: " + android.text.TextUtils.join(", ", history.getSeatNumbers()));
        holder.txtTotalPrice.setText("Tổng tiền: " + String.format("%,.0f", history.getTotalPrice()));
        holder.txtCreatedTime.setText("Thời gian đặt: " + history.getCreatedTime());
        holder.txtPaymentTime.setText("Thời gian thanh toán: " + history.getPaymentTime());
        holder.txtPaymentMethod.setText("Phương thức thanh toán: " + history.getPaymentMethod());

        byte[] image = history.getMovieImage();
        if (image != null) {
            Bitmap bitmap = ImageUtils.byteArrayToBitmap(image);
            holder.imgMovie.setImageBitmap(bitmap);
        } else {
            // Nếu không có ảnh phim, hiển thị ảnh mặc định
            holder.imgMovie.setImageResource(R.drawable.default_image);
        }

        return view;
    }
}
