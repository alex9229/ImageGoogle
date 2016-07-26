package alex.imagegoogle.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.content.Loader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
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
        ArrayList<String> resultArray;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = ProgressDialog.show(activity, "", "Please wait...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            resultArray =getData(start,strSearch);
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

        public ArrayList<String> getData(int start, String question) {
            ArrayList<String> imageList2 = new ArrayList<String>();
            HttpResponse response = null;
            try {
                // Create http client object to send request to server
                HttpClient client = new DefaultHttpClient();
                // Create URL string
                System.out.println(strSearch);
                String URL = "https://www.googleapis.com/customsearch/v1?key=AIzaSyD90KSCSr8FXWORmmETBi8Ij0O-lQS5eng&cx=000940462282111494871:jb70i3p1ijq&q=" + question + "&num=10&start=" + start+1;
                System.out.println(URL);
                // Create Request to server and get response
                HttpGet httpget = new HttpGet();
                httpget.setURI(new URI(URL));
                response = client.execute(httpget);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

                StringBuilder builder = new StringBuilder();

                for (String line = null; (line = reader.readLine()) != null; ) {
                    builder.append(line).append("\n");
                }
                JSONObject json = new JSONObject(builder.toString());
                System.out.println("2");
                System.out.println(json);
                JSONArray responseObject = json.getJSONArray("items");
                for (int i = 0; i < responseObject.length(); i++) {
                    try {

                        responseObject.get(i);
                        JSONObject tempjson = new JSONObject(responseObject.get(i).toString());
                        JSONObject tempJsonPagemap = new JSONObject(tempjson.get("pagemap").toString());
                        String s = tempJsonPagemap.getString("cse_image");
                        s=remSlash(s);
                        imageList2.add(s.substring(s.indexOf(':') + 2, s.length() - 3));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            return imageList2;
        }

        private String remSlash(String text){
            String tmp;
            while(text.contains("\\")){
                tmp="";
                if(text.indexOf("\\")!=0){tmp=text.substring(0,text.indexOf("\\"));}
                if(text.indexOf("\\")!=text.length()-1){tmp=tmp+text.substring(text.indexOf("\\")+1,text.length());}
                text=tmp;
            }
            return text;}



        /**
         * get Arraylist with Image from JSON Array
         */
        public ArrayList<GoogleImageBean> getImageList(ArrayList<String> imageList) {
            ArrayList<GoogleImageBean> listImages = new ArrayList<GoogleImageBean>();
            GoogleImageBean bean;


            if (imageList != null) {
                for (int i = 0; i < imageList.size(); i++) {
                    bean = new GoogleImageBean();
                    bean.setThumbUri(imageList.get(i));

                    System.out.println("Thumb URL => " + imageList.get(i));
                    listImages.add(bean);

                }

            }
            return listImages;
        }



    }

}