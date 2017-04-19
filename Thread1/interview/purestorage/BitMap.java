package purestorage;
/*
 *  pure storage buddy system bitmap
    Given a complete binary tree with nodes of values of either 1 or 0, the following rules always hold:
    (1) a node's value is 1 if and only if all its subtree nodes' values are 1
    (2) a leaf node can have value either 1 or 0
    Implement the following 2 APIs:
    set_bit(offset, length), set the bits at range from offset to offset+length-1
    clear_bit(offset, length), clear the bits at range from offset to offset+length-1
    
    i.e. The tree is like:
                 0
              /     \
             0        1
           /  \      /  \
          1    0    1    1
         /\   / \   / 
        1  1 1   0 1
        Since it's complete binary tree, the nodes can be stored in an array:
        [0,0,1,1,0,1,1,1,1,1,0,1]
    
    There are two ways to solve this problem. 
    One is DFS, which can be thought out first. 
    The other is BFS, which is most efficient because the values are
    stored in the array. read by level can increase the hit rate.
 */
public class BitMap {
	
	public void set_bit(int[] arr, int offset, int length) {
		if (arr.length == 0 || offset < 0 || length <= 0) {
			return;
		}

		int n = arr.length;
		for (int i = offset; i < n && i < offset +length; i++) {
			// if the bit is already 1, continue;
			if (arr[i] == 1) {
				continue;
			}
			arr[i] = 1;
			// set successors
			setbit_down(arr, i);
			// set ancestors
			setbit_up(arr, i);
		}
	}

	public void setbit_down(int[] arr, int pos) {
		int length = arr.length;
		if (pos > length) {
			return;
		}
		int left = pos * 2 + 1;
		int right = pos * 2 + 2;
		if (left < length && arr[left] == 0) {
			arr[left] = 1;
		}
		setbit_down(arr, left);
		if (right < length && arr[right] == 0) {
			arr[right] = 1;
		}
		setbit_down(arr, right);
	}
	public void setbit_up(int[] arr, int pos) {
		int length = arr.length;
		if (pos > length) {
			return;
		}
		while (pos > 0) {
			if (pos % 2 == 0 && arr[pos - 1] == 1 || (pos % 2 == 1 && pos < length - 1 && arr[pos + 1] == 1)) {
				arr[(pos - 1) / 2] = 1;
			}
			pos = (pos - 1) / 2;
		}
	}
	
	public void clear_bit(int[] arr, int offset, int length) {
		if (arr.length == 0 || offset < 0 || length <= 0) {
			return;
		}
		int n = arr.length;
		for (int i = offset; i < n && i < offset + length; i++) {
			if (arr[i] == 0) {
				continue;
			}
			arr[i] = 0;
			// clear descendants
			clearbit_down(arr, i);
			// clear ancestors
			clearbit_up(arr, i);
		}
	}
	public void clearbit_down(int[] arr, int pos) {
		// we only need to clear its left child
		int n = arr.length;
		int down = pos * 2 + 1;
		while (down < n) {
			arr[down] = 0;
			down = 2 * down + 1;
		}
	}

	public void clearbit_up(int[] arr, int pos) {
		int up = pos;
		while (up > 0) {
			// if its ancestor is already 0, break;
			if (arr[(up - 1) / 2] == 0) {
				break;
			}
			arr[(up - 1) / 2] = 0;
			up = (up - 1) / 2;		
		}
	}
	public static void main(String[] args) {
		int[] arr = new int[] {0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1};
		BitMap bm = new BitMap();
		// bm.set_bit(arr, 10, 1);
		bm.clear_bit(arr, 3, 1);
		
		for (int i : arr) {
			System.out.print(i + " ");	// 0 0 1 0 0 1 1 0 1 1 0 1 
		}
	}
}
