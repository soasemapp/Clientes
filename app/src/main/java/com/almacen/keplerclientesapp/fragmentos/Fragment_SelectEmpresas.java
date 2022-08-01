package com.almacen.keplerclientesapp.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.almacen.keplerclientesapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_SelectEmpresas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_SelectEmpresas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Fragment framentLogin, fragmentReUser, fragmenSelect;
    View view;
    Button btnJacve, btnAutodis, btnCecra, btnGuvi, btnVipla, btnPressa,btnSPR;
    int Pantalla = 0;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;


    public Fragment_SelectEmpresas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectEmpresas.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_SelectEmpresas newInstance(String param1, String param2) {
        Fragment_SelectEmpresas fragment = new Fragment_SelectEmpresas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_select_empresas, container, false);

        btnJacve = view.findViewById(R.id.btnJacve);
        btnAutodis = view.findViewById(R.id.btnAutodis);
        btnCecra = view.findViewById(R.id.btnCecra);
        btnGuvi = view.findViewById(R.id.btnGuvi);
        btnVipla = view.findViewById(R.id.btnVipla);
        btnPressa = view.findViewById(R.id.btnPressa);
        btnSPR = view.findViewById(R.id.btnSPR);


        framentLogin = new loginFragment();
        fragmentReUser = new requestUserFragment();
        fragmenSelect = new Fragment_SelectEmpresas();


        Bundle datosRecuperados = getArguments();

        if (datosRecuperados == null) {
            // No hay datos, manejar excepción
        } else {
            // Y ahora puedes recuperar usando get en lugar de put
            Pantalla = datosRecuperados.getInt("Pantalla");

        }


        btnJacve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Esta seguro que desea entrar a Jacve?").setIcon(R.drawable.icons_edificio).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Pantalla == 0){
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 0);
                            framentLogin.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, framentLogin);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }else{
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 0);
                            fragmentReUser.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, fragmentReUser);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Selección de empresa");
                titulo.show();
            }
        });

        btnAutodis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Esta seguro que desea entrar a Autodis?").setIcon(R.drawable.icons_edificio).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Pantalla == 0){
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 1);
                            framentLogin.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, framentLogin);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }else{
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 1);
                            fragmentReUser.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, fragmentReUser);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Selección de empresa");
                titulo.show();
            }
        });

        btnCecra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Esta seguro que desea entrar a CECRA?").setIcon(R.drawable.icons_edificio).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Pantalla == 0){
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 2);
                            framentLogin.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, framentLogin);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }else{
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 2);
                            fragmentReUser.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, fragmentReUser);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Selección de empresa");
                titulo.show();


            }
        });

        btnGuvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Esta seguro que desea entrar a CECRA?").setIcon(R.drawable.icons_edificio).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Pantalla == 0){
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 3);
                            framentLogin.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, framentLogin);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }else{
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 3);
                            fragmentReUser.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, fragmentReUser);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Selección de empresa");
                titulo.show();


            }
        });

        btnVipla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Esta seguro que desea entrar a CECRA?").setIcon(R.drawable.icons_edificio).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Pantalla == 0){
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 4);
                            framentLogin.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, framentLogin);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }else{
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 4);
                            fragmentReUser.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, fragmentReUser);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Selección de empresa");
                titulo.show();

            }
        });
        btnPressa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Esta seguro que desea entrar a CECRA?").setIcon(R.drawable.icons_edificio).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Pantalla == 0){
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 5);
                            framentLogin.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, framentLogin);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }else{
                            //Crear bundle, que son los datos que pasaremos
                            Bundle datosAEnviar = new Bundle();
                            // Aquí pon todos los datos que quieras en formato clave, valor
                            datosAEnviar.putInt("Empresa", 5);
                            fragmentReUser.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragmentos, fragmentReUser);
                            fragmentTransaction.addToBackStack(null);
                            // Terminar transición y nos vemos en el fragmento de destino
                            fragmentTransaction.commit();
                        }


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Selección de empresa");
                titulo.show();

            }
        });

btnSPR.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        alerta.setMessage("¿Esta seguro que desea entrar a SPR?").setIcon(R.drawable.icons_edificio).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Pantalla == 0){
                    //Crear bundle, que son los datos que pasaremos
                    Bundle datosAEnviar = new Bundle();
                    // Aquí pon todos los datos que quieras en formato clave, valor
                    datosAEnviar.putInt("Empresa", 6);
                    framentLogin.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contenedorFragmentos, framentLogin);
                    fragmentTransaction.addToBackStack(null);
                    // Terminar transición y nos vemos en el fragmento de destino
                    fragmentTransaction.commit();
                }else{
                    //Crear bundle, que son los datos que pasaremos
                    Bundle datosAEnviar = new Bundle();
                    // Aquí pon todos los datos que quieras en formato clave, valor
                    datosAEnviar.putInt("Empresa", 6);
                    fragmentReUser.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contenedorFragmentos, fragmentReUser);
                    fragmentTransaction.addToBackStack(null);
                    // Terminar transición y nos vemos en el fragmento de destino
                    fragmentTransaction.commit();
                }


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog titulo = alerta.create();
        titulo.setTitle("Selección de empresa");
        titulo.show();
    }
});



        return view;
    }
}