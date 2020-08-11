package com.codev.bitwinapp.Model;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MySmsReceiver extends BroadcastReceiver {
    private static final String TAG = MySmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String MyPREFERENCES = "MyContact";


    /**
     * Called when the BroadcastReceiver is receiving an Intent broadcast.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent received.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the SMS message.

        //Toast.makeText(context, "receiver", Toast.LENGTH_SHORT).show();

        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {

                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                if (msgs[i].getMessageBody().toLowerCase().contains("bitwin")) {
                    SharedPreferences sharedpreferences = context.getSharedPreferences("MyContact", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Forward", msgs[i].getOriginatingAddress());
                    editor.putString("isValid", "Yes");
                    editor.apply();
                    i= msgs.length;
                    LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    //new
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {


                                    if (sharedpreferences.getString("isValid", "No").equals("Yes")) {
                                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                                        List<Address> adresses = null;
                                        try {
                                            adresses = geocoder.getFromLocation(
                                                    location.getLatitude(), location.getLongitude(), 1
                                            );
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        String strMessage = "";
                                        String contact = "";
                                        SharedPreferences sharedPreferences = context.getSharedPreferences("MyContact", Context.MODE_PRIVATE);
                                        contact = sharedPreferences.getString("Forward", "0");
                                        strMessage += "Location of your lost phone fetched by Bitwin! ";

                                        strMessage += "\nCurrent location of your lost phone: ";

                                        strMessage += String.valueOf(adresses.get(0).getAddressLine(0));
                                        strMessage += "\n\nFind lost phone here: https://www.google.com/maps/search/?api=1&query=";
                                        strMessage += String.valueOf(location.getLatitude());
                                        strMessage += ",";
                                        strMessage += String.valueOf(location.getLongitude());

                                        // Toast.makeText(context, "address:"+strMessage, Toast.LENGTH_LONG).show();

                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            ArrayList<String> parts = smsManager.divideMessage(strMessage);
                                            smsManager.sendMultipartTextMessage(contact, null, parts, null, null);
                                            Toast.makeText(context, "Phone Location Tracked by Bitwin!!", Toast.LENGTH_LONG).show();
                                            editor.putString("isValid", "No");
                                            editor.apply();
                                            abortBroadcast();
                                        } catch (Exception ErrVar) {
                                            Toast.makeText(context, ErrVar.getMessage().toString(),
                                                    Toast.LENGTH_LONG).show();
                                            ErrVar.printStackTrace();
                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                break;
            }
        }
    }
}

                    //new end

//                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            // TODO Auto-generated method stub
//                            //Toast.makeText(context, "loc aai"+location.getLatitude(), Toast.LENGTH_SHORT).show();
//
//                            SharedPreferences sp =  context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
//
//
//                            if(sp.getString("isValid","No").equals("Yes"))
//                            {
//                                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//                                List<Address> adresses = null;
//                                try {
//                                    adresses = geocoder.getFromLocation(
//                                            location.getLatitude(), location.getLongitude(), 1
//                                    );
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                String strMessage = "";
//                                String contact = "";
//                                SharedPreferences sharedPreferences = context.getSharedPreferences("MyContact", Context.MODE_PRIVATE);
//                                contact = sharedPreferences.getString("Forward", "0");
//                                strMessage += "Location of your lost phone fetched by Bitwin! ";
//
//                                strMessage += "\nCurrent location of your lost phone: ";
//
//                                strMessage += String.valueOf(adresses.get(0).getAddressLine(0));
//                                strMessage += "\n\nFind lost phone here: https://www.google.com/maps/search/?api=1&query=";
//                                strMessage += String.valueOf(location.getLatitude());
//                                strMessage += ",";
//                                strMessage += String.valueOf(location.getLongitude());
//
//                                // Toast.makeText(context, "address:"+strMessage, Toast.LENGTH_LONG).show();
//
//                                try {
//                                    SmsManager smsManager = SmsManager.getDefault();
//                                    ArrayList<String> parts = smsManager.divideMessage(strMessage);
//                                    smsManager.sendMultipartTextMessage(contact, null, parts, null, null);
//                                    Toast.makeText(context, "Phone Location Tracked by Bitwin!!", Toast.LENGTH_LONG).show();
//                                    abortBroadcast();
//                                } catch (Exception ErrVar) {
//                                    Toast.makeText(context, ErrVar.getMessage().toString(),
//                                            Toast.LENGTH_LONG).show();
//                                    ErrVar.printStackTrace();
//                                }
//
//                                SharedPreferences shared = context.getSharedPreferences("MyContact", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor1 = shared.edit();
//                                editor1.putString("isValid","No");
//                                editor1.apply();
//                            }
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String provider) {
//                            // TODO Auto-generated method stub
//                            Toast.makeText(context.getApplicationContext(), "disabled", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String provider) {
//                            // TODO Auto-generated method stub
//                        }
//
//                        @Override
//                        public void onStatusChanged(String provider, int status,
//                                                    Bundle extras) {
//                            // TODO Auto-generated method stub
//                            Toast.makeText(context.getApplicationContext(), "changed", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });



//                    //Toast.makeText(context, "aya sms", Toast.LENGTH_SHORT).show();
//                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
//                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
//                    }
//                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Location> task) {
//                            Location location = task.getResult();
//                            if (location != null) {
//                                try {
//                                    SharedPreferences sp =  context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
////
//
//                            if(sp.getString("isValid","No").equals("Yes"))
//                            {
//                                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//                                List<Address> adresses = null;
//                                try {
//                                    adresses = geocoder.getFromLocation(
//                                            location.getLatitude(), location.getLongitude(), 1
//                                    );
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                String strMessage = "";
//                                String contact = "";
//                                SharedPreferences sharedPreferences = context.getSharedPreferences("MyContact", Context.MODE_PRIVATE);
//                                contact = sharedPreferences.getString("Forward", "0");
//                                strMessage += "Location of your lost phone fetched by Bitwin! ";
//
//                                strMessage += "\nCurrent location of your lost phone: ";
//
//                                strMessage += String.valueOf(adresses.get(0).getAddressLine(0));
//                                strMessage += "\n\nFind lost phone here: https://www.google.com/maps/search/?api=1&query=";
//                                strMessage += String.valueOf(location.getLatitude());
//                                strMessage += ",";
//                                strMessage += String.valueOf(location.getLongitude());
//
//                                // Toast.makeText(context, "address:"+strMessage, Toast.LENGTH_LONG).show();
//
//                                try {
//                                    SmsManager smsManager = SmsManager.getDefault();
//                                    ArrayList<String> parts = smsManager.divideMessage(strMessage);
//                                    smsManager.sendMultipartTextMessage(contact, null, parts, null, null);
//                                    Toast.makeText(context, "Phone Location Tracked by Bitwin!!", Toast.LENGTH_LONG).show();
//                                    abortBroadcast();
//                                } catch (Exception ErrVar) {
//                                    Toast.makeText(context, ErrVar.getMessage().toString(),
//                                            Toast.LENGTH_LONG).show();
//                                    ErrVar.printStackTrace();
//                                }
//
//                                SharedPreferences shared = context.getSharedPreferences("MyContact", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor1 = shared.edit();
//                                editor1.putString("isValid","No");
//                                editor1.apply();
//                            }
//                        } catch (Exception e) {
//                                    e.printStackTrace();
//                                }


//                                    Toast.makeText(context, "chala 1:", Toast.LENGTH_LONG).show();
//
//                                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//                                    List<Address> adresses = geocoder.getFromLocation(
//                                            location.getLatitude(), location.getLongitude(), 1
//                                    );
//
//                                    String strMessage = "";
//                                    String contact = "";
//                                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyContact",Context.MODE_PRIVATE);
//                                    contact = sharedPreferences.getString("Forward","0");
//                                    strMessage += "BitWin Auto-Fetched Location Message ";
//
//                                    strMessage +=  "\nMy Street Address is: ";
//
//                                    strMessage += String.valueOf(adresses.get(0).getAddressLine(0));
//                                    strMessage += "\n\nFind me here: https://www.google.com/maps/search/?api=1&query=";
//                                    strMessage +=  String.valueOf(location.getLatitude());
//                                    strMessage +=  ",";
//                                    strMessage +=  String.valueOf(location.getLongitude());
//
//                                    Toast.makeText(context, "address:"+strMessage, Toast.LENGTH_LONG).show();
//
//                                    try {
//                                        SmsManager smsManager = SmsManager.getDefault();
//                                        ArrayList<String> parts = smsManager.divideMessage(strMessage);
//                                        smsManager.sendMultipartTextMessage(contact, null, parts, null, null);
//                                        Toast.makeText(context, "Location Sent Successfully!", Toast.LENGTH_LONG).show();
//                                        abortBroadcast();
//                                    }
//                                    catch (Exception ErrVar)
//                                    {
//                                        Toast.makeText(context,ErrVar.getMessage().toString(),
//                                                Toast.LENGTH_LONG).show();
//                                        ErrVar.printStackTrace();
//                                    }
//
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//
//                                }
//                            }
//                        }
//                    });

//                    // Log and display the SMS message.
//                    Log.d(TAG, "onReceive: " + strMessage);
//                    Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
//
//
//                        SmsManager smsManager = SmsManager.getDefault();
//                        ArrayList<String> parts = smsManager.divideMessage(strMessage);
//                        smsManager.sendMultipartTextMessage(contact, null, parts, null, null);
//                        Toast.makeText(context, "Alert Forwarded to:" + contact + "!", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        }
//
//
//    }
//
