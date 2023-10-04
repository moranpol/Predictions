package alloction;

public class DtoAlloction {
    private final Integer requestId;
    private final String userName;
    private final String worldName;
    private final Integer totalAmount;
    private final String termaination;

    public DtoAlloction(Integer requestId, String userName, String worldName, Integer totalAmount, String termaination) {
        this.requestId = requestId;
        this.userName = userName;
        this.worldName = worldName;
        this.totalAmount = totalAmount;
        this.termaination = termaination;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public String getUserName() {
        return userName;
    }

    public String getWorldName() {
        return worldName;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public String getTermaination() {
        return termaination;
    }
}
