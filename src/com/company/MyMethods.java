package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MyMethods {

    public static String readFile(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            int c;
            StringBuilder s = new StringBuilder();
            while ((c = reader.read()) != -1) {
                s.append((char) c);
            }
            String string = s.toString();
            reader.close();
            return string;
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
            return null;
        }

    }

    public static void writeFile(String path, String s, boolean append) {
        try (FileWriter writer = new FileWriter(path, append)) {
            writer.write(s);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void outputFile(ArrayList<Student> students) {
        try (FileWriter writer = new FileWriter("output.txt", false)) {
            writer.write(String.format("%20s", " "));
            writer.write(getFormatShortSubjects() + "\n");
            int subjectCount = getSubjectCount();
            for (Student s : students) {
                writer.write(String.format("%-20s", s.getNameForOtput()));
                for (int i = 0; i < subjectCount; i++) {
                    writer.write(String.format("%5s", s.getMark(i)));
                }
                writer.write("\n");
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<Student> getArrayOfStudents() {
        String names;
        ArrayList<Student> students = new ArrayList();
        names = readFile("student.txt");
        int max = getStudentsCount();
        for (int i = 1; i < max; i++) {
            String s = names.substring(names.indexOf(' ') + 1, names.indexOf(i+1 + " ")-1);
            String surname = s.substring(0,s.indexOf(" "));
            s = s.substring(s.indexOf(" ") + 1);
            String name = s.substring(0,s.indexOf(" "));
            s = s.substring(s.indexOf(" ") + 1);
            String thirdname = s.substring(0,s.indexOf(" "));
            s = s.substring(s.indexOf(" ") + 1);
            String parallel = s.substring(s.length()-1);
            s = s.substring(0,s.length()-1);
            int schClass = Integer.parseInt(s);
            Student student = new Student(Integer.parseInt(names.substring(0, names.indexOf(' '))),surname,name,thirdname,schClass,parallel,getSubjectCount());
            names = names.substring(names.indexOf(i+1 + " "));
            students.add(student);
        }
        String s = names.substring(names.indexOf(' ') + 1);
        String surname = s.substring(0,s.indexOf(" "));
        s = s.substring(s.indexOf(" ") + 1);
        String name = s.substring(0,s.indexOf(" "));
        s = s.substring(s.indexOf(" ") + 1);
        String thirdname = s.substring(0,s.indexOf(" "));
        s = s.substring(s.indexOf(" ") + 1);
        s.replace(" ","");
        String parallel = s.substring(s.length()-1);
        s = s.substring(0,s.length()-1);
        int schClass = Integer.parseInt(s);
        Student student = new Student(Integer.parseInt(names.substring(0, names.indexOf(' '))),surname,name,thirdname,schClass,parallel,getSubjectCount());
        students.add(student);
        return students;
    }

    public static void setMarksOfUsers(ArrayList<Student> students) {
        String marks;
        marks = readFile("marks.txt");
        while (marks.contains(" ")) {
            for (Student s : students) {
                if (marks.indexOf(' ')!=-1 && Integer.toString(s.getId()).equals(marks.substring(0, marks.indexOf(' ')))) {
                    marks = marks.substring(marks.indexOf(' ')+1);
                    String subjectId = marks.substring(0,marks.indexOf(' '));
                    marks = marks.substring(marks.indexOf(' ')+1);
                    int mark;
                    try {
                        mark = Integer.parseInt(marks.substring(0, 1));
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                        mark = 0;
                    }
                    marks = marks.substring(marks.indexOf("\n")+1);
                    s.setMarks(mark,Integer.parseInt(subjectId));
                }
            }
        }
    }

    public static String getFormatShortSubjects() {
        String subjects;
        subjects = readFile("subject.txt");
        StringBuilder s = new StringBuilder();
        while (subjects.contains(" ")) {
            if (subjects.contains("\n")) {
                s.append(String.format("%5s", subjects.substring(subjects.indexOf(' ') + 1, subjects.indexOf(' ') + 4)));
                subjects = subjects.substring(subjects.indexOf("\n")+1);
            }
            else {
                s.append(String.format("%5s",subjects.substring(subjects.indexOf(' ') + 1, subjects.indexOf(' ') + 4)));
                subjects = "";
            }
        }
        return s.toString();
    }

    //return 0: no subject
    public static int getSubjectIdFromName(String request, int subjectCount) {
        String subjects;
        int id=1;
        subjects = readFile("subject.txt");
        String s;
        while (subjects.contains(" ")) {
            if (subjects.contains("\n")) s = subjects.substring(subjects.indexOf(' ') + 1, subjects.indexOf("\n")).toLowerCase();
            else s = subjects.substring(subjects.indexOf(' ')+1).toLowerCase();
            if (request.equals(s)) {
                return id;
            }
            subjects = subjects.substring(subjects.indexOf("\n")+1);
            if (id==subjectCount) return 0;
            id++;

        }
        return 0;
    }

    public static int getSubjectCount() {
        String subjects;
        subjects=readFile("subject.txt");
        int i=1;
        while (subjects.contains(Integer.toString(i))) {
            i++;
        }
        return i-1;
    }

    public static int getStudentsCount() {
        String subjects;
        subjects=readFile("student.txt");
        int i=1;
        while (subjects.contains(i + " ")) {
            i++;
        }
        return i-1;
    }

    public static Student findIdByName(String name, String surname, String thirdName,ArrayList<Student> students) {
        for (Student s : students) {
            if (name.equals(s.getName()) && surname.equals(s.surname) && thirdName.equals(s.getThirdName())) {
                return s;
            }
        }
        return null;
    }

    public static String updateId(String s,int id) {
        while (s.contains(id + " ")) {
            s = s.replace(id + " ", (id-1) + " ");
            id++;
        }
        return s;
    }

    //if (change students id) => start = 0
    //if (change marks id) => start = 2
    public static String deleteAndUpdateMarks(String s, int size,int start,int id) {
        int j = start;
        while (j < size) {
            if (s.substring(j, j + 1).equals(Integer.toString(id))) {
                if (j+5-start!=size) s = s.replace(s.substring(j - start, j - start + 6), "");
                else s = s.replace(s.substring(j - start-1, j - start + 5), "");
                size = s.length();
                j -= 6;
            }
            j += 6;
        }
        j = start;
        size = s.length();
        while (j < size) {
            int currentId = Integer.parseInt(s.substring(j, j + 1));
            if (currentId > id) {
                s = (s.substring(0, j) + (currentId - 1) + s.substring(j + 1));
            }
            j += 6;
        }
        return s;
    }

    public static String checkAndEditString(String s) {
        int i = 33;
        // check ascii table
        while (i<127) {
            char c = (char)i;
            i++;
            switch (i) {
                case 65 -> i = 91;
                case 97 -> i = 123;
            }
            if (s.contains(String.valueOf(c))) return "ERROR";
        }
        s = s.replace(" ","");
        s = s.toLowerCase();
        String S = s.substring(0,1).toUpperCase();
        s = S + s.substring(1);
        return s;
    }

    public static Boolean checkSubject(String request, int subjectCount) {
        String subjects;
        subjects = readFile("subject.txt");
        for (int id=1;id<subjectCount;id++) {
            String s;
            s = subjects.substring(subjects.indexOf(' ') + 1, subjects.indexOf(Integer.toString(id+1))-1);
            subjects = subjects.substring(subjects.indexOf(Integer.toString(id+1)));
            if (s.equals(request)) return false;
        }
        return true;
    }

}
