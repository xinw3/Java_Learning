package purestorage;

import java.util.HashSet;
import java.util.Set;

public class DrawCircle {
	
	public class Point {
		int x , y;
		public Point(int x_val, int y_val) {
			x = x_val;
			y = y_val;
		}
		@Override
		public boolean equals(Object other) {
			if (other == this) {
				return true;
			}
			if (other == null) {
				return false;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Point point = (Point)other;
			return point.x == x && point.y == y;
		}

		@Override
		public int hashCode () {
			return x;
		}
	}
	
	public void addPoints(Set<Point> set, int x, int y) {
		set.add(new Point(x, y));		
		set.add(new Point(-1 * x, y));
		set.add(new Point(x, -1 * y));
		set.add(new Point(-1 * x, -1 * y));
		set.add(new Point(y, x));
		set.add(new Point(-1 * y, x));
		set.add(new Point(y, -1 * x));
		set.add(new Point(-1 * y, -1 * x));
	}

	/* time complexity: O(n^2) 
	 * where n is the number of points in the circle.
	 * Even if we've used the binary search, it's just
	 * we don't need to go through every integer in the
	 * range. But we still need to go every possible points
	 * and add them to our set.
	 */
	public Set<Point> getPoints_binary(int radius) {
		Set<Point> set = new HashSet<Point>();
		int rSquare = radius * radius;
		
		int x = 1;
		
		int y_start = 0;
		int y_end = 0;
		
		while (x * x <= rSquare) {
			y_start = 0;
			y_end = x;
			while (y_start + 1 < y_end) {
				int y_mid = y_start + (y_end - y_start) / 2;
				int curr = x * x + y_mid * y_mid;
				if (curr == rSquare) {
					addPoints(set, x, y_mid);
				} else if (curr < rSquare) {
					addPoints(set, x, y_mid);
					y_start = y_mid;
				} else {
					y_end = y_mid;
				}
			}
			x++;
		}
		x--;
		int xSquare = x * x;
		if (xSquare + y_start * y_start <= rSquare) {
			addPoints(set, x, y_start);
		}
		if (xSquare + y_end * y_end <= rSquare) {
			addPoints(set, x, y_end);
		}
		return set;
	}
	/* time complexity: O(n^2) which is slower than the binary 
	 * search version but their complexity is the same.
	 */
	public Set<Point> getPoints(int radius) {
		Set<Point> set = new HashSet<Point>();
		int rSquare = radius * radius;
		int diameter = 2 * radius;
		for (int i = 0; i <= diameter; i++) {
			int x = i - radius;
			for (int j = 0; j <= diameter; j++) {		
				int y = j - radius;
				if (x * x + y * y <= rSquare) {
					set.add(new Point(x, y));
				}
			}
		}
		return set;
	}
	public static void main(String[] args) {
		
		DrawCircle dc = new DrawCircle();
		Set<Point> set = dc.getPoints_binary(3);
		System.out.println("binary search version:");
		System.out.println("total points are: " + set.size());
		for (Point p : set) {
			System.out.println("[" + p.x + ", " + p.y + "]");
		}
		System.out.println("\nfor loop version:");
		Set<Point> set2 = dc.getPoints(3);
		System.out.println("total points are: " + set2.size());
		for (Point p : set2) {
			System.out.println("[" + p.x + ", " + p.y + "]");
		}
	}
}
