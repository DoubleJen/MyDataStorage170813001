package tw.adouble.app.helloworld.mydatastorage170813001;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private File sdroot, approot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
        }else{
            init();
        }
    }

    private void  init(){
        sp = getSharedPreferences("gamedata", MODE_PRIVATE); //以xml型式儲存
        editor = sp.edit();

//        String state = Environment.getExternalStorageState();
//        Log.i("brad", state); //brad: mounted

//        sdroot = Environment.getExternalStorageDirectory();
//        Log.i("brad", sdroot.getAbsolutePath()); //brad: /storage/emulated/0

        sdroot = Environment.getExternalStorageDirectory();
        approot = new File(sdroot, "Android/data/" + getPackageName() + "/");
        if (!approot.exists()){
            approot.mkdirs(); //mkdirs=>父不在自動弄一個
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                init();
            }else {
                finish();
            }
        }

    }

    public  void test1(View view){
        //寫
        editor.putString("username", "Brad");
        editor.putInt("stage", 2);
        editor.putBoolean("sound", false);
        editor.commit();//必做, 才會真正寫出
        Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();


    }

    public  void test2(View view){
        //讀
        boolean sound = sp.getBoolean("sound", true);
        String username = sp.getString("username", "guest");

    }

    public void test3(View view) throws FileNotFoundException {
        //寫
        try(FileOutputStream fout = openFileOutput("data.txt", MODE_PRIVATE)){
            fout.write("Hello, World\nHello, Brad\n1234567".getBytes());
            fout.flush();
            Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT);

        }catch (Exception e){
            Log.i("brad", e.toString());
        }
    }

    public void test4(View view){
        //讀
        try(FileInputStream fin = openFileInput("data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin))){
                String line; StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) !=null ){
                sb.append(line + "\n");
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.i("brad", e.toString());
        }
    }

    public void test5(View view){
        File file = new File(sdroot, "file1.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write("OK".getBytes());
            fout.flush();
            fout.close();
        } catch (Exception e) {
            Log.i("brad", e.toString());
        }

    }

    public void test6(View view){
        File file = new File(approot, "file1.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write("OK".getBytes());
            fout.flush();
            fout.close();
        } catch (Exception e) {
            Log.i("brad", e.toString());
        }
    }
}
