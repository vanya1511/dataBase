package com.company;

import java.util.*;


public class Main extends MyMethods {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("PRESS:\n1. View BD\n" + "2. Output BD\n" + "3. Get average mark\n" + "5. Get sort output file\n" + "6. Input new record \n" + "7. Delete record \n" + "0. Exit\n");
        String s="";
        while (!s.equals("0")) {
            System.out.print("Waiting for your input: ");
            s = in.nextLine();
            switch (s) {
                case "1" -> first();

                case "2" -> second();

                case "3" -> {
                    System.out.println(readFile("subject.txt"));
                    System.out.print("Choose subject: ");
                    third(in.nextLine());
                }

                case "5" -> fifth();

                case "6" -> {
                    System.out.print("Choose file (student/subject/marks): ");
                    sixth(in.nextLine().toLowerCase());
                }

                case "7" -> {
                    System.out.print("Choose parameter (student/subject/mark): ");
                    seventh(in.nextLine().toLowerCase());
                }

                case "0" -> System.out.print("THE END");

                default -> System.out.println("INPUT ERROR");
            }
        }
    }

    public static void first() {
        ArrayList<Student> students = new ArrayList<>(getArrayOfStudents());
        setMarksOfUsers(students);
        String ans;
        ans = "Students:" + "\n" + readFile("student.txt") + "\n\n";
        ans += "Subjects:" + "\n" + readFile("subject.txt") + "\n\n";
        ans += "Marks:" + "\n" + readFile("marks.txt");
        System.out.println(ans);
    }

    public static void second() {
        ArrayList<Student> students = new ArrayList<>(getArrayOfStudents());
        setMarksOfUsers(students);
        outputFile(students);
    }

    public static void third(String request) {
        ArrayList<Student> students = new ArrayList<>(getArrayOfStudents());
        setMarksOfUsers(students);
        int id = getSubjectIdFromName(request.toLowerCase(),getSubjectCount()), summ = 0, count = 0;
        if (id != 0) {
            for (Student s : students) {
                if (s.getMark(id - 1) > 0 && s.getMark(id - 1) <= 5) {
                    summ += s.getMark(id - 1);
                    count++;
                }
            }
            if (count != 0) {
                System.out.print("Average mark is ");
                System.out.printf("%.3f", (double) summ / count);
            } else System.out.print("No marks");
            System.out.println();
        }
        else System.out.println("No such subject");
    }

    public static void fifth() {
        ArrayList<Student> students = new ArrayList<>(getArrayOfStudents());
        setMarksOfUsers(students);
        ArrayList<Student> sortStudents = new ArrayList<>(students);
        sortStudents.sort(Comparator.comparing(Student::getNameForOtput));
        outputFile(sortStudents);
    }

    public static void sixth(String requestName) {
        Scanner in = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>(getArrayOfStudents());
        setMarksOfUsers(students);
        switch (requestName) {
            case "marks" -> {
                System.out.println(readFile("student.txt"));
                System.out.print("StudentId: ");
                try {
                    int studentId = in.nextInt();
                    if (studentId > getStudentsCount() || studentId <= 0)
                        System.out.println("No such student in database");
                    else {
                        System.out.println(readFile("subject.txt"));
                        System.out.print("SubjectId: ");
                        try {
                            int subjectId = in.nextInt();
                            if (subjectId > getSubjectCount() || subjectId <= 0)
                                System.out.println("No such subject in database");
                            else {
                                System.out.print("mark: ");
                                try {
                                    int mark = in.nextInt();
                                    if (mark <= 5 && mark > 0) {
                                        String s = "\n" + studentId + " " + subjectId + " " + mark;
                                        writeFile("marks.txt", s, true);
                                    } else System.out.println("The mark should be in the range from 1 to 5");
                                } catch (Exception e) {
                                    System.out.println("Mark must be integer");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("ID must be integer");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ID must be integer");
                }
            }
            case "student" -> {
                System.out.print("Surname: ");
                String surname = in.nextLine();
                surname = checkAndEditString(surname);
                if (!surname.equals("ERROR")) {
                    System.out.print("Name: ");
                    String name = in.nextLine();
                    name = checkAndEditString(name);
                    if (!name.equals("ERROR")) {
                        System.out.print("Third name: ");
                        String thirdName = in.nextLine();
                        thirdName = checkAndEditString(thirdName);
                        if (!thirdName.equals("ERROR")) {
                            System.out.print("Class (integer): ");
                            String schClass = in.nextLine();
                            try {
                                int classInt = Integer.parseInt(schClass);
                                if (classInt>0 && classInt<=11) {
                                    System.out.print("Class parallel: ");
                                    String parallel = in.nextLine();
                                    parallel = checkAndEditString(parallel);
                                    if (parallel.equals("A") || parallel.equals("B") || parallel.equals("C") || parallel.equals("D") || parallel.equals("E") || parallel.equals("F")) {
                                        if (parallel.length() == 1) {
                                            String s = surname + " " + name + " " + thirdName + " " + schClass + parallel;
                                            writeFile("student.txt", "\n" + (getStudentsCount() + 1) + " " + s, true);
                                        } else System.out.println("The parallel must have 1 character");
                                    }
                                    else System.out.println("Parallel must be in the range from A to F");
                                }
                                else System.out.println("Class must be in the range from 1 to 11");
                            } catch (Exception e) {
                                System.out.println("The class must be integer");
                            }
                        }
                        else System.out.println("Third name must contain only latin letters");
                    }
                    else System.out.println("Name must contain only latin letters");
                }
                else System.out.println("Surname must contain only latin letters");
            }
            case "subject" -> {
                System.out.print("Subject name: ");
                String subject = in.nextLine();
                subject = checkAndEditString(subject);
                if (subject.length()>=3 && subject!="ERROR") {
                    if (checkSubject(subject,getSubjectCount())) writeFile("subject.txt", "\n" + (getSubjectCount() + 1) + " " + subject, true);
                    else System.out.println("Subject already in database");
                }
                else System.out.println("Subject must contain only latin letters and consist of at least 3 characters");
            }
            default -> System.out.println("INPUT ERROR");
        }
    }

    private static void seventh(String request) {
        Scanner in = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        students.addAll(getArrayOfStudents());
        setMarksOfUsers(students);
        switch (request) {
            case "mark" -> {
                try {
                    System.out.println(readFile("student.txt"));
                    System.out.print("StudentId: ");
                    int studentId = in.nextInt();
                    if (studentId<=getStudentsCount() && studentId>0) {
                        try {
                            System.out.println(readFile("subject.txt"));
                            System.out.print("SubjectId: ");
                            int subjectId = in.nextInt();
                            if (subjectId<=getSubjectCount() && subjectId>0) {
                                String s = readFile("marks.txt");
                                String delete = "\n" + studentId + " " + subjectId;
                                try {
                                    s = s.substring(0, s.lastIndexOf(delete)-1) + s.substring(s.lastIndexOf(delete) + 5);
                                } catch (Exception e) {
                                    //if deleting first row
                                    s = s.substring(0, s.lastIndexOf(delete) + 1) + s.substring(s.lastIndexOf(delete) + 8);
                                }
                                writeFile("marks.txt", s, false);
                            }
                            else System.out.println("No such subject in database");
                        } catch (Exception e) {
                            System.out.println("ID must be integer");
                        }
                    }
                    else System.out.println("No such student in database");
                } catch (Exception e) {
                    System.out.println("ID must be integer");
                }
            }
            case "student" -> {
                System.out.println(readFile("student.txt"));
                System.out.print("Surname: ");
                String surname = in.nextLine();
                System.out.print("Name: ");
                String name = in.nextLine();
                System.out.print("Third name: ");
                String thirdname = in.nextLine();
                name = checkAndEditString(name);
                surname = checkAndEditString(surname);
                thirdname = checkAndEditString(thirdname);
                if (!(name.equals("ERROR") || surname.equals("ERROR") || thirdname.equals("ERROR"))) {
                    String studentsBase = readFile("student.txt");
                    Student deletedStudent = findIdByName(name, surname, thirdname, students);
                    if (deletedStudent != null) {
                        String deleteString;
                        if (deletedStudent.getId() != 1)
                            deleteString = "\n" + deletedStudent.getId() + " " + deletedStudent.getSurname() + " " + deletedStudent.getName() + " " + deletedStudent.getThirdName() + " " + deletedStudent.getSchClass() + deletedStudent.getParallel();
                        else
                            deleteString = deletedStudent.getId() + " " + deletedStudent.getSurname() + " " + deletedStudent.getName() + " " + deletedStudent.getThirdName() + " " + deletedStudent.getSchClass() + deletedStudent.getParallel() + "\n";
                        studentsBase = studentsBase.replaceAll(deleteString, "");
                        studentsBase = updateId(studentsBase, deletedStudent.getId() + 1);
                        String marksBase = readFile("marks.txt");
                        marksBase = deleteAndUpdateMarks(marksBase, marksBase.length(), 0, deletedStudent.getId());
                        writeFile("student.txt", studentsBase, false);
                        writeFile("marks.txt", marksBase, false);
                    } else System.out.println("No such student in database");
                } else System.out.println("Name/surname/third name must contain only latin letters");
            }
            case "subject" -> {
                System.out.println(readFile("subject.txt"));
                System.out.print("Subject name: ");
                String subject = in.nextLine();
                subject = checkAndEditString(subject);
                if (subject!="ERROR") {
                    String s1 = readFile("subject.txt");
                    int id = getSubjectIdFromName(subject.toLowerCase(), getSubjectCount());
                    String replace;
                    if (id!=0) {
                        if (id > 1) replace = "\n" + id + " " + subject;
                        else {
                            if (getSubjectCount() != 1) replace = id + " " + subject + "\n";
                            else replace = s1;
                        }
                        if (id == 1 && getSubjectCount() == 1) replace = id + " " + subject;
                        s1 = s1.replace(replace, "");
                        s1 = updateId(s1, id + 1);
                        writeFile("subject.txt", s1, false);
                        String marks = readFile("marks.txt");
                        marks = deleteAndUpdateMarks(marks, marks.length(), 2, id);
                        writeFile("marks.txt", marks, false);
                    } else System.out.println("No such subject in database");
                }
                else System.out.println("Subject must contain only latin letters");
            }
        }
    }
}

