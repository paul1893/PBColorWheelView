package com.thefrenchtouch.pbcolorwheelview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.thefrenchtouch.pbcolorwheelview.R;

/**
 * Created by Paul on 22/09/15.
 */
public class ColorWheelView extends View {

    //Données
    private int w; //La largeur de la "Wheel"
    private int h; //La hauteur de la "Wheel"
    private float strokeWidth = 3.0f; //Epaisseur des sections
    private float textSize = 80 ; //Taille du texte des sections
    private int sectionNumber = 1; //Le nombre de section
    private String[] colors; //Tableau de couleurs pour les sections
    private String[] texts; //Tableau de textes pour les sections
    private String[] textsColor; //Tableau de couleurs pour les textes
    private Bitmap bitmap; //Image centrale de la "Wheel"

    //Objects
    private Paint mPiePaint; //Objet pinceau
    private TypedArray a;

    public ColorWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a = context.obtainStyledAttributes(
                attrs,
                R.styleable.ColorWheelView,
                0, 0
        );
        init();
    }

    public ColorWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a = context.obtainStyledAttributes(
                attrs,
                R.styleable.ColorWheelView,
                0, 0
        );
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorWheelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        a = context.obtainStyledAttributes(
                attrs,
                R.styleable.ColorWheelView,
                0, 0
        );
        init();
    }

    public ColorWheelView(Context context) {
        super(context);
        init();
    }


    private void init() {

        //On récupère les attributs définis par l'utilisateur
        try {
            strokeWidth = a.getDimensionPixelSize(R.styleable.ColorWheelView_stroke, 3);
            textSize = a.getDimensionPixelSize(R.styleable.ColorWheelView_textSize, 10);
            colors = getResources().getStringArray(a.getResourceId(R.styleable.ColorWheelView_colors, R.array.array_colors));
            textsColor = getResources().getStringArray((a.getResourceId(R.styleable.ColorWheelView_textsColor, R.array.array_texts_colors)));
            sectionNumber = colors.length;
            setImage(a.getResourceId(R.styleable.ColorWheelView_src, R.mipmap.ic_launcher));
            texts = getResources().getStringArray((a.getResourceId(R.styleable.ColorWheelView_texts, R.array.array_texts)));

        } catch (Exception e) {}

        //On prépare le pinceau
        mPiePaint = new Paint();
        mPiePaint.setColor(Color.parseColor(colors[0]));
        mPiePaint.setStyle(Paint.Style.STROKE);
        mPiePaint.setStrokeWidth(strokeWidth);
        mPiePaint.setTextSize(textSize);
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // On recadre la vue pour s'adapter au contenu
        w = MeasureSpec.getSize(widthMeasureSpec);
        h = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Zone de dessin
        RectF mBounds = new RectF(0 + strokeWidth, 0 + strokeWidth, w - strokeWidth, h - strokeWidth);

        //On calcul les angles
        int angle = 360 / sectionNumber;
        int lastAngle = 0;

        //On dessine le logo au centre
        canvas.drawBitmap(bitmap, (w / 2) - (bitmap.getWidth() / 2), (h / 2) - (bitmap.getHeight() / 2), mPiePaint);

        //On dessine la "Wheel" (Roue)
        for (int i = 0; i < sectionNumber; i++) {
            Log.i("Arc", "From: " + lastAngle + " to " + (lastAngle + angle));

            //On dessine l'arc
            mPiePaint.setColor(Color.parseColor(colors[i]));
            canvas.drawArc(mBounds, lastAngle, angle, false, mPiePaint);

           //On écrit le nom de la section
            mPiePaint.setColor(Color.parseColor(textsColor[i]));
            mPiePaint.setStyle(Paint.Style.FILL);
            mPiePaint.setTextSize(textSize);

            //Angle représentant le milieu de la section en radian
            double middleAngleRadian = ((lastAngle + (angle/2))*Math.PI)/180;
            //Rayon de la roue
            float rayon = w/2;
            //Projection sur les axes X et Y
            float x = (float) (Math.cos(middleAngleRadian) * rayon);
            float y = (float) (Math.sin(middleAngleRadian) * rayon);

            Log.i("Point ", "Angle: "+(lastAngle+(angle/2))+" , Cosinus: "+Math.cos(((lastAngle+(angle/2))*Math.PI)/180)+" , Sinus: "+Math.sin(((lastAngle+(angle/2))*Math.PI)/180));

            //On écrit le texte
            try {

                //Coeffcient rapprochement du centre
                float coefFromCenter = 1.2f;

                x = rayon+(x/coefFromCenter)-(texts[i].length()*8); //On décale de la moitié de la largeur du texte (pour centrer)
                y = rayon+(y/coefFromCenter);

                //On dessine
                canvas.drawText(texts[i], Math.abs(x), Math.abs(y), mPiePaint);

            }catch (NullPointerException e){

            }catch (ArrayIndexOutOfBoundsException e2){
                Log.e("ColorWheelView", "Vous avez définis moins de texte que de sections");
            }

            //On rétablit le pinceau pour tracer la prochaine section
            mPiePaint.setStyle(Paint.Style.STROKE);

            //On incrémente l'angle
            lastAngle += angle;
        }

    }

    public void setStrokeWidth(float strokeWidth) {
        mPiePaint.setStrokeWidth(strokeWidth);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setImage(int resId) {
        this.bitmap = BitmapFactory.decodeResource(getResources(),resId);
    }

    public void setColor(String[] color) {
        this.colors = color;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }

    public void setTextsColor(String[] textsColor) {
        this.textsColor = textsColor;
    }

    public void setTextSize(float size) {
        mPiePaint.setTextSize(size);
    }

    public void setSectionNumber(int number) {
        this.sectionNumber = number;
    }

}
