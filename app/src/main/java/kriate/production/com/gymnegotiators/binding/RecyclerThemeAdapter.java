package kriate.production.com.gymnegotiators.binding;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.R;

/**
 * Created by dima on 24.07.2017.
 */

public class RecyclerThemeAdapter extends RecyclerView.Adapter<RecyclerThemeAdapter.ViewHolder> {

    private ArrayList<Theme> mTheme;

    // Конструктор
    public RecyclerThemeAdapter(ArrayList<Theme> theme) {
        mTheme = theme;
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        public TextView photoText;
        public ImageView photoImage;

        public ViewHolder(View v) {
            super(v);
            photoText = (TextView) v.findViewById(R.id.photoText);
            photoImage = (ImageView) v.findViewById(R.id.photoImage);
        }
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public RecyclerThemeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Theme item = mTheme.get(position);

        holder.photoText.setText(mTheme.get(position).getName());
        holder.photoImage.setImageBitmap(mTheme.get(position).getPhotoBit());
        holder.photoImage.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(position, item);
        });
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mTheme.size();
    }


    private RecyclerThemeAdapter.OnItemClickListener<Theme> onItemClickListener;

    public interface OnItemClickListener<Theme> {
        void onItemClick(int position, Theme item);
    }
    public void setOnItemClickListener(RecyclerThemeAdapter.OnItemClickListener<Theme> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}