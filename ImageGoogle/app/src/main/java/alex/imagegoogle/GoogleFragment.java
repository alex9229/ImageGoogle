package alex.imagegoogle;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import alex.imagegoogle.adapters.GoogleImageBean;
import alex.imagegoogle.adapters.ListViewImageAdapter;
import alex.imagegoogle.utils.Load;


/**
 * Created by alex on 10.11.15.
 */
public class GoogleFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    private static GoogleFragment fragment;


    private ListView listViewImages;
    private EditText txtSearchText;
    private static Button btnSearch;
    private static ListViewImageAdapter adapter;
    private ArrayList<GoogleImageBean> listImages;
    private Loader<String> loader;
    String strSearch = null;
    private Integer point;


    private OnFragmentInteractionListener mListener;

    /**
     * @return A new instance of fragment GoogleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoogleFragment newInstance() {
        if (fragment == null) {
            fragment = new GoogleFragment();
        }

        return fragment;
    }

    public GoogleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    View.OnClickListener searchOnClickListener
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            strSearch = txtSearchText.getText().toString();
            strSearch = Uri.encode(strSearch);
            point = 0;
            System.out.println("Search string => " + strSearch);
            loader = new Load(getActivity(), strSearch, point);
            loader.forceLoad();


        }
    };
    /**
     * method for move down to last
     */
    public void onLast() {


        System.out.println("Search string => " + strSearch);
        point = point + 1;

        loader = new Load(getActivity(), strSearch, point);
        loader.forceLoad();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_google, container, false);
        point = 0;
        btnSearch = (Button) v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(searchOnClickListener);
        listViewImages = (ListView) v.findViewById(R.id.lviewImages);
        txtSearchText = (EditText) v.findViewById(R.id.txtViewSearch);

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {


        return loader;
    }


    @Override
    public void onLoadFinished(Loader<String> loader, String result) {

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    public void SetListViewAdapter(ArrayList<GoogleImageBean> images) {
        if (images != null) {
            if (point != 0) {
                adapter = new ListViewImageAdapter(getActivity(), setchange(adapter.listImages, images));

                listViewImages.setAdapter(adapter);
            } else {
                adapter = new ListViewImageAdapter(getActivity(), images);
                listViewImages.setAdapter(adapter);
            }
        }
    }

    public ArrayList<GoogleImageBean> setchange(ArrayList<GoogleImageBean> listImages1, ArrayList<GoogleImageBean> listImages2) {
        for (int i = 0; i < listImages2.size(); i++) {
            GoogleImageBean bean = new GoogleImageBean();
            bean.setThumbUri(listImages2.get(i).getThumbUri());
            listImages1.add(bean);
        }
        return listImages1;
    }


}
