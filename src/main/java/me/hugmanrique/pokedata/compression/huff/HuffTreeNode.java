package me.hugmanrique.pokedata.compression.huff;

import me.hugmanrique.pokedata.compression.HexInputStream;
import me.hugmanrique.pokedata.utils.Pair;

import java.io.IOException;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
public class HuffTreeNode {
    static int maxInPos = 0;
    HuffTreeNode node0, node1;
    int data = -1; // [-1, 0xFF]

    Pair<Boolean, Integer> getValue(LinkedListNode<Integer> code) throws Exception {
        Pair<Boolean, Integer> out = new Pair<>();
        out.setSecond(data);

        if (code == null) {
            out.setFirst(node0 == null && node1 == null && data >= 0);
            return out;
        }

        if (code.getValue() > 1) {
            throw new Exception("List should be a list of bytes ( < 2 ). Got " + code.getValue() + " instead");
        }

        int c = code.getValue();
        HuffTreeNode node = (c == 0 ? node0 : node1);

        if (node == null) {
            out.setFirst(false);
        }

        // TODO Check if we need to throw this NPE
        return node.getValue(code.getPrevious());
    }

    protected void parseData(HexInputStream stream) throws IOException {
        /*
		 * Tree Table (list of 8bit nodes, starting with the root node) Root
		 * Node and Non-Data-Child Nodes are: Bit0-5 Offset to next child node,
		 * Next child node0 is at (CurrentAddr AND NOT 1)+Offset*2+2 Next child
		 * node1 is at (CurrentAddr AND NOT 1)+Offset*2+2+1 Bit6 Node1 End Flag
		 * (1=Next child node is data) Bit7 Node0 End Flag (1=Next child node is
		 * data) Data nodes are (when End Flag was set in parent node): Bit0-7
		 * Data (upper bits should be zero if Data Size is less than 8)
		 */
        node0 = new HuffTreeNode();
        node1 = new HuffTreeNode();
        long pos = stream.getPos();
        int b = stream.readU8();
        long offset = b & 0x3F;
        boolean end0 = (b & 0x80) > 0;
        boolean end1 = (b & 0x40) > 0;

        // Parse data for node0 and node1
        stream.setPosition((pos - (pos & 1)) + offset * 2 + 2);
        parseData(node0, stream, end0, maxInPos);

        stream.setPosition((pos - (pos & 1)) + offset * 2 + 2 + 1);
        parseData(node1, stream, end1, maxInPos);

        stream.setPosition(pos);
    }

    private void parseData(HuffTreeNode node, HexInputStream stream, boolean end, int maxInPos) throws IOException {
        if (stream.getPos() < maxInPos) {
            if (end) {
                node.data = stream.readU8();
            } else {
                node.parseData(stream);
            }
        }
    }
}
