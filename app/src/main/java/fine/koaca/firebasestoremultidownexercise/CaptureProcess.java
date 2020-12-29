package fine.koaca.firebasestoremultidownexercise;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class CaptureProcess implements SurfaceHolder.Callback{
    Camera camera;
    MainActivity mainActivity;
    WindowGetDegree windowGetDegree;

    public CaptureProcess(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        preViewProcess();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }

    public void preViewProcess() {
        mainActivity.surfaceHolder.addCallback(this);
        mainActivity.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        camera=Camera.open();
        windowGetDegree=new WindowGetDegree(mainActivity);
        int degree=windowGetDegree.getDegree();
        camera.setDisplayOrientation(degree);
        try {
            camera.setPreviewDisplay(mainActivity.surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }
    public void captureProcess() {
        camera.takePicture(null,null,callback);
    }
    Camera.PictureCallback callback= new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            OutputStream fos=null;
            Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
            windowGetDegree=new WindowGetDegree(mainActivity);
            int degree=windowGetDegree.getDegree();
            bitmap=rotate(bitmap,degree);
            ContentResolver contentResolver=mainActivity.getContentResolver();
            GetUri getUri=new GetUri(mainActivity);
            Uri imageUri=getUri.getUri();

            try {
                fos=contentResolver.openOutputStream(imageUri);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
//            mainActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,getUri.getUri()));
            mainActivity.img_capture.setImageBitmap(bitmap);
            FirebaseStorageMethod firebaseStorageMethod=new FirebaseStorageMethod();
            firebaseStorageMethod.firebaseUpLoading(imageUri);

            camera.startPreview();

        }
    };

    private Bitmap rotate(Bitmap bitmap, int degree) {
        Matrix matrix=new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }
}
