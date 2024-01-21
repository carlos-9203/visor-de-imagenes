package software.ulpgc.visorimagenes;


public interface Image {
    String name();
    Image next();
    Image prev();
}
