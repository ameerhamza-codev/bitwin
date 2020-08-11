package com.codev.bitwinapp.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codev.bitwinapp.MainActivity;
import com.codev.bitwinapp.R;
import com.codev.bitwinapp.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public static final String MyPREFERENCES = "MyContact" ;

    //custom variables
    CardView btnDisaster, btnFire, btnMedical, btnPolice;

    ImageView btnEmergency;

    FusedLocationProviderClient fusedLocationProviderClient;

    String formattedLocation="";
    SharedPreferences sharedPreferences;
    String name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bindVariables(root);

        SharedPreferences sharedpref = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        name = sharedpref.getString("Name","John Doe");

        //onClick

        btnEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyScreen(view);
            }
        });

        btnDisaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDisasterScreen(view);
            }
        });

        btnFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFireScreen(view);
            }
        });

        btnMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMedicalScreen(view);
            }
        });

        btnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPoliceScreen(view);
            }
        });

        return root;
    }

    //Implementation

    private void showPoliceScreen(View view) {

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
            View view1 = inflater1.inflate(R.layout.police_selection_list, binding.getRoot());

            Button btnFine = view1.findViewById(R.id.btn_fine_police);
            Button btnTheft = view1.findViewById(R.id.btn_theft);
            Button btnAssault = view1.findViewById(R.id.btn_assault);
            Button btnAbduction = view1.findViewById(R.id.btn_abduction);
            Button btnGang = view1.findViewById(R.id.btn_gang);
            Button btnDrug = view1.findViewById(R.id.btn_drug);
            Button btnShooting = view1.findViewById(R.id.btn_shooting);

            btnTheft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Police number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(String.format(getResources().getString(R.string.policeSMS), name, btnTheft.getText().toString()), "Police");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnAssault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Police number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.policeSMS, name, btnAssault.getText().toString()), "Police");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnAbduction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Police number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.policeSMS, name, btnAbduction.getText().toString()), "Police");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnGang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Police number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.policeSMS, name,btnGang.getText().toString()), "Police");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnDrug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Police number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.policeSMS, name, btnDrug.getText().toString()), "Police");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnShooting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Police number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.policeSMS, name, btnShooting.getText().toString()), "Police");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });


            btnFine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view1.getContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                 Manifest.permission.SEND_SMS,
                                 Manifest.permission.READ_SMS}, 44);
        }

    }

    private void showMedicalScreen(View view) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
            View view1 = inflater1.inflate(R.layout.medical_selection_list, binding.getRoot());

            Button btnFine = view1.findViewById(R.id.btn_fine_medical);
            Button btnAccident = view1.findViewById(R.id.btn_accident);
            Button btnBleeding = view1.findViewById(R.id.btn_bleeding);
            Button btnAttack = view1.findViewById(R.id.btn_heart);
            Button btnBreathing = view1.findViewById(R.id.btn_breathing);
            Button btnSeizure = view1.findViewById(R.id.btn_seizure);
            Button btnStroke = view1.findViewById(R.id.btn_stroke);
            Button btnAllergy = view1.findViewById(R.id.btn_allergy);



            btnAccident.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Medical number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.medicalSMS, name, btnAccident.getText().toString()), "Medical");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnBleeding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Medical number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.medicalSMS, name, btnBleeding.getText().toString()), "Medical");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnAttack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Medical number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.medicalSMS, name, btnAttack.getText().toString()), "Medical");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnBreathing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Medical number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.medicalSMS, name, btnBreathing.getText().toString()), "Medical");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnSeizure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Medical number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.medicalSMS, name, btnSeizure.getText().toString()), "Medical");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnStroke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Medical number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.medicalSMS, name, btnStroke.getText().toString()), "Medical");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnAllergy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Medical number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.medicalSMS, name, btnAllergy.getText().toString()), "Medical");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnFine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view1.getContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_SMS}, 44);
        }
    }

    private void showFireScreen(View view) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
            View view1 = inflater1.inflate(R.layout.fire_selection_list, binding.getRoot());

            Button btnFine = view1.findViewById(R.id.btn_fine_fire);
            Button btnHome = view1.findViewById(R.id.btn_home);
            Button btnWork = view1.findViewById(R.id.btn_work);
            Button btnVehicle = view1.findViewById(R.id.btn_vehicle);

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Fire number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.fireSMS, name, btnHome.getText().toString()), "Fire");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Fire number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.fireSMS, name, btnWork.getText().toString()), "Fire");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnVehicle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Fire number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.fireSMS, name, btnVehicle.getText().toString()), "Fire");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });


            btnFine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view1.getContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_SMS}, 44);

        }
    }

    private void showDisasterScreen(View view) {

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {

            LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
            View view1 = inflater1.inflate(R.layout.disaster_selection_list, binding.getRoot());

            Button btnFine = view1.findViewById(R.id.btn_fine_disaster);
            Button btnStanded = view1.findViewById(R.id.btn_standed);
            Button btnLost = view1.findViewById(R.id.btn_lost);
            Button btnSupplies = view1.findViewById(R.id.btn_supplies);

            btnStanded.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Disaster number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.disasterSMS, name, btnStanded.getText().toString()), "Disaster");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnLost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Disaster number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                @SuppressLint("StringFormatMatches")
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.disasterSMS, name, btnLost.getText().toString()), "Disaster");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });

            btnSupplies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Disaster number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                @SuppressLint("StringFormatMatches")
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.disasterSMS, name, btnSupplies.getText().toString()), "Disaster");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });


            btnFine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view1.getContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_SMS}, 44);
        }

    }

    private void showEmergencyScreen(View view) {

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
            View view1 = inflater1.inflate(R.layout.emergency_selection_list, binding.getRoot());

            Button btnFine = view1.findViewById(R.id.btn_fine_emg);
            Button btnPickUp = view1.findViewById(R.id.btn_pick);
            Button btnAllowance = view1.findViewById(R.id.btn_allowance);
            Button btnLoad = view1.findViewById(R.id.btn_load);

            btnPickUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Emergency number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.emergencySMS,name, btnPickUp.getText().toString()), "Emergency");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();

                }
            });

            btnAllowance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Emergency number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.emergencySMS, name, btnAllowance.getText().toString()), "Emergency");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();

                }
            });

            btnLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Send SMS Alert");
                    builder.setMessage("Do you want to send SMS alert to registered Emergency number?")
                            .setPositiveButton("Send Alert", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    sendSMS(getResources().getString(R.string.emergencySMS, name, btnLoad.getText().toString()), "Emergency");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
            });


            btnFine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view1.getContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        } else {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_SMS}, 44);
        }
    }

    private void sendSMS(String s, String type) {

        String message = s;
        String contact = "";
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        contact = sharedPreferences.getString(type,"0");

        if(contact.equals("0"))
        {
            Intent i = new Intent(getContext(), MainActivity.class);
            startActivity(i);
            Toast.makeText(getContext(), "Contact Not Found! Update Contact First!", Toast.LENGTH_LONG).show();
        }
        else
        {
            performTask(message,contact);
            Intent i = new Intent(getContext(), MainActivity.class);
            startActivity(i);
        }

    }

    private synchronized void performTask(String message, String contact) {

        formattedLocation+= message;
        formattedLocation +=  "\nMy Street Address is: ";

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> adresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        formattedLocation += String.valueOf(adresses.get(0).getAddressLine(0));
                        formattedLocation += "\n\nFind me here: https://www.google.com/maps/search/?api=1&query=";
                        formattedLocation +=  String.valueOf(location.getLatitude());
                        formattedLocation +=  ",";
                        formattedLocation +=  String.valueOf(location.getLongitude());
                        //Toast.makeText(getContext(), "address:"+formattedLocation, Toast.LENGTH_LONG).show();

                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            ArrayList<String> parts = smsManager.divideMessage(formattedLocation);
                            smsManager.sendMultipartTextMessage(contact, null, parts, null, null);
                            Toast.makeText(getContext(), "Alert Sent Successfully!", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception ErrVar)
                        {
                            Toast.makeText(getContext(),ErrVar.getMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                            ErrVar.printStackTrace();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    //binding

    private void bindVariables(View root) {
        btnDisaster= (CardView) root.findViewById(R.id.btn_disaster);
        btnFire= (CardView) root.findViewById(R.id.btn_fire);
        btnMedical= (CardView) root.findViewById(R.id.btn_medical);
        btnPolice=(CardView) root.findViewById(R.id.btn_police);
        btnEmergency=(ImageView) root.findViewById(R.id.bell_icon);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}