package alex.imagegoogle.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import alex.imagegoogle.R;
import alex.imagegoogle.ShowImage;

/**
 * Created by alex on 10.11.15.
 */
public class ListViewImageAdapterCollec extends BaseAdapter {

    private Activity activity;
    public ArrayList<Object> listImages;
    private static LayoutInflater inflater = null;


    public ListViewImageAdapterCollec(Activity a, ArrayList<Object> listImages) {
        activity = a;
        this.listImages = listImages;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setchange(ArrayList<Object> listImages) {
        for (int i = 0; i < listImages.size(); i++) {
            this.listImages.add(listImages.get(i));
        }
    }

    public int getCount() {
        return listImages.size();
    }

    public Object getItem(int position) {
        //return position;
        return listImages.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public ImageView imgViewImage;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        final GoogleImageBean imageBean = (GoogleImageBean) listImages.get(position);
        if (convertView == null) {
            vi = inflater.inflate(R.layout.listview_row_coll, null);
            holder = new ViewHolder();

            holder.imgViewImage = (ImageView) vi.findViewById(R.id.imageView01);
            holder.imgViewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent imageopen = new Intent(activity, ShowImage.class);
                    imageopen.putExtra("Image", imageBean.getThumbUri());
                    activity.startActivity(imageopen);
                }
            });


            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();


        holder.imgViewImage.setTag(imageBean.getThumbUri());
        holder.imgViewImage.setImageURI(Uri.parse(imageBean.getThumbUri()));
        return vi;
    }



}