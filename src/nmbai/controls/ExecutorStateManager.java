package nmbai.controls;

public interface ExecutorStateManager {
    ExecutorState getState();

    void newAccount(String username, String password);

    void login(String username, String password);

    void changePassword(String password, String newPassword);

    void logout();

    void registerEvent(String eventName, String strAmount);

    void displayEvents();

    void displayAccount();

    void add();

    void addEvent(String eventName, String eventCost, String eventCapacity);
}
