package alex.imagegoogle.utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import alex.imagegoogle.ColectionFragment;
import alex.imagegoogle.GoogleFragment;
import alex.imagegoogle.db.Utill;

/**
 * Created by alex on 24.07.16.
 */
public class Save {
    private final String PATH = "ImageGoogle";
    private Handler h;

    public void saveImage (String ThumbUri){
        SaveImg saveImg = new SaveImg();
        saveImg.execute(ThumbUri);


    }

    class SaveImg extends AsyncTask<String, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... params) {

            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + "/" + PATH);
            if (!sdPath.exists()) {
                sdPath.mkdirs();
            }
            String filename = String.valueOf(params[0].hashCode()) + ".jpg";
            File f = new File(sdPath, filename);
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                InputStream is = new URL(params[0]).openStream();
                OutputStream os = new FileOutputStream(f);
                Utils.CopyStream(is, os);
                os.close();
                Utill.setimages(GoogleFragment.newInstance().getContext(), f.getAbsolutePath());


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ColectionFragment.newInstance().onCheck();
        }
    }


}
