package alex.imagegoogle.adapters;

/**
 * Сreate by alex
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;

import alex.imagegoogle.GoogleFragment;
import alex.imagegoogle.R;
import alex.imagegoogle.ShowImage;
import alex.imagegoogle.utils.ImageLoader;
import alex.imagegoogle.utils.Save;

/**
 * Created by alex on 10.11.15.
 */
public class ListViewImageAdapter extends BaseAdapter {

    private Activity activity;
    public ArrayList<GoogleImageBean> listImages;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public ListViewImageAdapter(Activity a, ArrayList<GoogleImageBean> listImages) {
        activity = a;
        this.listImages = listImages;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
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

        public CheckBox checkBox;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        final GoogleImageBean imageBean = (GoogleImageBean) listImages.get(position);
        if (position == listImages.size() - 1) {
            GoogleFragment.newInstance().onLast();
        }
        vi = inflater.inflate(R.layout.listview_row, null);
        holder = new ViewHolder();

        holder.imgViewImage = (ImageView) vi.findViewById(R.id.imageView01);

        holder.checkBox = (CheckBox) vi.findViewById(R.id.checkBox);
        holder.checkBox.setOnCheckedChangeListener(checkList);
        holder.checkBox.setTag(position);
        holder.imgViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageopen = new Intent(activity, ShowImage.class);
                imageopen.putExtra("Image", imageBean.getThumbUri());
                activity.startActivity(imageopen);
            }
        });
        vi.setTag(holder);

        holder.imgViewImage.setTag(imageBean.getThumbUri());
        imageLoader.DisplayImage(imageBean.getThumbUri(), activity, holder.imgViewImage);

        return vi;
    }


    CompoundButton.OnCheckedChangeListener checkList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {

                new Save().saveImage(listImages.get(Integer.parseInt(buttonView.getTag().toString())).getThumbUri());


            }

        }
    };
}