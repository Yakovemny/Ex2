package backupCell;

public class Spreadsheet {
    private int x;
    private int y;
    private Cell cell;

    public Spreadsheet(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Cell getCell(){
        return this.cell;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setCell(Cell cell){
        this.cell = cell;
    }
    public String toString(){
        return "["+this.x+"]["+this.y+"]";
    }
}
