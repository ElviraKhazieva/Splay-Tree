class SplayNode {

    SplayNode left, right, parent;
    int element;

    /** Constructor **/
    public SplayNode() {

        this(0, null, null, null);
    }
    /** Constructor **/
    public SplayNode(int ele) {

        this(ele, null, null, null);
    }
    /** Constructor **/
    public SplayNode(int ele, SplayNode left, SplayNode right, SplayNode parent) {

        this.left = left;
        this.right = right;
        this.parent = parent;
        this.element = ele;
    }
}