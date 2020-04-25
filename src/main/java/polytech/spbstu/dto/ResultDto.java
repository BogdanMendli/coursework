package polytech.spbstu.dto;

import java.util.Objects;

public class ResultDto {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDto resultDto = (ResultDto) o;
        return result == resultDto.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "result=" + result +
                '}';
    }
}
