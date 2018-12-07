
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author J-C. Filliatre and L. Castelli Aleardi (INF421, 2013, Ecole
 * Polytechnique) An implementation of an ordered set based on binary search
 * trees
 */
public class TreeNode<E extends Comparable<E>> {

    final E value;
    TreeNode<E> left, right;

    public TreeNode(TreeNode<E> left, E value, TreeNode<E> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public TreeNode(E value) { //adicionando folha
        this.value = value;
        this.left = null;
        this.right = null;

    }

    public boolean contains(E x) {
        if (x.compareTo(this.value) == 0) {
            return true;
        } else if (x.compareTo(this.value) > 0) { //x é maior (ir para a direita)
            if (this.right != null) {
                this.right.contains(x);
            } else {
                return false;
            }
        } else if (x.compareTo(this.value) < 0) {
            if (this.left != null) {
                this.left.contains(x);
            } else {
                return false;
            }
        }
        return false;
    }

    public TreeNode<E> add(E x) {
        if (x.compareTo(this.value) > 0) { //x é maior (ir para a direita)
            if (this.right == null) {
                this.right = new TreeNode<>(x);
            } else {
                this.right.add(x);
            }
        } else if (x.compareTo(this.value) < 0) {//x é menor (ir pra esquerda)
            if (this.left == null) {
                this.left = new TreeNode(x);
            } else {
                this.left.add(x);
            }
        } else if (x.compareTo(this.value) == 0) {
            return null;
        }
        return null;
    }

    public E getMin() {

        if (left == null) {
            return null;
        } else if (left != null) {
            return left.getMin();
        } else {
            return value;
        }
    }

    static <E extends Comparable<E>> TreeNode<E> ofList(Queue<E> l, int n, int k) {
        if (k == 0 && n>0) {
            return new TreeNode(l.poll());
            //l.poll() pega a head 
        } else if(k==0 && n==0) {
            return null;
        }
        if (k >= 0 && n >= 0) {
            return new TreeNode<>(ofList(l, (n - 1) / 2, k - 1), l.poll(), ofList(l, n - (n - 1) / 2 - 1, k - 1));
        }
        return null;
    }

    static <E extends Comparable<E>> TreeNode<E> ofList(Queue<E> l) {
        int n = l.size();
        return ofList(l, n, (int) (Math.log(n) / Math.log(2)));
    }

    /* subset */
    static <E extends Comparable<E>> boolean subset(TreeNode<E> s1, TreeNode<E> s2) {
        throw new Error("A completer: exo 2");
    }

    /* split(v,s) returns two trees, containing values
	   * from s smaller and greater than s
     */
    public Pair<TreeNode<E>> split(E x) {

        TreeNode left = null;
        TreeNode right = null;
        Queue<TreeNode<E>> fila = new LinkedList<>();
        //passo um valor x de referencia (valor da raiz)
        fila.add(this);
        while (!fila.isEmpty()) {
            TreeNode<E> noAtual = fila.element();
            fila.poll();

            if (x.compareTo(noAtual.value) < 0) { //x é maior (ir para a direita)
                if (this.right == null) {
                    this.right = new TreeNode<>(noAtual.value);
                } else {
                    this.right.add(noAtual.value);
                }
            }else if(x.compareTo(noAtual.value)>0){//x é menor (ir pra esquerda)
              if(this.left == null){
                this.left = new TreeNode(noAtual.value);
              }else{
                  this.left.add(noAtual.value);
              }    
            }

            
        }
        
        return new Pair<TreeNode<E>>(left, right);

    }

    /* union */
    public TreeNode<E> union(TreeNode<E> s2) {
        Pair<TreeNode<E>> divisao = s2.split(this.value);
        return new TreeNode<>(this.left.union(divisao.a), this.value, this.left.union(divisao.b));
    }

    public String infixOrder() {
        String result = "";
        if (this.left != null) {
            result = result + this.left.infixOrder();
        }
        result = result + " " + this.value;
        if (this.right != null) {
            result = result + this.right.infixOrder();
        }
        return result;
    }

    /**
     * Return the list of elements listed according to infix order
     */
    public LinkedList<E> toList() {
        LinkedList<E> result = null;
        if (this.left != null) {
            result = (this.left.toList());
        }
        if (result == null) {
            result = new LinkedList<E>();
        }
        result.add(this.value);
        if (this.right != null) {
            result.addAll(this.right.toList());
        }
        return result;
    }

    public String toString() {
        return "(" + this.left + "+" + this.value + "+" + this.right + ")";
    }
}
