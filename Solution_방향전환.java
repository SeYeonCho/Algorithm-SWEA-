package 방향전환;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_방향전환 {
	static int T, answer;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			
			int loc1[] = new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
			int loc2[] = new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
			
			int diffX = Math.abs(loc1[0] - loc2[0]);
			int diffY = Math.abs(loc1[1] - loc2[1]);
			
			int max = Math.max(diffX, diffY);
			answer = (diffX % 2 != diffY % 2) ? 2 * max -1 : 2 * max;
			
			System.out.println("#" + tc + " " + answer);
		}
	}
}
