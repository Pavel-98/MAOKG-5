import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.*;

public class AnimationPlane implements ActionListener, KeyListener {
    private Button go;
    private TransformGroup wholePlane;
    private Transform3D translateTransform;
    private Transform3D rotateTransformX;
    private Transform3D rotateTransformY;
    private Transform3D rotateTransformZ;

    private JFrame mainFrame;

    private float sign=1.0f;
    private float zoom=0.5f;
    private float xloc=0.3f;
    private float yloc=0.3f;
    private float zloc=0.0f;
    private int moveType=1;
    private Timer timer;

    public AnimationPlane(TransformGroup wholePlane, Transform3D trans,JFrame frame){
        go = new Button("Go");
        this.wholePlane=wholePlane;
        this.translateTransform=trans;
        this.mainFrame=frame;

        rotateTransformX= new Transform3D();
        rotateTransformY= new Transform3D();
        rotateTransformZ= new Transform3D();

        FirstMainClass.canvas.addKeyListener(this);
        timer = new Timer(100, this);

        Panel p =new Panel();
        p.add(go);
        mainFrame.add("North",p);
        go.addActionListener(this);
        go.addKeyListener(this);
    }

    private void initialPlaneState(){
        //xloc=0.0f;
        //yloc=0.0f;
        //zloc=0.0f;
        //zoom=0.2f;
       // moveType=1;
        sign=1.0f;
        rotateTransformY.rotY(-Math.PI/2.8);
        translateTransform.mul(rotateTransformY);
        if(timer.isRunning()){timer.stop();}
        go.setLabel("Go");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // start timer when button is pressed
       if (e.getSource()==go){
          if (!timer.isRunning()) {
             timer.start();
             go.setLabel("Stop");
          }
          else {
              timer.stop();
              go.setLabel("Go");
          }
       }
       else {
           Move();
           translateTransform.setScale(new Vector3d(zoom,zoom,zoom));
           translateTransform.setTranslation(new Vector3f(xloc,yloc,zloc));
           wholePlane.setTransform(translateTransform);
       }
}

    private void Move(){
        if(moveType==1){ //fly forward and back
            yloc -= 0.1;
            if ((yloc *2) <= -4 ) {



                rotateTransformX.rotX(-Math.PI/2);
                translateTransform.mul(rotateTransformX);
                moveType = 2;
            }
            return;
        }
        if(moveType==2){ //fly_away
                zloc += 0.1;
                if(zloc > 2){
                rotateTransformX.rotX(-Math.PI/2);
                    translateTransform.mul(rotateTransformX);
                moveType = 3;
                return;
            }
        }
        if(moveType==3){ //fly_away
            yloc += 0.1;
            if(yloc >= 0){
                rotateTransformX.rotX(-Math.PI/2);
                translateTransform.mul(rotateTransformX);
                moveType = 4;
                return;
            }
        }
        if(moveType==4){ //fly_away
            zloc -= 0.1;
            if(zloc <= 0){
                rotateTransformY.rotX(-Math.PI/2);
                translateTransform.mul(rotateTransformX);
                moveType = 1;
                return;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Invoked when a key has been typed.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Invoked when a key has been pressed.
        if (e.getKeyChar()=='s') {xloc = xloc + .05f;}
        if (e.getKeyChar()=='a') {xloc = xloc - .05f;}
        if (e.getKeyChar()=='w') {yloc = yloc + .05f;}
        if (e.getKeyChar()=='z') {yloc = yloc - .05f;}

        if (e.getKeyChar()=='1') {
            rotateTransformX.rotX(Math.PI/2);
            translateTransform.mul(rotateTransformX);
        }
        if (e.getKeyChar()=='2') {
            rotateTransformY.rotY(Math.PI/2);
            translateTransform.mul(rotateTransformY);
        }
        if (e.getKeyChar()=='3') {
            rotateTransformZ.rotZ(Math.PI/2);
            translateTransform.mul(rotateTransformZ);
        }
        if (e.getKeyChar()=='0'){
            rotateTransformY.rotY(Math.PI/2.8);
            translateTransform.mul(rotateTransformY);
            moveType=2;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Invoked when a key has been released.
    }

}
