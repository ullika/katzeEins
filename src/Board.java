import java.util.ArrayList;

public abstract class Board {
    int n=41;
    int nEmpty=22;

    int patternCliqueCounter;
    int colorCliqueCounter;
    int[] colors;
    int[] patterns;

    int[][] neighbors;

    int[] patternClique; // stores clique for each position
    int[] colorClique;
    ArrayList<Integer>[] patternCliqueMembers; //stores positions for each clique
    ArrayList<Integer>[] colorCliqueMembers;
    Boolean isEmpty(int position) {return -1==colors[position];}

    int updateCliques(int position,int quality,int counter,int[] qualityClique,ArrayList<Integer>[] qualityCliqueMembers, int[] qualities) {
        int[] nbs=neighbors[position];
        qualityClique[position]=++counter;
        patternCliqueMembers[counter]=new ArrayList<>(position);

        for (int nb:nbs
             ) {
            if (qualities[nb]==quality) {
                //melt nb's clique into the new one
                int clique=qualityClique[nb];
                for (int member:qualityCliqueMembers[clique]
                     ) {
                    qualityCliqueMembers[counter].add(member);
                    qualityClique[member]=counter;
                }
                qualityCliqueMembers[clique].clear();
            }
        }
       return counter;
    }



    int[] update(int position,Card card) {
        int pClique=updateCliques(position,card.pattern,patternCliqueCounter,patternClique,patternCliqueMembers,patterns);
        int cClique=updateCliques(position,card.color,colorCliqueCounter,colorClique,colorCliqueMembers,colors);

        colors[position]= card.color;
        patterns[position]=card.pattern;
        this.nEmpty--;
        return new int[]{card.pattern,pClique,card.color, cClique};
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
