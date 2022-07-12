package gold3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class B2638_치즈 {
	static int N, M, cheese;
	static int[][] map, cnt;
	static boolean[][] air;
	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { 1, 0, -1, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		cnt = new int[N][M];

		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < M; c++) {
				int state = Integer.parseInt(st.nextToken());
				if (state == 1) {
					map[r][c] = 1;
					cheese++;
				}
			}
		}

		System.out.println(simul());

	}

	static int simul() {
		int step = 0;
		while (true) {
			outsideAir();
			chkMelt();
			step++;
			if (cheese == 0)
				break;
		}

		return step;
	}

	static void chkMelt() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] == 0)
					continue;
				int chkair = 0;
				for (int d = 0; d < 4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];
					if (air[nr][nc])
						chkair++;
				}
				if (chkair >= 2) {
					map[r][c] = 0;
					cheese--;
				}

			}
		}
	}

	static void outsideAir() {
		air = new boolean[N][M];
		Queue<Pos> q = new LinkedList<>();
		q.add(new Pos(0, 0));
		air[0][0] = true;
		while (!q.isEmpty()) {
			Pos tmp = q.poll();
			for (int d = 0; d < 4; d++) {
				int nr = tmp.r + dr[d];
				int nc = tmp.c + dc[d];
				if (outPos(nr, nc) || air[nr][nc] || map[nr][nc] == 1)
					continue;
				q.add(new Pos(nr, nc));
				air[nr][nc] = true;
			}
		}

	}

	static boolean outPos(int r, int c) {
		if (r < 0 || c < 0 || r >= N || c >= M)
			return true;
		return false;
	}

	static class Pos {
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}

	}
}
