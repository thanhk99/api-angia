package api.angia.com.modules.membership.domain.exception;

import org.springframework.http.HttpStatus;

public class MembershipException extends RuntimeException {
    private final HttpStatus status;

    public MembershipException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() { return status; }

    public static MembershipException notFound(String id) {
        return new MembershipException(HttpStatus.NOT_FOUND,
            "Thực thể Membership không tồn tại với id: " + id);
    }

    public static MembershipException invalidState(String message) {
        return new MembershipException(HttpStatus.CONFLICT, message);
    }
}
