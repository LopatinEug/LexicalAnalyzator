package model;

public class Function extends Element implements First {

    public enum functionValue {
        cos,
        sin;
    }

    public Function(String value, int position) {
        super(value, position);
    }

}
