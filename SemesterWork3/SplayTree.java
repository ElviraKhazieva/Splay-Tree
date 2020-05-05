import java.util.NoSuchElementException;

class SplayTree {

    private SplayNode root;
    private int count = 0;

    /** Constructor **/
    public SplayTree()
    {
        root = null;
    }

    /** Function to check if tree is empty **/
    public boolean isEmpty()
    {
        return root == null;
    }

    /** clear tree **/
    public void clear() {

        root = null;
        count = 0;
    }

    /** function to insert element */
    public void insert(int ele) {

        SplayNode z = root;
        SplayNode p = null;
        while (z != null) {

            p = z;
            if (ele > p.element)
                z = z.right;
            else
                z = z.left;
        }
        z = new SplayNode();
        z.element = ele;
        z.parent = p;
        if (p == null)
            root = z;
        else if (ele > p.element)
            p.right = z;
        else
            p.left = z;
        Splay(z);
        count++;
    }
    /** rotate **/
    public void makeLeftChildParent(SplayNode c, SplayNode p) {

        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p)) {
            throw new RuntimeException("WRONG");
        }

        if (p.parent != null) {

            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.right != null)
            c.right.parent = p;

        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    /** rotate **/
    public void makeRightChildParent(SplayNode c, SplayNode p) {

        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p))
            throw new RuntimeException("WRONG");
        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.left != null)
            c.left.parent = p;
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }

    /** function splay **/
    private void Splay(SplayNode x)
    {
        while (x.parent != null) {

            SplayNode Parent = x.parent;
            SplayNode GrandParent = Parent.parent;
            if (GrandParent == null) //zig вершина - левый или правый ребенок корня
            {
                if (x == Parent.left)
                    makeLeftChildParent(x, Parent);
                else
                    makeRightChildParent(x, Parent);
            }
            else {

                if (x == Parent.left) {

                    if (Parent == GrandParent.left) { // zig-zig ребенок - левый, родитель - левый

                        makeLeftChildParent(Parent, GrandParent);
                        makeLeftChildParent(x, Parent);
                    }
                    else { //zig-zag ребенок - левый, родитель - правый

                        makeLeftChildParent(x, x.parent);
                        makeRightChildParent(x, x.parent);
                    }
                }
                else { //zig-zag ребенок - правый, родитель - левый

                    if (Parent == GrandParent.left) {

                        makeRightChildParent(x, x.parent);
                        makeLeftChildParent(x, x.parent);
                    }
                    else {//zig-zig ребенок - правый, родитель - правый

                        makeRightChildParent(Parent, GrandParent);
                        makeRightChildParent(x, Parent);
                    }
                }
            }
        }
        root = x;
    }

    /** function to remove element **/
    public void remove(int ele) {
        if (search(ele)) {
            SplayNode node = findNode(ele);
            remove(node);
        } else {
            throw new NoSuchElementException("Такого элемента не существует.");
        }
    }

    /** function to remove node **/
    private void remove(SplayNode node) {
        this.Splay(node);

        SplayTree leftSubtree = new SplayTree();
        leftSubtree.root = this.root.left;
        if(leftSubtree.root != null)
            leftSubtree.root.parent = null;

        SplayTree rightSubtree = new SplayTree();
        rightSubtree.root = this.root.right;
        if(rightSubtree.root != null)
            rightSubtree.root.parent = null;

        if(leftSubtree.root != null) {
            SplayNode m = leftSubtree.maximum(leftSubtree.root);
            leftSubtree.Splay(m);
            leftSubtree.root.right = rightSubtree.root;
            this.root = leftSubtree.root;
        }
        else {
            this.root = rightSubtree.root;
        }
    }

    public SplayNode maximum(SplayNode x) {
        while(x.right != null)
            x = x.right;
        return x;
    }

    /** Functions to count number of nodes **/
    public int countNodes() {

        return count;
    }

    /** Functions to search for an element **/
    public boolean search(int val) {

        return findNode(val) != null;
    }

    private SplayNode findNode(int ele) {

        SplayNode PrevNode = null;
        SplayNode z = root;
        while (z != null) {

            PrevNode = z;
            if (ele > z.element)
                z = z.right;
            else if (ele < z.element)
                z = z.left;
            else if(ele == z.element) {
                Splay(z);
                return z;
            }

        }
        if(PrevNode != null) {

            Splay(PrevNode);
            return null;
        }
        return null;
    }

    /** Function for inorder traversal **/
    public void inorder() {

        inorder(root);
    }
    private void inorder(SplayNode r) {

        if (r != null) {

            inorder(r.left);
            System.out.print(r.element +" ");
            inorder(r.right);
        }
    }
}
