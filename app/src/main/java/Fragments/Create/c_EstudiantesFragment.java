package Fragments.Create;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevca.labsqlite.ConexionSQLiteHelper;
import com.example.kevca.labsqlite.Dominio.Estudiante;
import com.example.kevca.labsqlite.R;
import com.example.kevca.labsqlite.Utilidades.Utilidades;

import java.util.ArrayList;

import Fragments.EstudiantesFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link c_EstudiantesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link c_EstudiantesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class c_EstudiantesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int codigoAlumno;
    private EditText c_alumno_nombre;
    private EditText c_alumno_apellido;
    private EditText c_alumno_cedula;
    private EditText c_alumno_edad;
    private TextView c_alumno_titulo;
    private Button c_alumno_btnGuardar;
    private ArrayList<Estudiante> alumnobl = new ArrayList<>();
    private Estudiante alumno;

    private OnFragmentInteractionListener mListener;

    public c_EstudiantesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment c_EstudiantesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static c_EstudiantesFragment newInstance(String param1, String param2) {
        c_EstudiantesFragment fragment = new c_EstudiantesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static c_EstudiantesFragment newInstance(int someInt) {
        c_EstudiantesFragment c_estudiantesFragment = new c_EstudiantesFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        c_estudiantesFragment.setArguments(args);

        return c_estudiantesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            codigoAlumno = getArguments().getInt("someInt", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_c__estudiantes, container, false);
        c_alumno_titulo= (TextView) vista.findViewById(R.id.title_c_alumno);
        c_alumno_cedula= (EditText) vista.findViewById(R.id.c_alumno_cedula);
        c_alumno_nombre= (EditText) vista.findViewById(R.id.c_alumno_nombre);
        c_alumno_apellido= (EditText) vista.findViewById(R.id.c_alumno_apellido);
        c_alumno_edad= (EditText) vista.findViewById(R.id.c_alumno_edad);
        c_alumno_btnGuardar = (Button) vista.findViewById(R.id.c_alumno_btnGuardar);
        if (codigoAlumno==0){
            c_alumno_titulo.setText("Crear Nuevo Estudiante");
        }else{
            alumno =findStudent(codigoAlumno);
            if (alumno!=null){
                updateAlumno(alumno);
            }else {
                Toast.makeText(getContext(),"No se encuentra el Alumno a modificar",Toast.LENGTH_LONG).show();
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame,new EstudiantesFragment()).commit();
            }

        }
        c_alumno_btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camposLlenos()){
                    alumno=new Estudiante(Integer.parseInt(c_alumno_cedula.getText().toString()),c_alumno_nombre.getText().toString(),c_alumno_apellido.getText().toString(),Integer.parseInt(c_alumno_edad.getText().toString()));
                    String salidaTOAST="";
                    Estudiante alumnoReturn=null;
                    //Se crea o modifica
                    if(codigoAlumno==0){
                        salidaTOAST="Se agrega el Alumno: '";
                        alumnoReturn= addStudent(alumno);
                    }
                    else {
                        salidaTOAST="Se modifica el Alumno: '";
                        alumnoReturn= updateStudent(alumno);
                    }
                    //Si se logra modificar o agregar se manda un toast
                    if(alumnoReturn!=null){
                        Toast.makeText(getContext(),salidaTOAST +alumnoReturn.getNombre()+"' Cedula: "+alumnoReturn.getId(),Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(),"No se agrega el Alumno",Toast.LENGTH_SHORT).show();
                    }
                    FragmentManager manager=getFragmentManager();
                    manager.beginTransaction().replace(R.id.content_frame,new EstudiantesFragment()).addToBackStack("bcaf").commit();
                }
                else Toast.makeText(getContext(),"Inserte informacion en todos los campos",Toast.LENGTH_SHORT).show();

            }
        });




        return  vista;
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

    private void updateAlumno(Estudiante alumno){
        c_alumno_titulo.setText("Modificar Estudiante");
        c_alumno_cedula.setText(String.valueOf(alumno.getId()));
        c_alumno_cedula.setEnabled(false);
        c_alumno_nombre.setText(String.valueOf(alumno.getNombre()));
        c_alumno_apellido.setText(String.valueOf(alumno.getApellidos()));
        c_alumno_edad.setText(String.valueOf(alumno.getEdad()));
        c_alumno_btnGuardar.setText("Modificar");
    }
    private boolean camposLlenos(){
        if(c_alumno_cedula.getText().toString().trim().equals("") || c_alumno_nombre.getText().toString().trim().equals("")|| c_alumno_apellido.getText().toString().trim().equals("") || c_alumno_edad.getText().toString().trim().equals("") ){
            return false;
        }
        return true;
    }
    private Estudiante addStudent(Estudiante estudiante){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_estudiantes",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_ID,estudiante.getId());
        values.put(Utilidades.CAMPO_NOMBRE,estudiante.getNombre());
        values.put(Utilidades.CAMPO_APPELLIDOS,estudiante.getApellidos());
        values.put(Utilidades.CAMPO_EDAD,estudiante.getEdad());
        Long idResultante=db.insert(Utilidades.TABLA_ESTUDIANTES,Utilidades.CAMPO_ID,values);

        db.close();
        if(idResultante.intValue() != -1){
            Toast.makeText(getContext(),"Se agrega: "+idResultante,Toast.LENGTH_LONG).show();
            return estudiante;
        }
        return null;
    }



    private Estudiante findStudent(int id){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "bd_estudiantes", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Estudiante estudiante = null;
        Cursor cursor = db.rawQuery("select * from estudiantes where id="+id+" ", null);
        cursor.moveToFirst();
        estudiante = new Estudiante(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        db.close();
        return estudiante;
    }
    private Estudiante updateStudent(Estudiante estudiante){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "bd_estudiantes", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros={String.valueOf(estudiante.getId())};
        ContentValues contentValues=new ContentValues();
        contentValues.put(Utilidades.CAMPO_NOMBRE,estudiante.getNombre());
        contentValues.put(Utilidades.CAMPO_APPELLIDOS,estudiante.getApellidos());
        contentValues.put(Utilidades.CAMPO_EDAD,estudiante.getEdad());
        db.update(Utilidades.TABLA_ESTUDIANTES,contentValues,Utilidades.CAMPO_ID+"=?",parametros);
        db.close();
        return estudiante;
    }
}
