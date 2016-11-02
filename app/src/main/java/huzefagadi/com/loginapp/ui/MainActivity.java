package huzefagadi.com.loginapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import huzefagadi.com.loginapp.R;
import huzefagadi.com.loginapp.utils.Constants;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginSuccess {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        if (savedInstanceState == null) {
            if (preferences.getString(Constants.USERNAME, null) != null) {
                OnLoginSuccess();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.root_layout, LoginFragment.newInstance(), "loginScreen")
                        .commit();
            }

        }
    }

    /*
    This method will get called when successfull login is done or user is already logged in.
     */
    @Override
    public void OnLoginSuccess() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, HomeFragment.newInstance(), "homeScreen")
                .commit();
    }
}
