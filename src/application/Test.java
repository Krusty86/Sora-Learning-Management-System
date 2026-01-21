package application;

import java.time.LocalDate;
import java.util.ArrayList;

import models.GradeDAO;
import models.Slot;
import models.SlotDAO;

public class Test {
	public static void main(String[] args) {
		LocalDate currentDate = LocalDate.now();
		System.out.println(currentDate);
		if(currentDate.)
		ArrayList<String> s = new ArrayList<>();
		s.add(null);
		s.add(null);
		s.add("String");
		System.out.println(s.size());
		ArrayList<Slot> slst = SlotDAO.getAllSlots();
		for(Slot c: slst) {
			System.out.println(c.getCourse_id());
		}
		System.out.println(GradeDAO.calculateGPA(12));
	}
}
