package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class postUploadActivity extends AppCompatActivity {

    TextView uploadTitle;
    TextView uploadCaption;
    Button uploadButton;
    ParseFile file;
    ParseObject object;
    byte[] byteArray;
    Fragment fragment;


    public void takePhoto(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();
        if(requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byteArray = stream.toByteArray();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void sharePost(View view)
    {
        file = new ParseFile("image.png",byteArray);
        object = new ParseObject("Image");
        object.put("image",file);
        object.put("username", ParseUser.getCurrentUser().getUsername());
        object.put("caption",uploadCaption.getText().toString());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)
                {
                    Toast.makeText(postUploadActivity.this,"Image Shared !",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(postUploadActivity.this,"Some Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*Intent intent = new Intent(getApplicationContext(),FragmentMainFeed.class);
        startActivity(intent);
        */
        fragment = new FragmentMainFeed();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upload);

        uploadButton = findViewById(R.id.uploadButton);
        uploadCaption = findViewById(R.id.uploadCaption);
    }
}
