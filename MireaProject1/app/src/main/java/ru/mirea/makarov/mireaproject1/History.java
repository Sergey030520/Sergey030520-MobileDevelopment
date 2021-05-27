package ru.mirea.makarov.mireaproject1;

public class History {
    private String name_history;
    private String content_history;

    public History(String copy_nameHistory, String copy_content_history){
        name_history = copy_nameHistory;
        content_history = copy_content_history;
    }

    public String getName_history() {
        return this.name_history;
    }

    public void setName_history(String name_history) {
        this.name_history = name_history;
    }

    public String getContent_history() {
        return this.content_history;
    }

    public void setContent_history(String content_history) {
        this.content_history = content_history;
    }

}
