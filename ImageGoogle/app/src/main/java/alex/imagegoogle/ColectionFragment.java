package alex.imagegoogle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import alex.imagegoogle.adapters.GoogleImageBean;
import alex.imagegoogle.adapters.ListViewImageAdapterCollec;
import alex.imagegoogle.db.Utill;

/**
 * Created by alex on
 */
public class ColectionFragment extends Fragment {
    private static ColectionFragment fragment;
    private static ListViewImageAdapterCollec adapter;
    private ArrayList<Object> listImages;
    private ListView listViewImages;


    public static ColectionFragment newInstance() {
        if (fragment == null) {
            fragment = new ColectionFragment();
        }


        return fragment;
    }

    public ColectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = new Bundle();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_colection, container, false);
        listViewImages = (ListView) view.findViewById(R.id.lviewImagesCol);
        listImages = getImageList();
        SetListViewAdapter(listImages);


        return view;
    }

    /**
     * Adaptor for ListView
     */
    public void SetListViewAdapter(ArrayList<Object> images) {
        if (images != null) {
            adapter = new ListViewImageAdapterCollec(getActivity(), images);

            listViewImages.setAdapter(adapter);
        }

    }
    /**
     * get ImageList
     */
    public ArrayList<Object> getImageList() {
        ArrayList<Object> listImages = new ArrayList<Object>();
        GoogleImageBean bean;
        ArrayList<String> check = Utill.getallimages(getContext());
        if (check != null) {
            if (check.size() != 0) {
                for (int i = 0; i < check.size() ; i++) {
                    bean = new GoogleImageBean();
                    bean.setThumbUri(check.get(i));
                    listImages.add(bean);
                }
                return listImages;
            }
        }

        return null;
    }

    /**
     * for add new image
     */
    public void onCheck() {

        listImages = getImageList();
        SetListViewAdapter(listImages);
    }
}
