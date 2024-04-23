public class MiniCat extends Cat {

    public int[] occupation(Board board, int position, int direction) {
        return new int[0];
    }




    public MiniCat(int[] patterns) {
        super(patterns);
        this.points=1;
        this.size=2;
        this.isFlexible=true;
        this.directions=new int[]{};
        this.name = "Mini";
    }


}
