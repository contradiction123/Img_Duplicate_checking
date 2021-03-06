package com.example.s8534.imgduplicatechecking;
        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.s8534.imgduplicatechecking.image.ImageUtils;
        import com.example.s8534.imgduplicatechecking.path.path_String;
        import com.gyf.barlibrary.ImmersionBar;

        import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CHOOSE_IMAGE = 0x01;

    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT = 0xff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this)
                .transparentNavigationBar()
                .fullScreen(false)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .init(); //初始化，默认透明状态栏和黑色导航栏

        //去掉默认的标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)actionBar.hide();


        prepareToOpenAlbum();
    }

    private void prepareToOpenAlbum() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT);
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(MainActivity.this, "You denied the write_external_storage permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_CANCELED){
            finish();
        }
        if(requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            Uri uri =  data.getData();
            Log.d("Tianma", "Uri = " + uri);
            String path = ImageUtils.getRealPathFromUri(this, uri);
            Log.d("Tianma", "realPath = " + path);

//            photoPath.setVisibility(View.VISIBLE);

            String[] all=path.split("/");
            String paths="";
            for(int i=1;i<all.length-1;i++){
                paths+="/"+all[i];
            }
            path_String.pathstring=paths.trim();
            Intent intent2=new Intent(MainActivity.this,All_img_path_Activity.class);
            startActivity(intent2);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(MainActivity.this,"1231",Toast.LENGTH_SHORT).show();
            finish();//返回关闭当前Activity
            return true;//应该是不在往下进行了
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

}
