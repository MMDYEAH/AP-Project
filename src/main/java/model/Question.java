package model;

import java.util.ArrayList;

public class Question {
    String question, answer;

    private static int questionNumberForRegistration;
    protected static ArrayList<Question> questions = new ArrayList<>();

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

    public static int getQuestionNumberForRegistration() {
        return questionNumberForRegistration;
    }

    public static void setQuestionNumberForRegistration(int questionNumberForRegistration) {
        Question.questionNumberForRegistration = questionNumberForRegistration;
    }

    public static ArrayList<Question> getQuestions() {
        return questions;
    }

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
