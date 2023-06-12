package com.example.example6;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private TextView textStep, textRotation, textCellNum;
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
    private List<ShapeDrawable> wallsInvisible;

    private ArrayList<Parallelogram> parallelograms;
    private ParticlesDrawable particlesDrawable;

    private int dotSize = 6;
    private int lineWidth = 4;
    int stepLength = 25;
    private int startX;
    private int startY;
    // width = 17.97
    // height = 30.81
    private int coefficient = 35;
    private float[] rotationMatrix = new float[9];
    private float[] orientationAngles = new float[3];
    private float azimuth = 0;

    private boolean isInitialized = false;

    private ArrayListLock direction = new ArrayListLock();

    int BoundaryWidth = 200;

    StepCounter stepCounter1;

    int stepCount = 0;
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
        textStep = (TextView) findViewById(R.id.textView);
        textRotation = (TextView) findViewById(R.id.textView1);
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
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
        }

        //set rotation vector
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
            rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_FASTEST);
        }

        //if the default accelerometer exists
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // set accelerometer
            Sensor accelerometer = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // register 'this' as a listener that updates values. Each time a sensor value changes,
            // the method 'onSensorChanged()' is called.
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }  // No accelerometer!

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
        wallsInvisible = new ArrayList<>();

        GenerateRectVisibleBounds generateRectVisibleBounds = new GenerateRectVisibleBounds(startX, startY, coefficient, lineWidth);
        ArrayList<Object> bounds = generateRectVisibleBounds.getBounds();

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

        GenerateRectInvisibleBounds generateRectInvisibleBounds = new GenerateRectInvisibleBounds(startX, startY, coefficient, BoundaryWidth);
        ArrayList<Object> bounds1 = generateRectInvisibleBounds.getBounds();

        for(Object bound: bounds1) {
            if (bound instanceof ArrayList) {
                ArrayList<Object> b = (ArrayList<Object>) bound;
                if (b.get(0) instanceof int[]) {
                    int[] bArray = (int[]) b.get(0);

                    if (b.get(1) instanceof Boolean) {
                        Boolean isBound = (Boolean) b.get(1);

                        ShapeDrawable d = new ShapeDrawable(new RectShape());
                        d.setBounds(bArray[0], bArray[1], bArray[2], bArray[3]);
                        if(isBound) {
                            wallsInvisible.add(d);
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
        for(ShapeDrawable wall : wallsInvisible) {
            wall.getPaint().setColor(Color.GRAY);
            wall.draw(canvas);
        }
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
        particlesDrawable.setBounds(wallsInvisible, parallelograms);

        stepCounter1 = new StepCounter();
        try {
            stepCounter1.collectReferenceSignal(readFromValueFile("reference_data.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        for(ShapeDrawable wall : wallsInvisible) {
            wall.getPaint().setColor(Color.GRAY);
            wall.draw(canvas);
        }
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
//        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
//            float stepCount = sensorEvent.values[0];
//            textStep.setText("\n\tStep Counter: " + (int) stepCount);
//
//            if (!isInitialized) {
//                isInitialized = true;
//            } else {
//                moveParticles(stepLength, (int) azimuth);
//            }
//
//            azimuth = 0;
//        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            // Convert the orientation angles from radians to degrees
            float azimuthDegrees = (float) Math.toDegrees(orientationAngles[0]);
            float pitchDegrees = (float) Math.toDegrees(orientationAngles[1]);
            float rollDegrees = (float) Math.toDegrees(orientationAngles[2]);

//            if (azimuth == 0) {
//                azimuth = azimuthDegrees;
//            } else {
//                azimuth += azimuthDegrees;
//                azimuth /= 2;
//            }
            direction.addItem(azimuthDegrees);

            textRotation.setText(
                    "\n\tazimuthDegrees: " + azimuthDegrees +
                            "\n\tpitchDegrees: " + pitchDegrees +
                            "\n\trollDegrees: " + rollDegrees);
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float aX = sensorEvent.values[0];
            float aY = sensorEvent.values[1];
            float aZ = sensorEvent.values[2];

            stepCounter1.processIncomingData(aY);

            if (stepCount != stepCounter1.getStepCount()) {
                float sum = 0;
                for (int i = 0; i < direction.size(); i++) {
                    sum += direction.getItem(i);
                }
                azimuth = sum / (float) direction.size();
                direction.clear();
                moveParticles(stepLength, (int) azimuth);
                textStep.setText("\n\tstep counter: " + stepCounter1.getStepCount() +
                "\n\tdirection " + azimuth);
                azimuth = 0;
                stepCount = stepCounter1.getStepCount();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void moveParticles(int distance, int direction) {
        Rect r = drawable.getBounds();
        int left = r.left, top = r.top, right = r.right, bottom = r.bottom;
        double radians = Math.toRadians(direction);
        double sinValue = Math.sin(radians);
        double cosValue = Math.cos(radians);

        left += (int) (distance * cosValue);
        right += (int) (distance * cosValue);
        top -= (int) (distance * sinValue);
        bottom -= (int) (distance * sinValue);

        drawable.setBounds(left, top, right, bottom);

        particlesDrawable.move(distance, direction);
        particlesDrawable.checkCollision();
        particlesDrawable.resample();
        textCellNum.setText("Cell Number = " + particlesDrawable.checkCellNum());

        // redrawing of the object
        canvas.drawColor(Color.WHITE);
        for(ShapeDrawable wall : wallsInvisible) {
            wall.getPaint().setColor(Color.GRAY);
            wall.draw(canvas);
        }
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

    private float[] readFromValueFile(String fileName) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        int row = 0;
        float[] value = new float[150];
        while ((line = bufferedReader.readLine()) != null) {
            value[row] = Float.parseFloat(line);
            row++;
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        return value;
    }
}