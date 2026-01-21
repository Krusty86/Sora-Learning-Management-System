package models;

public class InstructorStats {
	private int stu_count, course_count, ungraded_count;

	public InstructorStats(int stu_count, int course_count, int ungraded_count) {
		super();
		this.stu_count = stu_count;
		this.course_count = course_count;
		this.ungraded_count = ungraded_count;
	}

	public InstructorStats() {
		super();
	}

	public int getStu_count() {
		return stu_count;
	}

	public void setStu_count(int stu_count) {
		this.stu_count = stu_count;
	}

	public int getCourse_count() {
		return course_count;
	}

	public void setCourse_count(int course_count) {
		this.course_count = course_count;
	}

	public int getUngraded_count() {
		return ungraded_count;
	}

	public void setUngraded_count(int ungraded_count) {
		this.ungraded_count = ungraded_count;
	}
}
