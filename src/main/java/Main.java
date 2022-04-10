import view.SegmentView;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
/*        ArrayList<Integer>test = new ArrayList<>();
        test.add(1);
        test.add(4);
        test.add(7);
        test.add(11);
        binarySearch(test , 16);*/

        DIConfig context = new DIConfig();
        context.injectObjects();
        SegmentView.getInstance().run();
    }


    private static int binarySearch(ArrayList<Integer> arr, int x) {
        int left = 0, right = arr.size() - 1, mid;

        while (left <= right) {
            mid = left + (right - left) / 2;

            if (arr.get(mid)== x)
                return mid;

            if (arr.get(mid) < x)
                left = mid + 1;

            else
                right = mid - 1;
        }
        System.out.println(left + " and " +  right);
        return -1;
    }


}
