package sample;

public class Result {
    private String word;
    private String chosenArtikel;
    private String correctArtikel;

    public Result(String word, String chosenArtikel, String correctArtikel) {
        this.word = word;
        this.chosenArtikel = chosenArtikel;
        this.correctArtikel = correctArtikel;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getChosenArtikel() {
        return chosenArtikel;
    }

    public void setChosenArtikel(String chosenArtikel) {
        this.chosenArtikel = chosenArtikel;
    }

    public String getCorrectArtikel() {
        return correctArtikel;
    }

    public void setCorrectArtikel(String correctArtikel) {
        this.correctArtikel = correctArtikel;
    }
}
