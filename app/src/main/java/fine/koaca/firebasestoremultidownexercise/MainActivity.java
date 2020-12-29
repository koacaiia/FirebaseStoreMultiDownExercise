package fine.koaca.firebasestoremultidownexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.sql.ConnectionEventListener;

public class MainActivity extends AppCompatActivity {
    String[] permission_list={
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    FloatingActionButton fltButton;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    CaptureProcess captureProcess;
    ImageView img_capture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView=findViewById(R.id.surfaceView);
        surfaceHolder=surfaceView.getHolder();

        img_capture=findViewById(R.id.img_capture);


        requestPermissions(permission_list,0);
        fltButton=findViewById(R.id.btn_capture);

        fltButton.setOnClickListener(onClickListener);

        fltButton.setOnLongClickListener(onLongClickListener);
        captureProcess=new CaptureProcess(this);


    }
    View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           captureProcess.captureProcess();
        }
    };
    View.OnLongClickListener onLongClickListener= new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int results:grantResults){
            if(results== PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "permission allowed", Toast.LENGTH_SHORT).show();
                captureProcess.preViewProcess();
            }
        }
    }
}