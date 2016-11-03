package huzefagadi.com.loginapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import huzefagadi.com.loginapp.R;
import huzefagadi.com.loginapp.utils.Constants;
import huzefagadi.com.loginapp.utils.ValidationUtils;

/**
 * Created by huzefaasger on 02-11-2016.
 */
public class LoginFragment extends Fragment {
    boolean isUsernameValid = false, isPasswordValid = false;
    SharedPreferences preferences;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private OnLoginSuccess mListener;
    ValidationUtils validationUtils;

    public interface OnLoginSuccess {
        void OnLoginSuccess();
    }

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validationUtils = new ValidationUtils();
        preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final Button submit = (Button) view.findViewById(R.id.submit);
        final EditText username = (EditText) view.findViewById(R.id.username);
        final EditText password = (EditText) view.findViewById(R.id.password);
        /*
        This implementation is done mainly to enable/disable the submit button in realtime. i.e. when user is still
        entering the password and if it is valid it will enable it automatically given that username is also valid.
         */
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int length = password.getText().toString().trim().length();
                    if (length >= Constants.PASSWORD_LIMIT) {
                        isPasswordValid = true;
                    } else {
                        isPasswordValid = false;
                    }
                    if (isPasswordValid && isUsernameValid) {
                        submit.setEnabled(true);
                    } else {
                        submit.setEnabled(false);
                    }
                } catch (Exception e) {
                    //just log the error in stacktrace.

                    Log.d(Constants.LOG,e.getMessage());
                }

            }
        });
        /*
        This implementation is also done for the same reason which is done for password field. submit button enable/disable as well as error message is displayed in realtime while user
        is still entring the username
         */
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int valid = validationUtils.isStringValid(username.getText().toString());
                    String message;
                    switch (valid)
                    {
                        case 1:message = "true";
                            break;
                        case 2:message = getString(R.string.username_symbol_error);
                            break;
                        case 3:message = getString(R.string.username_invalid);
                            break;
                        default:message = getString(R.string.username_invalid);
                    }

                    if (message.equalsIgnoreCase("true")) {
                        if (username.getText().length() >= Constants.USERNAME_LIMIT) {
                            isUsernameValid = true;
                        } else {
                            isUsernameValid = false;
                        }

                    } else {
                        username.setError(message);
                        isUsernameValid = false;
                    }
                    if (isPasswordValid && isUsernameValid) {
                        submit.setEnabled(true);
                    } else {
                        submit.setEnabled(false);
                    }
                } catch (Exception e) {
                    //just log the error in stacktrace.
                    Log.d(Constants.LOG,e.getMessage());
                }

            }

        });
        /*
        Motive of this piece of code is to show error message on focus lost, so that user knows that it is not acceptable and submit button wont get enabled untill entered right password
         */
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if (!b) {
                        if (password.getText().toString().trim().length() < Constants.PASSWORD_LIMIT) {
                            String passwordError = String.format(getResources().getString(R.string.password_error), Constants.PASSWORD_LIMIT);
                            //password.setError("Your password cannot be less than "+Constants.PASSWORD_LIMIT+" characters");
                            Toast.makeText(getActivity(), passwordError, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    //just log the error in stacktrace.
                    Log.d(Constants.LOG,e.getMessage());
                }


            }
        });
         /*
        Motive of this piece of code is to show error message on focus lost, so that user knows that it is not acceptable and submit button wont get enabled untill entered right username
         */
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if (!b) {
                        if (username.getText().toString().trim().length() < Constants.USERNAME_LIMIT) {
                            // username.setError("Your username cannot be less than "+Constants.USERNAME_LIMIT+" characters");
                            String usernameError = String.format(getResources().getString(R.string.username_length_error), Constants.USERNAME_LIMIT);
                            Toast.makeText(getActivity(), usernameError, Toast.LENGTH_LONG).show();

                        }
                    }
                } catch (Exception e) {
                    //just log the error in stacktrace.
                    Log.d(Constants.LOG,e.getMessage());
                }

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                preferences.edit().putString(Constants.USERNAME, username.getText().toString()).commit();
                //always let the activity do the fragment trasition so thats why interface implementation is done
                mListener.OnLoginSuccess();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginSuccess) {
            mListener = (OnLoginSuccess) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnLoginSuccess Method.");
        }
    }
}
