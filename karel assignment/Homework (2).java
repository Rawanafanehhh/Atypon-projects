import stanford.karel.SuperKarel;
import java.lang.Math;

public class Homework extends SuperKarel {
    private int countstep;

    public void run() {
        countstep=0;
        int width = getWorld().getColumns();
        int height  = getWorld().getRows();
        if(width<7||height <7){return;}
        if (height  < width) {
            drawthestrightline(height , width, height , width);
        } else drawthestrightline(height , width, width, height );

        int step = step(width, height );
        if (width % 2 == 0 && height  < width) {
            move();
            countstep++;
        }
        motion(Math.min(height, width), step);
        if ((height  % 2 == 0 && ((height  > width) || height  == width)) || (height  % 2 == 0 && width % 2 == 0) || (width % 2 == 0 && height  < width)) {
            move();
            countstep++;
        }
        motion(Math.min(height, width), step);
        System.out.println("for width=" +width +" and the high ="+height +" the Steps count= "+ countstep);
    }

    private void putdouble(int high, int width) {

        int count1 = 0;
        while (count1 != 2) {
            move();
            countstep++;

            if (!beepersPresent()) {
                putBeeper();
            }
            if (frontIsBlocked() && count1 == 0) {
                if (high > width || high == width) {
                    turnRight();
                } else turnLeft();
                move();
                countstep++;
                if (high > width || high == width) {
                    turnRight();
                } else turnLeft();
                if (!beepersPresent()) {
                    putBeeper();
                    count1++;
                }
            }
            if (frontIsBlocked() && count1 == 1) break;
        }

    }

    private void gotowall() {
        while (!frontIsBlocked()) {
            move();
            countstep++;
        }
        turnLeft();
    }

    private void gotomiddle(int x) {
        int count = 0;
        while (count != (x - 1) / 2) {
            move();
            countstep++;
            count++;
        }
    }

    private void putone() {

        while (!frontIsBlocked()) {
            if (!beepersPresent()) {
                putBeeper();
            }
            move();
            countstep++;

        }
        putBeeper();
    }

    private int stepsstright(int steps) {
        int step = steps;
        while (steps != 0) {
            move();
            countstep++;
            if (!beepersPresent()) {
                putBeeper();
            }
            steps--;
        }
        return step;

    }

    private int step(int w, int h) {
        int stepw = (w / 2);
        int steph = (h / 2);
        int step;
        if (stepw < steph) {
            step = stepw - 1;
            if (w % 2 == 0) {
                step = stepw - 2;
            }
        } else {
            step = steph - 1;
            if (h % 2 == 0) step = steph - 2;

        }

            move();
            countstep++;

        turnRight();
        return step;

    }

    private void motion(int h, int step) {
        stepsstright(step);
        turnLeft();
        stepsstright(step);
        if (h % 2 == 0) {
            move();
            countstep++;
        }
        stepsstright(step);
        turnLeft();
        stepsstright(step);
    }

    private void drawthestrightline(int height , int width, int x, int y) {
        if (height < width) {
            turnLeft();
        }
        gotomiddle(x);
        if (x % 2 == 0) {
            putBeeper();
            if (height  < width) {
                turnRight();
            } else turnLeft();
            putdouble(height , width);
        } else {
            if (height  < width) {
                turnRight();
            } else turnLeft();
            putone();
        }
        turnLeft();
        gotowall();

        gotomiddle(y);

        if (y % 2 == 0) {
            if (height  < width) {
                move();
                countstep++;
            }
            putBeeper();
            turnLeft();
            putdouble(height , width);

        } else {
            turnLeft();
            putone();
        }
        turnAround();

    }
}
