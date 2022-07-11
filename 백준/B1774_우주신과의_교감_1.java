import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
// 문제를 잘 못 이해함
public class B1774_우주신과의_교감_1 {
	static int N, M;
	static double ans;
	static Node[] nodes;
	static int[] heads;
	static PriorityQueue<Edge> pq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		init();

		// 노드 기록
		for (int n = 0; n <= N; n++) {
			st = new StringTokenizer(br.readLine());
			nodes[n] = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}

		// 간선 생성
		for (int a = 0; a <= N; a++) {
			for (int b = a + 1; b <= N; b++) {
				if (heads[a] != heads[b])
					pq.add(new Edge(a, b, calcDis(nodes[a], nodes[b])));

			}
		}

		int cnt = M;
		while (!pq.isEmpty()) {
			if (cnt == N)
				break;

			Edge tmp = pq.poll();
			if (union(tmp.a, tmp.b)) {
				System.out.println(tmp.a + ", " + tmp.b + " 연결");
				cnt++;
				ans += tmp.dis;
			}

		}

		System.out.println(String.format("%.2f", ans));

	}

	static boolean union(int a, int b) {
		int aR = find(a);
		int bR = find(b);
		if (aR == bR)
			return false;
		heads[bR] = aR;
		return true;
	}

	static int find(int a) {
		if (a == heads[a])
			return a;
		return heads[a] = find(heads[a]);
	}

	static Double calcDis(Node a, Node b) {
		return Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y)));
	}

	static void init() {
		nodes = new Node[N + 1];
		heads = new int[N + 1];
		pq = new PriorityQueue<Edge>();
		for (int n = M + 1; n <= N; n++) {
			heads[n] = n;
		}

	}

	static class Node {
		int x, y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	static class Edge implements Comparable<Edge> {
		int a, b;
		double dis;

		public Edge(int a, int b, double dis) {
			this.a = a;
			this.b = b;
			this.dis = dis;
		}

		@Override
		public int compareTo(Edge o) {
			return Double.compare(this.dis, o.dis);
		}

	}
}
