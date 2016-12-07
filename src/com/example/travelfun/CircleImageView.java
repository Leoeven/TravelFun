package com.example.travelfun;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;



public class CircleImageView extends ImageView {
    private Paint mPaintCircle;      //��Բ��ͼ��ı�
    private Paint mPaintBorder;          //��Բ�α߽�ı�
    private Paint mPaintBackgroud;      //��������ɫ�ı�
    private BitmapShader mBitmapShader;      //ͼ����ɫ��������������Բ
    private Matrix mMatrix;          //ͼƬ�任������-��������ͼƬ����Ӧview�ؼ��Ĵ�С
    private int mWidth;        //��ÿؼ����
    private int mHeight;             //��ÿؼ��߶�
    private int mRadius;             //����԰�İ뾶
    private int mCircleBorderWidth;        //�߽���
    private int mCirlcleBorderColor;             //�߽�߿���ɫ
    private int mCircleBackgroudColor;      //Բ��ͷ�񱳾�ɫ

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleHead);//����ȡ������ת��Ϊ����������õ�����
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CircleHead_circleBorderHeadWidth:
                    mCircleBorderWidth = (int) typedArray.getDimension(attr, 0);
                    break;
                case R.styleable.CircleHead_ringHeadColor:
                    mCirlcleBorderColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CircleHead_backgroundHeadColor:
                    mCircleBackgroudColor = typedArray.getColor(attr, Color.YELLOW);
                    break;
            }
        }
        init();
    }

    private void init() {
        //��ʼ��ͼƬ�任������
        mMatrix = new Matrix();

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);//�����,û��������ݵĻ���ͼƬ�任����ѿ��ģ���Ϊ��Щ���ص��ʧ��
        mPaintCircle.setStrokeWidth(12);                     //����Բ�߽���
        


        //��ͼ�μӱ߿�
        mPaintBorder = new Paint();
        mPaintBorder.setAntiAlias(true);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setStrokeWidth(mCircleBorderWidth);
        mPaintBorder.setColor(mCirlcleBorderColor);

        //��������ɫ�ı�
        mPaintBackgroud = new Paint();
        mPaintBackgroud.setColor(mCircleBackgroudColor);
        mPaintBackgroud.setAntiAlias(true);
        mPaintBackgroud.setStyle(Paint.Style.FILL);
    }

    //ʹ��BitmapShader��Բͼ��
    private void setBitmapShader() {
        //��ͼƬת����Bitmap
        Drawable drawable = getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        //��bitmap�Ž�ͼ����ɫ������������ģʽ��x��y�������ģʽ��CLAMP��ʾ����
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //��ʼ��ͼƬ��view֮��������������Ϊ����һ���������������float����þ��ȶ�ʧ
        float scale = 1.0f;
        int bitmapSize = Math.min(bitmap.getHeight(), bitmap.getWidth());

        scale = mWidth*1.0f/bitmapSize;
        //�������ͼ��任������������������������������ͬ��������
        mMatrix.setScale(scale, scale);
        //���Ǹ�ͼ����ɫ�����ñ任���󣬻���ʱ�͸���view��size������ͼƬ��size
        //ʹͼƬ�Ľ�С��һ�����ŵ�view�Ĵ�Сһ�£������Ϳ��Ա���ͼƬ��С����CLAMP����ģʽ���������ʾ��ȫ
        mBitmapShader.setLocalMatrix(mMatrix);
        //Ϊ��������һ��Shader�ı���
        mPaintCircle.setShader(mBitmapShader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * ���������ø߶ȿ�ȣ������õĽ�СֵΪ׼����ֹ����Բ
         */
        mWidth = getWidth();
        mHeight = getHeight();
        int mCircleSize = Math.min(mHeight, mWidth);
        //Բ�İ뾶�̵Ķ���֮һ��Ϊ�뾶
        mRadius = mCircleSize / 2 ;
    }

    /**
     * ���ǿ���֪�����������ֱ����imageviewȻ������shapeŪ��Բ�εĻ�����ζ�����������imageview���߼�ֻ��д��fragment�ȵ������ˣ�������ȥ�����߼��ķֲ�.�����ң���ֻ����ʸ��ͼ��������Ҫ�����
     * ���������дimageview����Ϊ�˸��õط�װ�õ��imageview���߼�
     * һ����Ϊ������xml���õ�imageview�Ļ�������ռ����һ������������Ҫ���¶���һ������
     * ����ô���¶��廭���أ�������дonDrawȻ�������̳и��෽������ǰ���¶��廭����Ҳ������super����ǰ�����������ǣ���������漰����Ⱦ��㣬������oom
     * ����Ȼ�����ǽ�����һ�ַ�����ʹ��BitmapShader��Բ�εģ�ֻҪ��bitmap����ȥ��Ȼ���MatrixҲ����ȥ��ΪͼƬ���ŵĹ���
     */

    /*
       * Canvas����ϵͳ�ṩ�����ǵ�һ���ڴ�����(��ʵ������ֻ��һ�׻�ͼ��API���������ڴ��������Bitmap)��
        *���������ṩ��һ���׶�����ڴ�������в����ķ��������е���Щ�������ǻ�ͼAPI��
        *Ҳ����˵�����ַ�ʽ�������Ѿ���һ��һ������ʹ��Graphic������������Ҫ�Ķ����ˣ�Ҫ��ʲôҪ��ʾʲô���������Լ����ơ�
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //����ע�͵�onDraw��Ϊ�˲�����ԭ���Ļ���,���ʹ�õĻ�����ζ��������Ⱦһ�㣬�ͻ���ڶ���������������OOM
        //        super.onDraw(canvas);
        if (getDrawable() != null) {
            setBitmapShader();
            canvas.drawRect(0, 0, mWidth, mHeight, mPaintBackgroud);//ֱ�ӹ��죬�������ģ�Ϊʲô����������Ϊ�����Ƿ��ģ�����������Բ��ͷ����û��ֱ�Ӵ���߽ǵģ�������Framelayout����ȥ���ǣ��������ﶨ�������ɫ���ߴ�ң���ȻҲ��װ�ø����ʹ��
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaintCircle);
        } else {
        //�����xml������̳�ImageView����û�б�setͼƬ����Ĭ�ϵ�ImageView������
            super.onDraw(canvas);
        }

    }
}