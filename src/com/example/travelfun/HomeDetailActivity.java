package com.example.travelfun;

import android.view.*;
import android.widget.ImageView;
import android.app.*;
import android.os.*;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

 
public class HomeDetailActivity extends Activity{

    private ViewPager viewPager;
    private int[] images;   //ͼƬID����
    private int currentPage=0;   //��ǰչʾ��ҳ��

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        //��ʼ��ͼƬ��Դ
        images=new int[]{R.drawable.item1,R.drawable.item2,R.drawable.item3,R.drawable.item4,R.drawable.item5};      

        //-----��ʼ��PagerAdapter------
        PagerAdapter adapter=new PagerAdapter(){

            @Override
            public int getCount() {          	
                return images.length;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;
            }

            @Override
            public void destroyItem(ViewGroup container,int position,Object o){

            }
           
            @Override
            public Object instantiateItem(ViewGroup container,int position){
            	
                ImageView im=new ImageView(HomeDetailActivity.this);            
                im.setImageResource(images[position]);
                container.addView(im);
                return im;

            }

        };

        viewPager.setAdapter(adapter);      
   
    }

}