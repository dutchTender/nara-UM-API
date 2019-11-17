package gov.nara.common.web.exception;

import java.util.Objects;

public class APIErrorDto {
    private int status;
    private String message;
    private String developersMessage;

    public APIErrorDto(int status, String message, String developersMessage) {
        this.status = status;
        this.message = message;
        this.developersMessage = developersMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDevelopersMessage() {
        return developersMessage;
    }

    public void setDevelopersMessage(String developersMessage) {
        this.developersMessage = developersMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof APIErrorDto)) return false;
        APIErrorDto that = (APIErrorDto) o;
        return getStatus() == that.getStatus() &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getDevelopersMessage(), that.getDevelopersMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getMessage(), getDevelopersMessage());
    }


    @Override
    public String toString() {
        return "APIErrorDto{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", developersMessage='" + developersMessage + '\'' +
                '}';
    }
}
