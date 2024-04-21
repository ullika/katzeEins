import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Constraint {
    int[] positions;
    int fulfilled;
    int pointsHalf;
    int pointsFull;

    int[] code;

    public int getPointsHalf() {
        return pointsHalf;
    }

    public int getPointsFull() {
        return pointsFull;
    }

    public String toString() {
        return Arrays.toString(this.code);
    }
    public boolean dependsOn(int pos) {
        for (int position:positions
        ) {
            if (position == pos) {
                return true;
            }
        }
        return false;
    }

    public boolean determined(int[] quality) {
        for (int pos:positions
             ) {
            if (-1 == quality[pos]) {
                return false;
            }
        }
        return true;
    }

    public boolean isFulfilled() {
        return fulfilled>=0;
    }

    public void setFulfilled(int value) {
        this.fulfilled = value;
    }

    public Constraint(int pointsHalf,int pointsFull,int[] code,int[] positions) {
        this.positions = positions;
        this.pointsFull=pointsFull;
        this.pointsHalf=pointsHalf;
        this.code=code;
        this.fulfilled=-1;
    }

    public void reset() {
        this.fulfilled=-1;
    }


    public boolean checkQuality(int[] quality,int nQualities) {
        ArrayList<Integer> count = new ArrayList<>(nQualities);
        for (int i = 0; i < nQualities; i++) {
            count.add( 0);
        }

        for (int position:positions) {
            int val=count.get(quality[position]);
            count.set(quality[position], val+1);
        }
        Collections.sort(count);

        for (int i=0;i<code.length;i++
             ) {
            if (count.get(nQualities-(i+1))!=code[i])
                return false;
        }
        return true;

    }
}
