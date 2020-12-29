package fine.koaca.firebasestoremultidownexercise;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseStorageMethod {
    FirebaseStorage storage;
    StorageReference reference;
    StorageReference storageReference;
    MainActivity mainActivity;
    CalendarPick calendarPick;

    public void firebaseUpLoading(Uri imageUri){
        calendarPick=new CalendarPick();
        calendarPick.CalendarCall();
        mainActivity=new MainActivity();
        storage=FirebaseStorage.getInstance("gs://fir-storemultidownexercise.appspot.com");
        reference=storage.getReference();
        String date_today=calendarPick.date_today;
        Log.i("information",date_today+"___today");
        storageReference=reference.child("images/"+date_today+"/"+System.currentTimeMillis()+".jpg");
        Log.i("information4",imageUri+"___execute_Uri UpLoading");
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 Log.i("information","uploading successed");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Log.i("information","uploading failed");
                    }
                });
                }

    public void firebaseDownLoading(){

    }
}
