package requests;

import java.util.List;

public class DtoRequestsInfo {
    private final List<DtoRequestInfo> requestList;

    public DtoRequestsInfo(List<DtoRequestInfo> requestList) {
        this.requestList = requestList;
    }

    public List<DtoRequestInfo> getRequestList() {
        return requestList;
    }
}
