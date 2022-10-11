package 기출문제;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 사람은 1에서 탑승하고 n에서 내린다.
// 2n번째 칸은 1로 이동한다.
// 사람이 칸에 타거나 이동하면 해당 칸의 안정성은 즉시 1만큼 감소한다.

// 1. 무빙워크 한칸 회전
// 2. 회전 방향으로 한 칸 이동할 수 있으면 한 칸 이동,앞에 사람이 있거나 안정성이 0이면 이동 안함
// 3. 1번칸에 사람이 ㅇ벗고 안정성이 0이 아니면 사람 탑승
// 4. 안정성이 0인 칸이 k면 과정 종료, 그렇기 않으면 반ㅂ복
// 단 사람이 n번 칸에 위치하면 즉시 내림
//

// 안정성을 확인할 수 있는 숫자 배열 
// -> 0인 칸이 k개 이상이면 실험 종료 (반복 조건)
// 윗칸 아랫칸을 따로 관리할까?
// 중간에 사람들 이동해야하면 Queue로는 중간 객체 접근할 수 없다.
// -> 윗 칸은 linkedList를 사용
// -> 아래는 그냥 Queue 써도 될듯?

// 흠 그냥 객체로 만들어서 관리하자

public class 불안한_무빙워크 {
	static int N, K; // N 길이, K 종료 조건
	static int[] score; // 안정성 개수 현황 판, 사람 존재 여부
	static LinkedList<Info> l;
	static Queue<Info> q;

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("res/불안한_무빙워크_input"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		init(br);

		System.out.println(simul());

	}

	static void ride() {
		Info tmp = l.get(0);

		if (!tmp.human && tmp.hp > 0) {
			tmp.human = true;
			score[tmp.hp]--;
			score[--tmp.hp]++;
		}
	}

	static void moveHuman() {
		for (int i = N - 2; i >= 0; i--) {
			Info tmp1 = l.get(i);
			Info tmp2 = l.get(i + 1);
			if (tmp1.human && !tmp2.human && tmp2.hp > 0) {
				tmp1.human = false;
				tmp2.human = true;
				score[tmp2.hp]--;
				score[--tmp2.hp]++;
			}
		}
	}

	static void arrive() {
		Info tmp = l.get(N - 1);
		if (tmp.human)
			tmp.human = false;
	}

	static void move() {
		q.add(l.pollLast());
		l.addFirst(q.poll());
	}

	static int simul() {
		int step = 0;

		while (!chkEnd()) { // 종료 조건
			step++;
//			System.out.println(step + " : ---------------------");
			// 무빙 워크 회전
			move();
//			System.out.println("move ----------------------");
//			print();
			// N번째 칸 사람 내림
			arrive();
//			System.out.println("arrive ----------------------");
//			print();
			// 사람 이동
			moveHuman();
//			System.out.println("moveHuman ----------------------");
//			print();
			if (chkEnd())
				return step;
			// N번째 칸 사람 내림
			arrive();
//			System.out.println("arrive ----------------------");
//			print();
			// 1번에 사람 탑승
			ride();
//			System.out.println("ride ----------------------");
//			print();
			if (chkEnd())
				return step;

		}
		return step;
	}

	static boolean chkEnd() {
		return score[0] >= K ? true : false;
	}

	static void init(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		q = new LinkedList<>();
		l = new LinkedList<>();
		score = new int[1001];// 안정성 최대 1000 그냥 다 만들자... 귀찮다.
		String[] line = br.readLine().split(" ");
		for (int i = 0; i < 2 * N; i++) {
			if (i < N) {
//				int num = Integer.parseInt(line[N - 1 - i]);
				int num = Integer.parseInt(line[ i]);
				score[num]++;
				l.add(new Info(num, false));
			} else {
				int num = Integer.parseInt(line[2 * N - 1 - (i - N)]);
				score[num]++;
				q.add(new Info(num, false));
			}
		}

	}

	static void print() {
		for (Info i : l) {
			System.out.print(i.hp + " ");
		}
		System.out.println();
		for (Info i : q) {
			System.out.print(i.hp + " ");
		}
		System.out.println();
	}

	static class Info {
		int hp;
		boolean human;

		public Info(int hp, boolean human) {
			super();
			this.hp = hp;
			this.human = human;
		}

	}
}
