package sample.model;

/**
 * Created by Kamil on 2017-09-19.
 */
public class ViewQuestionModel {

    private int id;
    private String question;
    private String odpA;
    private String odpB;
    private String odpC;
    private String odpD;

    public ViewQuestionModel(String question, String odpA, String odpB, String odpC, String odpD) {
        this.question = question;
        this.odpA = odpA;
        this.odpB = odpB;
        this.odpC = odpC;
        this.odpD = odpD;
    }

    public ViewQuestionModel(int id , String question){
        this.question = question;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID=" + id +
                ", " + question + "\n";

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOdpA() {
        return odpA;
    }

    public void setOdpA(String odpA) {
        this.odpA = odpA;
    }

    public String getOdpB() {
        return odpB;
    }

    public void setOdpB(String odpB) {
        this.odpB = odpB;
    }

    public String getOdpC() {
        return odpC;
    }

    public void setOdpC(String odpC) {
        this.odpC = odpC;
    }

    public String getOdpD() {
        return odpD;
    }

    public void setOdpD(String odpD) {
        this.odpD = odpD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
