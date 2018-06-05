package Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kevca.labsqlite.Adapter.AdapterEstudiante;
import com.example.kevca.labsqlite.ConexionSQLiteHelper;
import com.example.kevca.labsqlite.Dominio.Estudiante;
import com.example.kevca.labsqlite.R;
import com.example.kevca.labsqlite.Utilidades.Utilidades;

import java.util.ArrayList;

import Fragments.Create.c_EstudiantesFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstudiantesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EstudiantesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstudiantesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recycler_estudiante;
    ArrayList<Estudiante> listaEstudiantes;
    EditText search_estudiante;
    AdapterEstudiante adapter;
    Button btnCrear;
    private ArrayList<Estudiante> alumnobl = new ArrayList<>();
    //public static AlumnoBL alumnobl = AlumnoBL.Companion.getInstance();//singleton


    private OnFragmentInteractionListener mListener;

    public EstudiantesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstudiantesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstudiantesFragment newInstance(String param1, String param2) {
        EstudiantesFragment fragment = new EstudiantesFragment();
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
        View vista = inflater.inflate(R.layout.fragment_estudiantes, container, false);
        btnCrear= (Button)vista.findViewById(R.id.btn_crearEstudiante);
        listaEstudiantes=new ArrayList<>();////////////////
        recycler_estudiante=(RecyclerView) vista.findViewById(R.id.recycler_estudiante);
        recycler_estudiante.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        adapter = new AdapterEstudiante(listaEstudiantes);
        recycler_estudiante.setAdapter(adapter);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame, c_EstudiantesFragment.newInstance(0)).addToBackStack("back2").commit();
            }
        });
        //Swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction==ItemTouchHelper.LEFT){
                    FragmentManager manager=getFragmentManager();
                    manager.beginTransaction().replace(R.id.content_frame,c_EstudiantesFragment.newInstance((int) viewHolder.itemView.getTag())).addToBackStack("backcaf").commit();

                }else {
                    eliminarEstudiante((int) viewHolder.itemView.getTag());
                    //Estudiante alumno= alumnobl.delete((int) viewHolder.itemView.getTag());
                    llenarLista();
                    adapter = new AdapterEstudiante(listaEstudiantes);
                    recycler_estudiante.setAdapter(adapter);
                    Toast.makeText(getContext(),"Eliminado ",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Paint color=new Paint();
                if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    if (dX>0){

                        color.setColor(Color.parseColor("#df013b"));
                        RectF fondo=new RectF((float)itemView.getLeft(),(float)itemView.getTop(),dX,(float)itemView.getBottom());
                        c.drawRect(fondo,color);



                    }else{
                        color.setColor(Color.parseColor("#01DFA5"));
                        RectF fondo=new RectF((float)itemView.getLeft(),(float)itemView.getTop(),itemView.getRight(),(float)itemView.getBottom());
                        c.drawRect(fondo,color);
                    }

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recycler_estudiante);
        search_estudiante=(EditText) vista.findViewById(R.id.search_estudiante);
        search_estudiante.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void llenarLista() {
        alumnobl = new ArrayList<>();
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "bd_estudiantes", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Estudiante estudiante = null;
        Cursor cursor = db.rawQuery("select * from estudiantes", null);
        while (cursor.moveToNext()) {
            estudiante = new Estudiante(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            alumnobl.add(estudiante);
        }
        listaEstudiantes=alumnobl;
        db.close();
    }

    private void filter(String text){
        ArrayList<Estudiante> listaAlumnosBusqueda=new ArrayList<>();
        for(Estudiante alumno : listaEstudiantes){
            if (alumno.getNombre().toLowerCase().contains(text.toLowerCase()) || String.valueOf(alumno.getId()).contains(text)){
                listaAlumnosBusqueda.add(alumno);
            }
        }
        adapter.filterList(listaAlumnosBusqueda);
    }
    private void eliminarEstudiante(int id){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "bd_estudiantes", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros={String.valueOf(id)};
        db.delete(Utilidades.TABLA_ESTUDIANTES,Utilidades.CAMPO_ID+"=?",parametros);
    }
}
