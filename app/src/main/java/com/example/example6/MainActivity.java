package com.example.example6;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Smart Phone Sensing Example 6. Object movement and interaction on canvas.
 */
public class MainActivity extends Activity implements OnClickListener {

    /**
     * The buttons.
     */
    private Button up, left, right, down;
    /**
     * The text view.
     */
    private TextView textView;
    /**
     * The shape.
     */
    private ShapeDrawable drawable;
    /**
     * The canvas.
     */
    private Canvas canvas;
    /**
     * The walls.
     */
    private List<ShapeDrawable> wallsNotBound;
    private List<ShapeDrawable> wallsBound;
    private ArrayList<Parallelogram> parallelograms;

    int dotSize = 10;
    int lineWidth = 4;
    int startX;
    int startY;
    // width = 17.97
    // height = 30.81
    int coefficient = 35;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the buttons
        up = (Button) findViewById(R.id.button1);
        left = (Button) findViewById(R.id.button2);
        right = (Button) findViewById(R.id.button3);
        down = (Button) findViewById(R.id.button4);

        // set the text view
        textView = (TextView) findViewById(R.id.textView1);

        // set listeners
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        // get the screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x; // width = 720
        int height = size.y; // height = 1280

        // create a drawable object
        startX = width/2-50;
        startY = height/2;

        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(Color.BLUE);
        drawable.setBounds(startX-dotSize/2, startY-dotSize/2, startX+dotSize/2, startY+dotSize/2);

        wallsNotBound = new ArrayList<>();
        wallsBound = new ArrayList<>();

        GenerateBounds generateBounds = new GenerateBounds(startX, startY, coefficient, lineWidth);
        ArrayList<Object> bounds = generateBounds.getBounds();

        for(Object bound: bounds) {
            if (bound instanceof ArrayList) {
                ArrayList<Object> b = (ArrayList<Object>) bound;
                if (b.get(0) instanceof int[]) {
                    int[] bArray = (int[]) b.get(0);

                    if (b.get(1) instanceof Boolean) {
                        Boolean isBound = (Boolean) b.get(1);

                        ShapeDrawable d = new ShapeDrawable(new RectShape());
                        d.setBounds(bArray[0], bArray[1], bArray[2], bArray[3]);
                        if(isBound) {
                            wallsBound.add(d);
                        }
                        else {
                            wallsNotBound.add(d);
                        }
                    }
                }
            }
        }

        parallelograms = new ArrayList<>();
        int topLeftX, topLeftY, bottomLeftX, bottomLeftY;

        // Cell 10
//        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
//        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81));
        topLeftX = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8+1.24-1.79));
        topLeftY = startY + (int) (coefficient*(-2.73/2-4.22));
        bottomLeftX = startX - (int) (1.79*coefficient/2);
        bottomLeftY = startY - (int) (2.73*coefficient/2);
        Parallelogram parallelogram1 = new Parallelogram(topLeftX, topLeftY, bottomLeftX, bottomLeftY, (int)(coefficient*1.79), lineWidth);
        parallelograms.add(parallelogram1);

        // Cell 11
//        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
//        y = startY + (int) (coefficient*(2.73/2+4.22));
        topLeftX = startX - (int) (1.79*coefficient/2);
        topLeftY = startY + (int) (2.73*coefficient/2);
        bottomLeftX = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8+1.24-1.79));
        bottomLeftY = startY + (int) (coefficient*(2.73/2+4.22));
        Parallelogram parallelogram2 = new Parallelogram(topLeftX, topLeftY, bottomLeftX, bottomLeftY, (int)(coefficient*1.79), lineWidth);
        parallelograms.add(parallelogram2);


//        textView.setText(
//                "\tleft = " + left +
//                "\n\ttop = " + top +
//                "\n\tright = " + right +
//                "\n\tbottom = " + bottom);

        // create a canvas
        ImageView canvasView = (ImageView) findViewById(R.id.canvas);
        Bitmap blankBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        canvasView.setImageBitmap(blankBitmap);

        // draw the objects
        drawable.draw(canvas);
        for(ShapeDrawable wall : wallsNotBound) {
            wall.getPaint().setColor(Color.BLUE);
            wall.draw(canvas);
        }
        for(ShapeDrawable wall : wallsBound) {
            wall.getPaint().setColor(Color.RED);
            wall.draw(canvas);
        }
        for(Parallelogram p : parallelograms) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            p.draw(canvas, paint);
        }


//        textView.setText(
//                "\n\twidth = " +  width +
//                "\n\theight = " + height);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // This happens when you click any of the four buttons.
        // For each of the buttons, when it is clicked we change:
        // - The text in the center of the buttons
        // - The margins
        // - The text that shows the margin
        int stepLength = 10;
        switch (v.getId()) {
            // UP BUTTON
            case R.id.button1: {
                Toast.makeText(getApplication(), "UP", Toast.LENGTH_SHORT).show();
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left,r.top-stepLength/2,r.right,r.bottom-stepLength/2);
                textView.setText("\n\tMove Up" + "\n\tTop Margin = "
                        + drawable.getBounds().top);
                break;
            }
            // DOWN BUTTON
            case R.id.button4: {
                Toast.makeText(getApplication(), "DOWN", Toast.LENGTH_SHORT).show();
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left,r.top+stepLength/2,r.right,r.bottom+stepLength/2);
                textView.setText("\n\tMove Down" + "\n\tTop Margin = "
                        + drawable.getBounds().top);
                break;
            }
            // LEFT BUTTON
            case R.id.button2: {
                Toast.makeText(getApplication(), "LEFT", Toast.LENGTH_SHORT).show();
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left-stepLength/2,r.top,r.right-stepLength/2,r.bottom);
                textView.setText("\n\tMove Left" + "\n\tLeft Margin = "
                        + drawable.getBounds().left);
                break;
            }
            // RIGHT BUTTON
            case R.id.button3: {
                Toast.makeText(getApplication(), "RIGHT", Toast.LENGTH_SHORT).show();
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left+stepLength/2,r.top,r.right+stepLength/2,r.bottom);
                textView.setText("\n\tMove Right" + "\n\tLeft Margin = "
                        + drawable.getBounds().left);
                break;
            }
        }
        // if there is a collision between the dot and any of the walls
        if(isCollision()) {
            // reset dot to center of canvas
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            drawable.setBounds(startX-dotSize/2, startY-dotSize/2, startX+dotSize/2, startY+dotSize/2);

        }

        // redrawing of the object
        canvas.drawColor(Color.WHITE);
        drawable.draw(canvas);
        for(ShapeDrawable wall : wallsNotBound) {
            wall.getPaint().setColor(Color.BLUE);
            wall.draw(canvas);
        }
        for(ShapeDrawable wall : wallsBound) {
            wall.getPaint().setColor(Color.RED);
            wall.draw(canvas);
        }
        for(Parallelogram p : parallelograms) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            p.draw(canvas, paint);
        }
    }

    /**
     * Determines if the drawable dot intersects with any of the walls.
     * @return True if that's true, false otherwise.
     */
    private boolean isCollision() {
        for(ShapeDrawable wall : wallsBound) {
            if(isCollision(wall,drawable))
                return true;
        }
        for(Parallelogram p : parallelograms) {
            if(isCollision(p,drawable))
                return true;
        }
        return false;
    }

    /**
     * Determines if two shapes intersect.
     * @param first The first shape.
     * @param second The second shape.
     * @return True if they intersect, false otherwise.
     */
    private boolean isCollision(ShapeDrawable first, ShapeDrawable second) {
        Rect firstRect = new Rect(first.getBounds());
        return firstRect.intersect(second.getBounds());
    }

    private boolean isCollision(Parallelogram first, ShapeDrawable second) {
        return false;
    }
}