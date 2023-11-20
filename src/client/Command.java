package client;

interface Command {
    void execute();
    void undo();
}
