package com.example.s8534.imgduplicatechecking;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s8534.imgduplicatechecking.path.path_String;
import com.gyf.barlibrary.ImmersionBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ColorImage_activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ColorImageView mColorImageView;
    Button mTextView,but_cxxz;

    int color1,color2;
    Bitmap bitmap1,bitmap2;
    Drawable drawable;
    private String TAG = "Colorimageview";


    private ArrayAdapter<String> adapter;
    private ListView mShowPathLv;
    ConstraintLayout constraintLayout;

    private  int count,sum_count;

    private int[][] x_y=new int[2][30];
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
        mColorImageView = (ColorImageView) findViewById(R.id.iv_civ);
        mTextView = (Button) findViewById(R.id.tv_test);
        but_cxxz=(Button) findViewById(R.id.but_cxxz);
        but_cxxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(ColorImage_activity.this,MainActivity.class);
                startActivity(intent2);
            }
        });
        mShowPathLv = (ListView) findViewById(R.id.lv_like_path);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1);

        constraintLayout=findViewById(R.id.c1);
        constraintLayout.setVisibility(View.GONE);

        mShowPathLv.setOnItemClickListener(this);
        mColorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeimg();
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeimg();
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
                adapter.addAll(imagePathList);
                mShowPathLv.setAdapter(adapter);
                imagePathList.clear();
            }
            mTextView.setText("共"+count+"组，共"+sum_count+"张");
        }
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
        Drawable drawable = new BitmapDrawable(getResources(),bmp);
//        mColorImageView.setImageBitmap(bmp);
        mColorImageView.setBackgroundDrawable(drawable);
        constraintLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //通过view获取其内部的组件，进而进行操作
        String s=mShowPathLv.getItemAtPosition(position)+"";
        image(s);
    }

    public void closeimg(){
        constraintLayout.setVisibility(View.GONE);
    }
}