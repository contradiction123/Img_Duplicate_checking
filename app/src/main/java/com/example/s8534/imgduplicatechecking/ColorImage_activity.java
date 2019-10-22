package com.example.s8534.imgduplicatechecking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s8534.imgduplicatechecking.adapter.ImageItmeAdapter;
import com.example.s8534.imgduplicatechecking.image.GetFileSize;
import com.example.s8534.imgduplicatechecking.path.path_String;
import com.example.s8534.imgduplicatechecking.pojo.ImageItmepojo;
import com.gyf.barlibrary.ImmersionBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ColorImage_activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ImageView mColorImageView;
    Button mTextView,but_cxxz,but_exit;
    Bitmap bitmap1,bitmap2;
    Drawable drawable;
    TextView textView;
    ConstraintLayout constraintLayout;

    private ListView mShowPathLv;

    private int color1,color2;
    private  int count,sum_count;
    private int[][] x_y=new int[2][30];
    private List<String> pathlist=new ArrayList<>();

    private List<ImageItmepojo> imageItmepojoList=new ArrayList<>();
    private ImageItmeAdapter imageItmeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorimageview);

        ImmersionBar.with(this)
                .transparentNavigationBar()
                .fullScreen(false)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .init(); //初始化，默认透明状态栏和黑色导航栏

        //去掉默认的标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)actionBar.hide();


        //找到控件
        mColorImageView = (ImageView) findViewById(R.id.iv_civ);
        mTextView = (Button) findViewById(R.id.tv_test);
        but_cxxz=(Button) findViewById(R.id.but_cxxz);
        but_cxxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path_String.imagePathListname.clear();
                path_String.imgsun=0;
                path_String.pathstring="";
                Intent intent2=new Intent(ColorImage_activity.this,MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });
        mShowPathLv = (ListView) findViewById(R.id.lv_like_path);

        constraintLayout=findViewById(R.id.c1);
        constraintLayout.setVisibility(View.GONE);

        mShowPathLv.setOnItemClickListener(this);

        textView=findViewById(R.id.zhedang);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeimg();
            }
        });

        but_exit=findViewById(R.id.but_exit);
        but_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path_String.imagePathListname.clear();
                path_String.imgsun=0;
                path_String.pathstring="";
                finish();
            }
        });

        mColorImageViewsetonclick();
    }
    public void mColorImageViewsetonclick(){
        sum_count=0;
        count=0;
        List<String> imagePathList = new ArrayList<String>();
        for(int ij=0;ij<path_String.imgsun;ij++){
            if(!(select_img(ij,ij))){
                Toast.makeText(ColorImage_activity.this,"加载图片失败！！！",Toast.LENGTH_LONG).show();
                return ;
            }
            for(int i=ij+1;i<path_String.imgsun;i++){
                if(select_img(i,ij)){
                    if(bitmap1.getHeight()==bitmap2.getHeight() && bitmap1.getWidth()==bitmap2.getWidth())
                    {
                        if(paduan()){
                            if(imagePathList.size()==0)imagePathList.add("第"+(count+1)+"组");
                            imagePathList.add(path_String.imagePathListname.get(i));
                            path_String.imagePathListname.remove(i);
                            path_String.imgsun--;
                            i--;
//                                Toast.makeText(ColorImage_activity.this,"相同",Toast.LENGTH_SHORT).show();
                        }else{
//                                Toast.makeText(ColorImage_activity.this,"不相同",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(ColorImage_activity.this,"加载图片失败！！！",Toast.LENGTH_SHORT).show();
                }
            }
            if(imagePathList.size()>0){
                count++;
                sum_count+=imagePathList.size();
                imagePathList.add(path_String.imagePathListname.get(ij));//这里要加上作为判断的图
                imagePathList.set(0,imagePathList.get(0)+"，有"+(imagePathList.size()-1)+"个");//这里要减去没组相同的组号

                ImageItmepojo ipojo=new ImageItmepojo();
                ipojo.setText_zu(imagePathList.get(0));
                imageItmepojoList.add(ipojo);

                pathlist.add(imagePathList.get(0));

                for(int a=1;a<imagePathList.size();a++){
                    ImageItmepojo imageItmepojo=new ImageItmepojo();
                    imageItmepojo.setPath_item(imagePathList.get(a));
                    imageItmepojo.setStyle_item(imagePathList.get(a).substring(imagePathList.get(a).lastIndexOf(".")+1));
                    imageItmepojo.setName_item(imagePathList.get(a).substring(imagePathList.get(a).lastIndexOf("/")+1,imagePathList.get(a).lastIndexOf(".")));
                    imageItmepojo.setSize_item(GetFileSize.getsize(imagePathList.get(a)));

                    imageItmepojoList.add(imageItmepojo);

                    pathlist.add(imagePathList.get(a));
                }

                imagePathList.clear();
            }
            mTextView.setText("共"+count+"组，共"+sum_count+"张");
        }
        imageItmeAdapter = new ImageItmeAdapter(this,R.layout.image_itme,imageItmepojoList);
        mShowPathLv.setAdapter(imageItmeAdapter);
    }
    public boolean select_img(int s,int now){
        String img_path = path_String.imagePathListname.get(s);
        if(s==now){
            bitmap1= BitmapFactory.decodeFile(img_path);
            if(bitmap1==null){
                return false;
            }else{
                return true;
            }
        }else{
            bitmap2= BitmapFactory.decodeFile(img_path);
            if(bitmap2==null){
                return false;
            }else{
                return true;
            }
        }
    }

    public boolean paduan(){
        for(int i=0;i<30;i++){
            x_y[0][i]=(int)(Math.random()*bitmap1.getWidth());
            x_y[1][i]=(int)(Math.random()*bitmap1.getHeight());
            color1 = bitmap1.getPixel(x_y[0][i], x_y[1][i]);
//            int r1 = Color.red(color1);
//            int g1 = Color.green(color1);
//            int b1 = Color.blue(color2);
            color2 = bitmap2.getPixel(x_y[0][i], x_y[1][i]);
//            int r2 = Color.red(color2);
//            int g2 = Color.green(color2);
//            int b2 = Color.blue(color2);
            if(!(color1==color2)) {
                return false;
            }
        }
        return true;
    }

    public void image(String img_path){
        Bitmap bmp= BitmapFactory.decodeFile(img_path);
        mColorImageView.setImageBitmap(bmp);
        constraintLayout.setVisibility(View.VISIBLE);
    }

    int count1=0;
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (count1<1){
            Toast.makeText(this,"exit?",Toast.LENGTH_SHORT).show();
            count1++;
        }else {
            path_String.imagePathListname.clear();
            path_String.imgsun=0;
            path_String.pathstring="";
            finish();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //通过view获取其内部的组件，进而进行操作
        String s=pathlist.get(position);
        if(s.charAt(0)=='/'){
            image(s);
        }
    }

    public void closeimg(){
        constraintLayout.setVisibility(View.GONE);
    }

}
