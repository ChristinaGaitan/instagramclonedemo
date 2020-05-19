package com.lcgt.instagramcloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        setTitle(username + "'s Feed");

        final LinearLayout layout = findViewById(R.id.linearLayout);



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        query.whereEqualTo("username", username);
        query.addDescendingOrder("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    if (objects != null && objects.size() > 0) {
                        for(ParseObject object : objects) {
                            ParseFile fileObject = (ParseFile) object.get("image");

                            fileObject.getDataInBackground(new GetDataCallback() {
                                public void done(byte[] data, ParseException e) {

                                    if (e == null) {
                                        Log.d("test", "Correct data.");

                                        // Decode the Byte[] into
                                        // Bitmap
                                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        // initialize
//                                            ImageView image = (ImageView) findViewById(R.id.image);
                                        ImageView imageView = new ImageView(getApplicationContext());
//                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 200);
//                                        imageView.setLayoutParams(lp);

                                        // Set the Bitmap into the
                                        // ImageView
                                        imageView.setImageBitmap(bmp);
                                        layout.addView(imageView);
                                    } else {
                                        Log.d("test", "Problem load image the data.");
                                    }
                                }
                            });
                        }

                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

//
//        for(int i=0; i< imgList.size(); i++) {
//            ImageView imgView = new ImageView(NoticeContent.this);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 200);
//            imgView.setLayoutParams(lp);
////            Glide.with(NoticeContent.this)
////                .load(imgList.get(i))
////                .override(200,200)
////                .into(imgView);
//            layout.addView(imgView);
//        }
    }
}
