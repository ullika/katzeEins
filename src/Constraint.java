import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Constraint {
    int[] positions;
    int fulfilled;
    int pointsHalf;
    int pointsFull;

    int[] code;

    double difficulty;

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

    public boolean full(int[] quality) {
        return this.nOccupied(quality)==6;
    }

    public int benefit(int[] quality, int qualityValue,int reward) {
        for (int pos:positions
        ) {
           if (quality[pos]==qualityValue) {
               //System.out.println("found quality");
               return this.nOccupied(quality)*reward;
           }
        }
        return 0;
    }
    public int nOccupied(int[] quality) {
        int occupied=6;
        for (int pos:positions
                ) {
            if (-1 == quality[pos]) {
                occupied--;
            }
        }
        return occupied;
    }

    public boolean invalid(int[] quality,int nQuality) {

        int maxDifferent = this.code.length;
        int maxOfOne = code[0];
        int[] qualityCount=new int[nQuality];
        Arrays.fill(qualityCount, 0);

        for (int pos:positions
             ) {
            if (-1 != quality[pos]) {
                qualityCount[quality[pos]]+=1;
                if (qualityCount[quality[pos]] > maxOfOne) {
                    return true;
                }
            }
        }
        int nDifferent=0;
        for (int j : qualityCount) {
            if (j > 0) {
                nDifferent++;
                if (nDifferent > maxDifferent) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFulfilled() {
        return fulfilled>=0;
    }

    public void setFulfilled(int value) {
        this.fulfilled = value;
    }

    public Constraint(int pointsHalf,int pointsFull,int[] code,int[] positions,double difficulty) {
        this.positions = positions;
        this.pointsFull=pointsFull;
        this.pointsHalf=pointsHalf;
        this.code=code; //code has to be sorted in reverse order!!
        this.fulfilled=-1;
        this.difficulty=difficulty;
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
