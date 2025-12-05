package dev.aurakle.mocha.compiler;

public record LineColumn(int line, int column) {
    public static LineColumn from(String sourceText, int pos) {
        int l = 0;
        int c = 0;

        for (String line : sourceText.lines().toArray(String[]::new)) {
            l++;

            if (pos > line.length()) {
                pos -= line.length() + 1;
            } else {
                c = pos + 1;
                break;
            }
        }

        return new LineColumn(l, c);
    }
}
