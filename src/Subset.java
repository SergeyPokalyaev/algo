import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: spokalyaev
 * Date: 09.12.13
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
public class Subset {

    public static void main(String[] args) {

        int size = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQ = new RandomizedQueue<String>();

        for (;;) {
            String str = StdIn.readString();
            if ("".equals(str)){
                break;
            } else {
                rQ.enqueue(str);
            }
        }

        Iterator<String> iterator = rQ.iterator();

        for (int i = 0; i < size; i++) {
            System.out.println(iterator.next());
        }

    }
}
