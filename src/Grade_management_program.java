import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

class Student {
	String name;
	String code; // 학번
	int term; // 학기
	int major_score; // 전공 점수
	int gyoyang_score; // 교양 점수
	int sisa_score; // 시사 점수

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
	String name; // 이름
	String code; // 학번
	int sum; // 합계
	double avg; // 평균
	int grade; // 석차
	String rank; // 등급

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
			System.out.println(("1 : 여러명의 학생 성적(전공, 교양, 시사)을 키보드로 입력 받아서 파일로 저장하기"));
			System.out.println(("2 : 학생들의 합계/평균/석차/등급이 표시되는 집계표를 파일로 출력하기 (이름순 집계표, 석차순 집계표)"));
			System.out.println(("3 : 학번/이름을 입력하여 해당 학생의 성적 정보를 추가/수정/삭제하기 "));
			System.out.println(("4 : 학생들의 평균 성적 데이터 그래프 보기 "));
			System.out.println(("5 : 현재 학생 리스트 보기"));
			System.out.println(("그 외 번호 선택시 종료합니다"));
			System.out.println("===========================================================================");
			System.out.print("번호를 선택하세요 : ");
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

	public static void Input() { // 이름,학번,학기,전공점수, 교양점수, 시사점수 순으로 입력받아서 파일에 저장 (1번)
		FileWriter fout;
		String name, code;
		int term, major_score, gyoyang_score, sisa_score, input_Student;
		Scanner scanner = new Scanner(System.in);
		System.out.print("몇명의 학생의 정보를 입력하시겠습니까? : ");
		input_Student = scanner.nextInt();
		System.out.println("===========================================================================");
		System.out.println("(이름) (학번) (학기) (전공점수) (교양점수) (시사점수) 순으로 입력하세요 : ");

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
			choice(); // 작업끝나면 선택화면으로!
		}

		catch (IOException e) {
			System.out.println("입출력 오류");
		}

		scanner.close();
	}

	public static void Output() { // 입력한 벡터 내용에서 성적의합, 평균, 석차, 등급을 구해서 정렬하여 파일에 저장하는 작업 (2번)
		int size = student.size();
		stu.removeAll(stu); // 2번 호출할 때마다 성적순과 이름순 백터 초기화
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

		for (int i = 0; i < size1; i++) { // 합계로 석차 구하기
			int sum_rank = 1;
			for (int j = 0; j < size1; j++) {
				if (stu.get(i).sum < stu.get(j).sum) {
					sum_rank++;
				}
			}
			stu.get(i).grade = sum_rank;
		}

		FileWriter foutt = null; // 파일에 성적순으로 리스트를 저장하는 작업
		try {
			foutt = new FileWriter("c:\\temp\\final_gradeOrder.txt");
			foutt.write("============\n");
			foutt.write("성적순 집계표\n");
			foutt.write("============\n");
			foutt.write("이름" + "\t" + "학번" + "\t\t" + "합계" + "\t" + "평균" + "\t" + "석차" + "\t" + "등급" + "\n");

			System.out.println("============");
			System.out.println("성적순 집계표");
			System.out.println("============");
			System.out.println("이름" + "\t" + "학번" + "\t\t" + "합계" + "\t" + "평균" + "\t" + "석차" + "\t" + "등급");

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
			System.out.println("입출력 오류");
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

		try { // 파일에 이름순으로 리스트를 저장하는 작업
			foutt = new FileWriter("c:\\temp\\fianl_name.txt");
			foutt.write("============\n");
			foutt.write("이름순 집계표\n");
			foutt.write("============\n");
			foutt.write("이름" + "\t" + "학번" + "\t\t" + "합계" + "\t" + "평균" + "\t" + "석차" + "\t" + "등급" + "\n");

			srank = 1;
			System.out.println("============");
			System.out.println("이름순 집계표");
			System.out.println("============");
			System.out.println("이름" + "\t" + "학번" + "\t\t" + "합계" + "\t" + "평균" + "\t" + "석차" + "\t" + "등급");
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
			System.out.println("입출력 오류");
		}

		choice();
	}

	public static void database() {
		while (true) {
			int dnum;
			Scanner scanner = new Scanner(System.in);
			System.out.println("===========================================================================");
			System.out.println(("1 : 학생 성적 정보 추가"));
			System.out.println(("2 : 학생 성적 정보 수정"));
			System.out.println(("3 : 학생 성적 정보 삭제"));
			System.out.println(("4 : 뒤로가기 (메인화면이동)"));
			System.out.println("===========================================================================");
			System.out.print("번호를 선택하세요 : ");
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
				System.out.println("번호를 다시 선택해주세요.");
				break;
			}
		}
	}

	public static void add() { // 학생 추가 작업
		String name, code;
		int term, major_score, gyoyang_score, sisa_score, input_Student;
		Scanner scanner = new Scanner(System.in);
		System.out.print("추가할 학생의 이름을 입력하세요 : ");
		name = scanner.next();
		System.out.print("추가할 학생의 학번을 입력하세요 : ");
		code = scanner.next();
		System.out.print("추가할 학생의 학기를 입력하세요 : ");
		term = scanner.nextInt();
		System.out.print("추가할 학생의 전공 교양 시사 점수를 각각 입력하세요 : ");
		major_score = scanner.nextInt();
		gyoyang_score = scanner.nextInt();
		sisa_score = scanner.nextInt();

		student.add(new Student(name, code, term, major_score, gyoyang_score, sisa_score));
		System.out.println("학생 정보가 정상적으로 추가되었습니다.");
		choice(); // 작업끝나면 선택화면으로!
	}

	public static void modify() { // 학생 수정 작업
		String name, code;
		int term, major_score, gyoyang_score, sisa_score, cnum;
		int size = student.size();
		Scanner scanner = new Scanner(System.in);
		System.out.print("수정할 학생의 이름을 입력하세요 : ");
		name = scanner.next();
		System.out.print("수정할 학생의 학번을 입력하세요 : ");
		code = scanner.next();
		System.out.print("수정할 학생의 학기를 입력하세요 : ");
		term = scanner.nextInt();

		boolean modify_flag = true;
		int error = 0; // 번호를 잘 입력했는제 확인하는 플래그
		while (modify_flag) { // 수정되었는지 확인하는 플래그
			for (int i = 0; i < size; i++) {
				if ((student.get(i).name.equals(name) // 학생,학번,학기 다 일치하는 사람을 찾음
						&& (student.get(i).code.equals(code) && (student.get(i).term == term)))) {
					System.out.print("해당 학기의 학생의 수정할 과목을 선택하세요 (1:전공, 2:교양, 3:시사) : ");
					cnum = scanner.nextInt();

					switch (cnum) {
					case 1:
						System.out.print("새로운 전공 점수를 입력하여 수정하세요 : ");
						major_score = scanner.nextInt();
						student.get(i).major_score = major_score;
						modify_flag = false;
						System.out.println("성적이 정상적으로 변경되었습니다.");
						break;
					case 2:
						System.out.print("새로운 교양 점수를 입력하여 수정하세요 : ");
						gyoyang_score = scanner.nextInt();
						student.get(i).gyoyang_score = gyoyang_score;
						modify_flag = false;
						System.out.println("성적이 정상적으로 변경되었습니다.");
						break;
					case 3:
						System.out.print("새로운 시사 점수를 입력하여 수정하세요 : ");
						sisa_score = scanner.nextInt();
						student.get(i).sisa_score = sisa_score;
						modify_flag = false;
						System.out.println("성적이 정상적으로 변경되었습니다.");
						break;
					default:
						System.out.println("잘못된 번호입니다 다시 입력해주세요");
						error = 1;
						break;
					}
				}
			}

			if (modify_flag == true && error != 1) {
				System.out.println("존재하지 않는 학생입니다");
				modify();
			}
		}
		choice();
	}

	public static void delete() {// 학생 삭제 작업
		int size = student.size();
		boolean delete_flag = false; // 삭제되었는지 판별하는 플래그
		String name, code;
		int term, major_score, gyoyang_score, sisa_score;
		Scanner scanner = new Scanner(System.in);
		System.out.print("삭제할 학생의 이름을 입력하세요 : ");
		name = scanner.next();
		System.out.print("삭제할 학생의 학번을 입력하세요 : ");
		code = scanner.next();
		System.out.print("삭제할 학생의 학기를 입력하세요 : ");
		term = scanner.nextInt();

		for (int i = 0; i < size; i++) {
			if ((student.get(i).name.equals(name) // 학생,학번,학기 다 일치하는 사람들을 찾아서 삭제
					&& (student.get(i).code.equals(code) && (student.get(i).term == term)))) {
				student.remove(i);
				delete_flag = true;
				break;
			}
		}

		if (delete_flag == true)
			System.out.println("입력하신 학생을 삭제하였습니다.");
		else {
			System.out.println("입력하신 학생은 없는 학생입니다");
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

	public static void list() { // 벡터에 저장된 리스트 출력
		int size = student.size();
		FileWriter ffout;
		try {
			ffout = new FileWriter("c:\\temp\\final_test.txt"); // 저장된 벡터 내용을 파일에 저장
			ffout.write("============\n");
			ffout.write("모든 학생 집계표\n");
			ffout.write("============\n");
			ffout.write("이름" + "\t" + "학번" + "\t\t" + "학기" + "\t" + "전공" + "\t" + "교양" + "\t" + "시사" + "\n");
			for (int i = 0; i < size; i++) {
				ffout.write(student.get(i).name + "\t" + student.get(i).code + "\t" + student.get(i).term + "\t");
				ffout.write(student.get(i).major_score + "\t" + student.get(i).gyoyang_score + "\t"
						+ student.get(i).sisa_score + "\n");
			}
			ffout.close();
		} catch (IOException e) {
			System.out.println("입출력 오류");
		}

		String[][] array = new String[100][6];
		int cnt = 0;
		try {
			File file = new File("c:\\temp\\final_test.txt");
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while ((line = bufReader.readLine()) != null) { // 파일을 줄마다 읽어서 배열에 넣음
				array[cnt] = line.split("\t");
				cnt++;
			}
			System.out.println("============");
			System.out.println("모든 학생 집계표");
			System.out.println("============");
			System.out.println("이름" + "\t" + "학번" + "\t\t" + "학기" + "\t" + "전공" + "\t" + "교양" + "\t" + "시사");
			for (int i = 4; i < cnt; i++) { // 파일안의 내용을 줄마다 배열에 넣어서 출력
				System.out.print(array[i][0] + "\t" + array[i][1] + "\t" + array[i][2] + "\t" + array[i][3] + "\t");
				System.out.print(array[i][4] + "\t" + array[i][5] + "\n");
			}
		} catch (IOException e) {
			System.out.println("입출력 오류");
		}

		choice();
	}

	public static String grade_rank(int sum) // 합계로 등급 출력하는 함수
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
		this.setTitle("학생의 평균 그래프");
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




