package entity.color;

import java.util.Objects;

public class Color {
    private Long id;
    private String color;

    public Color(Long id, String color) {
        this.id = id;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color{" +
               "id=" + id +
               ", color='" + color + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color1 = (Color) o;
        return Objects.equals(id, color1.id)
               && Objects.equals(color, color1.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Color() {

    }
}
