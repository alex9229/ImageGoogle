package alex.imagegoogle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import alex.imagegoogle.utils.Utils;

/**
 * Сreate by alex
 */

public class ShowImage extends AppCompatActivity {
    ImageView imageView;
    String thumbUrl;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        imageView = (ImageView) findViewById(R.id.imView);
        thumbUrl = getIntent().getStringExtra("Image");

        if(thumbUrl.substring(0,2).equals("htt")){
        imageView.setImageBitmap(getBitmap(thumbUrl));}
        else {imageView.setImageURI(Uri.parse(thumbUrl));}
    }

    /**
     * get Bitmap from url
     */
    private Bitmap getBitmap(final String url) {
        String filename = String.valueOf(url.hashCode());
        File cacheDir;
        cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), this.getString(R.string.cache_dirname));
        final File f = new File(cacheDir, filename);


        Bitmap bitmap = null;
        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(url);
                InputStream is = null;
                try {
                    is = new URL(url).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                OutputStream os = null;
                try {
                    os = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Utils.CopyStream(is, os);
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
        bitmap = decodeFile(f);
        return bitmap;


    }

    /**
     * Decode the file(with bitmap) into Bitmap
     *
     *
     */
    private Bitmap decodeFile(File f) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
