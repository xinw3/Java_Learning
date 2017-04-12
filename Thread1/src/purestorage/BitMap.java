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
 */
public class BitMap {
	
	public void set_bit(int[] arr, int offset, int length) {
		if (arr.length == 0 || offset < 0 || length <= 0) {
			return;
		}
		
		int n = arr.length;
		for (int i = offset; i < n && i < offset +length; i++) {
			// the the bit is already 1, continue;
			if (arr[i] == 1) {
				continue;
			}
			arr[i] = 1;
			
			// set descendants
			// if we set the parent to be 1, we need to set all its children to be 1.
			setbit_down(arr, i, n);
			
			// set ancestors
			while (i > 0) {
				if (i % 2 == 0 && arr[i - 1] == 1 || (i % 2 == 1 && i < n - 1 && arr[i + 1] == 1)) {
					arr[(i - 1) / 2] = 1;
				}
				i = (i - 1) / 2;
			}
			
		}
		
	}
	
	public void setbit_down(int[] arr, int pos, int length) {
		if (pos > length) {
			return;
		}
		int left = pos * 2 + 1;
		int right = pos * 2 + 2;
		if (left < length && arr[left] == 0) {
			arr[left] = 1;
		}
		setbit_down(arr, left, length);
		if (right < length && arr[right] == 0) {
			arr[right] = 1;
		}
		setbit_down(arr, right, length);
		
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
			// we can only clear its left child
			int down = i * 2 + 1;
			while (down < n) {
				arr[down] = 0;
				down = 2 * down + 1;
			}
			int up = i;
			while (up > 0) {
				// if its ancestor is already 0, break;
				if (arr[(up - 1) / 2] == 0) {
					break;
				}
				arr[(up - 1) / 2] = 0;
				up = (up - 1) / 2;		
			}
			
			// clear ancestors
		}
		
		
	}

	public static void main(String[] args) {
		int[] arr = new int[] {0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1};
		BitMap bm = new BitMap();
		// bm.set_bit(arr, 10, 1);
		bm.clear_bit(arr, 3, 1);
		
		for (int i : arr) {
			System.out.print(i + " ");
		}

	}

}
