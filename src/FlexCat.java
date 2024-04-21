public class FlexCat extends Cat {

    public int[] occupation(Board board, int position, int direction) {
        return new int[0];
    }




    public FlexCat(int[] patterns) {
        super(patterns);
        this.points=3;
        this.size=3;
        this.isFlexible=true;
        this.directions=new int[]{};
        this.name = "Flex";
    }


}
