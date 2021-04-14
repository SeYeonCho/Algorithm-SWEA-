package 벽돌깨기;
// 1. 배열 매개변수로 넘겨주기 (재귀함수 안에서 배열 변경 일어나고, 계속 배열을 활용해야 할 때)
// 2. 재귀 후 반복문 돌기 전에 배열 저장, 매 반복마다 저장된 배열로 초기화
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_5656_벽돌깨기_solved {
	
	static class block {
		int r, c, range;	// r:행, c:열, range:폭발범위

		public block(int r, int c, int range) {
			super();
			this.r = r;
			this.c = c;
			this.range = range;
		}
		
	}
	
	static int T, W, H, N, answer;
	static boolean[][] isVisited;
	static final int INF = Integer.MAX_VALUE;
	static Queue<block> q = new ArrayDeque<>();
	static Queue<Integer> q2 = new ArrayDeque<>();
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());	// 구슬 떨어뜨리는 횟수
			W = Integer.parseInt(st.nextToken());	// 넓이
			H = Integer.parseInt(st.nextToken());	// 높이
			answer = INF;	// 최솟값을 구해야하므로 초기값을 INF로 준다
			
			int[][] blocks = new int[H][W];
			
			for(int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < W; j++) {
					blocks[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			getMin(0, blocks);
			
			System.out.println("#" + tc + " " + answer);
		}
	}
	
	/** 모든 경우에 대해서 수행해본 뒤 최솟값 구하기 */
	static void getMin(int n, int[][] blocks) { // ******** 매개변수 이용 *********
		if(n==N) {
			answer = Math.min(answer, count(blocks));
			return;
		}
		// ********** 재귀 들어오면, 반복문 돌기전에 배열 저장 **********
		int[][] blocksCopy = new int[H][W];
		deepCopy(blocks, blocksCopy);
		
		for(int i = 0; i < W; i++) {
			// ************* 매 반복마다 미리 저장해둔 배열로 초기화 **************
			deepCopy(blocksCopy, blocks);
			// 구슬 떨어뜨리기, 폭발
			blocks = shoot(i, blocks);
			// 다음 구슬
			getMin(n+1, blocks);
		}
	}
	
	static int[][] shoot(int k, int[][] blocks) {
		/** step1. k열에 구슬 떨어뜨렸을 때 폭발 로직 */
		int h = 0;	// h: k열 맨 위 블럭의 좌표
		isVisited = new boolean[H][W];
		while(h < H && blocks[h][k] == 0) {
			h++;
		}
		if(h==H) return blocks;	// k열에 블럭이 없으면 return
		
		isVisited[h][k] = true;
		q.offer(new block(h, k, blocks[h][k]));
		
		while(!q.isEmpty()) {
			blocks = explosion(q.poll(), blocks);
		}
		
		/** step2. 블록 사이 빈칸 채우기 */
		// 남은 블럭을 아래부터 스캔하며 큐에 넣음
		for(int i = 0; i < W; i++) {
			for(int j = H-1; j >= 0; j--) {
				int temp = blocks[j][i];
				if(temp != 0) q2.offer(temp);
			}
			for(int j = H-1; j >= 0; j--) {
				if(!q2.isEmpty()) blocks[j][i] = q2.poll();	// 큐에 남아있는 만큼 아래부터 채우고
				else blocks[j][i] = 0;						// 큐가 비면, 남은 공간은 0으로 채운다
			}
		}
		
		return blocks;
	}
	
	
	
	/** 블럭이 폭발 
	 * 
	 *  range만큼 d방향으로 visited되지 않은 블럭 q에 집어넣기
	 * 	범위를 벗어나면 중지, 블럭이 없거나 visited 된 것은 뛰어넘고 끝까지 가기
	 */
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static int[][] explosion(block b, int[][] blocks) {
		int r = b.r;
		int c = b.c;
		int range = b.range;
		blocks[r][c] = 0;
		
		for(int d = 0; d < 4; d++) {
			int nr = r;
			int nc = c;
			for(int i = 0; i < range -1; i++) {
				nr += dr[d];
				nc += dc[d];
				if(nr < 0 || nc < 0 || nr >= H || nc >= W) break;
				if(isVisited[nr][nc] || blocks[nr][nc] == 0) continue;
				// 사방탐색 중 범위 내에서 조건 만족 시, 방문처리 후 큐에 넣는다.
				isVisited[nr][nc] = true;
				if(blocks[nr][nc] == 1) {
					blocks[nr][nc] = 0;	// 블록의 range가 1이면 폭발 범위에 자기 자신만 포함되므로 큐에 넣지 않고 바로 처리한다
				}else {
					q.offer(new block(nr, nc, blocks[nr][nc]));
				}
			}
		}
		return blocks;
	}
	
	/** 남은 블럭 수 계산 */
	static int count(int[][] blocks) {
		int cnt = 0;
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < W; j++) {
				if(blocks[i][j] != 0) cnt++;
			}
		}
		return cnt;
	}
	
	static void deepCopy(int[][] a, int[][] b) {
		for(int i = 0; i < H; i++) {
			System.arraycopy(a[i], 0, b[i], 0, a[i].length);
		}
	}
}
