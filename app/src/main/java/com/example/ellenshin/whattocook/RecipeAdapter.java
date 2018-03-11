package com.example.ellenshin.whattocook;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ellenshin on 3/10/18.
 */

public class RecipeAdapter extends BaseAdapter {
    // adapter takes the app itself and a list of data to display
    private Context myContext;
    private ArrayList<Recipe> myRecipeList;
    private LayoutInflater myInflater;

    //constructor
    public RecipeAdapter(Context myContext, ArrayList<Recipe> myRecipeList) {
        //initialize instance variable
        this.myContext = myContext;
        this.myRecipeList = myRecipeList;
        this.myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    //methods
    //a list of methods we need to override

    //gives ypu the number of recipes in the data source

    @Override
    public int getCount() {
        return myRecipeList.size();
    }

    // returns the item at specific positin in the data source

    @Override
    public Object getItem(int position) {
        return myRecipeList.get(position);
    }

    // returns the row id as associated with the specific position in the list
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // check if the view already exists
        //if yes, you dont need to inflate and findViewbyID again
        if (convertView == null) {
            //inflate
            convertView = myInflater.inflate(R.layout.activity_result, parent, false);
            //add the views to the holder
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_serving);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.prepTextView = convertView.findViewById(R.id.recipe_list_prep);
            holder.cookingButton = convertView.findViewById(R.id.cooking_button);
            //add the holder to the view
            convertView.setTag(holder);
        } else {
            //get the view holder from convertView
            holder = (ViewHolder)convertView.getTag();
        }
        //get relevant subview of the row view
        TextView titleTextView = holder.titleTextView;
        TextView servingTextView = holder.servingTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        TextView prepTextView = holder.prepTextView;
        ImageButton cookingButton = holder.cookingButton;

        //get corresponding revipe for each row
        final Recipe recipe = (Recipe)getItem(position);

        //update the row view's textviews ad imageView to display the information
        titleTextView.setText(recipe.title);
        titleTextView.setTextColor(ContextCompat.getColor(myContext, R.color.colorAccent));
        titleTextView.setTextSize(18);

        servingTextView.setText(recipe.serving + " servings");
        servingTextView.setTextSize(14);
        servingTextView.setTextColor(ContextCompat.getColor(myContext, R.color.colorPrimaryDark));

        prepTextView.setText(recipe.prepTime);
        servingTextView.setTextSize(14);
        servingTextView.setTextColor(ContextCompat.getColor(myContext, R.color.colorPrimary));

        //use Picasso library to load image from the image url;
        Picasso.with(myContext).load(recipe.imageUrl).into(thumbnailImageView);

        cookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(recipe.title, recipe.instructionUrl);
            }
        });

        return convertView;
    }

    // viewholder
    // is used to customize what you want to put into the view
    // it depends on the layout design of your row
    // this will be a private static class you have to define
    private static class ViewHolder {
        public TextView titleTextView;
        public TextView servingTextView;
        public ImageView thumbnailImageView;
        public TextView prepTextView;
        public ImageButton cookingButton;
    }

    private void launchActivity(String title, String url){
        // package intent
        // start activity

        //build a notif for this launch
        // before 23, you only need to pass the context as parameter
        // now you need to pass contxt, channel id -> "default"
        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, "default");

        //set icons
        builder.setSmallIcon(android.R.drawable.btn_star);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // create a pending intent for the notification with the intent I created
        PendingIntent pendingIntent = PendingIntent.getActivity(myContext,0, intent, 0);
        builder.setContentIntent(pendingIntent);

        //set the titile and content of the notif
        builder.setContentTitle("Cooking Instruction");
        builder.setContentText("The instruction for " + title + " can be found here!");

        // get the system service to display this notification
        NotificationManager notificationManager = (NotificationManager) myContext.getSystemService(NOTIFICATION_SERVICE);

        //notify
        notificationManager.notify(1, builder.build());


    }

    //intent is used to pass information between activities
    // intent -> package
    // sender, receiver

}