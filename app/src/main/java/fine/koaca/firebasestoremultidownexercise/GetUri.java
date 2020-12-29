package fine.koaca.firebasestoremultidownexercise;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class GetUri {
    ContentResolver contentResolver;
    ContentValues contentValues;
    MainActivity mainActivity;
    Uri imageUri;

    public GetUri(MainActivity mainActivity){
        this.mainActivity=mainActivity;

    }

    public Uri getUri(){
        contentResolver=mainActivity.getContentResolver();
        contentValues=new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,System.currentTimeMillis()+".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"image/*");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+"/Fine");
        imageUri=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

        return imageUri;
    }
}
