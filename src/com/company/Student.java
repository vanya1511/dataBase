package com.company;

import java.util.ArrayList;

public class Student {
    String name,surname, thirdName,parallel;
    int id, schClass;
    ArrayList<Integer> marks = new ArrayList<>();

    public Student(int id, String surname, String name, String thirdName, int schClass, String parallel,int subjectCount) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.thirdName = thirdName;
        this.schClass = schClass;
        this.parallel = parallel;
        for (int i=0;i<subjectCount;i++) marks.add(0);
    }

    public int getSchClass() {
        return schClass;
    }

    public String getParallel() {
        return parallel;
    }

    public String getNameForOtput() {
        return surname + " " + name.substring(0,1) + "."+ thirdName.substring(0,1) + ". ";
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getThirdName() {
        return thirdName;
    }

    public int getId() { return id; }

    public void setMarks(int mark,int subjID) {
        marks.set(subjID-1,mark);
    }

    public int getMark(int position) {return marks.get(position);}
}
