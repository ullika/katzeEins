import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    int n=44;
    int nEmpty=22;
    int nFixed=22; //44-22 fixed at the beginning

    int patternCliqueCounter;
    int colorCliqueCounter;
    int[] colors;
    int[] patterns;

    int[][] neighbors;

    int[] patternClique; // stores clique for each position
    int[] colorClique;
    ArrayList<Integer>[] patternCliqueMembers; //stores positions for each clique
    ArrayList<Integer>[] colorCliqueMembers;

    public Board() {
        this.patternCliqueCounter=n-nEmpty-1;
        this.colorCliqueCounter=n-nEmpty-1;
        this.colors = new int[n];
        this.patterns = new int[n];
        this.patternClique = new int[n];
        this.colorClique = new int[n];
        this.neighbors = new int[n][n];
        this.patternCliqueMembers = new ArrayList[n];
        this.colorCliqueMembers = new ArrayList[n];

    }

    public Board copy() {
        Board copy=new Board();
        copy.patternCliqueCounter=this.patternCliqueCounter;
        copy.colorCliqueCounter=this.colorCliqueCounter;
        copy.colors=this.colors.clone();
        copy.patterns=this.patterns.clone();
        copy.patternCliqueMembers = new ArrayList[n];
        for (int i=0;i<=patternCliqueCounter; i++) {
            copy.patternCliqueMembers[i]=(ArrayList<Integer>) patternCliqueMembers[i].clone();
        }
        copy.colorCliqueMembers = new ArrayList[n];
        for (int i=0;i<=colorCliqueCounter; i++) {

            copy.colorCliqueMembers[i]=(ArrayList<Integer>) colorCliqueMembers[i].clone();
        }
        copy.patternClique=this.patternClique.clone();
        copy.colorClique=this.colorClique.clone();
        copy.neighbors=this.neighbors;

        return copy;

    }


    Boolean isEmpty(int position) {return -1==colors[position];}

    int updateCliques(int position,int quality,int counter,int[] qualityClique,ArrayList<Integer>[] qualityCliqueMembers, int[] qualities) {
        int[] nbs=neighbors[position];
        qualityClique[position]=++counter;
        qualityCliqueMembers[counter]=new ArrayList<>(position);
        qualityCliqueMembers[counter].add(position);

        for (int nb:nbs
             ) {


            if (-1 == nb) {
                continue;
            }
            //System.out.printf("Current quality: %d. NB's quality: %d%n",quality,qualities[nb]);
            if (qualities[nb]==quality&&qualityClique[nb]!=counter) {

              //  System.out.println(String.format("Position %d has neighbor %d with same quality. old clique id %d. " +
              //          "number of members: %d ",position,nb,qualityClique[nb],qualityCliqueMembers[qualityClique[nb]].size()));

                //melt nb's clique into the new one
                int clique=qualityClique[nb];
                for (int member:qualityCliqueMembers[clique]
                     ) {

                    //System.out.println(String.format("old clique id: %d, new id: %d",clique,counter));
                    qualityCliqueMembers[counter].add(member);
                    qualityClique[member]=counter;
                }
                qualityCliqueMembers[clique].clear();
            }
        }
       return counter;
    }



    int[] update(int position,Card card) {
        this.patternCliqueCounter=updateCliques(position,card.pattern,patternCliqueCounter,patternClique,patternCliqueMembers,patterns);
        this.colorCliqueCounter=updateCliques(position,card.color,colorCliqueCounter,colorClique,colorCliqueMembers,colors);

        colors[position]= card.color;
        patterns[position]=card.pattern;
        this.nEmpty--;
        return new int[]{card.pattern,this.patternCliqueCounter,card.color, this.colorCliqueCounter};
    }

    int goDirection(int position, int direction, int nSteps) {
        if (-1 == position) {
            return -1;
        }
        if (1==nSteps) {
            return neighbors[position][direction];
        }
            return goDirection(neighbors[position][direction],direction,nSteps-1);
    }

    int pattern(int position) {
        return patterns[position];
    }

    int color(int position) {
        return colors[position];
    }


    public ArrayList<Integer> getPatternCliqueMembers(int clique) {
        return patternCliqueMembers[clique];
    }
    public ArrayList<Integer> getColorCliqueMembers(int clique) {
        return colorCliqueMembers[clique];
    }
}
