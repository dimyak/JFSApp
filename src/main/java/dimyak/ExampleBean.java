package dimyak;

import javax.inject.Named;

@Named(value = "firstBean")
public class ExampleBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
