package com.example.tentsering.googlebookreloaded;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TenSherab on 3/10/18.
 * {@link EbooksAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a date source, which is a list of {@link Ebooks} objects.
 *
 * In plain english, an ArrayAdapter is a built-in class that convert xml files to corresponding java object
 */

public class EbooksAdapter extends ArrayAdapter<Ebooks>{

    private static final String LOG_TAG = EbooksAdapter.class.getSimpleName();

    /**
     * This is a custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want to populate
     *
     * @param context       The current context, used to inflate the layout file.
     * @param Ebooks  A list of ThreeColumn objects to display in a list
     */
    public EbooksAdapter(Activity context, ArrayList<Ebooks> Ebooks){
        super(context, 0, Ebooks);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc)
     *
     * @param position  the position in which the list of data that should be displayed in the list item vjew
     * @param convertView   The recycled view to populate
     * @param parent    The parent ViewGroup that is used for inflation.
     * @return  The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //check if the existing view is being re-used, otherwise inflate the view

        View listItemView = convertView;

        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.ebook_layout, parent, false);
        }

        Ebooks currentPosition = getItem(position);

        TextView titleText = (TextView) listItemView.findViewById(R.id.book_title);
        titleText.setText(currentPosition.getmTitle());

        TextView titleAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        titleAuthor.setText(currentPosition.getmAuthor());

        ImageView thumbnail = (ImageView) listItemView.findViewById(R.id.image_book);
        Picasso.with(getContext()).load(currentPosition.getmThumbnail()).into(thumbnail);

        return listItemView;
    }

}