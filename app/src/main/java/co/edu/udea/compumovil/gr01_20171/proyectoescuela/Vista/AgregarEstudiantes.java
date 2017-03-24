package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class AgregarEstudiantes extends AppCompatActivity {

    EditText nombreEstudiante, apellidoEstudiante, idEstudiante;
    Button returnbtn, addEst, addFoto;
    ImageView imgEst;
    OperacionesBaseDeDatos datos;
    Grupo grupo;
    Estudiante estudiante;
    //
    String picturePath="";
    Intent intent;
    Bundle bundle;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_estudiantes);
        init();
        getApplicationContext().deleteDatabase("pedidos.db");
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());

        addFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AgregarEstudiantes.this
                ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ingresar = new Intent(AgregarEstudiantes.this,PantallaProfesor.class);
                startActivity(ingresar);
                try {
                    finish();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        addEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    datos.getDb().beginTransaction();
                    intent = getIntent();
                    bundle = intent.getExtras();
                    grupo = (Grupo) intent.getSerializableExtra("GRUPO");
                    estudiante = new Estudiante(Integer.parseInt(idEstudiante.getText().toString()),
                                nombreEstudiante.getText().toString(),apellidoEstudiante.getText().toString(),
                                picturePath,grupo.getCurso(),grupo.getGrupo());
                    //datos.insertarEstudiante(estudiante);
                    datos.insertarEstudiante2(estudiante);
                    datos.getDb().setTransactionSuccessful();
                    Toast.makeText(getApplicationContext(),"Estudiante agregado",Toast.LENGTH_SHORT).show();
                    nombreEstudiante.setText("");
                    apellidoEstudiante.setText("");
                    idEstudiante.setText("");
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Se ha producido un error",Toast.LENGTH_SHORT).show();
                }finally{
                    datos.getDb().endTransaction();
                }

            }
        });
    }

  private byte[] imageViewToByte(ImageView imgEst) {
        Bitmap bitmap = ((BitmapDrawable)imgEst.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //SI ALGO MALO SUCEDE CAMBIAR .PNG,100
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(),"No tiene permisos suficientes",Toast.LENGTH_SHORT);
            }
            return;

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            /*try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgEst.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgEstudiante);

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(uri);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void init(){
        this.nombreEstudiante = (EditText) findViewById(R.id.nombreEstudiante);
        this.apellidoEstudiante = (EditText) findViewById(R.id.apellidoEstudiante);
        this.idEstudiante = (EditText) findViewById(R.id.idEstudiante);
        this.returnbtn = (Button) findViewById(R.id.returnbtn);
        this.addEst = (Button) findViewById(R.id.anadirEst);
        this.addFoto = (Button) findViewById(R.id.addFoto);
        this.imgEst = (ImageView) findViewById(R.id.imgEstudiante);
        }
}
