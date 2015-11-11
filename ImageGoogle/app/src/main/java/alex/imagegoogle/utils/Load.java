package alex.imagegoogle.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import alex.imagegoogle.GoogleFragment;
import alex.imagegoogle.adapters.GoogleImageBean;

/**
 * Created by alex on 10.11.15.
 */
public class Load extends Loader<String> {
    private Activity activity;
    private Integer point;
    private String strSearch;

    public Load(Activity activity, String strSearch, Integer point) {
        super(activity);
        this.activity = activity;
        this.strSearch = strSearch;
        this.point = point;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();

        getImagesTask im = new getImagesTask();
        im.setStart(point);
        im.execute();


    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }


    public class getImagesTask extends AsyncTask<Void, Void, Void> {
        Integer start;

        public void setStart(Integer start) {
            this.start = start;
        }

        ProgressDialog dialog;
        JSONArray resultArray;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = ProgressDialog.show(activity, "", "Please wait...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            resultArray = DoSearch(start);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            System.out.println(resultArray);

            ArrayList<GoogleImageBean> listImages;
            listImages = getImageList(resultArray);
            GoogleFragment.newInstance().SetListViewAdapter(listImages);


        }
        /**
         * Do search image
         */
        private JSONArray DoSearch(Integer start) {
            JSONArray resultArray = null;
            try {
                URL url1 = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                        "v=1.0&q=" + strSearch + "&rsz=5&start=" + start); //&key=ABQIAAAADxhJjHRvoeM2WF3nxP5rCBRcGWwHZ9XQzXD3SWg04vbBlJ3EWxR0b0NVPhZ4xmhQVm3uUBvvRF-VAA&userip=192.168.0.172");
                URL url2 = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                        "v=1.0&q=" + strSearch + "&rsz=5&start=" + (start + 5)); //&key=ABQIAAAADxhJjHRvoeM2WF3nxP5rCBRcGWwHZ9XQzXD3SWg04vbBlJ3EWxR0b0NVPhZ4xmhQVm3uUBvvRF-VAA&userip=192.168.0.172");

                URLConnection connection1 = url1.openConnection();
                URLConnection connection2 = url2.openConnection();
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                while ((line = reader1.readLine()) != null) {
                    builder.append(line);
                }

                System.out.println("Builder string => " + builder.toString());
//
                JSONObject json = new JSONObject(builder.toString());

                StringBuilder builder2 = new StringBuilder();
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                while ((line = reader2.readLine()) != null) {
                    builder2.append(line);
                }
                System.out.println("Builder string => " + builder.toString());
                JSONObject json2 = new JSONObject(builder2.toString());
                JSONObject responseObject = json.getJSONObject("responseData");
                resultArray = responseObject.getJSONArray("results");
                JSONObject responseObject2 = json2.getJSONObject("responseData");
                JSONArray resultArray2 = responseObject2.getJSONArray("results");
                for (int i = 0; i < resultArray2.length(); i++) {
                    resultArray.put(resultArray2.get(i));
                }

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            return resultArray;
        }

        /**
         * get Arraylist with Image from JSON Array
         */
        public ArrayList<GoogleImageBean> getImageList(JSONArray resultArray) {
            ArrayList<GoogleImageBean> listImages = new ArrayList<GoogleImageBean>();
            GoogleImageBean bean;

            try {
                if (resultArray != null) {
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject obj;
                        obj = resultArray.getJSONObject(i);
                        bean = new GoogleImageBean();
                        bean.setTitle(obj.getString("title"));
                        bean.setThumbUrl(obj.getString("tbUrl"));
                        System.out.println("Thumb URL => " + obj.getString("tbUrl"));
                        listImages.add(bean);

                    }
                    return listImages;
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
    }

}