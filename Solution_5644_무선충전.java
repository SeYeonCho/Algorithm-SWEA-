package 무선충전;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_5644_무선충전 {
	static class BC implements Comparable<BC>{
		int[] loc = new int[2];
		int C, P;
		public BC(int x, int y, int c, int p) {
			loc[0] = x;
			loc[1] = y;
			C = c;
			P = p;
		}
		@Override
		public int compareTo(BC o) { // P에대한 내림차순으로 정렬
			return (this.P - o.P)*(-1);
		}
	}
	
	static int[] dy = {0, -1, 0, 1, 0};
	static int[] dx = {0, 0, 1, 0, -1};	// 문제에서 주어진 좌표 -> (dc, dr)순서
	
	static int T, M, A, answer;	// T:테스트 수, M:총 이동시간, A:BC의 수
	static int[] userA, userB, locA, locB;
	static BC[] BCs;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			
			st = new StringTokenizer(br.readLine());
			
			M = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			
			locA = new int[2];	// A, B의 위치
			locB = new int[2];
			locA[0] = 1; locA[1] = 1;
			locB[0] = 10; locB[1] = 10;
			
			userA = new int[M];
			userB = new int[M];
			BCs = new BC[A];
			
			answer = 0;
			
			st = new StringTokenizer(br.readLine());	// 사용자A의 이동정보
			for(int i = 0; i < M; i++) {
				userA[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());	// 사용자B의 이동정보
			for(int i = 0; i < M; i++) {
				userB[i] = Integer.parseInt(st.nextToken());
			}
			for(int i = 0; i < A; i++) {				// BC의 정보
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int p = Integer.parseInt(st.nextToken());
				BCs[i] = new BC(x, y, c, p);
			}
			Arrays.sort(BCs);				// BC들을 충전량이 큰 순서대로 정렬
			
			for(int t = 0; t <= M; t++) {
				int chkA = -1, chkB = -1;
				for(int i = 0; i < A; i++) {	// 현재 위치에서 충전량을 최대로 하도록하여 충전량만큼 answer에 더해줌
					if(getDistance(locA, BCs[i].loc)<=BCs[i].C) {
						if(chkA==-1) {chkA = i;}
						else if(chkA==chkB){ chkA = i;}
					}
					if(getDistance(locB, BCs[i].loc)<=BCs[i].C) {
						if(chkB==-1) {chkB = i;}
						else if(chkA==chkB){ chkA = i;}
					}
				}
				if(chkA!=-1) answer += BCs[chkA].P;
				if(chkA != chkB && chkB!=-1) answer += BCs[chkB].P;
				
				if(t!=M) {			// locA, locB 다음 위치로 이동
					locA[0] += dx[userA[t]]; locA[1] += dy[userA[t]];
					locB[0] += dx[userB[t]]; locB[1] += dy[userB[t]];
				}
			}
			
			System.out.println("#" + tc + " " + answer);
		}
		
		
	}
	
	// 두 좌표 사이의 거리
	static int getDistance(int[] a, int[] b) {
		return Math.abs(a[0]-b[0]) + Math.abs(a[1]-b[1]);
	}
}
