package tw.adouble.app.helloworld.mydatastorage170813001;

import android.content.SharedPreferences;
import android.os.Environment;
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
}
