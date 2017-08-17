package net.accedegh.locationupdates;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class AppConstants {
    public static final int PERMISSION_ACCESS_FINE_LOCATION = 0;
    public static final int PLACE_REQUEST_CODE_GET_LOCATION = 1;
    public static final int PLACE_REQUEST_CODE_MAKE_REQUEST = 2;
    public static final int KEY_SEARCH_FROM_FAVOURITE_LOCATIONS = 3;
    public static final int NOTIFICATION_REQUEST_CODE = 4;
    //FavouriteLocation
    public static final String KEY_FAVOURITE_LOCATIONS = "favourite_locations";
    public static final String KEY_FAVOURITE_LOCATION_LONGITUDE = "favourite_location_longitude";
    public static final String KEY_FAVOURITE_LOCATION_LATITUDE = "favourite_location_latitude";
    public static final String KEY_FAVOURITE_LOCATION_NAME = "favourite_location_name";
    //Checkout
    public static final String KEY_CHECKOUT_REDIRECT_URL = "checkout_redirect_url";
    //Billing keys
    public static final String KEY_BILL_ID = "bill_id";

    public static final String KEY_REGISTRATION_PHONE_NUMBER = "key_phone_number";
    //Shared preferences name
    static final String PREF_NAME = "qcservices";
    //All user preferences
    static final String IS_FIRST_TIME_LAUNCH = "is_launch_firsttime";
    static final String IS_USER_LOGGED_IN = "is_user_logged_in";
    static final String KEY_ACCESS_TOKEN = "key_access_token";

    /*User settings shared preferences Keys*/
    static final String KEY_FIRST_NAME = "key_first_name";
    static final String KEY_LAST_NAME = "key_last_name";
    static final String KEY_EMAIL_ADDRESS = "key_email_address";
    static final String KEY_PHONE_NUMBER = "key_phone_number";
    static final String KEY_RECEIVE_NOTIFICATIONS = "key_recieve_notifs";
    static final String KEY_PUBLIC_Alice = "cipher_public_key_a";
    static final String KEY_PRIVATE_Alice = "cipher_private_key_a";
    static final String KEY_PUBLIC_Bob = "cipher_public_key_b";
    static final String KEY_PRIVATE_Bob = "cipher_private_key_b";
    /*End of User settings shared preferences Keys*/

    //Notification
    public static final int NOTIFICATION_ID = 30;



    //Checking if user is connected to the Internet
    public interface NetworkAvailableCallback{
        void onNetworkChecked(boolean isNetworkAvailable);
    }

    //Checking if user is connected to the Internet
    public static void isNetworkAvailable(final NetworkAvailableCallback callback) {
        new AsyncTask<Void, Void, Boolean>(){

            @Override
            protected Boolean doInBackground(Void... params) {

                int timeOut = 5000;
                InetAddress inetAddress = null;

                try {
                    Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                        @Override
                        public InetAddress call() {
                            try {
                                return InetAddress.getByName("google.com");
                            } catch (UnknownHostException e) {
                                return null;
                            }
                        }
                    });
                    inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS);
                    future.cancel(true);

                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                } catch (TimeoutException e) {
                }

                return inetAddress!=null && !inetAddress.equals("");
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                callback.onNetworkChecked(aBoolean);
            }
        }.execute();


    }


    //Hide Soft Keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager iim = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (activity.getCurrentFocus() != null)
            iim.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /*public static void displayAlert(Context context, View view, String message){
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }*/

    public static void displayAlertDialogMessage(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.yes, null);
        builder.create().show();
    }
    public static String formatDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d yyyy, hh:mm a");
        String formattedDate = formatter.format(new Date(Long.parseLong(date)*1000L));
        return formattedDate;
    }
}
