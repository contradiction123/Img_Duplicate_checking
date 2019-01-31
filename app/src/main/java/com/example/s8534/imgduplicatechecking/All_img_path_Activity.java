package com.example.s8534.imgduplicatechecking;
        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.os.Environment;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.support.constraint.ConstraintLayout;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.example.s8534.imgduplicatechecking.path.path_String;
        import com.gyf.barlibrary.ImmersionBar;

public class  All_img_path_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ArrayAdapter<String> adapter;
    private ListView mShowPathLv;
    private Button rgbbutton,button_cxxz;
    private ConstraintLayout constraintLayout;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_img_path);

        ImmersionBar.with(this)
                .transparentNavigationBar()
                .fullScreen(false)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .init(); //初始化，默认透明状态栏和黑色导航栏

        //去掉默认的标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)actionBar.hide();

        mShowPathLv = (ListView) findViewById(R.id.lv_show_path);
        constraintLayout=findViewById(R.id.ConstraintLayout);
        imageView=findViewById(R.id.img_ColorImageView);
        constraintLayout.setVisibility(View.GONE);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.GONE);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.GONE);
            }
        });
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1);
        adapter.addAll(getImagePathFromSD());
        mShowPathLv.setAdapter(adapter);
        rgbbutton=findViewById(R.id.rgbbutton);
        rgbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(All_img_path_Activity.this,ColorImage_activity.class);
                startActivity(intent2);
            }
        });
        button_cxxz=findViewById(R.id.button_cxxz);
        button_cxxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path_String.imagePathListname.clear();
                path_String.imgsun=0;
                path_String.pathstring="";
                Intent intent2=new Intent(All_img_path_Activity.this,MainActivity.class);
                startActivity(intent2);
            }
        });
        mShowPathLv.setOnItemClickListener(this);
    }

    /**
     * 从sd卡获取图片资源
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = path_String.pathstring;
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
                path_String.imagePathListname.add(file.getPath());
                path_String.imgsun++;
//                Toast.makeText(All_img_path_Activity.this,""+file.getPath(),Toast.LENGTH_SHORT).show();
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s=mShowPathLv.getItemAtPosition(position)+"";
        image(s);
    }
    public void image(String img_path){
        Bitmap bmp= BitmapFactory.decodeFile(img_path);
//        Drawable drawable = new BitmapDrawable(getResources(),bmp);
//        mColorImageView.setImageBitmap(bmp);
        imageView.setImageBitmap(bmp);
        constraintLayout.setVisibility(View.VISIBLE);
    }
}
