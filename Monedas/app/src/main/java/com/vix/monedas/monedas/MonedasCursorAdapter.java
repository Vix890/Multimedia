package com.vix.monedas.monedas;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.vix.monedas.R;

import java.io.File;

public class MonedasCursorAdapter extends CursorAdapter {

    public MonedasCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_moneda, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView taskViewName = view.findViewById(R.id.task_view_name);
        final ImageView avatar = view.findViewById(R.id.imagen);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("moneda"));
        String avatarUri = cursor.getString(cursor.getColumnIndexOrThrow("imagen"));

        taskViewName.setText(name);

        File imagePath = context.getDir("imagenes", Context.MODE_PRIVATE);

        Glide
                .with(context)
                .asBitmap()
                .load(Uri.parse("file://" + imagePath + "/" + avatarUri))
                .error(R.drawable.exit_icon)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatar.setImageDrawable(drawable);
                    }
                });
    }
}
