package Mxcomplier.IR;

import java.util.ArrayList;

public class Track {
    public ArrayList<Block> blocks;
    public Track() {
        blocks = new ArrayList<>();
    }

    public void add(Block b) {
        blocks.add(b);
    }
}
