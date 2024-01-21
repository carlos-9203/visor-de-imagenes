package software.ulpgc.visorimagenes;


import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

class FileImageLoader implements ImageLoader {
    private final File[] files;

    public FileImageLoader(File folder) {
        this.files = folder.listFiles(isImage());
    }

    private static final Set<String> imageExtensions = Set.of(".jpg", ".png");

    private static FilenameFilter isImage() {
        return (dir, name) -> imageExtensions.stream().anyMatch(name::endsWith);
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    private Image imageAt(int i) {
        if (i >= 0 && i < files.length) {
            return new Image() {
                @Override
                public String name() {
                    return files[i].getAbsolutePath();
                }

                @Override
                public Image next() {
                    return imageAt((i + 1) % files.length);
                }

                @Override
                public Image prev() {
                    return imageAt((i - 1 + files.length) % files.length);
                }
            };
        } else {
            return null;
        }
    }
}
