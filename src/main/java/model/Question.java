package model;

import java.util.ArrayList;

public class Question {
    String question, answer;
    protected static ArrayList<Question> questions = new ArrayList<>();

    private static Question nowQuestion;

    public static Question getQuestionByNumber(int number) {
        return questions.get(number);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static ArrayList<Question> getQuestions() {
        return questions;
    }

    public static Question getNowQuestion() {
        return nowQuestion;
    }

    public static void setNowQuestion(Question nowQuestion) {
        Question.nowQuestion = nowQuestion;
    }

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
