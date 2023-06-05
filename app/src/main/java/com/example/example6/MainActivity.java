package com.example.example6;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Smart Phone Sensing Example 6. Object movement and interaction on canvas.
 */
public class MainActivity extends Activity implements OnClickListener, SensorEventListener  {

    /**
     * The buttons.
     */
    private Button up, left, right, down;
    /**
     * The text view.
     */
    private TextView textView, textCellNum;
    /**
     * The shape.
     */
    private ShapeDrawable drawable;
    /**
     * The canvas.
     */
    private Canvas canvas;
    /**
     * The sensors.
     */
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private Sensor rotationVector;
    /**
     * The walls.
     */
    private List<ShapeDrawable> wallsNotBound;
    private List<ShapeDrawable> wallsBound;
    private ArrayList<Parallelogram> parallelograms;
    private ParticlesDrawable particlesDrawable;

    private int dotSize = 6;
    private int lineWidth = 4;
    int stepLength = 5;
    private int startX;
    private int startY;
    // width = 17.97
    // height = 30.81
    private int coefficient = 35;
    private float[] rotationMatrix = new float[9];
    private float[] orientationAngles = new float[3];
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
        textCellNum = (TextView) findViewById(R.id.cellNum);

        // set listeners
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        // set step counter
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }

        //set rotation vector
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
            rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        }

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

        GenerateRectBounds generateRectBounds = new GenerateRectBounds(startX, startY, coefficient, lineWidth);
        ArrayList<Object> bounds = generateRectBounds.getBounds();

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

        GenerateParalBounds generateParalBounds = new GenerateParalBounds(startX, startY, coefficient, lineWidth);
        parallelograms = generateParalBounds.getBounds();

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

        particlesDrawable = new ParticlesDrawable(dotSize, coefficient, startX, startY, lineWidth);
        particlesDrawable.draw(canvas);
        particlesDrawable.setBounds(wallsBound, parallelograms);
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
        switch (v.getId()) {
            // UP BUTTON
            case R.id.button1: {
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left,r.top-stepLength,r.right,r.bottom-stepLength);
                particlesDrawable.move(stepLength, 90);

                break;
            }
            // DOWN BUTTON
            case R.id.button4: {
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left,r.top+stepLength,r.right,r.bottom+stepLength);
                particlesDrawable.move(stepLength, 270);

                break;
            }
            // LEFT BUTTON
            case R.id.button2: {
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left-stepLength,r.top,r.right-stepLength,r.bottom);
                particlesDrawable.move(stepLength, 180);

                break;
            }
            // RIGHT BUTTON
            case R.id.button3: {
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left+stepLength,r.top,r.right+stepLength,r.bottom);
                particlesDrawable.move(stepLength, 0);

                break;
            }
        }
        // if there is a collision between the dot and any of the walls
        if(particlesDrawable.isCollision(drawable)) {
            // reset dot to center of canvas
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            drawable.setBounds(startX-dotSize/2, startY-dotSize/2, startX+dotSize/2, startY+dotSize/2);
        }

        particlesDrawable.checkCollision();
        particlesDrawable.resample();
        textCellNum.setText("Cell Number = " + particlesDrawable.checkCellNum());

        // redrawing of the object
        canvas.drawColor(Color.WHITE);
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
        particlesDrawable.draw(canvas);
        drawable.draw(canvas);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            float stepCount = sensorEvent.values[0];
            textView.setText("\n\tStep Counter: " + (int) stepCount);
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            // Convert the orientation angles from radians to degrees
            float azimuthDegrees = (float) Math.toDegrees(orientationAngles[0]);
            float pitchDegrees = (float) Math.toDegrees(orientationAngles[1]);
            float rollDegrees = (float) Math.toDegrees(orientationAngles[2]);

            textView.setText(
                    "\n\tazimuthDegrees: " + azimuthDegrees +
                            "\n\tpitchDegrees: " + pitchDegrees +
                            "\n\trollDegrees: " + rollDegrees);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}