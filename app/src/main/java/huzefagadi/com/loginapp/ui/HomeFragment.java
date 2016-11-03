package huzefagadi.com.loginapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import huzefagadi.com.loginapp.R;
import huzefagadi.com.loginapp.utils.Constants;

/**
 * Created by huzefaasger on 02-11-2016.
 */
public class HomeFragment extends Fragment {
    SharedPreferences preferences;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            TextView username = (TextView) view.findViewById(R.id.username);
            ImageView image = (ImageView) view.findViewById(R.id.user_image);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            String usernameStr = preferences.getString(Constants.USERNAME, null);
            String welcomeMessage = "Welcome, <b>" + usernameStr + "</b>";
            if (Build.VERSION.SDK_INT >= 24) {
                username.setText(Html.fromHtml(welcomeMessage, Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL));
            } else {
                username.setText(Html.fromHtml(welcomeMessage));
            }

            /*

            Picasso is used here to handle the image download and caching. It is an open source library primarily used for image operations.

            We can implement the image handling using the conventional way as well, like using google provided code to load only resized image and not the whole Image at once.
             */
            Picasso picasso = new Picasso.Builder(getActivity()).build();
            picasso.load("https://fi.google.com/about/static/images/fi_logo_sq_1200.png")
                    .error(R.drawable.error)
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            // TODO Auto-generated method stub
                            progressBar.setVisibility(View.GONE);
                        }});
        } catch (Exception e) {
            Log.d(Constants.LOG,e.getMessage());
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
