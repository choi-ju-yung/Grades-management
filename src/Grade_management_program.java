import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

class Student {
	String name;
	String code; // �й�
	int term; // �б�
	int major_score; // ���� ����
	int gyoyang_score; // ���� ����
	int sisa_score; // �û� ����

	public Student(String a, String b, int c, int d, int e, int f) {
		name = a;
		code = b;
		term = c;
		major_score = d;
		gyoyang_score = e;
		sisa_score = f;
	}
}

class Stu {
	String name; // �̸�
	String code; // �й�
	int sum; // �հ�
	double avg; // ���
	int grade; // ����
	String rank; // ���

	public Stu(String n, String c, int s, double a) {
		name = n;
		sum = s;
		avg = a;
		code = c;
	};
}

public class Grade_management_program {
	public static Vector<Student> student = new Vector<Student>(100);
	public static Vector<Stu> stu = new Vector<Stu>(100);

	public static void choice() {
		while (true) {
			int num;
			Scanner scanner = new Scanner(System.in);
			System.out.println("===========================================================================");
			System.out.println(("1 : �������� �л� ����(����, ����, �û�)�� Ű����� �Է� �޾Ƽ� ���Ϸ� �����ϱ�"));
			System.out.println(("2 : �л����� �հ�/���/����/����� ǥ�õǴ� ����ǥ�� ���Ϸ� ����ϱ� (�̸��� ����ǥ, ������ ����ǥ)"));
			System.out.println(("3 : �й�/�̸��� �Է��Ͽ� �ش� �л��� ���� ������ �߰�/����/�����ϱ� "));
			System.out.println(("4 : �л����� ��� ���� ������ �׷��� ���� "));
			System.out.println(("5 : ���� �л� ����Ʈ ����"));
			System.out.println(("�� �� ��ȣ ���ý� �����մϴ�"));
			System.out.println("===========================================================================");
			System.out.print("��ȣ�� �����ϼ��� : ");
			num = scanner.nextInt();

			switch (num) {
			case 1:
				Input();
			case 2:
				Output();
			case 3:
				database();
			case 4:
				graph();
				break;
			case 5:
				list();
			default:
				System.exit(0);
			}
		}
	}

	public static void Input() { // �̸�,�й�,�б�,��������, ��������, �û����� ������ �Է¹޾Ƽ� ���Ͽ� ���� (1��)
		FileWriter fout;
		String name, code;
		int term, major_score, gyoyang_score, sisa_score, input_Student;
		Scanner scanner = new Scanner(System.in);
		System.out.print("����� �л��� ������ �Է��Ͻðڽ��ϱ�? : ");
		input_Student = scanner.nextInt();
		System.out.println("===========================================================================");
		System.out.println("(�̸�) (�й�) (�б�) (��������) (��������) (�û�����) ������ �Է��ϼ��� : ");

		try {
			fout = new FileWriter("c:\\temp\\final_test.txt", true);

			for (int i = 0; i < input_Student; i++) {
				name = scanner.next();
				code = scanner.next();
				term = scanner.nextInt();
				major_score = scanner.nextInt();
				gyoyang_score = scanner.nextInt();
				sisa_score = scanner.nextInt();
				student.add(new Student(name, code, term, major_score, gyoyang_score, sisa_score));
			}

			int size = student.size();
			for (int i = 0; i < size; i++) {
				fout.write(student.get(i).name + " " + student.get(i).code + " " + student.get(i).term + " ");
				fout.write(student.get(i).major_score + " " + student.get(i).gyoyang_score + " "
						+ student.get(i).sisa_score + "\n");
			}

			fout.close();
			choice(); // �۾������� ����ȭ������!
		}

		catch (IOException e) {
			System.out.println("����� ����");
		}

		scanner.close();
	}

	public static void Output() { // �Է��� ���� ���뿡�� ��������, ���, ����, ����� ���ؼ� �����Ͽ� ���Ͽ� �����ϴ� �۾� (2��)
		int size = student.size();
		stu.removeAll(stu); // 2�� ȣ���� ������ �������� �̸��� ���� �ʱ�ȭ
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (student.get(i).name.equals(student.get(j).name) && student.get(i).code.equals(student.get(j).code)
						&& student.get(i).term != student.get(j).term) {
					String name = student.get(i).name;
					String code = student.get(i).code;
					int sum = student.get(i).major_score + student.get(i).gyoyang_score + student.get(i).sisa_score
							+ student.get(j).major_score + student.get(j).gyoyang_score + student.get(j).sisa_score;
					double avg = sum / 6;

					stu.add(new Stu(name, code, sum, avg));
				}
			}
		}

		String realrank;
		int size1 = stu.size();
		for (int i = 0; i < size1; i++) {
			realrank = grade_rank(stu.get(i).sum);
			stu.get(i).rank = realrank;
		}

		for (int i = 0; i < size1; i++) { // �հ�� ���� ���ϱ�
			int sum_rank = 1;
			for (int j = 0; j < size1; j++) {
				if (stu.get(i).sum < stu.get(j).sum) {
					sum_rank++;
				}
			}
			stu.get(i).grade = sum_rank;
		}

		FileWriter foutt = null; // ���Ͽ� ���������� ����Ʈ�� �����ϴ� �۾�
		try {
			foutt = new FileWriter("c:\\temp\\final_gradeOrder.txt");
			foutt.write("============\n");
			foutt.write("������ ����ǥ\n");
			foutt.write("============\n");
			foutt.write("�̸�" + "\t" + "�й�" + "\t\t" + "�հ�" + "\t" + "���" + "\t" + "����" + "\t" + "���" + "\n");

			System.out.println("============");
			System.out.println("������ ����ǥ");
			System.out.println("============");
			System.out.println("�̸�" + "\t" + "�й�" + "\t\t" + "�հ�" + "\t" + "���" + "\t" + "����" + "\t" + "���");

			int sum_rank = 1;
			int i = 0;
			while (true) {
				if (sum_rank == size1 + 1) {
					break;
				}
				if (stu.get(i).grade == sum_rank) {
					foutt.write(stu.get(i).name + "\t" + stu.get(i).code + "\t" + stu.get(i).sum + "\t" + stu.get(i).avg
							+ "\t" + stu.get(i).grade + "\t" + stu.get(i).rank + "\n");

					System.out.println(stu.get(i).name + "\t" + stu.get(i).code + "\t" + stu.get(i).sum + "\t"
							+ stu.get(i).avg + "\t" + stu.get(i).grade + "\t" + stu.get(i).rank);
					i = 0;
					sum_rank++;
				} else {
					i++;
				}
			}

			foutt.close();
		}

		catch (IOException e) {
			System.out.println("����� ����");
		}

		int[] sreal = new int[100];

		int srank = 1;
		int j;
		for (int i = 0; i < size1; i++) {
			for (j = 0; j < size1; j++) {
				if (stu.get(i).name.compareTo(stu.get(j).name) > 0) {
					srank++;
				}
			}
			sreal[i] = srank;
			srank = 1;
		}

		try { // ���Ͽ� �̸������� ����Ʈ�� �����ϴ� �۾�
			foutt = new FileWriter("c:\\temp\\fianl_name.txt");
			foutt.write("============\n");
			foutt.write("�̸��� ����ǥ\n");
			foutt.write("============\n");
			foutt.write("�̸�" + "\t" + "�й�" + "\t\t" + "�հ�" + "\t" + "���" + "\t" + "����" + "\t" + "���" + "\n");

			srank = 1;
			System.out.println("============");
			System.out.println("�̸��� ����ǥ");
			System.out.println("============");
			System.out.println("�̸�" + "\t" + "�й�" + "\t\t" + "�հ�" + "\t" + "���" + "\t" + "����" + "\t" + "���");
			while (true) {
				if (srank == size1 + 1)
					break;

				for (int i = 0; i < size1; i++) {
					if (sreal[i] == srank) {
						foutt.write(stu.get(i).name + "\t" + stu.get(i).code + "\t" + stu.get(i).sum + "\t"
								+ stu.get(i).avg + "\t" + stu.get(i).grade + "\t" + stu.get(i).rank + "\n");
						System.out.println(stu.get(i).name + "\t" + stu.get(i).code + "\t" + stu.get(i).sum + "\t"
								+ stu.get(i).avg + "\t" + stu.get(i).grade + "\t" + stu.get(i).rank);
						i = 0;
						srank++;
					}
				}
			}
			foutt.close();
		}

		catch (IOException e) {
			System.out.println("����� ����");
		}

		choice();
	}

	public static void database() {
		while (true) {
			int dnum;
			Scanner scanner = new Scanner(System.in);
			System.out.println("===========================================================================");
			System.out.println(("1 : �л� ���� ���� �߰�"));
			System.out.println(("2 : �л� ���� ���� ����"));
			System.out.println(("3 : �л� ���� ���� ����"));
			System.out.println(("4 : �ڷΰ��� (����ȭ���̵�)"));
			System.out.println("===========================================================================");
			System.out.print("��ȣ�� �����ϼ��� : ");
			dnum = scanner.nextInt();

			switch (dnum) {
			case 1:
				add();
			case 2:
				modify();
			case 3:
				delete();
			case 4:
				choice();
			default:
				System.out.println("��ȣ�� �ٽ� �������ּ���.");
				break;
			}
		}
	}

	public static void add() { // �л� �߰� �۾�
		String name, code;
		int term, major_score, gyoyang_score, sisa_score, input_Student;
		Scanner scanner = new Scanner(System.in);
		System.out.print("�߰��� �л��� �̸��� �Է��ϼ��� : ");
		name = scanner.next();
		System.out.print("�߰��� �л��� �й��� �Է��ϼ��� : ");
		code = scanner.next();
		System.out.print("�߰��� �л��� �б⸦ �Է��ϼ��� : ");
		term = scanner.nextInt();
		System.out.print("�߰��� �л��� ���� ���� �û� ������ ���� �Է��ϼ��� : ");
		major_score = scanner.nextInt();
		gyoyang_score = scanner.nextInt();
		sisa_score = scanner.nextInt();

		student.add(new Student(name, code, term, major_score, gyoyang_score, sisa_score));
		System.out.println("�л� ������ ���������� �߰��Ǿ����ϴ�.");
		choice(); // �۾������� ����ȭ������!
	}

	public static void modify() { // �л� ���� �۾�
		String name, code;
		int term, major_score, gyoyang_score, sisa_score, cnum;
		int size = student.size();
		Scanner scanner = new Scanner(System.in);
		System.out.print("������ �л��� �̸��� �Է��ϼ��� : ");
		name = scanner.next();
		System.out.print("������ �л��� �й��� �Է��ϼ��� : ");
		code = scanner.next();
		System.out.print("������ �л��� �б⸦ �Է��ϼ��� : ");
		term = scanner.nextInt();

		boolean modify_flag = true;
		int error = 0; // ��ȣ�� �� �Է��ߴ��� Ȯ���ϴ� �÷���
		while (modify_flag) { // �����Ǿ����� Ȯ���ϴ� �÷���
			for (int i = 0; i < size; i++) {
				if ((student.get(i).name.equals(name) // �л�,�й�,�б� �� ��ġ�ϴ� ����� ã��
						&& (student.get(i).code.equals(code) && (student.get(i).term == term)))) {
					System.out.print("�ش� �б��� �л��� ������ ������ �����ϼ��� (1:����, 2:����, 3:�û�) : ");
					cnum = scanner.nextInt();

					switch (cnum) {
					case 1:
						System.out.print("���ο� ���� ������ �Է��Ͽ� �����ϼ��� : ");
						major_score = scanner.nextInt();
						student.get(i).major_score = major_score;
						modify_flag = false;
						System.out.println("������ ���������� ����Ǿ����ϴ�.");
						break;
					case 2:
						System.out.print("���ο� ���� ������ �Է��Ͽ� �����ϼ��� : ");
						gyoyang_score = scanner.nextInt();
						student.get(i).gyoyang_score = gyoyang_score;
						modify_flag = false;
						System.out.println("������ ���������� ����Ǿ����ϴ�.");
						break;
					case 3:
						System.out.print("���ο� �û� ������ �Է��Ͽ� �����ϼ��� : ");
						sisa_score = scanner.nextInt();
						student.get(i).sisa_score = sisa_score;
						modify_flag = false;
						System.out.println("������ ���������� ����Ǿ����ϴ�.");
						break;
					default:
						System.out.println("�߸��� ��ȣ�Դϴ� �ٽ� �Է����ּ���");
						error = 1;
						break;
					}
				}
			}

			if (modify_flag == true && error != 1) {
				System.out.println("�������� �ʴ� �л��Դϴ�");
				modify();
			}
		}
		choice();
	}

	public static void delete() {// �л� ���� �۾�
		int size = student.size();
		boolean delete_flag = false; // �����Ǿ����� �Ǻ��ϴ� �÷���
		String name, code;
		int term, major_score, gyoyang_score, sisa_score;
		Scanner scanner = new Scanner(System.in);
		System.out.print("������ �л��� �̸��� �Է��ϼ��� : ");
		name = scanner.next();
		System.out.print("������ �л��� �й��� �Է��ϼ��� : ");
		code = scanner.next();
		System.out.print("������ �л��� �б⸦ �Է��ϼ��� : ");
		term = scanner.nextInt();

		for (int i = 0; i < size; i++) {
			if ((student.get(i).name.equals(name) // �л�,�й�,�б� �� ��ġ�ϴ� ������� ã�Ƽ� ����
					&& (student.get(i).code.equals(code) && (student.get(i).term == term)))) {
				student.remove(i);
				delete_flag = true;
				break;
			}
		}

		if (delete_flag == true)
			System.out.println("�Է��Ͻ� �л��� �����Ͽ����ϴ�.");
		else {
			System.out.println("�Է��Ͻ� �л��� ���� �л��Դϴ�");
			delete();
		}

	}

	public static void graph() {
		int size1 = stu.size();
		int[] totalScore = new int[100];
		String[] name = new String[100];
		for (int i = 0; i < size1; i++) {
			totalScore[i] = stu.get(i).sum;
			name[i] = stu.get(i).name;
		}

		new paintJPanelEx(name, totalScore, size1);
	}

	public static void list() { // ���Ϳ� ����� ����Ʈ ���
		int size = student.size();
		FileWriter ffout;
		try {
			ffout = new FileWriter("c:\\temp\\final_test.txt"); // ����� ���� ������ ���Ͽ� ����
			ffout.write("============\n");
			ffout.write("��� �л� ����ǥ\n");
			ffout.write("============\n");
			ffout.write("�̸�" + "\t" + "�й�" + "\t\t" + "�б�" + "\t" + "����" + "\t" + "����" + "\t" + "�û�" + "\n");
			for (int i = 0; i < size; i++) {
				ffout.write(student.get(i).name + "\t" + student.get(i).code + "\t" + student.get(i).term + "\t");
				ffout.write(student.get(i).major_score + "\t" + student.get(i).gyoyang_score + "\t"
						+ student.get(i).sisa_score + "\n");
			}
			ffout.close();
		} catch (IOException e) {
			System.out.println("����� ����");
		}

		String[][] array = new String[100][6];
		int cnt = 0;
		try {
			File file = new File("c:\\temp\\final_test.txt");
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while ((line = bufReader.readLine()) != null) { // ������ �ٸ��� �о �迭�� ����
				array[cnt] = line.split("\t");
				cnt++;
			}
			System.out.println("============");
			System.out.println("��� �л� ����ǥ");
			System.out.println("============");
			System.out.println("�̸�" + "\t" + "�й�" + "\t\t" + "�б�" + "\t" + "����" + "\t" + "����" + "\t" + "�û�");
			for (int i = 4; i < cnt; i++) { // ���Ͼ��� ������ �ٸ��� �迭�� �־ ���
				System.out.print(array[i][0] + "\t" + array[i][1] + "\t" + array[i][2] + "\t" + array[i][3] + "\t");
				System.out.print(array[i][4] + "\t" + array[i][5] + "\n");
			}
		} catch (IOException e) {
			System.out.println("����� ����");
		}

		choice();
	}

	public static String grade_rank(int sum) // �հ�� ��� ����ϴ� �Լ�
	{
		switch (sum / 60) {
		case 10:
		case 9:
			return "A";
		case 8:
			return "B";
		case 7:
			return "C";
		case 6:
			return "D";
		default:
			return "F";
		}
	}

	public static void main(String[] args) {
		choice();
	}
}

class paintJPanelEx extends JFrame {

	int[] totalScore = new int[100];
	String[] name = new String[100];
	int realsize;

	paintJPanelEx(String[] _name, int[] _total_score, int size1) {
		this.realsize = size1;
		this.name = _name;
		this.totalScore = _total_score;
		this.setTitle("�л��� ��� �׷���");
		MyPanel panel = new MyPanel(realsize);
		this.add(panel, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.setSize(600, 350);
		this.setVisible(true);
	}

	class MyPanel extends JPanel {
		int size1;

		public MyPanel(int realsize) {
			this.size1 = realsize;
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			g.drawLine(50, 250, 550, 250);
			for (int cnt = 1; cnt <= 10; cnt++) {
				g.drawString(cnt * 10 + "", 25, 255 - 20 * cnt);
				g.drawLine(50, 250 - 20 * cnt, 550, 250 - 20 * cnt);
			}
			g.drawLine(50, 20, 50, 250);

			int aa = 70;
			for (int i = 0; i < size1; i++) {
				g.setColor(Color.BLACK);
				g.drawString(name[i], aa, 270);
				g.setColor(Color.YELLOW);
				g.fillRect(aa, 250 - (totalScore[i] / 6 * 2), 10, totalScore[i] / 6 * 2);
				aa += 50;
			}
		}
	}
}




