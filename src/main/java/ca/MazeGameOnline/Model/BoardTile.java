package ca.MazeGameOnline.Model;

/*
    A data type "BoardTile" definition class.
    A board tile owns one maze item, and has a boolean to store the info whether this board tile is revealed by the
        player from the fog.
*/

public class BoardTile {
    private int itemOnBoard;
    private boolean revealed;

    public BoardTile(int itemOnBoard) {
        this.itemOnBoard = itemOnBoard;
        revealed = false;
    }

    public int getItemOnBoard() {
        return itemOnBoard;
    }

    public void setItemOnBoard(int itemOnBoard) {
        this.itemOnBoard = itemOnBoard;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setToRevealed() {
        this.revealed = true;
    }
}
