package 파핑파핑지뢰찾기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Solution_파핑파핑지뢰찾기 {
	static Queue<int[]> q = new ArrayDeque<>();
	static int T, N, lightedMap[][], clickCount;
	static char[][] map;
	static boolean[][] isClicked;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			clickCount = 0;
			map = new char[N][N];
			lightedMap = new int[N][N];
			isClicked = new boolean[N][N];
			
			for(int i = 0; i < N; i++) {
				map[i] = br.readLine().toCharArray();
			}
			
			// 지도 밝히기
			lighting();
			
			// 0인 지점 모두 클릭하기 (한 번 클릭할 때마다 연쇄작용 고려)
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(lightedMap[i][j]==0 && !isClicked[i][j]) {
						isClicked[i][j] = true;
						clickCount++;
						q.offer(new int[] {i, j});
						click();
					}
				}
			}
			
			// 남은 수들을 한번씩 다 클릭해준다.
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(!isClicked[i][j]) clickCount++;
				}
			}
			
			System.out.println("#" + tc + " " + clickCount);
		}
		
	}
	static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
	static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
	static void lighting() {
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				if(map[r][c]=='.') {
					int count = 0;
					for(int d= 0; d < 8; d++) {
						int nr = r + dr[d];
						int nc = c + dc[d];
						if(nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
						if(map[nr][nc]=='*') count++;
					}
					lightedMap[r][c] = count;
				} else if (map[r][c]=='*') {
					lightedMap[r][c] = -1;
					isClicked[r][c] = true;
				}
			}
		}
	}
	
	static void click() {
		while(!q.isEmpty()) {
			int r = q.peek()[0];
			int c= q.poll()[1];
			for(int d= 0; d < 8; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				if(nr < 0 || nc < 0 || nr >= N || nc >= N || isClicked[nr][nc]) continue;
				isClicked[nr][nc] = true;
				if(lightedMap[nr][nc]==0) q.offer(new int[] {nr, nc});
			}
		}
	}
}
