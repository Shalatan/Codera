package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.support.v4.app.Fragment;

import java.util.List;

public class mainUserFeedActivity extends AppCompatActivity {

    LinearLayout linlayout;

    public void shareButtonClicked(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(),postUploadActivity.class);
                startActivity(intent);
            }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.message)
        {
            Intent intent = new Intent(getApplicationContext(),messageUserListActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.myPofile)
        {
            Intent intent = new Intent(getApplicationContext(), singleUserFeedActivity.class);
            intent.putExtra("username",ParseUser.getCurrentUser().getUsername());
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.userList)
        {
            Intent intent = new Intent(getApplicationContext(),userListActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.logout)
        {
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(),loginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_feed);

        linlayout = findViewById(R.id.linLayout);

        final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size()>0)
                {
                    for(ParseObject object : objects)
                    {
                        ParseFile file = (ParseFile) object.get("image");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e == null && data != null)
                                {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));
                                    imageView.setImageBitmap(bitmap);
                                    linlayout.addView(imageView);
                                }
                            }
                        });
                    }
                }
            }
        });

    }
}
